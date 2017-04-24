package com.fellowtraveler.controller.map;

import com.fellowtraveler.model.User;
import com.fellowtraveler.model.jwtauth.AccountCredentials;
import com.fellowtraveler.model.map.*;
import com.fellowtraveler.service.UserService;
import com.fellowtraveler.service.map.PointService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * Created by igorkasyanenko on 17.04.17.
 */
@RestController
@RequestMapping(path = "/map")
public class MapController {

    @Autowired
    UserService userService;
    @Autowired
    PointService pointService;

    @RequestMapping(value = "/routes", method = RequestMethod.POST)
    public
    @ResponseBody
    ResponseEntity<Route> createRoute(@RequestBody Route route) {
        User user = userService.findBySSO(SecurityContextHolder.getContext().getAuthentication().getName());
        route.setOwner(user.getId().longValue());
        route.setPrice(route.getPrice() == null ? 0 : route.getPrice());
        Long routeId = pointService.createRoute(route);
        route.setId(routeId);
        return new ResponseEntity<>(route, HttpStatus.CREATED);
    }

    @RequestMapping(value = "/routes", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<?> getRoute(@RequestParam("id") Optional<Long> routeId,
                                      @RequestParam("owner") Optional<Long> ownerId,
                                      @RequestParam("offset") Optional<Long> offset,
                                      @RequestParam("limit") Optional<Long> limit) {
        User user = userService.findBySSO(SecurityContextHolder.getContext().getAuthentication().getName());
        if (routeId.isPresent()) {
            return new ResponseEntity<Route>(pointService.findRouteById(routeId.get()), HttpStatus.OK);
        }
        if (ownerId.isPresent()) {
            return new ResponseEntity<List<Route>>(pointService.findRoutesByOwnerId(ownerId.get(),
                    offset.isPresent() ? offset.get() : new Long(0),
                    limit.isPresent() ? limit.get() : new Long(0)),
                    HttpStatus.OK);
        }
        return new ResponseEntity<List<Route>>(pointService.findAllRoutes(
                offset.isPresent() ? offset.get() : new Long(0),
                limit.isPresent() ? limit.get() : new Long(0)),
                HttpStatus.OK);
    }


    @RequestMapping(value = "/routes/search", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<List<Route>> searchRoutes(@RequestParam("latitude1") Optional<Double> latitude1,
                                          @RequestParam("longitude1") Optional<Double> longitude1,
                                          @RequestParam("radius1") Optional<Double> radius1,
                                          @RequestParam("latitude2") Optional<Double> latitude2,
                                          @RequestParam("longitude2") Optional<Double> longitude2,
                                          @RequestParam("radius2") Optional<Double> radius2,
                                          @RequestParam("offset") Optional<Long> offset,
                                          @RequestParam("limit") Optional<Long> limit) {
        User user = userService.findBySSO(SecurityContextHolder.getContext().getAuthentication().getName());

        if (latitude1.isPresent() && longitude1.isPresent() && radius1.isPresent() && latitude2.isPresent() && longitude2.isPresent() && radius2.isPresent()) {

            SearchRoute searchRoute = new SearchRoute(
                    new SearchPoint(latitude1.get(), longitude1.get(), radius1.get()),
                    new SearchPoint(latitude2.get(), longitude2.get(), radius2.get()));

            return new ResponseEntity<>(pointService.findSuitableRoutes(searchRoute),
                    HttpStatus.OK);

        }else {
            throw new IllegalArgumentException();
        }


    }


    @RequestMapping(value = "/routes/{routeId}", method = RequestMethod.DELETE)
    public
    @ResponseBody
    ResponseEntity<?> deleteRoute(@PathVariable("routeId") Long routeId) {
        User user = userService.findBySSO(SecurityContextHolder.getContext().getAuthentication().getName());
        pointService.deleteRouteById(routeId, user);
        return new ResponseEntity<>(HttpStatus.OK);
    }


    @RequestMapping(value = "/subscribes/{collectionDataId}", method = RequestMethod.DELETE)
    public
    @ResponseBody
    ResponseEntity<?> unsubscribeFromPoint(@PathVariable("collectionDataId") Long collectionDataId) {
        User user = userService.findBySSO(SecurityContextHolder.getContext().getAuthentication().getName());
        pointService.unsubscribe(collectionDataId, user.getId().longValue());
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping(value = "/subscribes/{collectionDataId}", method = RequestMethod.PUT)
    public
    @ResponseBody
    ResponseEntity<?> subscribeOnPoint(@PathVariable("collectionDataId") Long collectionDataId) {
        User user = userService.findBySSO(SecurityContextHolder.getContext().getAuthentication().getName());
        pointService.subscribe(collectionDataId, user.getId().longValue());
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping(value = "/subscribes", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<?> findRoutesBySubscriber(
            @RequestParam("offset") Optional<Long> offset,
            @RequestParam("limit") Optional<Long> limit) {
        User user = userService.findBySSO(SecurityContextHolder.getContext().getAuthentication().getName());
        return new ResponseEntity<>(pointService.findRoutesBySubscriberId(user.getId().longValue(),
                offset.isPresent() ? offset.get() : new Long(0),
                limit.isPresent() ? limit.get() : new Long(0)),
                HttpStatus.OK);
    }


}
