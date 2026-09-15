package org.sav.fornas.iotback.dto.iot;

import java.util.List;

public interface PortView {
	int getId();
	String getName();
	String getGpio();
	Double getValue();
	String getIoType();
	List<PortSchedulerView> getSchedulers();
}