package service.beans;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class RolesByUser
{
	protected String username;
	protected int role;//1=admin, 2=promoter
	
	public RolesByUser()
	{
	}
	
	public RolesByUser(String username, int role)
	{
		this.username = username;
		this.role = role;
	}
	
	public void setUsername(String username)
	{
		this.username=username;
	}
	
	public String getUsername()
	{
		return this.username;
	}
	
	public void setRole(int role)
	{
		this.role=role;
	}
	
	public int getRole()
	{
		return this.role;
	}
}