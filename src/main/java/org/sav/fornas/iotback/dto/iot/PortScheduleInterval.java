package org.sav.fornas.iotback.dto.iot;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalTime;

@Getter
@Setter
@ToString
public class PortScheduleInterval {
	private Integer id;
	@JsonDeserialize(using = FlexibleLocalTimeDeserializer.class)
	private LocalTime startTime;
	@JsonDeserialize(using = FlexibleLocalTimeDeserializer.class)
	private LocalTime endTime;
	private boolean enabled;
}
