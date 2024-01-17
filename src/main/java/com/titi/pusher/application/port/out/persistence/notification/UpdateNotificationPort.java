package com.titi.pusher.application.port.out.persistence.notification;

import com.titi.pusher.domain.notification.Notification;

public interface UpdateNotificationPort {

	void update(Notification notification);

}
