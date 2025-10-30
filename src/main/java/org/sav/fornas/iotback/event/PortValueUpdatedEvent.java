package org.sav.fornas.iotback.event;

import lombok.Value;

@Value
public class PortValueUpdatedEvent {
	int deviceId;
	int portId;
	double newValue;
}