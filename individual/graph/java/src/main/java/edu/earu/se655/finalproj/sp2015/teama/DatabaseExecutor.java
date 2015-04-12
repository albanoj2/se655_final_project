package edu.earu.se655.finalproj.sp2015.teama;

import edu.earu.se655.finalproj.sp2015.teama.data.DataSet;

public abstract class DatabaseExecutor {
	
	private long startTime = 0;
	private long endTime = 0;
	protected DataSet dataSet;
	
	public DatabaseExecutor (DataSet dataSet) {
		this.dataSet = dataSet;
	}

	public DatabaseExecutor tick () {
		this.startTime = System.currentTimeMillis();
		return this;
	}
	
	public DatabaseExecutor tock () {
		this.endTime = System.currentTimeMillis();
		return this;
	}
	
	public long getEllapsedTime () {
		return this.endTime - this.startTime;
	}
	
	public DataSet getDataSet () {
		return this.dataSet;
	}
	
	public abstract DatabaseExecutor open ();
	public abstract DatabaseExecutor populate ();
	public abstract DatabaseExecutor execute ();
	public abstract DatabaseExecutor shutdown ();
}
