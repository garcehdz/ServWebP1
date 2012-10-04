package service.storage;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.List;

import service.beans.*;

public class NumberedTicketStore
{
	private static String[][] data = {};
		
	private static Map<String,NumberedTicket> store;
	private static NumberedTicketStore instance = null;
	
	private NumberedTicketStore() {
		store = new HashMap<String,NumberedTicket>();
		initStore();
	}
	
	public static Map<String,NumberedTicket> getStore() {
		if(instance==null) {
			instance = new NumberedTicketStore();
		}
		return store;
	}
	
	private void initStore() {
		for (int a = 0; a < data.length; a++)
		{
			NumberedTicket instance = new NumberedTicket();
			instance.setLocationNumber(Integer.parseInt(data[a][0]));
			instance.setTicketCode(Integer.parseInt(data[a][1]));
			store.put(instance.getLocationNumber()+ "$" + instance.getTicketCode(), instance);
		}
	}
}