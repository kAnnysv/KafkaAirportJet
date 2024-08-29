package com.it_prom.jet.office.listener.processors;

import com.it_prom.jet.common.bean.Airport;
import com.it_prom.jet.common.bean.Board;
import com.it_prom.jet.common.bean.Route;
import com.it_prom.jet.common.messages.AirPortStateMessage;
import com.it_prom.jet.common.messages.BoardStateMessage;
import com.it_prom.jet.common.procossor.MessageConverter;
import com.it_prom.jet.common.procossor.MessageProcessor;
import com.it_prom.jet.office.provider.AirPortsProvider;
import com.it_prom.jet.office.provider.BoardsProvider;
import com.it_prom.jet.office.service.WaitingRoutesService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Slf4j
@Component("BOARD_STATE")
@RequiredArgsConstructor
public class BoardStateProcessor implements MessageProcessor<BoardStateMessage> {

    private final MessageConverter messageConverter;
    private final WaitingRoutesService waitingRoutesService;
    private final BoardsProvider boardsProvider;
    private final KafkaTemplate<String, String> kafkaTemplate;
    private final AirPortsProvider airPortsProvider;
    @Override
    public void process(String jsonMessage) {
        BoardStateMessage message = messageConverter.extractMessage(jsonMessage, BoardStateMessage.class);
        Board board = message.getBoard();
        Optional<Board> previousOpt = boardsProvider.getBoard(board.getName());
        Airport airport = airPortsProvider.getAirport(board.getLocation());

        boardsProvider.addBoard(board);
        if(previousOpt.isPresent() && board.hasRoute() && !previousOpt.get().hasRoute()){
            Route route = board.getRoute();
            waitingRoutesService.remove(route);
        }
        if(previousOpt.isEmpty() || !board.isBusy() && previousOpt.get().isBusy()){
            airport.addBoard(board.getName());
            kafkaTemplate.sendDefault(messageConverter.toJson(new AirPortStateMessage(airport)));
        }

    }
}
