package org.sav.fornas.iotback.repository;

import org.sav.fornas.iotback.entity.DevicePorts;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface PortRepository extends JpaRepository<DevicePorts, Integer> {

	@Query("""
        SELECT dp
        FROM DevicePorts dp
            JOIN dp.device d
            JOIN d.place p
            JOIN p.userPlaces up
        WHERE dp.id = :portId
          AND up.userId = :userId
    """)
	Optional<DevicePorts> findByPortIdAndUserId(
			@Param("portId") int portId,
			@Param("userId") Long userId
	);
}
