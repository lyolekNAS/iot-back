package org.sav.fornas.iotback.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.sav.fornas.dto.iot.DeviceView;
import org.sav.fornas.iotback.service.DeviceService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/api/device", produces = "application/json")
@RequiredArgsConstructor
@Slf4j
public class DeviceController {

	private final DeviceService deviceService;

	@GetMapping("/all")
	public List<DeviceView> getAll(){
		log.debug(">>> getAll()");
		return deviceService.getAll();
	}

	@GetMapping("/{id}")
	public DeviceView getById(@PathVariable Integer id){
		log.debug(">>> getById({})", id);
		return deviceService.getById(id);
	}
}
