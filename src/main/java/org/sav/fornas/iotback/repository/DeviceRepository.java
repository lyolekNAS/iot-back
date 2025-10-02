package org.sav.fornas.iotback.repository;

import org.sav.fornas.dto.iot.DeviceDto;
import org.sav.fornas.iotback.entity.Device;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface DeviceRepository extends JpaRepository<Device, Integer> {


	@Query("SELECT new org.sav.fornas.dto.iot.DeviceDto(d.id, d.name, d.lastUpdated, d.username, d.password) FROM Device d")
	public List<DeviceDto> findAllDeviceDto();
}
