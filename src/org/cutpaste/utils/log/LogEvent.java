package org.cutpaste.utils.log;

public class LogEvent {
	private final String logMessage;
	private final LogLevel level;
	private final Throwable throwable;
	private final Object payload;
	
	public LogEvent(LogLevel level, String logMessage, Object payload, Throwable cause) {
		super();
		this.logMessage = logMessage;
		this.level = level;
		this.payload = payload;
		this.throwable = cause;
	}

	public LogEvent(LogLevel level, String logMessage, Object payload) {
		this(level, logMessage, payload, null);
	}

	public LogEvent(LogLevel level, String logMessage, Throwable throwable) {
		this(level, logMessage, null, throwable);
	}

	public LogEvent(LogLevel level, String logMessage) {
		this(level, logMessage, null, null);
	}
	
	public LogEvent(String logMessage) {
		this(LogLevel.INFO, logMessage, null, null);
	}

	public String getLogMessage() {
		return logMessage;
	}

	public LogLevel getLevel() {
		return level;
	}

	public Throwable getThrowable() {
		return throwable;
	}

	public Object getPayload() {
		return payload;
	}
	
	public <T> T getPayloadAs(Class<T> clazz) {
		T result = null;
		try {
			result = clazz.cast(payload);
		} catch (ClassCastException e) {
			//OK, do nothing, just return null
		}
		return result;
	}
		
	public boolean hasPayload() {
		return null != payload;
	}
	
	public boolean hasThrowable() {
		return null != throwable;
	}
}
