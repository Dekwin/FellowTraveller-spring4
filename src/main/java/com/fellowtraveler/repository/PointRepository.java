package com.fellowtraveler.repository;

import com.fellowtraveler.model.map.Route;
import com.fellowtraveler.model.map.RoutePoint;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.GraphRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by igorkasyanenko on 26.03.17.
 */
@Repository
public interface PointRepository extends GraphRepository<RoutePoint> {


    String routeForming =

            "WITH route, point, CASE WHEN pointData is NULL THEN NULL ELSE {id: ID(pointData), datetime: pointData.datetime, subscribers: pointData.subscribers} END as pd " +
                    "WITH route, COLLECT({latitude: point.latitude, longitude:point.longitude, collectionData: pd}) as points \n" +
                    "WITH {id: ID(route), owner: route.owner, title:route.title, price: route.price, car: route.car, seats: route.seats, points: points} as result \n" +
                    "return result.id as id, result.owner as owner, result.title as title, result.price as price, result.car as car, result.seats as seats, result.points as points\n";


    String routeFormingOffsetLimit =
            "WITH route, point, CASE WHEN pointData is NULL THEN NULL ELSE {id: ID(pointData), datetime: pointData.datetime, subscribers: pointData.subscribers} END as pd " +
                    "WITH route, COLLECT({latitude: point.latitude, longitude:point.longitude, collectionData: pd}) as points \n" +
                    "WITH {id: ID(route), owner: route.owner, title:route.title, price: route.price, car: route.car, seats: route.seats, points: points} as result SKIP $skip LIMIT $limit \n" +
                    "return result.id as id, result.owner as owner, result.title as title, result.price as price,  result.car as car, result.seats as seats, result.points as points\n";


    @Query("CALL spatial.closest($layerName,{lon:$lon,lat:$lat}, $radius)")
    List<RoutePoint> findClosest(@Param("layerName") String layerName, @Param("lon") double lon, @Param("lat") double lat, @Param("radius") double radius);

    @Query("CALL spatial.closest('geom',{latitude: 6867, longitude: 84}, 3008888)  YIELD node AS point WITH point MATCH(point)-[:NEXT*]->(nextPoints) RETURN point,nextPoints")
    List<RoutePoint> findClosestRoutes(@Param("layerName") String layerName, @Param("lon") double lon, @Param("lat") double lat, @Param("radius") double radius);

    @Query("MATCH (fromPoint:RoutePoint {latitude:$lat,longitude:$lon})-[:NEXT*]->(nextPoints) RETURN fromPoint, nextPoints")
    List<RoutePoint> findRestPath(@Param("lat") double lat, @Param("lon") double lon);


    @Query("MATCH (data:CollectionData) WHERE ID(data) = $dataId SET data.subscribers = FILTER(x IN data.subscribers WHERE x <> $userId)")
    List<RoutePoint> unsubscribeFromPoint(@Param("dataId") Long collectionDataId, @Param("userId") Long userId);


    @Query("MATCH (data:CollectionData) WHERE ID(data) = $dataId WITH data, CASE WHEN $userId IN data.subscribers THEN data.subscribers ELSE data.subscribers + $userId END as subs SET data.subscribers = subs")
    List<RoutePoint> subscribeOnPoint(@Param("dataId") Long collectionDataId, @Param("userId") Long userId);

    @Query("  CALL spatial.closest($layerName,{latitude: $lat, longitude: $lon}, $radius)\n" +
            "    YIELD node AS point MATCH(point)<-[x*]-(r:Route) WITH r as route, COLLECT(point) AS nodes \n" +
            "    WITH route, nodes[0] as uniq\n" +
            "    MATCH(uniq)-[:NEXT*]->(nextPoints) WITH  route,[uniq] + COLLECT(nextPoints) as pointsList \n" +
            "    unwind pointsList as point  OPTIONAL MATCH (point)-[:COLLECTION]->(pointData) \n" +
            routeForming)
    List<Route> findPotentialRoutes(@Param("layerName") String layerName, @Param("lat") double latitude, @Param("lon") double longitude, @Param("radius") double radius);


    @Query("MATCH (r:Route)-[:BEGIN]->(f:RoutePoint)-[:NEXT*]->(n:RoutePoint) " +
            "WITH r, [f] + COLLECT(n) as points " +
            "UNWIND points as p " +
            "MATCH (p)-[:COLLECTION]->(pd:CollectionData) " +
            "WHERE $subscriber IN pd.subscribers WITH DISTINCT r as route " +
            "MATCH (route)-[:BEGIN]->(firstPoint)-[:NEXT*]->(nextPoints) \n" +
            "WITH route, ([firstPoint] + COLLECT(nextPoints)) as pointsList\n" +
            "UNWIND pointsList as point OPTIONAL MATCH (point)-[:COLLECTION]->(pointData) \n" +
            routeForming)
    List<Route> findRoutesBySubscriberId(@Param("subscriber") Long subscriberId);


    @Query("MATCH (r:Route)-[:BEGIN]->(f:RoutePoint)-[:NEXT*]->(n:RoutePoint)-[:COLLECTION]->(pd:CollectionData) WHERE $subscriber IN pd.subscribers WITH DISTINCT r as route " +
            "MATCH (route)-[:BEGIN]->(firstPoint)-[:NEXT*]->(nextPoints) \n" +
            "with route, ([firstPoint] + COLLECT(nextPoints)) as pointsList\n" +
            "UNWIND pointsList as point  MATCH (point)-[:COLLECTION]->(pointData) \n" +
            routeFormingOffsetLimit)
    List<Route> findRoutesBySubscriberId(@Param("subscriber") Long subscriberId, @Param("skip") Long offset, @Param("limit") Long limit);

