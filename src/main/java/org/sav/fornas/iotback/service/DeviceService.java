package org.sav.fornas.iotback.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.sav.fornas.dto.iot.DeviceDto;
import org.sav.fornas.iotback.entity.Device;
import org.sav.fornas.iotback.repository.DeviceRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class DeviceService {
	private final DeviceRepository deviceRepository;


	public List<DeviceDto> getAll() {
		return deviceRepository.findAllDeviceDto();
	}
}
