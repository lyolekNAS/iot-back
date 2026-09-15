package org.sav.fornas.iotback.dto.iot;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
public class PortScheduleUpdateRequest {
	private List<PortScheduleInterval> intervals;
}
