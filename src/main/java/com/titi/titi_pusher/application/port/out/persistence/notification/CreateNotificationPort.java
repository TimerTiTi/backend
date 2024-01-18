package com.titi.titi_pusher.application.port.out.persistence.notification;

import com.titi.titi_pusher.domain.notification.Notification;

public interface CreateNotificationPort {

	void create(Notification notification);

}
