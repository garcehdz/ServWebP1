package service.beans;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Location
{
	private int locationType;//1=sol, 2=sombra, 3=platea, 4=palco, 5=preferencial, 6=VIP
	private int eventCode;
	private int price;
	private int quantity;
	private boolean numbered;
	
	public Location()
	{
	}
	
	public Location(int locationType, int eventCode, int price, int quantity, boolean numbered)
	{
		this.locationType=locationType;//1=sol, 2=sombra, 3=platea, 4=palco, 5=preferencial, 6=VIP
		this.eventCode=eventCode;
		this.price=price;
		this.quantity=quantity;
		this.numbered=numbered;
	}
	
	public void setLocationType(int locationType)
	{
		this.locationType=locationType;
	}
	
	public int getLocationType()
	{
		return this.locationType;
	}
	
	public void setEventCode(int eventCode)
	{
		this.eventCode=eventCode;
	}
	
	public int getEventCode()
	{
		return this.eventCode;
	}
	
	public void setPrice(int price)
	{
		this.price=price;
	}
	
	public int getPrice()
	{
		return this.price;
	}
	
	public void setQuantity(int quantity)
	{
		this.quantity=quantity;
	}
	
	public int getQuantity()
	{
		return this.quantity;
	}
	
	public void setNumbered(boolean numbered)
	{
		this.numbered=numbered;
	}
	
	public boolean getNumbered()
	{
		return this.numbered;
	}
}