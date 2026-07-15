package org.sav.fornas.iotback.dto.iot;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DeviceCommand {
	private int deviceId;
	private int portId;
	private double value;
}

