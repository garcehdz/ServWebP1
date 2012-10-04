package service.beans;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Event
{
	private int code;
	private String name;
	private int eventType; //1=concierto 2=recital 3= obra de teatro
	private String artists;
	private String date;
	private String place;
	private int promoterId;
	
	public Event()
	{
	}
	
	public Event(int code, String name, int eventType, String artists, String date, String place, int promoterId)
	{
		this.code = code;
		this.name = name;
		this.eventType = eventType; //1=concierto 2=recital 3= obra de teatro
		this.artists = artists;
		this.date = date;
		this.place = place;
		this.promoterId = promoterId;
	}
	
	public void setCode(int code)
	{
		this.code=code;
	}
	
	public int getCode()
	{
		return this.code;
	}
	
	public void setName(String name)
	{
		this.name=name;
	}
	
	public String getName()
	{
		return this.name;
	}
	
	public void setEventType(int eventType)
	{
		this.eventType=eventType;
	}
	
	public int getEventType()
	{
		return this.eventType;
	}
	
	public void setArtists(String artists)
	{
		this.artists=artists;
	}
	
	public String getArtists()
	{
		return this.artists;
	}
	
	public void setDate(String date)
	{
		this.date=date;
	}
	
	public String getDate()
	{
		return this.date;
	}
	
	public void setPlace(String place)
	{
		this.place=place;
	}
	
	public String getPlace()
	{
		return this.place;
	}
	
	public void setPromoterId(int promoterId)
	{
		this.promoterId=promoterId;
	}
	
	public int getPromoterId()
	{
		return this.promoterId;
	}
}