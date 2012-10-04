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

@Path("/ticket")
public class TicketResource
{
	@Context
	UriInfo uriInfo;
	@Context
	Request request;

	@GET
	@Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
	public List<Ticket> getCustomers()
	{
		List<Ticket> tickets = new ArrayList<Ticket>();
		tickets.addAll(TicketStore.getStore().values());
		return tickets;
	}

	@GET
	@Path("/detail")
	@Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
	public Ticket getCustomer(@QueryParam("code") int code)
	{
		Ticket ticket = TicketStore.getStore().get(code);
		if(ticket == null)
		{
			throw new NotFoundException("Not such object on this server!");
		}
		return ticket;
	}

	@POST
	public void insertTicket(@QueryParam("code") int code,@QueryParam("locationType") int locationType,@QueryParam("eventCode") int eventCode,@QueryParam("customerId") String customerId,@QueryParam("quantity") int quantity, @QueryParam("numbered") int numbered) throws Exception
	{
		//Event event = EventStore.getStore().get(eventCode);
		//if (event != null)
		//{
			Location location = LocationStore.getStore().get(locationType + "$" + eventCode);
			if (location != null)
			{
				Ticket ticket = new Ticket(code, locationType, eventCode, customerId, quantity, quantity * location.getPrice());
				TicketStore.getStore().put(ticket.getCode(), ticket);
				//--
				if (location.getNumbered())
				{
					int limit = location.getQuantity();
					int needed = quantity;
					int offset = numbered;
					int up = offset, down = offset - 1;
					while (needed > 0)
					{
						if (up >=1 && up <= limit)
						{
							NumberedTicket numberedTicket = new NumberedTicket(up, code);
							NumberedTicketStore.getStore().put(up + "$" + code, numberedTicket);
							needed--;
							up++;
							if (needed <= 0)
							{
								break;
							}
						}
						if (down >= 1 && down <= limit)
						{
							NumberedTicket numberedTicket = new NumberedTicket(down, code);
							NumberedTicketStore.getStore().put(down + "$" + code, numberedTicket);
							needed--;
							down--;
						}
					}
				}
				//--
			}
		//}
	}
/*
	@PUT
	public void updateCustomer(@QueryParam("code") int code,@QueryParam("locationType") int locationType,@QueryParam("eventCode") int eventCode,@QueryParam("customerId") String customerId,@QueryParam("quantity") int quantity,@QueryParam("ammount") int ammount) throws Exception
	{
		if(TicketStore.getStore().get(code) != null)
		{
			Ticket ticket = new Ticket(code, locationType, eventCode, customerId, quantity, ammount);
			TicketStore.getStore().put(ticket.getCode(), ticket);
		}
		else
		{
			throw new NotFoundException("Not such object on this server!");
		}
	}
	
	@DELETE
	public void deleteCustomer(@QueryParam("code") int code) throws Exception
	{
		if(TicketStore.getStore().remove(code) == null)
		{
			throw new NotFoundException("Not such object on this server!");
		}
	}
*/
}
