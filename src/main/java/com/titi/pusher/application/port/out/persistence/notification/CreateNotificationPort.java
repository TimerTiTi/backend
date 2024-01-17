package com.titi.pusher.application.port.out.persistence.notification;

import com.titi.pusher.domain.notification.Notification;

public interface CreateNotificationPort {

	void create(Notification notification);

}
