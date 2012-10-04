package service.storage;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.List;

import service.beans.*;

public class TicketStore
{
	private static String[][] data = {};
		
	private static Map<Integer,Ticket> store;
	private static TicketStore instance = null;
	
	private TicketStore() {
		store = new HashMap<Integer,Ticket>();
		initStore();
	}
	
	public static Map<Integer,Ticket> getStore() {
		if(instance==null) {
			instance = new TicketStore();
		}
		return store;
	}
	
	private void initStore() {
		for (int a = 0; a < data.length; a++)
		{
			Ticket instance = new Ticket();
			instance.setCode(Integer.parseInt(data[a][0]));
			instance.setLocationType(Integer.parseInt(data[a][1]));
			instance.setEventCode(Integer.parseInt(data[a][2]));
			instance.setCustomerId(data[a][3]);
			instance.setQuantity(Integer.parseInt(data[a][4]));
			instance.setAmmount(Integer.parseInt(data[a][5]));
			store.put(instance.getCode(), instance);
		}
	}
}