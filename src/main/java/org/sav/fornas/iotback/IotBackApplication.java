package org.sav.fornas.iotback;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@Slf4j
@SpringBootApplication
@EnableScheduling
public class IotBackApplication {

	public static void main(String[] args) {
		SpringApplication.run(IotBackApplication.class, args);
		log.info("-----------AppStarted!------------");
	}

}
