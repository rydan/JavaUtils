package org.cutpaste.notificationCenter;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.Date;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class NotificationCenterTest
{

    public final String NOTIFICATION_NAME_ONE = "NotificationName1";
    public final String NOTIFICATION_NAME_TWO = "NotificationName2";
    private final String       NOTIFICATION_STRING   = "This is a notification";
    private NotificationCenter notificationCenter;

    @Before
    public void setUp()
    {
        notificationCenter = NotificationCenter.getNotificationCenter();
    }

    @After
    public void tearDown()
    {
        NotificationCenter.stop();
    }

    @Test
    public void testGetNotificationCenter()
    {
        assertNotNull(notificationCenter);
        final NotificationCenter notificationCenter2 = NotificationCenter.getNotificationCenter();
        assertSame(notificationCenter, notificationCenter2);
    }

    @Test
    public void testAddNotificationListener()
    {
        assertEquals(0, notificationCenter.nrOfRegisteredListeners(NOTIFICATION_NAME_ONE));
        notificationCenter.addNotificationListener(NOTIFICATION_NAME_ONE, new StringNotificationListener());
        assertEquals(1, notificationCenter.nrOfRegisteredListeners(NOTIFICATION_NAME_ONE));
        assertEquals(0, notificationCenter.nrOfRegisteredListeners(NOTIFICATION_NAME_TWO));
    }

    @Test
    public void testAddSameListenerTwice()
    {
        final StringNotificationListener notificationListener = new StringNotificationListener();
        notificationCenter.addNotificationListener(NOTIFICATION_NAME_ONE, notificationListener);
        notificationCenter.addNotificationListener(NOTIFICATION_NAME_ONE, notificationListener);
        assertEquals(1, notificationCenter.nrOfRegisteredListeners(NOTIFICATION_NAME_ONE));
    }

    @Test
    public void testRemoveListener()
    {
        final StringNotificationListener notificationListener = new StringNotificationListener();
        notificationCenter.addNotificationListener(NOTIFICATION_NAME_ONE, notificationListener);
        assertEquals(1, notificationCenter.nrOfRegisteredListeners(NOTIFICATION_NAME_ONE));
        notificationCenter.removeNotificationListener(NOTIFICATION_NAME_ONE, notificationListener);
        assertEquals(0, notificationCenter.nrOfRegisteredListeners(NOTIFICATION_NAME_ONE));
    }

    @Test
    public void testNotify()
    {
        final StringNotificationListener notificationListener1 = new StringNotificationListener();
        final StringNotificationListener notificationListener2 = new StringNotificationListener();
        notificationCenter.addNotificationListener(NOTIFICATION_NAME_ONE, notificationListener1);
        notificationCenter.addNotificationListener(NOTIFICATION_NAME_ONE, notificationListener2);
        notificationCenter.sendNotification(new Notification(NOTIFICATION_NAME_ONE, NOTIFICATION_STRING), false);
        assertEquals(NOTIFICATION_STRING, notificationListener1.notification.getNotification());
        assertEquals(NOTIFICATION_STRING, notificationListener1.notification.getNotification());
        assertEquals(NOTIFICATION_NAME_ONE, notificationListener1.notification.getNotificationName());
    }

    @Test
    public void testNotifyAsync()
    {
        final StringNotificationListener notificationListener1 = new StringNotificationListener();
        notificationCenter.addNotificationListener(NOTIFICATION_NAME_ONE, notificationListener1);
        final long time = new Date().getTime();
        notificationCenter.sendNotification(new Notification(NOTIFICATION_NAME_ONE, NOTIFICATION_STRING), true);
        while (notificationListener1.notification == null)
        {
            if (new Date().getTime() > time + 1000)
            {
                fail();
            }
            sleep(10);
        }
        assertEquals(NOTIFICATION_STRING, notificationListener1.notification.getNotification());
    }

    @Test
    public void testMultipleAsyncs()
    {
        final SlowNotifyer faster = new SlowNotifyer(10);
        final SlowNotifyer slower = new SlowNotifyer(1000);
        notificationCenter.addNotificationListener(NOTIFICATION_NAME_ONE, faster);
        notificationCenter.addNotificationListener(NOTIFICATION_NAME_TWO, slower);
        final long time = new Date().getTime();
        notificationCenter.sendNotification(new Notification(NOTIFICATION_NAME_TWO, NOTIFICATION_STRING), true);
        notificationCenter.sendNotification(new Notification(NOTIFICATION_NAME_ONE, NOTIFICATION_STRING), true);
        while (!(faster.done && slower.done))
        {
            if (new Date().getTime() > time + 5000)
            {
                fail();
            }
            sleep(10);
        }
        assertTrue(faster.doneTime < slower.doneTime);
    }

    private void sleep(final long time)
    {
        try
        {
            Thread.sleep(time);
        }
        catch (final InterruptedException e)
        {
            e.printStackTrace();
        }
    }

    private class SlowNotifyer implements NotificationListener
    {

        private final long sleepTime;
        public boolean     done = false;
        public long        doneTime;

        public SlowNotifyer(final long sleepTime)
        {
            this.sleepTime = sleepTime;
        }

        @SuppressWarnings("synthetic-access")
		@Override
        public void notify(final Notification notification)
        {
            done = false;
            sleep(sleepTime);
            done = true;
            doneTime = new Date().getTime();
        }

    }

}
