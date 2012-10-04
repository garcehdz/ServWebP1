package service.storage;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.List;

import service.beans.*;

public class PromoterStore
{
	private static String[][] data =
	{
		{"0", "Luis Roldan Chacon", "Heredia centro", "22357898", "Nacional", "10", "luis"},
		{"1", "Gilberth Arce Hdez", "Heredia centro", "22618797", "Nacional", "10", "chubby"}
	};
		
	private static Map<Integer,Promoter> store;
	private static PromoterStore instance = null;
	
	private PromoterStore() {
		store = new HashMap<Integer,Promoter>();
		initStore();
	}
	
	public static Map<Integer,Promoter> getStore() {
		if(instance==null) {
			instance = new PromoterStore();
		}
		return store;
	}
	
	private void initStore() {
		for (int a = 0; a < data.length; a++)
		{
			Promoter instance = new Promoter();
			instance.setCode(Integer.parseInt(data[a][0]));
			instance.setName(data[a][1]);
			instance.setAddress(data[a][2]);
			instance.setTelephone(data[a][3]);
			instance.setBank(data[a][4]);
			instance.setCommision(Integer.parseInt(data[a][5]));
			instance.setUsername(data[a][6]);
			store.put(instance.getCode(), instance);
		}
	}
}