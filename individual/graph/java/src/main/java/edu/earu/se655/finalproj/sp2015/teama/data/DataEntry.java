package edu.earu.se655.finalproj.sp2015.teama.data;

import java.util.List;
import java.util.stream.Collectors;

import org.json.simple.JSONArray;

public class DataEntry {
	private int id;
	private List<Integer> relationships;
	
	@SuppressWarnings("unchecked")
	public DataEntry (int id, JSONArray relationshipIds) {
		// Extract the data from the entry
		this.id = id;
		this.relationships = (List<Integer>) relationshipIds.stream()
				.map((Object val) -> { return Integer.parseInt(val.toString()); })
				.collect(Collectors.toList());
	}
	
	public int getId () { 
		return this.id;
	}
	
	public List<Integer> getRelationshipIds () {
		return this.relationships;
	}
}
