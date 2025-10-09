package org.sav.fornas.iotback.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.sav.fornas.iotback.entity.Place;
import org.sav.fornas.iotback.repository.PlaceRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class PlaceService {
	private final PlaceRepository placeRepository;

	public List<Place> getAll() {
		return placeRepository.findAll();
	}
}
