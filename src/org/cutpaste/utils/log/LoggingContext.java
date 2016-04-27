package org.cutpaste.utils.log;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * LoggingContext is a class intended to be passed in into method calls in order to collect error/logging 
 * information that might arise during that methods execution, and that is needed to be able to log/report, 
 * but doesn't warrant abnormal exit via exception or early return.
 * 
 */

public class LoggingContext {

	private List<LogEvent> logEvents = new ArrayList<>();
	
	public List<LogEvent> getLogEvents() {
		List<LogEvent> result = new ArrayList<>(this.logEvents);
		Collections.sort(result, new Comparator<LogEvent>() {
			@Override
			public int compare(LogEvent o1, LogEvent o2) {
				return o2.getLevel().getLevel() - o1.getLevel().getLevel();
			}
		});
		return result;
	}
	
	public void addLogEvent(LogEvent event) {
		logEvents.add(event);
	}
	
	public void addAll(LoggingContext loggingContext) {
		this.logEvents.addAll(loggingContext.getLogEvents());
	}
	
	public boolean hasEventsOfLevelEqualOrAbove(LogLevel level) {
		for (LogEvent event : logEvents) {
			if (event.getLevel().isEqualOrHigherLevel(level)) {
				return true;
			}
		}
		return false;
	}
	
	public List<LogEvent> getLogEventsWithPayload() {
		List<LogEvent> result = new ArrayList<>();
		for (LogEvent event : getLogEvents()) {
			if (event.hasPayload()) {
				result.add(event);
			}
		}
		return result;
	}

	public <T> List<LogEvent> getLogEventsWithPayloadOfType(Class<T> clazz) {
		List<LogEvent> result = new ArrayList<>();
		for (LogEvent event : getLogEvents()) {
			T payload = event.getPayloadAs(clazz);
			if (null != payload) {
				result.add(event);
			}
		}
		return result;		
	}
}
