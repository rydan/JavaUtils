package org.cutpaste.utils.log;

import static org.junit.Assert.*;

import java.math.BigDecimal;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

public class LoggingContextTest {
	private LoggingContext loggingContext;
	private String payload1 = "Payload1";
	private BigDecimal payload2 = BigDecimal.valueOf(4711.17);
	
	@Before
	public void setUp() {
		loggingContext = new LoggingContext();
		loggingContext.addLogEvent(new LogEvent("Log event one"));
		loggingContext.addLogEvent(new LogEvent("Log event two"));
		loggingContext.addLogEvent(new LogEvent(LogLevel.WARN, "Log event warn"));
		loggingContext.addLogEvent(new LogEvent(LogLevel.DEBUG, "Log with BigDecimal payload", payload2));
		loggingContext.addLogEvent(new LogEvent(LogLevel.ERROR, "Log event error"));
		loggingContext.addLogEvent(new LogEvent(LogLevel.WTF, "Log event wtf"));
		loggingContext.addLogEvent(new LogEvent(LogLevel.DEBUG, "Log with exception", new IllegalArgumentException("TestException")));
		loggingContext.addLogEvent(new LogEvent(LogLevel.DEBUG, "Log with exception and String Payload", payload1, new IllegalArgumentException("TestException")));
	}
	
	@Test
	public void testHasEventsAbove() {
		LoggingContext testContext = new LoggingContext();
		assertFalse(testContext.hasEventsOfLevelEqualOrAbove(LogLevel.DEBUG));
		testContext.addLogEvent(new LogEvent(LogLevel.DEBUG, "Lowest log level"));
		assertTrue(testContext.hasEventsOfLevelEqualOrAbove(LogLevel.DEBUG));
		assertFalse(testContext.hasEventsOfLevelEqualOrAbove(LogLevel.INFO));
		testContext.addLogEvent(new LogEvent(LogLevel.WARN, "Warn level"));
		assertTrue(testContext.hasEventsOfLevelEqualOrAbove(LogLevel.INFO));
		assertTrue(testContext.hasEventsOfLevelEqualOrAbove(LogLevel.WARN));
		assertFalse(testContext.hasEventsOfLevelEqualOrAbove(LogLevel.ERROR));
	}
	
	@Test
	public void testGetLogEventsWithPayload() {
		assertEquals(2, loggingContext.getLogEventsWithPayload().size());
	}
	
	@Test
	public void testGetLogEventsWithPayloadOfType() {
		List<LogEvent> events = loggingContext.getLogEventsWithPayloadOfType(String.class);
		assertEquals(1, events.size());
		assertEquals(payload1, events.get(0).getPayload());
		events = loggingContext.getLogEventsWithPayloadOfType(List.class);
		assertEquals(0, events.size());
		events = loggingContext.getLogEventsWithPayloadOfType(BigDecimal.class);
		assertEquals(1, events.size());
	}
	
	@Test
	public void testAddAll() {
		LoggingContext testContext = new LoggingContext();
		testContext.addLogEvent(new LogEvent(LogLevel.DEBUG, "Lowest log level"));
		testContext.addLogEvent(new LogEvent(LogLevel.WARN, "Warn level"));
		assertEquals(2, testContext.getLogEvents().size());
		testContext.addAll(loggingContext);
		assertEquals(10, testContext.getLogEvents().size());
	}
	
	@Test
	public void testGetIsSorted() {
		loggingContext.addLogEvent(new LogEvent(LogLevel.WTF, "Log event wtf2"));
		loggingContext.addLogEvent(new LogEvent(LogLevel.INFO, "Log event info new"));
		
		List<LogEvent> logEvents = loggingContext.getLogEvents();
		assertEquals(10, logEvents.size());
		assertEquals(LogLevel.WTF, logEvents.get(0).getLevel());
		assertEquals(LogLevel.WTF, logEvents.get(1).getLevel());
		assertEquals(LogLevel.ERROR, logEvents.get(2).getLevel());
		assertEquals(LogLevel.WARN, logEvents.get(3).getLevel());
		assertEquals(LogLevel.INFO, logEvents.get(4).getLevel());
		assertEquals(LogLevel.INFO, logEvents.get(5).getLevel());
		assertEquals(LogLevel.INFO, logEvents.get(6).getLevel());
		assertEquals(LogLevel.DEBUG, logEvents.get(7).getLevel());
		assertEquals(LogLevel.DEBUG, logEvents.get(8).getLevel());
		assertEquals(LogLevel.DEBUG, logEvents.get(9).getLevel());
	}
	
}
