package org.sav.fornas.iotback.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.sav.fornas.dto.iot.DeviceView;
import org.sav.fornas.dto.iot.PortHistoryView;
import org.sav.fornas.iotback.service.DeviceService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping(value = "/api/device", produces = "application/json")
@RequiredArgsConstructor
@Slf4j
public class DeviceController {

	private final DeviceService deviceService;
	private static final String CLAIM_USER_ID = "userId";

	@GetMapping("/{id}")
	public DeviceView getById(@PathVariable Integer id, @AuthenticationPrincipal Jwt jwt){
		log.debug(">>> getById({}, {})", id, jwt.getClaim(CLAIM_USER_ID).toString());
		return deviceService.getById(id, jwt.getClaim(CLAIM_USER_ID));
	}

	@PostMapping("/port/{id}/value/{value}")
	public boolean updatePort(@PathVariable Integer id, @PathVariable Double value, @AuthenticationPrincipal Jwt jwt){
		log.debug(">>> updatePort({}, {})", id, value);
		deviceService.updatePortValue(id, jwt.getClaim(CLAIM_USER_ID), value);
		return true;
	}

	@GetMapping("/port/{id}/history/{onDate}")
	public List<PortHistoryView> getPortHistoty(@PathVariable Integer id, @PathVariable LocalDate onDate, @AuthenticationPrincipal Jwt jwt){
		log.debug(">>> getPortHistoty({}, {})", id, onDate);
		return deviceService.getPortHistory(id, onDate, jwt.getClaim(CLAIM_USER_ID));
	}

}
