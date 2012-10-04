package service.beans;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class NumberedTicket
{
	private int locationNumber;
	private int ticketCode;
	
	public NumberedTicket()
	{
	}

	public NumberedTicket(int locationNumber, int ticketCode)
	{
		this.locationNumber=locationNumber;
		this.ticketCode=ticketCode;
	}
	
	public void setLocationNumber(int locationNumber)
	{
		this.locationNumber=locationNumber;
	}
	
	public int getLocationNumber()
	{
		return this.locationNumber;
	}
	
	public void setTicketCode(int ticketCode)
	{
		this.ticketCode=ticketCode;
	}
	
	public int getTicketCode()
	{
		return this.ticketCode;
	}
}