package edu.erau.se655.finalproj.sp2015.teama.group.graph;



public abstract class DatabaseExecutor {
	
	private long startTime = 0;
	private long endTime = 0;

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
	
	public abstract String getName ();
	public abstract DatabaseExecutor populate ();
	public abstract DatabaseExecutor execute ();
	public abstract DatabaseExecutor shutdown ();
}