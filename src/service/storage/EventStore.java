package service.storage;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.List;

import service.beans.*;

public class EventStore
{
	private static String[][] data = {
		{"0", "Evento 0", "1", "Artistas", "Fecha", "Lugar", "1"},
		{"1", "Evento 1", "2", "Artistas", "Fecha", "Lugar", "0"},
		{"2", "Evento 2", "3", "Artistas", "Fecha", "Lugar", "1"},
		{"3", "Evento 3", "1", "Artistas", "Fecha", "Lugar", "0"}
	};
		
	private static Map<Integer,Event> store;
	private static EventStore instance = null;
	
	private EventStore() {
		store = new HashMap<Integer,Event>();
		initStore();
	}
	
	public static Map<Integer,Event> getStore() {
		if(instance==null) {
			instance = new EventStore();
		}
		return store;
	}
	
	private void initStore() {
		for (int a = 0; a < data.length; a++)
		{
			Event instance = new Event();
			instance.setCode(Integer.parseInt(data[a][0]));
			instance.setName(data[a][1]);
			instance.setEventType(Integer.parseInt(data[a][2]));
			instance.setArtists(data[a][3]);
			instance.setDate(data[a][4]);
			instance.setPlace(data[a][5]);
			instance.setPromoterId(Integer.parseInt(data[a][6]));
			store.put(instance.getCode(), instance);
		}
	}
}