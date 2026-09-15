package org.sav.fornas.iotback.dto.iot;

import java.time.LocalTime;

public interface PortSchedulerView {
	int getId();
	LocalTime getStartTime();
	LocalTime getEndTime();
	Boolean getEnabled();
}
