package service.storage;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.List;

import service.beans.*;

public class CustomerStore
{
	private static String[][] data =
	{
		{"402080812", "Luis Roldán Chacón", "Heredia", "22603957", "402080812", "Credomatic"}
	};
		
	private static Map<String,Customer> store;
	private static CustomerStore instance = null;
	
	private CustomerStore() {
		store = new HashMap<String,Customer>();
		initStore();
	}
	
	public static Map<String,Customer> getStore() {
		if(instance==null) {
			instance = new CustomerStore();
		}
		return store;
	}
	
	private void initStore() {
		for (int a = 0; a < data.length; a++)
		{
			Customer instance = new Customer();
			instance.setId(data[a][0]);
			instance.setName(data[a][1]);
			instance.setAddress(data[a][2]);
			instance.setTelephone(data[a][3]);
			instance.setCardnumber(data[a][4]);
			instance.setCardType(data[a][5]);
			store.put(instance.getId(), instance);
		}
	}
}