package org.cutpaste.notificationCenter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 
 * NotificationCenter is a class to handle callbacks, in the same vein as
 * NSNotificationCenter i ObjC. Listeners need to implement the
 * NotifiationListener interface Listeners register with the center, using the
 * NotificationName as key to determine what notifications they want to listen
 * to
 * 
 * @author rydan@cutpaste.org
 * 
 */

public final class NotificationCenter {

	private final HashMap<String, ArrayList<NotificationListener>>	notificationListeners;
	private static NotificationCenter								notificationCenter	= null;
	private static ExecutorService									asyncNotificationExecutor;

	/**
	 * Private constructor, subclassing not allowed
	 */

	private NotificationCenter() {
		notificationListeners = new HashMap<String, ArrayList<NotificationListener>>();
		asyncNotificationExecutor = Executors.newCachedThreadPool();
	}

	/**
	 * 
	 * Factory method for getting the (singleton) NotificationCenter.
	 * 
	 * @return the NotificationCenter singleton
	 */

	public synchronized static NotificationCenter getNotificationCenter() {
		if (null == notificationCenter) {
			notificationCenter = new NotificationCenter();
		}
		return notificationCenter;
	}

	/**
	 * Stop the NotificationCenter. This is necessary to call in order to quit
	 * cleanly, since the NotificationCenter uses it's own running threads
	 */

	public static synchronized void stop() {
		if (null != notificationCenter) {
			notificationCenter = null;
			asyncNotificationExecutor.shutdown();
		}
	}

	/**
	 * 
	 * This is called to add a listener to the notification center. The listener
	 * will only be triggered by events with the correct notification name. It
	 * is possible to register the same listener for listening to multiple
	 * notifications simply by adding it multiple times, once for each desired
	 * notification name
	 * 
	 * @param notificationName
	 *            only notifications with this notification name will trigger
	 *            the listener
	 * @param notificationListener
	 *            the listener to be notified of events of the desired
	 *            notification name.
	 */

	public synchronized void addNotificationListener(
			final String notificationName,
			final NotificationListener notificationListener) {
		ArrayList<NotificationListener> listeners = notificationListeners
				.get(notificationName);
		if (null == listeners) {
			listeners = new ArrayList<NotificationListener>();
			notificationListeners.put(notificationName, listeners);
		}
		if (!listeners.contains(notificationListener)) {
			listeners.add(notificationListener);
		}
	}

	/**
	 * 
	 * Remove a notification listener for a specific notification name.
	 * Notifications with this name will no longer be sent to the notification
	 * listener.
	 * 
	 * @param notificationName
	 *            the notification name desired not to listen to any more
	 * @param notificationListener
	 *            the listener to not be sent notifications with the given
	 *            notification name
	 */

	public synchronized void removeNotificationListener(
			final String notificationName,
			final NotificationListener notificationListener) {
		ArrayList<NotificationListener> listeners = notificationListeners
				.get(notificationName);
		if (null != listeners) {
			listeners.remove(notificationListener);
		}
	}

	/**
	 * 
	 * Any object may call this to have the supplied notification be sent to all
	 * listeners registered to receive notifications with the given notification
	 * name (notification name is part of the Notification itself)
	 * 
	 * @param notification the notification to be sent to the listeners
	 * @param asyncronous decides if the call should return immediately (true), or block until all listeners has received the notification (false). 
	 */

	public synchronized void sendNotification(final Notification notification,
			final boolean asyncronous) {
		ArrayList<NotificationListener> listeners = notificationListeners
				.get(notification.getNotificationName());
		if (null != listeners) {
			for (final NotificationListener l : listeners) {
				if (asyncronous) {
					asyncNotificationExecutor.execute(new Runnable() {
						@Override
						public void run() {
							l.notify(notification);
						}
					});

				} else {
					l.notify(notification);
				}
			}
		}
	}

	/**
	 * 
	 * Returns the number of listeners registered to receive notifications of the given notification name
	 * 
	 * @param notificationName the desired notification name
	 * @return the numbers of listeners currently registered to receive notifications of the given notification name
	 */
	public synchronized int nrOfRegisteredListeners(
			final String notificationName) {
		ArrayList<NotificationListener> listeners = notificationListeners
				.get(notificationName);
		int result = 0;
		if (null != listeners) {
			result = listeners.size();
		}
		return result;
	}

}
