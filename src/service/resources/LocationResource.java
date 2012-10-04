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

@Path("/location")
public class LocationResource
{
	@Context
	UriInfo uriInfo;
	@Context
	Request request;

	@GET
	@Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
	public List<Location> getLocations()
	{
		List<Location> locations = new ArrayList<Location>();
		locations.addAll(LocationStore.getStore().values());
		return locations;
	}

	@GET
	@Path("event")
	@Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
	public List<Location> getLocationsByEventCode(@QueryParam("eventCode") int eventCode)
	{
		List<Location> locations = new ArrayList<Location>();
		for (Location location : LocationStore.getStore().values())
		{
			if (location.getEventCode() == eventCode)
			{
				locations.add(location);
			}
		}
		return locations;
	}
/*
	@GET
	@Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
	public Location getLocation(@QueryParam("locationType") int locationType,@QueryParam("eventCode") int eventCode) throws Exception
	{
		Location location = LocationStore.getStore().get(locationType + "$" + eventCode);
		if(location == null)
		{
			throw new NotFoundException("Not such object on this server!");
		}
		return location;
	}
*/
	@POST
	public void insertLocation(@QueryParam("locationType") int locationType,@QueryParam("eventCode") int eventCode,@QueryParam("price") int price,@QueryParam("quantity") int quantity,@QueryParam("numbered") String numbered) throws Exception
	{
		boolean num = numbered.equals("true");
		Location location = new Location(locationType, eventCode, price, quantity, num);
		LocationStore.getStore().put(locationType + "$" + eventCode, location);
	}
	
	@PUT
	public void updateLocation(@QueryParam("locationType") int locationType,@QueryParam("eventCode") int eventCode,@QueryParam("price") int price,@QueryParam("quantity") int quantity,@QueryParam("numbered") boolean numbered) throws Exception
	{
		if(LocationStore.getStore().get(locationType) != null)
		{
			Location location = new Location(locationType, eventCode, price, quantity, numbered);
			LocationStore.getStore().put(locationType + "$" + eventCode, location);
		}
		else
		{
			throw new NotFoundException("Not such object on this server!");
		}
	}
	
	/*
	@DELETE
	public void deleteLocation(@QueryParam("locationType") int locationType,@QueryParam("eventCode") int eventCode) throws Exception
	{
		if(LocationStore.getStore().remove(locationType + "$" + eventCode) == null)
		{
			throw new NotFoundException("Not such object on this server!");
		}
	}
	*/

	@DELETE
	public void deleteLocationByEventCode(@QueryParam("eventCode") int eventCode) throws Exception
	{
		List<String> keys = new ArrayList<String>();
		for (Location location : LocationStore.getStore().values())
		{
			if (location.getEventCode() == eventCode)
			{
				keys.add(location.getLocationType() + "$" + eventCode);
			}
		}
		for (String key : keys)
		{
			LocationStore.getStore().remove(key);
		}
	}

}
