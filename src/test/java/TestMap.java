import java.io.File;
import java.util.Iterator;
import java.util.Map.Entry;

import net.openhft.chronicle.core.values.LongValue;
import net.openhft.chronicle.core.values.StringValue;
import net.openhft.chronicle.map.ChronicleMap;
import net.openhft.chronicle.map.ChronicleMapBuilder;
import net.openhft.chronicle.values.Values;
import noobchain.transaction.transactionoutput.TransactionOutput;

public class TestMap {

	private static final long HOW_MANY  = 50000l;
	
	private static final int VALUE_LENGTH = 2;
	
	private static void log(String txt, long start) {
		long now = System.currentTimeMillis();
		System.out.println("time:"+now+" diff:"+(now-start)+" - "+txt);
	}
	
	public static void main(String[] args) throws Exception {
		File path = new File("DB/chronicle");
		path.mkdirs();
		path = new File("DB/chronicle/fff");
		if(path.exists()) path.delete();
		
		long start = System.currentTimeMillis();
		ChronicleMap<StringValue, TransactionOutput> map = null;
		try {
			map = ChronicleMapBuilder
					.of(StringValue.class, TransactionOutput.class)
					.name("map")
					.entries(50000l)
					.averageValue(new TransactionOutput())
					.createOrRecoverPersistedTo(path);

			StringValue key = Values.newHeapInstance(StringValue.class);
			
			for(int i=0;i<HOW_MANY;i++) {
				key.setValue("");
				map.put(key, new TransactionOutput());
			}
			log("Loaded", start);
			start = System.currentTimeMillis();
			log("Size "+map.longSize(), start);
			start = System.currentTimeMillis();
		} finally {
			if(map!=null) {
				map.close();
			}
		}

		log("Start reading with iterator.", start);
		
		try {
			start = System.currentTimeMillis();
			map = ChronicleMapBuilder
					.of(StringValue.class, TransactionOutput.class)
					.name("map")
					.entries(HOW_MANY)
					.averageValue(new TransactionOutput())
					.createOrRecoverPersistedTo(path);

			StringValue key = Values.newHeapInstance(StringValue.class);
			
			Iterator<Entry<StringValue, TransactionOutput>> iterator = map.entrySet().iterator();
			int i=9000;
			while(iterator.hasNext()) {
				Entry<StringValue, TransactionOutput> next = iterator.next();
				key.setValue("");
				map.put(key, new TransactionOutput());
				i++;
			}
			
			log("After itaration", start);
			start = System.currentTimeMillis();
			
			map.clear();
			
			log("After clear", start);
			start = System.currentTimeMillis();
			for(int x=0;x<HOW_MANY;x++) {
				key.setValue("x");
				map.put(key,  new TransactionOutput());
			}
			
			log("Deleting and inserting while performing iteration over map. Map size is: "+map.longSize(), start);
			start = System.currentTimeMillis();
			iterator = map.entrySet().iterator();
			i=0;
			while(iterator.hasNext()) {
				Entry<StringValue, TransactionOutput> next = iterator.next();
				map.remove(next.getKey());
				map.put(next.getKey(), new TransactionOutput());
				i++;
			}
			
			log("Map size is: "+map.longSize()+" iteration: "+i, start);
		} finally {
			if(map!=null) {
				map.close();
			}
		}
		
		if(path.exists()) path.delete();
	}

	protected static int[] generateValueArray(long start) {
		int[] val = new int[VALUE_LENGTH];
		val[0] = (int)(start/1000)+1;
		val[1] = (int)(System.currentTimeMillis()/1000);
		return val;
	}
	
}
