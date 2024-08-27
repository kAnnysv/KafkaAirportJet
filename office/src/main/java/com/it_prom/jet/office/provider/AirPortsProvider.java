package com.it_prom.jet.office.provider;

import com.it_prom.jet.common.bean.Airport;
import com.it_prom.jet.common.bean.RoutePoint;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

@Slf4j
@Getter
@Component
@ConfigurationProperties(prefix = "application")
public class AirPortsProvider {
    private final List<Airport> ports = new ArrayList<>();

    public Airport findAirPortRemoveBoard(String boardName){
        AtomicReference<Airport> res = new AtomicReference<>();
        ports.stream().filter(airport -> airport.getBoards().contains(boardName))
                .findFirst()
                .ifPresent(airport -> {
                    airport.removeBoard(boardName);
                    res.set(airport);
                });
        return res.get();
    }

    public Airport getAirport(String airportName){
        return ports.stream()
                .filter(airport -> airport.getName().equals(airportName))
                .findFirst()
                .orElse(new Airport());
    }

    public RoutePoint getRoutePoint(String airPortName){
        return new RoutePoint(getAirport(airPortName));
    }
}
