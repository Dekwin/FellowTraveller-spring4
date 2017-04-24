package com.fellowtraveler.service.map;

import com.fellowtraveler.model.User;
import com.fellowtraveler.model.map.Route;
import com.fellowtraveler.model.map.RoutePoint;
import com.fellowtraveler.model.map.SearchRoute;

import java.util.List;

/**
 * Created by igorkasyanenko on 15.04.17.
 */
public interface PointService {
    void setSpatialLayerName(String spatialLayerName);
    RoutePoint savePoint(RoutePoint point);
    List<RoutePoint> findClosest(double lon, double lat, double radius);
    List<Route> findSuitableRoutes(SearchRoute searchRoute);
    Long createRoute(Route route);
    List<Route> findAllRoutes(Long offset,Long limit);
    List<Route> findRoutesByOwnerId(long ownerId, Long offset,Long limit);
    Route findRouteById(long id);

    void deleteRouteById(Long id, User owner);
    void unsubscribe(Long collectionDataId,Long userId);
    void subscribe(Long collectionDataId,Long userId);
    List<Route> findRoutesBySubscriberId(Long subscriberId, Long offset,Long limit);

}
