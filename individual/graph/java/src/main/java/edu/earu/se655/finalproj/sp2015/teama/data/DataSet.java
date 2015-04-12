package edu.earu.se655.finalproj.sp2015.teama.data;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.json.simple.JSONArray;

public class DataSet {
	
	private String name;
	private List<DataEntry> dataEntries;
	
	@SuppressWarnings("unchecked")
	public DataSet (String name, JSONArray jsonArray) {
		
		// Store the name of the data set
		this.name = name;
		
		// Create a list of data entries
		this.dataEntries = new ArrayList<>();
		
		// Extract an iterator
		Iterator<JSONArray> it = jsonArray.iterator();
		
		for (int i = 0; it.hasNext(); i++) {
			// Extract the data from the iterator
			this.dataEntries.add(new DataEntry(i, it.next()));
		}
	}
	
	public List<DataEntry> getDataEntries () {
		return this.dataEntries;
	}

	public int getNumberOfNodes () {
		return this.getDataEntries().size();
	}
	
	public long getNumberOfRelationships () {
		return this.getDataEntries().stream().flatMap(entry -> entry.getRelationshipIds().stream()).count();
	}
	
	public String getName () {
		return this.name;
	}
}
