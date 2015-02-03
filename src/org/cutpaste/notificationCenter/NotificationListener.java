package org.cutpaste.notificationCenter;

/**
 * 
 * The interface all classes wishing to register with the NotificationCenter as listeners must implement
 * 
 * @author rydan
 *
 */

public interface NotificationListener
{
	
	/**
	 * 
	 * This method will be called by the NotificationCenter whenever an event occurs that the implementing class has registered to listen for 
	 * 
	 * @param notification the Notification object that triggered the listener, containing notification type, data and original sender.
	 */
	
    public void notify(Notification notification);
}
