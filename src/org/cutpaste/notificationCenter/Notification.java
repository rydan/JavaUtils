package org.cutpaste.notificationCenter;

import java.io.Serializable;

/**
 * 
 * Notification represents a single notification of an event, sent via the NotificationCenter
 * 
 * @author rydan
 *
 */


public class Notification implements Serializable
{
    /**
	 * 
	 */
	private static final long	serialVersionUID	= -2468284302017104636L;
	
	private final String                   notificationName;
    private final Serializable notification;
    private transient final Object                   notificationSender;

    /**
     * 
     * Constructor creating an immutable Notification, ready to be sent by the NotificationCenter
     * 
     * @param notificationName The "key" value, describing the notification type
     * @param notification Additional data that might be needed for the notification receiver
     */
    
    public Notification (final String notificationName, final Serializable notification) {
    	this.notificationName = notificationName;
    	this.notification = notification;
    	this.notificationSender = null;
    }
    
    /**
     * 
     * Constructor creating an immutable Notification, ready to be sent by the NotificationCenter
     * 
     * @deprecated: as of 1.2.0 Better to include NotificationSender in the notification itself, if needed...
     * 
     * @param notificationName The "key" value, describing the notification type
     * @param notification Additional data that might be needed for the notification receiver
     * @param notificationSender The object responsible for generating the notification (usually "this"). Note that the sender is transient, since it generally won't make much sense after serialization.
     */

    @Deprecated
    public Notification(final String notificationName, final Serializable notification, final Object notificationSender)
    {
        this.notificationName = notificationName;
        this.notification = notification;
        this.notificationSender = notificationSender;
    }
        

    /**
     * 
     * Getter method for notification name
     * 
     * @return the notification name
     */
    
    public String getNotificationName()
    {
        return notificationName;
    }

    /**
     * 
     * Getter method for the notification data
     * 
     * @return notification data
     */
    
    public Object getNotification()
    {
        return notification;
    }

    /**
     * 
     * Getter method for the notification sender
     * 
     * @deprecated as of 1.2.0.If needed, embed sender in the notification object
     * 
     * @return notification sender
     */
    
    @Deprecated
    public Object getNotificationSender()
    {
        return notificationSender;
    }

}
