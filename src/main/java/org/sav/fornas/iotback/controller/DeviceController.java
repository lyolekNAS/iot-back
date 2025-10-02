package org.sav.fornas.iotback.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.sav.fornas.dto.iot.DeviceDto;
import org.sav.fornas.iotback.repository.DeviceRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/device")
@RequiredArgsConstructor
@Slf4j
public class DeviceController {

	private final DeviceRepository deviceRepository;

	@GetMapping("/all")
	public List<DeviceDto> getAll(){
		log.debug(">>> getAll()");
		return deviceRepository.findAllDeviceDto();
	}
}
