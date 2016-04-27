package org.cutpaste.utils.log;

public enum LogLevel {
	DEBUG(0), INFO(100), WARN(200), ERROR(300), WTF(400);
	
	private final int level;

	private LogLevel(int level) {
		this.level = level;
	}
	
	public int getLevel() {
		return this.level;
	}
	
	public boolean isEqualOrHigherLevel(LogLevel baseLevel) {
		return this.getLevel() >= baseLevel.getLevel();
	}
}
