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

@Path("/numberedTicket")
public class NumberedTicketResource
{
	@Context
	UriInfo uriInfo;
	@Context
	Request request;
/*
	@GET
	@Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
	public List<NumberedTicket> getNumberedLocations()
	{
		List<NumberedTicket> numberedTickets = new ArrayList<NumberedTicket>();
		numberedTickets.addAll(NumberedTicketStore.getStore().values());
		return numberedTickets;
	}
*/
	@GET
	@Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
	public List<NumberedTicket> getNumberedLocationsByTicketCode(@QueryParam("ticketCode") int ticketCode)
	{
		List<NumberedTicket> numberedTickets = new ArrayList<NumberedTicket>();
		for (NumberedTicket numberedTicket : NumberedTicketStore.getStore().values())
		{
			if (numberedTicket.getTicketCode() == ticketCode)
			{
				numberedTickets.add(numberedTicket);
			}
		}
		return numberedTickets;
	}
/*
	@POST
	public void insertNumberedTicket(@QueryParam("locationNumber") int locationNumber,@QueryParam("ticketCode") int ticketCode) throws Exception
	{
		NumberedTicket numberedTicket = new NumberedTicket(locationNumber, ticketCode);
		NumberedTicketStore.getStore().put(locationNumber + "$" + ticketCode, numberedTicket);
	}
	
	@DELETE
	public void deleteNumberedTicket(@QueryParam("locationNumber") int locationNumber,@QueryParam("ticketCode") int ticketCode) throws Exception
	{
		if(NumberedTicketStore.getStore().remove(locationNumber + "$" + ticketCode) == null)
		{
			throw new NotFoundException("Not such object on this server!");
		}
	}
*/
}
