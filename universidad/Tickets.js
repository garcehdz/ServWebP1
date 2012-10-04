function EventsList() {
	d3.json("http://localhost:8089/rest/event", function(json) {
		var content = d3.select("#content");
		content.selectAll("div").remove();
		var div = content.append("div");
		div.append("h2").text("Listado de Eventos");
		var table = div.append("table");
	  
		var headers = ["Nombre", "Tipo Evento", "Artistas", "Fecha", "Lugar", "Promotor"];
		var cols = ["code","name", "eventType", "artists", "date", "place", "promoterId"];
		CreateHeaders(table, headers);
		CreateList(table, json.event, "ticketsNew(this)", cols);
    });
}

function ticketsNew(row)
{
	d3.json("http://localhost:8089/rest/event/detail?code="+row.id, function(event)
	{
		var content = d3.select("#content");
		content.selectAll("div").remove();
		var div = content.append("div");
		div.append("h2").text("Compra de ticket para evento " + event.name);
		//--Cliente
		div.append("label").text("Cédula");
		div.append("br");
		AppendInput(div, "text", "id", "");
		div.append("label").text("Nombre");
		div.append("br");
		AppendInput(div, "text", "name", "");
		div.append("label").text("Dirección");
		div.append("br");
		AppendInput(div, "text", "address", "");
		div.append("label").text("Teléfono");
		div.append("br");
		AppendInput(div, "text", "telephone", "");
		div.append("label").text("Número de tarjeta");
		div.append("br");
		AppendInput(div, "text", "cardnumber", "");
		div.append("label").text("Tipo de tarjeta");
		div.append("br");
		AppendInput(div, "text", "cardType", "");
		//--Ticket
		div.append("h3").text("Ticket");
		div.append("br");
		d3.json("http://localhost:8089/rest/location/event?eventCode=" + event.code, function(locations)
		{
			if (locations != null)
			{
				div.append("select")
					.attr("id", "locationTypeId")
					.selectAll("option")
					.data(locations.location)
					.enter()
					.append("option")
						.attr("value", function(d) { return d.locationType; })
						.text
						(
							function(d)
							{
								return getLocationDescription(d.locationType)
								+ (d.numbered === "true" ? " numerada" : "")
								+ " - " + d.quantity;
							}
						);
				div.append("br");
				div.append("label").text("Numerada");
				AppendInput(div, "text", "numbered", "");
				div.append("label").text("Cantidad");
				AppendInput(div, "text", "quantity", "");
				AppendInput(div, "hidden", "eventCode", row.id);
				//--
				div.append("br");
				AppendButton(div, "Guardar", "ticketsInsert()");
			}
		});
	});
}

function ticketsInsert()
{
	var queryString = "id=" + d3.select("#id").property("value")
		+ "&name=" + d3.select("#name").property("value")
		+ "&address=" + d3.select("#address").property("value")
		+ "&telephone=" + d3.select("#telephone").property("value")
		+ "&cardnumber=" + d3.select("#cardnumber").property("value")
		+ "&cardType=" + d3.select("#cardType").property("value");
	d3.xhr2("http://localhost:8089/rest/customer?" + queryString, "PUT", function(json)
	{
		var ticketCode = Math.floor(Math.random() * 1000000);
		var numbered = d3.select("#numbered").property("value");
		if (numbered == "")
		{
			numbered = "0";
		}
		var queryString = "code=" + ticketCode
			+ "&locationType=" + d3.select("#locationTypeId").property("value")
			+ "&eventCode=" + d3.select("#eventCode").property("value")
			+ "&customerId=" + d3.select("#id").property("value")
			+ "&quantity=" + d3.select("#quantity").property("value");
			+ "&numbered=" + numbered;
		d3.xhr2("http://localhost:8089/rest/ticket?" + queryString, "POST", function(json)
		{
			showInvoice(ticketCode);
		});
	});
}

function showInvoice(ticketCode)
{
	d3.json("http://localhost:8089/rest/ticket/detail?code=" + ticketCode, function(ticket)
	{
		if (ticket != null)
		{
			d3.json("http://localhost:8089/rest/event/detail?code=" + ticket.eventCode, function(event)
			{
				if (event != null)
				{
					d3.json("http://localhost:8089/rest/customer/detail?id=" + ticket.customerId, function(customer)
					{
						if (customer != null)
						{
							var content = d3.select("#content");
							content.selectAll("div").remove();
							var div = content.append("div");
							div.append("h2").text("Factura de ticket para evento " + event.name);
							//--Cliente
							div.append("label").text("Cédula: " + customer.Id);
							div.append("br");
							div.append("label").text("Nombre: " + customer.name);
							div.append("br");
							div.append("label").text("Dirección: " + customer.address);
							div.append("br");
							div.append("label").text("Teléfono: " + customer.telephone);
							div.append("br");
							div.append("label").text("Número de tarjeta: " + customer.cardnumber);
							div.append("br");
							div.append("label").text("Tipo de tarjeta: " + customer.cardType);
							div.append("br");
							//--Ticket
							div.append("h3").text("Ticket");
							div.append("br");
							getLocationDescription(ticket.locationType)
							//--Numeradas
							div.append("br");
							div.append("label").text("Numerada: ");
							div.append("label").attr("id", "Numeradas");
							//--
							div.append("br");
							div.append("label").text("Cantidad: " + ticket.quantity);
							div.append("br");
							div.append("label").text("Total: " + ticket.ammount);
							//--
							div.append("br");
							AppendButton(div, "ok", "EventsList()");
							d3.json("http://localhost:8089/rest/ticket/detail?code=" + ticketCode, function(numberedLocations)
							{
								if (numberedLocations != null)
								{
									d3.select("#Numeradas").selectAll("span").data(numberedLocations).enter().append("span").text(function (d) { return d.locationNumber + " "});
								}
							});
						}
					});
				}
			});
		}
	});
}

function getLocationDescription(locationTypeId)
{
	switch(locationTypeId)
	{
		case "1": return "Sol";
		case "2": return "Sombra";
		case "3": return "Platea";
		case "4": return "Palco";
		case "5": return "Preferencial";
		case "6": return "VIP";
	}
}