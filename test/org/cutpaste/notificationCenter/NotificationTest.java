package org.cutpaste.notificationCenter;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;

import org.junit.Test;

public class NotificationTest {

	@Test
	public void testSerialize() throws IOException, ClassNotFoundException {
		final Notification notification = new Notification("SerialNotification", "ThisIsTheMessage", this);
		final MemoryBuffer memoryBuffer = new MemoryBuffer(1024);
		final ObjectOutputStream out = new ObjectOutputStream(new MemoryOutputStream(memoryBuffer));
		final ObjectInputStream in = new ObjectInputStream(new MemoryInputStream(memoryBuffer));
		out.writeObject(notification);
		out.flush();
		final Notification readObject = (Notification)in.readObject();
		assertEquals(notification.getNotificationName(), readObject.getNotificationName());
		assertEquals(notification.getNotification(), readObject.getNotification());
		assertNull(readObject.getNotificationSender());
		in.close();
		out.close();
	}
	
	
	private class MemoryInputStream extends InputStream {

		private final MemoryBuffer	buffer;

		public MemoryInputStream(final MemoryBuffer buffer) {
			super();
			this.buffer = buffer;
		}
		
		@Override
		public int read() throws IOException {
			return buffer.read();
		}
		
	}
	
	private class MemoryOutputStream extends OutputStream {

		private final MemoryBuffer	buffer;

		public MemoryOutputStream(final MemoryBuffer buffer) {
			this.buffer = buffer;
		}
		
		@Override
		public void write(final int b) throws IOException {
			buffer.write(b);
		}
		
	}

	private class MemoryBuffer {
		private final int[] memory;
		private int currentReadPos = 0;
		private int currentWritePos = 0;
		
		public MemoryBuffer(final int size) {
			this.memory = new int[size];
		}
		
		public void write (final int data) {
			memory[currentWritePos] = data;
			currentWritePos++;
		}
		
		public int read() {
			final int result = memory[currentReadPos];
			currentReadPos++;
			return result;
		}
	}

}
