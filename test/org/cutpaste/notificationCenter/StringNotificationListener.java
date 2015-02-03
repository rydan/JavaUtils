package org.cutpaste.notificationCenter;

public class StringNotificationListener implements NotificationListener
{

    public Notification notification;

    @Override
    public void notify(final Notification notification)
    {
        this.notification = notification;
    }

}
