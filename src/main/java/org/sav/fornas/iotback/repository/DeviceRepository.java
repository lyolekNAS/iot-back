package org.sav.fornas.iotback.repository;

import org.sav.fornas.dto.iot.DeviceView;
import org.sav.fornas.iotback.entity.Device;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface DeviceRepository extends JpaRepository<Device, Integer> {

	@Query("""
        SELECT d
            FROM Device d
            JOIN d.place p
            JOIN UserPlace up ON up.place = p
            WHERE d.id = :deviceId AND up.userId = :userId
    """)
	Optional<DeviceView> findProjectedByIdAndUserId(
			@Param("deviceId") Integer deviceId,
			@Param("userId") Long userId
	);
}
