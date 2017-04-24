package com.fellowtraveler.service.map;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fellowtraveler.model.User;
import com.fellowtraveler.model.map.Route;
import com.fellowtraveler.model.map.SearchRoute;
import com.fellowtraveler.repository.PointRepository;
import com.fellowtraveler.model.map.RoutePoint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by igorkasyanenko on 26.03.17.
 */
@Service("pointService")
public class PointServiceImpl implements PointService {
    @Autowired
    private PointRepository pointRepository;

    private String spatialLayerName = "spatialLayerPoint";

    private final double EARTH_RADIUS = 6371;

    private ObjectMapper objectMapper = new ObjectMapper();

    public String getSpatialLayerName() {
        return spatialLayerName;
    }

    @Override
    public void setSpatialLayerName(String spatialLayerName) {
        this.spatialLayerName = spatialLayerName;
    }

    @Override
    public RoutePoint savePoint(RoutePoint point) {
        return pointRepository.save(point);
    }

    @Override
    public List<RoutePoint> findClosest(double lon, double lat, double radius) {
        return pointRepository.findClosest(spatialLayerName, lon, lat, radius);
    }

    @Override
    public Long createRoute(Route route) {
        return pointRepository.createRoute(spatialLayerName, route);
    }

    @Override
    public void deleteRouteById(Long id, User owner) {
        if (owner != null)
            pointRepository.deleteRouteById(id, owner.getId().longValue());
    }

    @Override
    public void unsubscribe(Long collectionDataId, Long userId) {
        pointRepository.unsubscribeFromPoint(collectionDataId, userId);
    }

    @Override
    public void subscribe(Long collectionDataId, Long userId) {
        pointRepository.subscribeOnPoint(collectionDataId, userId);
    }

    @Override
    public List<Route> findRoutesBySubscriberId(Long subscriberId, Long offset, Long limit) {
        if (offset == null || limit == null || offset == 0 && limit == 0) {
            return pointRepository.findRoutesBySubscriberId(subscriberId);
        } else {
            return pointRepository.findRoutesBySubscriberId(subscriberId, offset, limit);
        }
    }

    @Override
    public List<Route> findAllRoutes(Long offset, Long limit) {
        if (offset == null || limit == null || offset == 0 && limit == 0) {
            return pointRepository.findAllRoutes();
        } else {
            return pointRepository.findAllRoutes(offset, limit);
        }
    }

    @Override
    public List<Route> findRoutesByOwnerId(long ownerId, Long offset, Long limit) {
        if (offset == null || limit == null || offset == 0 && limit == 0) {
            return pointRepository.findRoutesByOwnerId(ownerId);
        } else {
            return pointRepository.findRoutesByOwnerId(ownerId, offset, limit);
        }
    }

    @Override
    public Route findRouteById(long id) {
        return pointRepository.findRouteById(id);
    }


    public double calculateSubroutePrice(Route route) {

        double priceForSubroute = 0;

        if (route.getPoints().size() > 1) {
            RoutePoint prevPoint = route.getPoints().get(0);
            for (int i = 1; i < route.getPoints().size(); i++) {
                RoutePoint currentPoint = route.getPoints().get(i);

                //price for whole
                priceForSubroute += getDistanceFromLatLonInKm(prevPoint.getLatitude(), prevPoint.getLongitude(), currentPoint.getLatitude(), currentPoint.getLongitude()) * route.getPrice();

                prevPoint = currentPoint;
            }
        }

        return priceForSubroute;
    }


    private double getDistanceFromLatLonInKm(double lat1, double lon1, double lat2, double lon2) {
        // Radius of the earth in km
        double dLat = deg2rad(lat2 - lat1);  // deg2rad below
        double dLon = deg2rad(lon2 - lon1);
        double a =
                Math.sin(dLat / 2) * Math.sin(dLat / 2) +
                        Math.cos(deg2rad(lat1)) * Math.cos(deg2rad(lat2)) *
                                Math.sin(dLon / 2) * Math.sin(dLon / 2);
        double c = 2. * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        double d = EARTH_RADIUS * c; // Distance in km
        return d;
    }

    double deg2rad(double deg) {
        return deg * (Math.PI / 180);
    }

    @Override
    public List<Route> findSuitableRoutes(SearchRoute searchRoute) {

        List<Route> potentialRoutes = pointRepository.findPotentialRoutes(
                spatialLayerName,
                searchRoute.getStart().getLatitude(),
                searchRoute.getStart().getLongitude(),
                searchRoute.getStart().getRadius());

        List<Route> suitableSubroutes = new ArrayList<>();

        for (Route route : potentialRoutes) {
            //костыль
            List<RoutePoint> points = objectMapper.convertValue(route.getPoints(), new TypeReference<List<RoutePoint>>() {
            });
            route.setPoints(points);
            //

            if (route.getPoints().size() > 1) {
                RoutePoint prevPoint = route.getPoints().get(0);
                for (int i = 1; i < route.getPoints().size(); i++) {
                    RoutePoint currentPoint = route.getPoints().get(i);
                    boolean isIntersects = intersectsCircle(prevPoint.getLongitude(),
                            prevPoint.getLatitude(),
                            currentPoint.getLongitude(),
                            currentPoint.getLatitude(),
                            searchRoute.getFinish().getLongitude(),
                            searchRoute.getFinish().getLatitude(),
                            searchRoute.getFinish().getRadius());

                    if (isIntersects) {

                        if (route.getPoints().size() > i) {
                            route.getPoints().subList(i + 1, route.getPoints().size()).clear();
                        }
                        route.setPrice((float) calculateSubroutePrice(route));
                        suitableSubroutes.add(route);

                        break;
                    }

                    prevPoint = currentPoint;
                }
            }


        }

        return suitableSubroutes;
    }

    private boolean intersectsCircle(double x1, double y1, double x2, double y2, double xC, double yC, double R) {
        x1 -= xC;
        y1 -= yC;
        x2 -= xC;
        y2 -= yC;

        double dx = x2 - x1;
        double dy = y2 - y1;

        //составляем коэффициенты квадратного уравнения на пересечение прямой и окружности.
        //если на отрезке [0..1] есть отрицательные значения, значит отрезок пересекает окружность
        double a = dx * dx + dy * dy;
        double b = 2. * (x1 * dx + y1 * dy);
        double c = x1 * x1 + y1 * y1 - R * R;

        //а теперь проверяем, есть ли на отрезке [0..1] решения
        if (-b < 0)
            return (c < 0);
        if (-b < (2. * a))
            return ((4. * a * c - b * b) < 0);

        return (a + b + c < 0);
    }
}
