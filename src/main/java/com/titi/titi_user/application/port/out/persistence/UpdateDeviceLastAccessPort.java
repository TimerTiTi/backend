package com.titi.titi_user.application.port.out.persistence;

import com.titi.titi_user.domain.device.Device;

public interface UpdateDeviceLastAccessPort {

	Device invoke(Device device);

}
