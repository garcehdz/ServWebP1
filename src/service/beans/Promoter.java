package service.beans;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Promoter extends Usuario
{
	private int code;
	private String name;
	private String address;
	private String telephone;
	private String bank;
	private int commision;
	private String username;

	public Promoter()
	{
	}
	
	public Promoter(int code, String name, String address, String telephone, String bank, int commision ,String username, String password)
	{
		this.code=code;
		this.name=name;
		this.address=address;
		this.telephone=telephone;
		this.bank=bank;
		this.commision=commision;
		this.username=username;
		this.username = username;
		this.password = password;
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
	
	public void setBank(String bank)
	{
		this.bank=bank;
	}
	
	public String getBank()
	{
		return this.bank;
	}
	
	public void setCommision(int commision)
	{
		this.commision=commision;
	}
	
	public int getCommision()
	{
		return this.commision;
	}
	
	public void setUsername(String username)
	{
		this.username=username;
	}
	
	public String getUsername()
	{
		return this.username;
	}
}