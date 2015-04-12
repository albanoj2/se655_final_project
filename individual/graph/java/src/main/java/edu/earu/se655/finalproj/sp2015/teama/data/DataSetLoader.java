package edu.earu.se655.finalproj.sp2015.teama.data;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import org.apache.commons.io.FilenameUtils;
import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class DataSetLoader {
	
	private static String CURRENT_DIR = System.getProperty("user.dir");
	private DataSet dataSet;

	public DataSetLoader load (String path) {
		// Create the parser to consume the JSON file
		JSONParser parser = new JSONParser();
		
		// Obtain the name of the data set from the file name
		String name = FilenameUtils.removeExtension(new File(path).getName());
		
		try {
			// Parse the JSON file into a JSON object
			this.dataSet = new DataSet(name, (JSONArray) parser.parse(new FileReader(CURRENT_DIR + "data_sets/" + path)));
		} 
		catch (FileNotFoundException e) {
			e.printStackTrace();
		} 
		catch (IOException e) {
			e.printStackTrace();
		} 
		catch (ParseException e) {
			e.printStackTrace();
		}
		
		return this;
	}
	
	public DataSet getDataSet () {
		return this.dataSet;
	}

}
