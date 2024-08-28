package com.it_prom.jet.office.service;

import com.it_prom.jet.common.bean.Route;
import com.it_prom.jet.common.bean.RoutePath;
import com.it_prom.jet.common.bean.RoutePoint;
import com.it_prom.jet.office.provider.AirPortsProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PathService {
    private final AirPortsProvider airPortsProvider;

    public RoutePath makePath(String from, String to){
        return new RoutePath(airPortsProvider.getRoutePoint(from), airPortsProvider.getRoutePoint(to), 0);
    }


    public Route convertLocationToRoute(List<String> locations) {
        Route route = new Route();
        List<RoutePath> path = new ArrayList<>();
        List<RoutePoint> points = new ArrayList<>();

        locations.forEach(location -> {
            airPortsProvider.getPorts().stream()
                    .filter(airport -> airport.getName().equals(location))
                    .findFirst()
                    .ifPresent(airport -> {
                        RoutePoint point = new RoutePoint(airport);
                        points.add(point);
                    });
        });

        for (int i = 0; i < points.size() - 1; i++) {
            path.add(new RoutePath());

        }

        path.forEach(row -> {
            int ind = path.indexOf(row);
            if (row.getFrom() == null) {
                row.setFrom(points.get(ind));
            }
            if (points.size() > ind + 1) {
                row.setTo(points.get(ind + 1));
            } else {
                row.setTo(points.get(ind));
            }
        });
        route.setPath(path);

        return route;
    }
}
