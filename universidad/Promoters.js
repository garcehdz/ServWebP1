function promotersList()
{
	d3.json("http://localhost:8089/rest/promoter", function(json)
	{
		var content = d3.select("#content");
		content.selectAll("div").remove();
		var div = content.append("div");
		div.append("h2").text("Listado de promotores");

		var table = div.append("table");

		table.append("thead").selectAll("th").data(["Nombre", "Dirección", "Telefono", "Banco", "Comisión", "Usuario", "", ""]).enter().append("th").text(function(d) { return d; });
		var tr = table.selectAll("tr").data(json.promoter).enter().append("tr");
		tr.append("td").text(function(d) { return d.name; });
		tr.append("td").text(function(d) { return d.address; });
		tr.append("td").text(function(d) { return d.telephone; });
		tr.append("td").text(function(d) { return d.bank; });
		tr.append("td").text(function(d) { return d.commision; });
		tr.append("td").text(function(d) { return d.username; });
		tr.append("td").append("a").attr("href", function(d) { return "javascript:promotersDetail(" + d.code + ")"; }).text("Modificar");
		tr.append("td").append("a").attr("href", function(d) { return "javascript:promotersDelete(" + d.code + ")"; }).text("Eliminar");
		div.append("button").attr("onclick", "promotersNew()").text("Nuevo");
    });
}

function promotersDetail(code)
{
	var queryString = "code=" + code;
	d3.json("http://localhost:8089/rest/promoter/detail?" + queryString, function(json)
	{
		var content = d3.select("#content");
		content.selectAll("div").remove();
		var div = content.append("div");
		AppendInput(div, "hidden", "code", json.code);
		div.append("label").text("Nombre");
		div.append("br");
		AppendInput(div, "text", "name", json.name);
		div.append("label").text("Direccion");
		div.append("br");
		AppendInput(div, "text", "address", json.address);
		div.append("label").text("Telefono");
		div.append("br");
		AppendInput(div, "text", "telephone", json.telephone);
		div.append("label").text("Banco");
		div.append("br");
		AppendInput(div, "text", "bank", json.bank);
		div.append("label").text("Comision");
		div.append("br");
		AppendInput(div, "text", "commision", json.commision);
		div.append("label").text("Nombre de usuario");
		div.append("br");
		div.append("input").attr("type", "text").attr("id", "username").attr("readonly", "readonly").attr("value", json.username);
		div.append("label").text("Contraseña");
		div.append("br");
		AppendInput(div, "password", "password", json.password);
		div.append("br");
		AppendButton(div, "Guardar", "promotersUpdate()");
	});
}

function promotersUpdate()
{
	var queryString = "code=" + d3.select("#code").property("value")
		+ "&name=" + d3.select("#name").property("value")
		+ "&address=" + d3.select("#address").property("value")
		+ "&telephone=" + d3.select("#telephone").property("value")
		+ "&bank=" + d3.select("#bank").property("value")
		+ "&commision=" + d3.select("#commision").property("value")
		+ "&username=" + d3.select("#username").property("value")
		+ "&password=" + d3.select("#password").property("value");
	d3.xhr2("http://localhost:8089/rest/promoter?" + queryString, "PUT", function(json){ promotersList(); });
}

function promotersNew()
{
	var content = d3.select("#content");
	content.selectAll("div").remove();
	var div = content.append("div");
	div.append("label").text("Nombre");
	div.append("br");
	AppendInput(div, "text", "name", "");
	div.append("label").text("Direccion");
	div.append("br");
	AppendInput(div, "text", "address", "");
	div.append("label").text("Telefono");
	div.append("br");
	AppendInput(div, "text", "telephone", "");
	div.append("label").text("Banco");
	div.append("br");
	AppendInput(div, "text", "bank", "");
	div.append("label").text("Comision");
	div.append("br");
	AppendInput(div, "text", "commision", "");
	div.append("label").text("Nombre de usuario");
	div.append("br");
	AppendInput(div, "text", "username", "");
	div.append("label").text("Contraseña");
	div.append("br");
	AppendInput(div, "password", "password", "");
	div.append("br");
	AppendButton(div, "Guardar", "promotersInsert()");
}

function promotersInsert()
{
	var queryString = "code=" + Math.floor(Math.random() * 100000000)
		+ "&name=" + d3.select("#name").property("value")
		+ "&address=" + d3.select("#address").property("value")
		+ "&telephone=" + d3.select("#telephone").property("value")
		+ "&bank=" + d3.select("#bank").property("value")
		+ "&commision=" + d3.select("#commision").property("value")
		+ "&username=" + d3.select("#username").property("value")
		+ "&password=" + d3.select("#password").property("value");
	d3.xhr2("http://localhost:8089/rest/promoter?" + queryString, "POST", function(json) { promotersList(); });
	
}

function promotersDelete(code)
{
	var queryString = "code=" + code;
	d3.xhr2("http://localhost:8089/rest/promoter?" + queryString, "DELETE", function(json) { promotersList(); });
}