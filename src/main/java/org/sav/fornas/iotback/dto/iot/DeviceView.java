package org.sav.fornas.iotback.dto.iot;

import java.time.LocalDateTime;
import java.util.List;

public interface DeviceView {
	int getId();
	String getName();
	LocalDateTime getLastUpdated();
	List<PortView> getDevicePorts();
}
