package org.sav.fornas.iotback.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.sav.fornas.dto.iot.PlaceView;
import org.sav.fornas.iotback.security.annotation.IsAdmin;
import org.sav.fornas.iotback.service.PlaceService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/api/place", produces = "application/json")
@RequiredArgsConstructor
@Slf4j
public class PlaceController {

	private final PlaceService placeService;
	private static final String CLAIM_USER_ID = "userId";


	@IsAdmin
	@GetMapping("/all")
	public List<PlaceView> getAll(){
		log.debug(">>> getAll()");
		return placeService.getAll();
	}

	@GetMapping("/allByUser")
	public List<PlaceView> getAllByUser(@AuthenticationPrincipal Jwt jwt){
		log.debug(">>>>>> getAllByUser for {}", jwt.getClaim(CLAIM_USER_ID).toString());
		return placeService.getAllByUser(jwt.getClaim(CLAIM_USER_ID));
	}
}
