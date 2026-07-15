package org.sav.fornas.iotback.dto.iot;

import java.time.LocalDateTime;

public interface DeviceShortView {
	int getId();
	String getName();
	LocalDateTime getLastUpdated();
}
