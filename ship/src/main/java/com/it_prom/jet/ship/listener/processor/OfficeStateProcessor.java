package com.it_prom.jet.ship.listener.processor;

import com.it_prom.jet.common.messages.BoardStateMessage;
import com.it_prom.jet.common.messages.OfficeStateMessage;
import com.it_prom.jet.common.procossor.MessageConverter;
import com.it_prom.jet.common.procossor.MessageProcessor;
import com.it_prom.jet.ship.provider.BoardProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Slf4j
@Component("OFFICE_STATE")
@RequiredArgsConstructor
public class OfficeStateProcessor implements MessageProcessor<OfficeStateMessage> {

    private final MessageConverter messageConverter;
    private final BoardProvider boardProvider;
    private final KafkaTemplate<String, String> kafkaTemplate;
    @Override
    public void process(String jsonMessage) {
        boardProvider.getBoards().forEach(board -> {
            kafkaTemplate.sendDefault(messageConverter.toJson(new BoardStateMessage()));
        });

    }
}
