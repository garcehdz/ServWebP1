function EventsByPromoter() {
	d3.json("http://localhost:8089/rest/event/promoter?promoterId="+gblPromoterCode, function(json) {
		var content = d3.select("#content");
		content.selectAll("div").remove();
		var div = content.append("div");
		div.append("h2").text("Listado de Eventos del Promotor");
		var table = div.append("table");
	  
		var headers = ["Nombre", "Tipo Evento", "Artistas", "Fecha", "Lugar", "Promotor"];
		var cols = ["code","name", "eventType", "artists", "date", "place", "promoterId"];
		CreateHeaders(table, headers);
		CreateList(table, json.event, "EventDetail(this)", cols);
		
		AppendButton(div, "Agregar","NewEvent()");
    });
}

function EventDetail(row) {
	d3.json("http://localhost:8089/rest/event/detail?code="+row.id, function(event) {
		
		var div = CreateDiv();
		AppendInput(div, "hidden", "code", event.code, null);
		div.append("h2").text("Detalle de Evento");
		var table = div.append("table");
		
		var labels = ["Nombre:", "Tipo Evento:", "Artistas:", "Fecha:", "Lugar:"];
		var values = [event.name, event.eventType, event.artists, event.date, event.place];
		var ids = ["name", "eventType", "artists", "date", "place"];
		var types = ["Text", "Select", "Text", "Text", "Text"];
		var tr;
		
		for(var i=0; i< labels.length; i++){
			if(i%2 == 0)
				tr = table.append("tr");
				
			tr.append("td").text(labels[i]);
			if(types[i] != "Select")
				AppendInput(tr.append("td"), types[i], ids[i], values[i]);
			else
				AppendSelectFromArray(tr.append("td"), ids[i], values[i], gblEventTypes);
		}

		AppendSelectFromServer(tr, "promoter", event.promoterId, "promoterId", "Promotor:", "name", "code");
		CreateTableLocations(div);
		LoadEventLocations();
		AppendButton(div, "Eliminar", "EventDelete()");
		AppendButton(div, "Actualizar", "EventUpdate()");		
    });
}

function NewEvent(){
	var div = CreateDiv();
	div.append("h2").text("Nuevo Evento");
	var table = div.append("table");
	
	var labels = ["Nombre:", "Tipo Evento:", "Artistas:", "Fecha:", "Lugar:"];
	
	var ids = ["name", "eventType", "artists", "date", "place"];
	var types = ["Text", "Select", "Text", "Text", "Text"];
	var tr;
	
	for(var i=0; i< labels.length; i++){
		
		if(i%2 == 0)
			tr = table.append("tr");
				
		tr.append("td").text(labels[i]);
		if(types[i] != "Select")
			AppendInput(tr.append("td"), types[i], ids[i], null);
		else
			AppendSelectFromArray(tr.append("td"), ids[i], null, gblEventTypes);
	}

	AppendSelectFromServer(tr, "promoter", null, "promoterId", "Promotor:", "name", "code");
	CreateTableLocations(div);
	LoadLocationTypes();
	AppendButton(div, "Agregar", "EventInsert()");
}

function CreateTableLocations(div){
	div.append("h2").text("Localidades");
	div.append("table").attr("id", "tableLocations");
}

function LoadLocationTypes(){
	var table = d3.select("#tableLocations");
	for(var i = 0; i<gblLocationTypes.length; i++){
		
		var locationType = gblLocationTypes[i][0];
		var locationName = gblLocationTypes[i][1];
		var row = table.select("#locRow" +locationType);
		
		if(row == ""){
			var tr = table.append("tr").attr("id", "locRow" +locationType);
			var td = tr.append("td");
			td.text(locationName + ":");
			AppendInput(td, "checkbox", "locationType" + locationType, null);

			td = tr.append("td");
			td.text("Numerada:");
			AppendInput(td, "checkbox", "numbered" + locationType, null);
			
			td = tr.append("td");
			td.text("Precio:");
			AppendInput(td, "text", "price" + locationType, null);
			
			td = tr.append("td");
			td.text("Cantidad:");
			AppendInput(td, "text", "quantity" + locationType, null);
		}
		else{
			var td = row.selectAll("td")[0][0];
			td.innerHTML = locationName + ":" + td.innerHTML;
		}
		
	}
}

function LoadEventLocations(){
	var table = d3.select("#tableLocations");
	var eventCode = d3.select("#code").property("value");
	d3.json("http://localhost:8089/rest/location/event?eventCode="+eventCode, function(json) {
		if(json != null){
			var data = json.location;
			
			var tr = table.selectAll("tr").data(data).enter().append("tr");
			tr.attr("id", function(d){ return "locRow" + d.locationType; });
			
			var td = tr.append("td");
			AppendInput(td, "checkbox",  function(d){ return "locationType" + d.locationType; }, "true");
			
			td = tr.append("td");
			td.text("Numerada:");
			AppendInput(td, "checkbox",  function(d){ return "numbered" + d.locationType; }, function(d){ return d.numbered; });
			
			td = tr.append("td");
			td.text("Precio:");
			AppendInput(td, "text",  function(d){ return "price" + d.locationType; }, function(d){ return d.price; });
			
			td = tr.append("td");
			td.text("Cantidad:");
			AppendInput(td, "text",  function(d){ return "quantity" + d.locationType; }, function(d){ return d.quantity; });	
		}
		LoadLocationTypes();
	});
}

function EventUpdate() {
	var params = ["code","name", "eventType", "artists", "date", "place", "promoterId"];
	var values = [];
	
	for(var i = 0; i<params.length; i++)
		values.push(d3.select("#" + params[i]).property("value"));
	
	var queryString = CreateQueryString(params, values);
	
    d3.xhr2("http://localhost:8089/rest/event?" + queryString,"PUT",function(json) {});
	var eventCode =  d3.select("#code").property("value");
	UpdateLocations(eventCode);
    EventsByPromoter();
}

function UpdateLocations(eventCode){
	d3.xhr2("http://localhost:8089/rest/location?eventCode=" + eventCode,"DELETE",function(json) {});
	
	for(var i = 0; i< gblLocationTypes.length; i++){
		var id = gblLocationTypes[i][0];
		var input = d3.select("#locationType" + id);
		
		if(input.property("checked") == true){
			var params = ["locationType", "eventCode", "price", "quantity", "numbered"];
			var values = [id, eventCode];

			values.push(d3.select("#price" + id).property("value"));
			values.push(d3.select("#quantity" + id).property("value"));
			values.push(d3.select("#numbered" + id).property("checked"));
			
			var queryString = CreateQueryString(params, values);
//			alert(queryString);
			d3.xhr2("http://localhost:8089/rest/location?" + queryString,"POST",function(json) {});
		}
	}
	
}

function EventInsert(){
	var params = ["name", "eventType", "artists", "date", "place", "promoterId"];
	var values = [];
	
	values.push(Math.floor(Math.random() * 1000))
	for(var i = 0; i<params.length; i++)
		values.push(d3.select("#" + params[i]).property("value"));
	params.unshift("code");
	
	var queryString = CreateQueryString(params, values);
    d3.xhr2("http://localhost:8089/rest/event?" + queryString,"POST",function(json) {});
	UpdateLocations(values[0]);
    EventsByPromoter();
}

function EventDelete(){
	var queryString = "code=" + d3.select("#code").property("value");
	d3.xhr2("http://localhost:8089/rest/event?" + queryString,"DELETE", function(json) {});
	EventsByPromoter();
}