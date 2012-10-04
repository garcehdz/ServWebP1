package service.storage;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.List;

import service.beans.*;

public class LocationStore
{
	private static String[][] data = {
		{"1", "0", "1000", "100", "true"},
		{"2", "0", "2000", "200", "true"},
		{"3", "0", "2000", "200", "true"},
		{"3", "1", "3000", "330", "false"},
		{"2", "1", "4000", "550", "true"},
		{"1", "1", "5000", "250", "false"}
	};
		
	private static Map<String,Location> store;
	private static LocationStore instance = null;
	
	private LocationStore() {
		store = new HashMap<String,Location>();
		initStore();
	}
	
	public static Map<String,Location> getStore() {
		if(instance==null) {
			instance = new LocationStore();
		}
		return store;
	}
	
	private void initStore() {
		for (int a = 0; a < data.length; a++)
		{
			Location instance = new Location();
			instance.setLocationType(Integer.parseInt(data[a][0]));
			instance.setEventCode(Integer.parseInt(data[a][1]));
			instance.setPrice(Integer.parseInt(data[a][2]));
			instance.setQuantity(Integer.parseInt(data[a][3]));
			instance.setNumbered(Boolean.parseBoolean(data[a][4]));//"true", "false"
			store.put(instance.getLocationType() + "$" + instance.getEventCode(), instance);
		}
	}
}