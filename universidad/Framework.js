var gblPromoter = false;
var gblAdministrator = false;
var gblUsername = "";
var gblPromoterCode = -1;

function CreateDiv(){
	var content = d3.select("#content");
	content.selectAll("div").remove();
	var div = content.append("div");
	return div;
}

function CreateHeaders(table, headers){
	var thead = table.append("thead");
	thead.selectAll("th").data(headers).enter().append("th").text(function(d) {return d;});
}

function CreateList(table, data, accion, cols){
	var tr = table.selectAll("tr").data(data).enter().append("tr").attr("onClick",accion);
	tr.attr("id",function(d) {return d[cols[0]];});

	for(var i=1; i<cols.length; i++)
		tr.append("td").text(function(d) {return d[cols[i]];});
}

function AppendButton(item,value, accion){
	item.append("input").attr("type","button").attr("value",value).attr("id","button").attr("onClick",accion);
}

function AppendInput(item, type, id, value){
	var input = item.append("input").attr("value",value).attr("id",id).attr("type",type);

	if(type == "checkbox" && value == "true")
		input.attr("checked", "true");
}

function AppendSelectFromArray(item, id, value, data){
	var select = item.append("select").attr("id", id);
	select.selectAll("option").data(data).enter().append("option").text(function(d) {return d[1];}).attr("value", function(d) {return d[0];});
	
	if(value != null)
		select.selectAll("option[value='" + value + "']").attr("selected", "true");
}

function AppendSelectFromServer(row, url, value, id, label, text, code){
	d3.json("http://localhost:8089/rest/"+url, function(json) {
		if(json != null){
			row.append("td").text(label);
			var select = row.append("td").append("select").attr("id", id);
			var data = json.promoter;
			select.selectAll("option").data(data).enter().append("option").text(function(d) {return d[text];}).attr("value", function(d) {return d[code];});
			select.selectAll("option[value='" + value + "']").attr("selected", "true");
		}
	});
}

function CreateQueryString(params, values){
	var queryString = "";
	for(var i = 0; i<params.length; i++){
		if(i != 0)
			queryString += "&";
		queryString += params[i] + "=" + values[i];
	}
	return queryString;
}

var gblEventTypes = [["1", "Concierto"],["2", "Recital"],["3", "Obra de Teatro"],["4", "Danza"]];
var gblLocationTypes = [[1, "Sol"],[2, "Sombra"],[3, "Platea"],[4, "Palco"],[5, "Preferencial"],[6, "VIP"]];


d3.xhr2 = function(url, method, callback)
{
	var req = new XMLHttpRequest();
	req.open(method, url, true);
	req.setRequestHeader("Connection", "close");
	req.onreadystatechange = function()
	{
		if (req.readyState === 4)
		{
			var s = req.status;
			callback(s >= 200 && s < 300 || s === 304 ? req : null);
		}
    };
	req.send();
};
