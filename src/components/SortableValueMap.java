package components;

import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class SortableValueMap<K, V extends Comparable<V>> extends LinkedHashMap<K, V> {
	private static final long serialVersionUID = 1L;
	
	public static int ASC = 0;
	public static int DESC = 1;
	
	public SortableValueMap() { }

	public SortableValueMap( Map<K, V> map ) {
		super( map );
	}

	public void sortByValue(final int sort) {
		List<Map.Entry<K, V>> list = new LinkedList<Map.Entry<K, V>>( entrySet() );

		Collections.sort( list, new Comparator<Map.Entry<K, V>>() {
			public int compare( Map.Entry<K, V> entry1, Map.Entry<K, V> entry2 ) {
				return sort == 0 ? entry1.getValue().compareTo( entry2.getValue() ) : entry2.getValue().compareTo( entry1.getValue() );
			}
		});

		clear();

		for( Map.Entry<K, V> entry : list ) {
			put( entry.getKey(), entry.getValue() );
		}
	}
	
	public void sortByDesc() {
		List<Map.Entry<K, V>> list = new LinkedList<Map.Entry<K, V>>( entrySet() );

		Collections.sort( list, new Comparator<Map.Entry<K, V>>() {
			public int compare( Map.Entry<K, V> entry1, Map.Entry<K, V> entry2 ) {
				return entry2.getKey().toString().compareTo(entry1.getKey().toString());
			}
		});

		clear();

		for( Map.Entry<K, V> entry : list ) {
			put( entry.getKey(), entry.getValue() );
		}
	}

	public void print( String text, Map<String, Double> map ) {
		System.out.println( text );

		for( String key : map.keySet() ) {
			System.out.println( "key/value: " + key + "/" + map.get( key ) );
		}
	}

	public void printByDate( String text, Map<String, Date> map ) {
		System.out.println( text );

		for( String key : map.keySet() ) {
			System.out.println( "key/value: " + key + "/" + map.get( key ) );
		}
	}

	public static void main( String[] args ) {
		SortableValueMap<String, Double> map = new SortableValueMap<String, Double>();

		map.put( "A", 67.5 );
		map.put( "B", 99.5 );
		map.put( "C", 82.4 );
		map.put( "D", 42.0 );

		map.print( "Unsorted map", map );
		map.sortByValue(DESC);
		map.print( "Sorted map", map );
	}
}