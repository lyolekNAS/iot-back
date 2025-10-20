package org.sav.fornas.iotback.repository;

import org.sav.fornas.dto.iot.PortHistoryView;
import org.sav.fornas.iotback.entity.DevicePortsHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
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
                AND dph.onTime >= :dateStart
                AND dph.onTime < :dateFin
                AND up.userId = :userId
            ORDER BY dph.onTime
    """)
	List<PortHistoryView> findAllByPortIdAndUserAndDate(
			@Param("portId") Integer portId,
			@Param("userId") Long userId,
			@Param("dateStart") LocalDateTime dateStart,
			@Param("dateFin") LocalDateTime dateFin
	);

	@Modifying
	@Transactional
	@Query(value = "CALL aggregate_port_history(:startDate, :endDate)", nativeQuery = true)
	void aggregatePortHistory(
			@Param("startDate") LocalDateTime startDate,
			@Param("endDate") LocalDateTime endDate
	);
}
