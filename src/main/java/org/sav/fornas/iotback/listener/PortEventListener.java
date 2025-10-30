package org.sav.fornas.iotback.listener;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.sav.fornas.iotback.event.PortValueUpdatedEvent;
import org.sav.fornas.iotback.service.PortEventPublisher;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Slf4j
@Component
@RequiredArgsConstructor
public class PortEventListener {

	private final PortEventPublisher portEventPublisher;

	@TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
	public void onPortValueUpdated(PortValueUpdatedEvent event) {
		log.debug(">>> After commit: sending Kafka command for port {}", event.getPortId());
		portEventPublisher.publish(event);
	}
}
