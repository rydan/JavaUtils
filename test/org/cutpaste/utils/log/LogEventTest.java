package org.cutpaste.utils.log;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;

public class LogEventTest {

	@Test
	public void testGetPayloadAs() {
		String payload = "PayloadString!";
		LogEvent logEvent = new LogEvent(LogLevel.INFO, "TestPayloadMessage", payload);
		assertEquals("PayloadString!", logEvent.getPayloadAs(String.class));
		assertTrue(null == logEvent.getPayloadAs(List.class));
	}

}
