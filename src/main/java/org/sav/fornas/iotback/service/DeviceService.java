package org.sav.fornas.iotback.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.sav.fornas.dto.iot.DeviceView;
import org.sav.fornas.iotback.repository.DeviceRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class DeviceService {
	private final DeviceRepository deviceRepository;

	public DeviceView getById(Integer id, Long userId){
		return deviceRepository.findProjectedByIdAndUserId(id, userId).orElseThrow(() -> new RuntimeException("Пристрій не знайдено"));
	}
}
