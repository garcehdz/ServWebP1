package service.resources;

import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.Consumes;
import javax.ws.rs.QueryParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.DELETE;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import com.sun.jersey.api.NotFoundException;
import service.beans.*;
import service.storage.*;

import org.restlet.resource.Get;
import org.restlet.resource.ServerResource;

@Path("/promoter")
public class PromoterResource
{
	@Context
	UriInfo uriInfo;
	@Context
	Request request;

	@GET
	@Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
	public List<Promoter> getPromoters()
	{
		List<Promoter> promoters = new ArrayList<Promoter>();
		promoters.addAll(PromoterStore.getStore().values());
		return promoters;
	}
	
	@GET
	@Path("/detail")
	@Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
	public Promoter getPromoter(@QueryParam("code") int code)
	{
		Promoter promoter = PromoterStore.getStore().get(code);
		if(promoter == null)
		{
			throw new NotFoundException("Not such object on this server!");
		}
		return promoter;
	}
	
	@GET
	@Path("/user")
	@Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
	public Promoter getPromoter(@QueryParam("username") String username)
	{
		for (Promoter promoter : PromoterStore.getStore().values())
		{
			if (promoter.getUsername().equals(username))
			{
				return promoter;
			}
		}
		throw new NotFoundException("Not such object on this server!");
	}
	
	@POST
	public void insertPromoter(@QueryParam("code") int code,@QueryParam("name") String name,@QueryParam("address") String address,@QueryParam("telephone") String telephone,@QueryParam("bank") String bank,@QueryParam("commision") int commision, @QueryParam("username") String username, @QueryParam("password") String password) throws Exception
	{
		Promoter promoter = new Promoter(code, name, address, telephone, bank, commision ,username, password);
		PromoterStore.getStore().put(promoter.getCode(), promoter);
		Usuario user = new Usuario(username, password);
		UsuarioStore.getStore().put(username, user);
		RolesByUser rolesByUser = new RolesByUser(username, 2);
		RolesByUserStore.getStore().put(username + "$2", rolesByUser);
	}
	
	@PUT
	public void updatePromoter(@QueryParam("code") int code,@QueryParam("name") String name,@QueryParam("address") String address,@QueryParam("telephone") String telephone,@QueryParam("bank") String bank,@QueryParam("commision") int commision, @QueryParam("username") String username, @QueryParam("password") String password) throws Exception
	{
		Promoter promoterToUpdate = PromoterStore.getStore().get(code);
		if(promoterToUpdate != null)
		{
			if (promoterToUpdate.getUsername().equals(username))
			{
				Promoter promoter = new Promoter(code, name, address, telephone, bank, commision ,username, password);
				PromoterStore.getStore().put(promoter.getCode(), promoter);
				Usuario user = new Usuario(username, password);
				UsuarioStore.getStore().put(username, user);
			}
			else
			{
				throw new Exception("Can't update username");
			}
		}
		else
		{
			throw new NotFoundException("Not such object on this server!");
		}
	}
	
	@DELETE
	public void deletePromoter(@QueryParam("code") int code) throws Exception
	{
		Promoter promoter = PromoterStore.getStore().remove(code);
		if(promoter == null)
		{
			throw new NotFoundException("Not such object on this server!");
		}
		else
		{
			if (UsuarioStore.getStore().remove(promoter.getUsername()) == null)
			{
				throw new NotFoundException("Not such object on this server!");
			}
			else
			{
				List<String> keys = new ArrayList<String>();
				for (RolesByUser rolesByUser : RolesByUserStore.getStore().values())
				{
					if (rolesByUser.getUsername().equals(promoter.getUsername()))
					{
						keys.add(rolesByUser.getUsername() + "$" + rolesByUser.getRole());
					}
				}
				for (String key : keys)
				{
					RolesByUserStore.getStore().remove(key);
				}
			}
		}
	}
}
