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

@Path("/event")
public class EventResource
{
	@Context
	UriInfo uriInfo;
	@Context
	Request request;

	@GET
	@Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
	public List<Event> getEvents()
	{
		List<Event> events = new ArrayList<Event>();
		events.addAll(EventStore.getStore().values());
		return events;
	}
	
	@GET
	@Path("detail")
	@Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
	public Event getEvent(@QueryParam("code") int code)
	{
		Event event = EventStore.getStore().get(code);
		if(event == null)
		{
			throw new NotFoundException("Not such object on this server!");
		}
		return event;
	}
	
	@GET
	@Path("promoter")
	@Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
	public List<Event> getEventsByPromoter(@QueryParam("promoterId") int promoterId)
	{
		List<Event> events = new ArrayList<Event>();
		for (Event event : EventStore.getStore().values())
		{
			if (event.getPromoterId() == promoterId)
			{
				events.add(event);
			}
		}
		return events;
	}

	@POST
	public void insertEvent(@QueryParam("code") int code,@QueryParam("name") String name,@QueryParam("eventType") int eventType,@QueryParam("artists") String artists,@QueryParam("date") String date,@QueryParam("place") String place, @QueryParam("promoterId") int promoterId) throws Exception
	{
		if (PromoterStore.getStore().get(promoterId) != null)
		{
			Event event = new Event(code, name, eventType, artists, date, place, promoterId);
			EventStore.getStore().put(event.getCode(), event);
		}
	}
	
	@PUT
	public void updateEvent(@QueryParam("code") int code,@QueryParam("name") String name,@QueryParam("eventType") int eventType,@QueryParam("artists") String artists,@QueryParam("date") String date,@QueryParam("place") String place, @QueryParam("promoterId") int promoterId) throws Exception
	{
		if(EventStore.getStore().get(code) != null && PromoterStore.getStore().get(promoterId) != null)
		{
			Event event = new Event(code, name, eventType, artists, date, place, promoterId);
			EventStore.getStore().put(event.getCode(), event);
		}
		else
		{
			throw new NotFoundException("Not such object on this server!");
		}
	}
	
	@DELETE
	public void deleteEvent(@QueryParam("code") int code) throws Exception
	{
		if(EventStore.getStore().remove(code) == null)
		{
			throw new NotFoundException("Not such object on this server!");
		}
		else
		{
			List<String> keys = new ArrayList<String>();
			for (Location location : LocationStore.getStore().values())
			{
				if (location.getEventCode() == code)
				{
					keys.add(location.getLocationType() + "$" + code);
				}
			}
			for (String key : keys)
			{
				LocationStore.getStore().remove(key);
			}
		}
	}
}
