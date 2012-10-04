function loginForm()
{
	var content = d3.select("#content");
	content.selectAll("div").remove();
	var div = content.append("div");
	div.append("label").text("Nombre");
	div.append("br");
	AppendInput(div, "text", "username", "");
	div.append("label").text("Contraseña");
	div.append("br");
	AppendInput(div, "password", "password", "");
	AppendButton(div, "Guardar", "login()");
}

function login()
{
	var username = d3.select("#username").property("value");
	var password = d3.select("#password").property("value");
	d3.json("http://localhost:8089/rest/user/credentials?username=" + username + "&password=" +  password, function(json)
	{
		if (json != null)
		{
			gblUsername = username;
			d3.json("http://localhost:8089/rest/user/role?username=" + username + "&role=1", function(json)
			{
				if (json!= null)
				{
					gblAdministrator = true;
				}
				d3.json("http://localhost:8089/rest/user/role?username=" + username + "&role=2", function(json)
				{
					if (json != null)
					{
						gblPromoter = true;
					}
					d3.json("http://localhost:8089/rest/promoter/user?username=" + username, function(json)
					{
						if (json != null)
						{
							gblPromoterCode = json.code;
						}
						firstRun();
					});
				});
			});
		}
	});
}

function logout()
{
	gblPromoter = false;
	gblAdministrator = false;
	gblUsername = "";
	gblPromoterCode = -1;
	firstRun();
}

function firstRun()
{
	setNavigationTabs();
	EventsList();
}

function setNavigationTabs()
{
	var navigation = d3.select("#navigation");
	navigation.selectAll("ul").remove();
	var ul = navigation.append("ul");
	ul.append("li").append("a").attr("href", "javascript:EventsList()").text("Compra");
	if (gblAdministrator)
	{
		ul.append("li").append("a").attr("href", "javascript:promotersList()").text("Promotores");
	}
	if (gblPromoter)
	{
		ul.append("li").append("a").attr("href", "javascript:EventsByPromoter()").text("Eventos");
	}
	if (gblUsername != "")
	{
		ul.append("li").append("a").attr("href", "javascript:logout()").text("Salir");
	}
	else
	{
		ul.append("li").append("a").attr("href", "javascript:loginForm()").text("Entrar");
	}
}