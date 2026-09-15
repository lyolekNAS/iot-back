package org.sav.fornas.iotback.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.sav.fornas.iotback.dto.iot.DeviceView;
import org.sav.fornas.iotback.dto.iot.PortHistoryView;
import org.sav.fornas.iotback.dto.iot.PortScheduleInterval;
import org.sav.fornas.iotback.dto.iot.PortScheduleUpdateRequest;
import org.sav.fornas.iotback.entity.DevicePorts;
import org.sav.fornas.iotback.entity.DevicePortsScheduler;
import org.sav.fornas.iotback.event.PortValueUpdatedEvent;
import org.sav.fornas.iotback.repository.DeviceRepository;
import org.sav.fornas.iotback.repository.PortHistoryRepository;
import org.sav.fornas.iotback.repository.PortRepository;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class DeviceService {
	private final DeviceRepository deviceRepository;
	private final PortRepository portRepository;
	private final PortHistoryRepository portHistoryRepository;
	private final ApplicationEventPublisher eventPublisher;

	public DeviceView getById(Integer id, Long userId){
		return deviceRepository.findProjectedByIdAndUserId(id, userId).orElseThrow(() -> new EntityNotFoundException("Пристрій не знайдено"));
	}

	@Transactional
	public void updatePortValue(int portId, Long userId, Double newValue) {
		DevicePorts port = portRepository.findByPortIdAndUserId(portId, userId)
				.orElseThrow(() -> new EntityNotFoundException("Пристрій не знайдено"));

		port.setValue(newValue);
		portRepository.save(port);

		eventPublisher.publishEvent(new PortValueUpdatedEvent(
				port.getDevice().getId(),
				port.getId(),
				newValue
		));
	}

	@Transactional
	public void updatePortSchedule(Integer portId, Long userId, PortScheduleUpdateRequest request) {
		log.debug(">>>> updatePortSchedule({}, {})", portId, request);
		if (request == null || request.getIntervals() == null) {
			return;
		}

		DevicePorts port = portRepository.findByPortIdAndUserId(portId, userId)
				.orElseThrow(() -> new EntityNotFoundException("Порт не знайдено"));
		log.debug(">>>> Found port: {}", port);
		List<DevicePortsScheduler> currentSchedulers = port.getSchedulers() == null
				? new ArrayList<>()
				: port.getSchedulers();
		Map<Integer, DevicePortsScheduler> schedulerById = currentSchedulers.stream()
				.filter(scheduler -> scheduler.getId() > 0)
				.collect(Collectors.toMap(DevicePortsScheduler::getId, Function.identity()));
		log.debug(">>>> Current schedulers: {}", currentSchedulers);

		Set<Integer> keptSchedulerIds = new HashSet<>();
		for (PortScheduleInterval item : request.getIntervals()) {
			log.debug(">>>> Found interval: {}", item);
			if (item == null) {
				continue;
			}
			if (item.getStartTime() == null || item.getEndTime() == null) {
				throw new IllegalArgumentException("Початок і кінець інтервалу обов'язкові");
			}

			DevicePortsScheduler scheduler = item.getId() != null && item.getId() > 0 && schedulerById.containsKey(item.getId())
					? schedulerById.get(item.getId())
					: new DevicePortsScheduler();

			if (scheduler.getId() == 0) {
				scheduler.setPort(port);
				currentSchedulers.add(scheduler);
			}
			log.debug(">>>> Current scheduler: {}", scheduler);

			scheduler.setStartTime(item.getStartTime());
			scheduler.setEndTime(item.getEndTime());
			scheduler.setEnabled(item.isEnabled());
			log.debug(">>>> Modified scheduler: {}", scheduler);

			if (scheduler.getId() > 0) {
				keptSchedulerIds.add(scheduler.getId());
			}
			log.debug(">>>> Kept scheduler IDs: {}", keptSchedulerIds);
		}

		currentSchedulers.removeIf(scheduler -> scheduler.getId() > 0 && !keptSchedulerIds.contains(scheduler.getId()));
		log.debug(">>>> Final schedulers to save: {}", currentSchedulers);
		port.setSchedulers(currentSchedulers);
		portRepository.save(port);
		log.debug(">>>> Port saved with updated schedulers: {}", port.getSchedulers());
	}

	public List<PortHistoryView> getPortHistory(Integer portId, LocalDate onDate, Long userId){

		Instant dateStart = onDate.atStartOfDay(ZoneId.systemDefault()).toInstant();
		Instant dateFin = onDate.plusDays(1).atStartOfDay(ZoneId.systemDefault()).toInstant();
		return portHistoryRepository.findAllByPortIdAndUserAndDate(portId, userId, dateStart, dateFin);
	}
}
