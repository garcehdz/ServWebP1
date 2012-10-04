package service.beans;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Ticket
{
	private int code;
	private int locationType;//1=sol, 2=sombra, 3=platea, 4=palco, 5=preferencial, 6=VIP
	private int eventCode;
	private String customerId;
	private int quantity;
	private int ammount;
	
	public Ticket()
	{
	}
	
	public Ticket(int code, int locationType, int eventCode, String customerId, int quantity, int ammount)
	{
		this.code=code;
		this.locationType=locationType;
		this.eventCode=eventCode;
		this.customerId=customerId;
		this.quantity=quantity;
		this.ammount=ammount;
	}
	
	public void setCode(int code)
	{
		this.code=code;
	}
	
	public int getCode()
	{
		return this.code;
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
	
	public void setCustomerId(String customerId)
	{
		this.customerId=customerId;
	}
	
	public String getCustomerId()
	{
		return this.customerId;
	}
	
	public void setQuantity(int quantity)
	{
		this.quantity=quantity;
	}
	
	public int getQuantity()
	{
		return this.quantity;
	}
	
	public void setAmmount(int ammount)
	{
		this.ammount=ammount;
	}
	
	public int getAmmount()
	{
		return this.ammount;
	}
}