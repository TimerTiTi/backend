package com.titi.titi_user.adapter.out.persistence;

import java.util.UUID;

import org.springframework.stereotype.Component;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

import com.titi.titi_user.application.port.out.persistence.UpdateDeviceLastAccessPort;
import com.titi.titi_user.data.jpa.entity.DeviceEntity;
import com.titi.titi_user.data.jpa.entity.mapper.EntityMapper;
import com.titi.titi_user.data.jpa.repository.DeviceEntityRepository;
import com.titi.titi_user.domain.device.Device;

@Component
@RequiredArgsConstructor
class UpdateDeviceLastAccessPortAdapter implements UpdateDeviceLastAccessPort {

	private final DeviceEntityRepository deviceEntityRepository;

	@Override
	@Transactional
	public Device invoke(Device device) {
		if (device.deviceId() == null) {
			return EntityMapper.INSTANCE.toDomain(this.deviceEntityRepository.save(EntityMapper.INSTANCE.toEntity(device)));
		}
		final DeviceEntity deviceEntity = this.deviceEntityRepository.findById(UUID.fromString(device.deviceId()))
			.orElseGet(() -> this.deviceEntityRepository.save(EntityMapper.INSTANCE.toEntity(device)));
		deviceEntity.updateLastAccessedAt(device.lastAccessedAt());
		return EntityMapper.INSTANCE.toDomain(deviceEntity);
	}

}
