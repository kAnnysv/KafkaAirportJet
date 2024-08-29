package com.it_prom.jet.office.controlers;

import com.it_prom.jet.common.bean.Route;
import com.it_prom.jet.office.service.PathService;
import com.it_prom.jet.office.service.WaitingRoutesService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/roues")
public class RouteRest {

    private final PathService pathService;
    private final WaitingRoutesService waitingRoutesService;
    @PostMapping(path = "route")
    public void addRoute(@RequestBody List<String> routeLocations){
        Route route = pathService.convertLocationToRoute(routeLocations);
        waitingRoutesService.add(route);
    }
}
