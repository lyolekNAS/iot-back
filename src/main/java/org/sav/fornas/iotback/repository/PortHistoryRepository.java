package org.sav.fornas.iotback.repository;

import org.sav.fornas.dto.iot.PortHistoryView;
import org.sav.fornas.iotback.entity.DevicePortsHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface PortHistoryRepository extends JpaRepository<DevicePortsHistory, Integer> {
	@Query("""
        SELECT dph
        FROM DevicePortsHistory dph
            JOIN dph.port dp
            JOIN dp.device d
            JOIN d.place p
            JOIN p.userPlaces up
            WHERE dph.port.id = :portId
                AND DATE(dph.onTime) = :date
                AND up.userId = :userId
    """)
	List<PortHistoryView> findAllByPortIdAndUserAndDate(
			@Param("portId") Integer portId,
			@Param("userId") Long userId,
			@Param("date") LocalDate date
	);
}
