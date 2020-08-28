package org.ceiridge.stickcrafter.util;

public class TimeStopper {
	private long last, target;

	public TimeStopper(long target) {
		this.target = target;
	}

	public TimeStopper() {
		this(-1);
	}

	public long getLast() {
		return this.last;
	}
	
	public long getReached() {
		return System.currentTimeMillis() - this.getLast();
	}
	
	public long getTarget() {
		return this.target;
	}

	public boolean hasReached(long milliseconds) {
		return getReached() >= milliseconds;
	}
	
	public boolean hasReached() {
		return hasReached(this.getTarget());
	}

	public void setLast(long current) {
		this.last = current;
	}
	
	public void setTarget(long target) {
		this.target = target;
	}
	
	public void reset() {
		this.setLast(System.currentTimeMillis());
	}
}