    @Query("MATCH (route:Route)-[:BEGIN]->(firstPoint)-[:NEXT*]->(nextPoints) \n" +
            "WHERE ID(route) = $id\n" +
            "with route, ([firstPoint] + COLLECT(nextPoints)) as pointsList\n" +
            "UNWIND pointsList as point  OPTIONAL MATCH (point)-[:COLLECTION]->(pointData) \n" +
            routeForming)
    Route findRouteById(@Param("id") Long id);

    @Query("MATCH (route:Route)-[:BEGIN]->(firstPoint)-[:NEXT*]->(nextPoints) \n" +
            "WHERE ID(route) = $id\n" +
            "WITH route, ([firstPoint] + COLLECT(nextPoints)) as pointsList\n" +
            "UNWIND pointsList as point  OPTIONAL MATCH (point)-[:COLLECTION]->(pointData) " +
            "DETACH DELETE route, point, pointData \n")
    Route deleteRouteById(@Param("id") Long id);

    @Query("MATCH (route:Route)-[:BEGIN]->(firstPoint)-[:NEXT*]->(nextPoints) \n" +
            "WHERE ID(route) = $id AND route.owner = $owner \n" +
            "WITH route, ([firstPoint] + COLLECT(nextPoints)) as pointsList\n" +
            "UNWIND pointsList as point  OPTIONAL MATCH (point)-[:COLLECTION]->(pointData) " +
            "DETACH DELETE route, point, pointData \n")
    Route deleteRouteById(@Param("id") Long id, @Param("owner") Long ownerId);

    @Query("MATCH (route:Route)-[:BEGIN]->(firstPoint)-[:NEXT*]->(nextPoints) \n" +
            "WHERE route.owner = $owner \n" +
            "with route, ([firstPoint] + COLLECT(nextPoints)) as pointsList\n" +
            "UNWIND pointsList as point  OPTIONAL MATCH (point)-[:COLLECTION]->(pointData) \n" +
            routeFormingOffsetLimit)
    List<Route> findRoutesByOwnerId(@Param("owner") Long ownerId, @Param("skip") Long offset, @Param("limit") Long limit);


    @Query("MATCH (route:Route)-[:BEGIN]->(firstPoint)-[:NEXT*]->(nextPoints) \n" +
            "WHERE route.owner = $owner \n" +
            "with route, ([firstPoint] + COLLECT(nextPoints)) as pointsList\n" +
            "UNWIND pointsList as point  OPTIONAL MATCH (point)-[:COLLECTION]->(pointData) \n" +
            routeForming)
    List<Route> findRoutesByOwnerId(@Param("owner") Long ownerId);

    @Query("MATCH (route:Route)-[:BEGIN]->(firstPoint)-[:NEXT*]->(nextPoints) \n" +
            "with route, ([firstPoint] + COLLECT(nextPoints)) as pointsList\n" +
            "UNWIND pointsList as point  OPTIONAL MATCH (point)-[:COLLECTION]->(pointData) \n" +
            routeForming)
    List<Route> findAllRoutes();


    @Query("MATCH (route:Route)-[:BEGIN]->(firstPoint)-[:NEXT*]->(nextPoints) \n" +
            "with route, ([firstPoint] + COLLECT(nextPoints)) as pointsList\n" +
            "UNWIND pointsList as point  OPTIONAL MATCH (point)-[:COLLECTION]->(pointData) \n" +
            routeFormingOffsetLimit)
    List<Route> findAllRoutes(@Param("skip") Long offset, @Param("limit") Long limit);


    //'m.*'
    @Query("MATCH (route:Route)-[:BEGIN]->(firstPoint)-[:NEXT*]->(nextPoints) \n" +
            "WHERE route.title =~ $pattern \n" +
            "with route, ([firstPoint] + COLLECT(nextPoints)) as pointsList\n" +
            "UNWIND pointsList as point  OPTIONAL MATCH (point)-[:COLLECTION]->(pointData) \n" +
            routeFormingOffsetLimit)
    List<Route> findAllRoutes(@Param("pattern") String pattern, @Param("skip") Long offset, @Param("limit") Long limit);


    @Query(" WITH $route \n" +
            "AS r  \n" +
            "CREATE (route:Route {owner: r.owner, title: r.title, price: r.price, car: r.car, seats: r.seats }) \n" +
            "WITH r.points as points,route\n" +
            "              UNWIND points AS point   \n" +
            "              CREATE (p:RoutePoint {latitude:point.latitude, longitude:point.longitude })   \n" +
            "              WITH p,point,route\n" +
            "              FOREACH(ignoreMe IN CASE WHEN exists(point.collectionData) THEN [1] ELSE [] END | \n" +
            "            CREATE  (c:CollectionData {subscribers: [],  datetime:point.collectionData.datetime})\n" +
            "             MERGE  (p)-[:COLLECTION]->(c))\n" +
            "              WITH route,COLLECT(p) AS points   \n" +
            "              FOREACH(i in RANGE(0, size(points)-2) |   \n" +
            "               FOREACH(point1 in [points[i]] |   \n" +
            "                 FOREACH(point2 in [points[i+1]] |   \n" +
            "                   MERGE  (point1)-[:NEXT]->(point2))))\n" +
            "                   with route,points,points[0] as firstPoint \n" +
            "                   MERGE  (route)-[:BEGIN]->(firstPoint)\n" +
            "                   with route,points\n" +
            "                   unwind points as p\n" +
            "                   with route,p\n" +
            "                   where (p)-[:COLLECTION]->()\n" +
            "                   CALL spatial.addNode($layerName,p) YIELD node \n" +
            "                   with route,collect(node) as n\n" +
            "                   return ID(route)")
    Long createRoute(@Param("layerName") String layerName, @Param("route") Route route);
}
