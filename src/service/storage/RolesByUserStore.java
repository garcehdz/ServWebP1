package service.storage;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.List;

import service.beans.*;

public class RolesByUserStore
{
	private static String[][] data =
	{
		{"luis", "2"}//promoter
		, {"admin", "1"}//admin
		, {"root", "1"}
		, {"root", "2"}
	};

	private static Map<String,RolesByUser> store;
	private static RolesByUserStore instance = null;
	
	private RolesByUserStore() {
		store = new HashMap<String,RolesByUser>();
		initStore();
	}
	
	public static Map<String,RolesByUser> getStore() {
		if(instance==null) {
			instance = new RolesByUserStore();
		}
		return store;
	}
	
	private void initStore() {
		for (int a = 0; a < data.length; a++)
		{
			RolesByUser instance = new RolesByUser();
			instance.setUsername(data[a][0]);
			instance.setRole(Integer.parseInt(data[a][1]));
			store.put(instance.getUsername() + "$" + instance.getRole(), instance);
		}
	}
}