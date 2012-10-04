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

@Path("/user")
public class UserResource
{
	@Context
	UriInfo uriInfo;
	@Context
	Request request;

	@GET
	@Path("/role")
	@Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
	public RolesByUser confirmRole(@QueryParam("username") String username, @QueryParam("role") int role)
	{
		RolesByUser rolesByUser = RolesByUserStore.getStore().get(username + "$" + role);
		if (rolesByUser != null)
		{
			return rolesByUser;
		}
		throw new NotFoundException("Not such object on this server!");
	}

	@GET
	@Path("/credentials")
	@Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
	public Usuario confirmCredentials(@QueryParam("username") String username, @QueryParam("password") String password)
	{
		Usuario user = UsuarioStore.getStore().get(username);
		if (user != null && user.getPassword().equals(password))
		{
			return user;
		}
		throw new NotFoundException("Not such object on this server!");
	}
	
/*
	@POST
	public void insertCustomer(@QueryParam("username") String username,@QueryParam("password") String password) throws Exception
	{
		Usuario user = new Usuario(username, password);
		UsuarioStore.getStore().put(user.getUsername(), user);
	}
	@PUT
	public void updateCustomer(@QueryParam("username") String username,@QueryParam("password") String password) throws Exception
	{
		if(UsuarioStore.getStore().get(username) != null)
		{
			Usuario user = new Usuario(username, password);
			UsuarioStore.getStore().put(user.getUsername(), user);
		}
		else
		{
			throw new NotFoundException("Not such object on this server!");
		}
	}
	
	@DELETE
	public void deleteCustomer(@QueryParam("username") String username) throws Exception
	{
		if(UsuarioStore.getStore().remove(username) == null)
		{
			throw new NotFoundException("Not such object on this server!");
		}
	}
*/
}
