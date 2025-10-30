package org.sav.fornas.iotback.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.sav.fornas.dto.iot.DeviceView;
import org.sav.fornas.dto.iot.PortHistoryView;
import org.sav.fornas.iotback.entity.DevicePorts;
import org.sav.fornas.iotback.event.PortValueUpdatedEvent;
import org.sav.fornas.iotback.repository.DeviceRepository;
import org.sav.fornas.iotback.repository.PortHistoryRepository;
import org.sav.fornas.iotback.repository.PortRepository;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

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

	public List<PortHistoryView> getPortHistory(Integer portId, LocalDate onDate, Long userId){
		LocalDateTime dateStart = onDate.atStartOfDay();
		LocalDateTime dateFin = dateStart.plusDays(1);
		return portHistoryRepository.findAllByPortIdAndUserAndDate(portId, userId, dateStart, dateFin);
	}
}
