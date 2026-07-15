package org.sav.fornas.iotback.dto.iot;

import java.util.List;

public interface PlaceView {
	int getId();
	String getName();
	List<DeviceShortView> getDevices();
}
