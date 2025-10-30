package org.sav.fornas.iotback.service;


import lombok.extern.slf4j.Slf4j;
import org.sav.fornas.dto.iot.DeviceCommand;
import org.sav.fornas.iotback.event.PortValueUpdatedEvent;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class PortEventPublisher {

	private final StreamBridge streamBridge;

	public PortEventPublisher(StreamBridge streamBridge) {
		this.streamBridge = streamBridge;
	}

	public void publish(PortValueUpdatedEvent event) {
		DeviceCommand command = new DeviceCommand(event.getDeviceId(), event.getPortId(), event.getNewValue());
		log.debug(">> Sending command to Kafka: {}", command);
		streamBridge.send("portValueOut-out-0", command);
	}
}
