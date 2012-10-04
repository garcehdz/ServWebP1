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

@Path("/customer")
public class CustomerResource
{
	@Context
	UriInfo uriInfo;
	@Context
	Request request;
/*
	@GET
	@Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
	public List<Customer> getCustomers() throws Exception
	{
		List<Customer> customers = new ArrayList<Customer>();
		customers.addAll(CustomerStore.getStore().values());
		return customers;
	}
*/
	@GET
	@Path("/detail")
	@Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
	public Customer getCustomer(@QueryParam("id") String id)
	{
		Customer customer = CustomerStore.getStore().get(id);
		if(customer == null)
		{
			throw new NotFoundException("Not such object on this server!");
		}
		return customer;
	}
/*
	@POST
	public void insertCustomer(@QueryParam("id") String id,@QueryParam("name") String name,@QueryParam("address") String address,@QueryParam("telephone") String telephone,@QueryParam("cardnumber") String cardnumber,@QueryParam("cardType") String cardType) throws Exception
	{
		Customer customer = new Customer(id, name, address, telephone, cardnumber, cardType);
		CustomerStore.getStore().put(customer.getId(), customer);
	}
*/
	@PUT
	public void updateCustomer(@QueryParam("id") String id,@QueryParam("name") String name,@QueryParam("address") String address,@QueryParam("telephone") String telephone,@QueryParam("cardnumber") String cardnumber,@QueryParam("cardType") String cardType) throws Exception
	{
		Customer customer = new Customer(id, name, address, telephone, cardnumber, cardType);
		CustomerStore.getStore().put(customer.getId(), customer);
	}
/*
	@DELETE
	public void deleteCustomer(@QueryParam("id") String id) throws Exception
	{
		if(CustomerStore.getStore().remove(id) == null)
		{
			throw new NotFoundException("Not such object on this server!");
		}
	}
*/
}
