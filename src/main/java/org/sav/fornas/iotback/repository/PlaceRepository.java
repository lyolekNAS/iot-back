package org.sav.fornas.iotback.repository;

import org.sav.fornas.dto.iot.PlaceView;
import org.sav.fornas.iotback.entity.Place;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PlaceRepository extends JpaRepository<Place, Integer> {

	List<PlaceView> findAllProjectedBy();
}
