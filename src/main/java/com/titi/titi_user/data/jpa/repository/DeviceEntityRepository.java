package com.titi.titi_user.data.jpa.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.titi.titi_user.data.jpa.entity.DeviceEntity;

public interface DeviceEntityRepository extends JpaRepository<DeviceEntity, UUID> {

}
