package service.storage;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.List;

import service.beans.*;

public class UsuarioStore
{
	private static String[][] data = 
	{
		{"luis", "pass"}
		, {"admin", "admin"}
		, {"root", "root"}
	};
		
	private static Map<String,Usuario> store;
	private static UsuarioStore instance = null;
	
	private UsuarioStore() {
		store = new HashMap<String,Usuario>();
		initStore();
	}
	
	public static Map<String,Usuario> getStore() {
		if(instance==null) {
			instance = new UsuarioStore();
		}
		return store;
	}
	
	private void initStore() {
		for (int a = 0; a < data.length; a++)
		{
			Usuario instance = new Usuario();
			instance.setUsername(data[a][0]);
			instance.setPassword(data[a][1]);
			store.put(instance.getUsername(), instance);
		}
	}
}