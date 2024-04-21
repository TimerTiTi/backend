package com.titi.titi_user.adapter.out.persistence;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.BDDMockito.*;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.titi.titi_user.data.jpa.entity.DeviceEntity;
import com.titi.titi_user.data.jpa.repository.DeviceEntityRepository;
import com.titi.titi_user.domain.device.Device;
import com.titi.titi_user.domain.member.Member;

@ExtendWith(MockitoExtension.class)
class UpdateDeviceLastAccessPortAdapterTest {

	@Mock
	private DeviceEntityRepository deviceEntityRepository;

	@InjectMocks
	private UpdateDeviceLastAccessPortAdapter updateDeviceLastAccessPortAdapter;

	@Test
	void whenDeviceIdIsNullThenCreateDeviceEntity() {
		// given
		final DeviceEntity mockDeviceEntity = mock(DeviceEntity.class);
		final LocalDateTime now = LocalDateTime.now();
		final UUID uuid = UUID.randomUUID();
		given(mockDeviceEntity.getUuid()).willReturn(uuid);
		given(mockDeviceEntity.getLastAccessedAt()).willReturn(now);
		given(deviceEntityRepository.save(any(DeviceEntity.class))).willReturn(mockDeviceEntity);

		// when
		final Device device = updateDeviceLastAccessPortAdapter.invoke(
			Device.builder()
				.deviceId(null)
				.member(mock(Member.class))
				.lastAccessedAt(now)
				.build()
		);

		// then
		assertThat(device).isNotNull();
		assertThat(device.deviceId()).isEqualTo(uuid.toString());
		assertThat(device.lastAccessedAt()).isEqualTo(now);
		verify(deviceEntityRepository, never()).findById(any(UUID.class));
		verify(deviceEntityRepository, times(1)).save(any(DeviceEntity.class));
	}

	@Test
	void whenDeviceIdIsNotInDbThenCreateDeviceEntity() {
		// given
		final DeviceEntity mockDeviceEntity = mock(DeviceEntity.class);
		final LocalDateTime now = LocalDateTime.now();
		final UUID uuid = UUID.randomUUID();
		given(mockDeviceEntity.getUuid()).willReturn(uuid);
		given(mockDeviceEntity.getLastAccessedAt()).willReturn(now);
		given(deviceEntityRepository.findById(any(UUID.class))).willReturn(Optional.empty());
		given(deviceEntityRepository.save(any(DeviceEntity.class))).willReturn(mockDeviceEntity);

		// when
		final Device device = updateDeviceLastAccessPortAdapter.invoke(
			Device.builder()
				.deviceId(uuid.toString())
				.member(mock(Member.class))
				.lastAccessedAt(now)
				.build()
		);

		// then
		assertThat(device).isNotNull();
		assertThat(device.deviceId()).isEqualTo(uuid.toString());
		assertThat(device.lastAccessedAt()).isEqualTo(now);
		verify(deviceEntityRepository, times(1)).save(any(DeviceEntity.class));
		verify(deviceEntityRepository, times(1)).findById(any(UUID.class));
	}

	@Test
	void whenDeviceIdIsInDbThenUpdateLastAccessedAt() {
		// given
		final DeviceEntity mockDeviceEntity = mock(DeviceEntity.class);
		final UUID uuid = UUID.randomUUID();
		final LocalDateTime now = LocalDateTime.now();
		given(mockDeviceEntity.getUuid()).willReturn(uuid);
		given(mockDeviceEntity.getLastAccessedAt()).willReturn(now);
		given(deviceEntityRepository.findById(any(UUID.class))).willReturn(Optional.of(mockDeviceEntity));

		// when
		final Device device = updateDeviceLastAccessPortAdapter.invoke(
			Device.builder()
				.deviceId(uuid.toString())
				.member(mock(Member.class))
				.lastAccessedAt(now)
				.build()
		);

		// then
		assertThat(device).isNotNull();
		assertThat(device.deviceId()).isEqualTo(uuid.toString());
		assertThat(device.lastAccessedAt()).isEqualTo(now);
		verify(deviceEntityRepository, never()).save(any(DeviceEntity.class));
		verify(deviceEntityRepository, times(1)).findById(any(UUID.class));
	}

}