package org.sav.fornas.iotback.service.schedule;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.sav.fornas.iotback.repository.PortHistoryRepository;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class HistoryAggregationService {

	private final PortHistoryRepository repository;

	@Scheduled(cron = "0 5 0 * * *")
	public void aggregateYesterday() {
		log.debug(">>> started");
		LocalDate yesterday = LocalDate.now().minusDays(1);


		Instant dateStart = yesterday.atStartOfDay(ZoneId.systemDefault()).toInstant();
		Instant dateFin = yesterday.plusDays(1).atStartOfDay(ZoneId.systemDefault()).toInstant();

		log.debug(">>> start: {} end: {}", dateStart, dateFin);
		repository.aggregatePortHistory(dateStart, dateFin);
		log.debug(">>> finished");
	}
}
