package org.sav.fornas.iotback.service.schedule;

import lombok.RequiredArgsConstructor;
import org.sav.fornas.iotback.repository.PortHistoryRepository;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class HistoryAggregationService {

	private final PortHistoryRepository repository;

	// щодня о 01:00 ночі
	@Scheduled(cron = "0 0 1 * * *")
	public void aggregateYesterday() {
		LocalDate yesterday = LocalDate.now().minusDays(1);
		LocalDateTime start = yesterday.atStartOfDay();
		LocalDateTime end = yesterday.plusDays(1).atStartOfDay();
		repository.aggregatePortHistory(start, end);
	}
}
