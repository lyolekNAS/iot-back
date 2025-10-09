package org.sav.fornas.iotback.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.sav.fornas.iotback.entity.Place;
import org.sav.fornas.iotback.service.PlaceService;
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
	@GetMapping("/all")
	public List<Place> getAll(){
		log.debug(">>> getAll()");
		return placeService.getAll();
	}
}
