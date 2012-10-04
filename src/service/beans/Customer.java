package service.beans;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Customer
{
	private String Id;
	private String name;
	private String address;
	private String telephone;
	private String cardnumber;
	private String cardType;
	
	public Customer()
	{	
	}
	
	public Customer(String Id, String name, String address, String telephone, String cardnumber, String cardType)
	{
		this.Id=Id;
		this.name=name;
		this.address=address;
		this.telephone=telephone;
		this.cardnumber=cardnumber;
		this.cardType=cardType;
	}
	
	public void setId(String Id)
	{
		this.Id=Id;
	}
	
	public String getId()
	{
		return this.Id;
	}
	
	public void setName(String name)
	{
		this.name=name;
	}
	
	public String getName()
	{
		return this.name;
	}
	
	public void setAddress(String address)
	{
		this.address=address;
	}
	
	public String getAddress()
	{
		return this.address;
	}
	
	public void setTelephone(String telephone)
	{
		this.telephone=telephone;
	}
	
	public String getTelephone()
	{
		return this.telephone;
	}
	
	public void setCardnumber(String cardnumber)
	{
		this.cardnumber=cardnumber;
	}
	
	public String getCardnumber()
	{
		return this.cardnumber;
	}
	
	public void setCardType(String cardType)
	{
		this.cardType=cardType;
	}
	
	public String getCardType()
	{
		return this.cardType;
	}
}