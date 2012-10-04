package service.beans;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Usuario
{
	protected String username;
	protected String password;
	
	public Usuario()
	{
	}
	
	public Usuario(String username, String password)
	{
		this.username = username;
		this.password = password;
	}
	
	public void setUsername(String username)
	{
		this.username=username;
	}
	
	public String getUsername()
	{
		return this.username;
	}
	
	public void setPassword(String password)
	{
		this.password=password;
	}
	
	public String getPassword()
	{
		return this.password;
	}
}