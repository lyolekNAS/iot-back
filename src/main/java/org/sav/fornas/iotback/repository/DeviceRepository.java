package org.sav.fornas.iotback.repository;

import org.sav.fornas.dto.iot.DeviceView;
import org.sav.fornas.iotback.entity.Device;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface DeviceRepository extends JpaRepository<Device, Integer> {

	public List<DeviceView> findAllProjectedBy();

	public Optional<DeviceView> findProjectedById(Integer id);
}
