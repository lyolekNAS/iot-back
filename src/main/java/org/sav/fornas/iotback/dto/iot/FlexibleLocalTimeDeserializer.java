package org.sav.fornas.iotback.dto.iot;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

import java.io.IOException;
import java.time.LocalTime;

public class FlexibleLocalTimeDeserializer extends JsonDeserializer<LocalTime> {

	@Override
	public LocalTime deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
		JsonToken token = p.currentToken();
		if (token == JsonToken.VALUE_STRING) {
			return LocalTime.parse(p.getValueAsString());
		}
		if (token == JsonToken.START_OBJECT) {
			int hour = 0;
			int minute = 0;
			int second = 0;
			int nano = 0;

			while (p.nextToken() != JsonToken.END_OBJECT) {
				String fieldName = p.currentName();
				p.nextToken();
				switch (fieldName) {
					case "hour" -> hour = p.getIntValue();
					case "minute" -> minute = p.getIntValue();
					case "second" -> second = p.getIntValue();
					case "nano" -> nano = p.getIntValue();
					default -> p.skipChildren();
				}
			}

			return LocalTime.of(hour, minute, second, nano);
		}
		if (token == JsonToken.VALUE_NULL) {
			return null;
		}
		throw new IOException("Unsupported LocalTime value: " + token);
	}
}
