package org.sav.fornas.iotback.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.sav.fornas.dto.iot.DeviceCommand;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class DeviceCommandProducer {

	private final KafkaTemplate<String, DeviceCommand> kafkaTemplate;

	public void sendUpdatePortCommand(int deviceId, int portId, double value) {
		var command = new DeviceCommand(deviceId, portId, value);
		log.debug(">> Sending command to Kafka: {}", command);
		kafkaTemplate.send("device-commands", String.valueOf(deviceId), command);
	}
}

