package org.sav.fornas.iotback.repository;

import org.sav.fornas.dto.iot.PlaceView;
import org.sav.fornas.iotback.entity.Place;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PlaceRepository extends JpaRepository<Place, Integer> {

	List<PlaceView> findAllProjectedBy();

	@Query("""
        SELECT DISTINCT p
            FROM Place p
            JOIN UserPlace up ON up.place = p
            WHERE up.userId = :userId
    """)
	List<PlaceView> findUserPlaces(@Param("userId") Long userId);
}
