package com.fellowtraveler.controller.review;

import com.fellowtraveler.model.Car;
import com.fellowtraveler.model.Review;
import com.fellowtraveler.model.User;
import com.fellowtraveler.service.ReviewService;
import com.fellowtraveler.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ExecutionException;

/**
 * Created by igorkasyanenko on 17.06.17.
 */
@RestController
@RequestMapping("/reviews")
public class ReviewController {

    @Autowired
    UserService userService;
    @Autowired
    ReviewService reviewService;

    @PostMapping("")
    public ResponseEntity<Review> createReview(@RequestBody Review newReview) throws ExecutionException, InterruptedException {
        User user = userService.findBySSO(SecurityContextHolder.getContext().getAuthentication().getName());
        User recipient = userService.findById(newReview.getRecipient().getId());
        if (recipient != null && !recipient.getId().equals(user.getId())) {
            newReview.setSender(user);
            newReview.setRecipient(recipient);
            reviewService.saveReview(newReview);
            return new ResponseEntity<Review>(newReview, HttpStatus.OK);
        } else {
            throw new IllegalArgumentException("Wrong recipient");
        }
    }

    @RequestMapping(value = "", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<List<Review>> getReviews(@RequestParam("recipient") Optional<Integer> recipient,
                                           @RequestParam("forDriver") Optional<Boolean> forDriver,
                                           @RequestParam("offset") Optional<Integer> offset,
                                           @RequestParam("limit") Optional<Integer> limit) throws ExecutionException, InterruptedException {
        User user = userService.findBySSO(SecurityContextHolder.getContext().getAuthentication().getName());


        if(recipient.isPresent()) {
            return new ResponseEntity<List<Review>>(reviewService.findAllForRecipient(
                    recipient.get(),
                    forDriver.isPresent() ? forDriver.get().booleanValue() : false,
                    offset.isPresent() ? offset.get() : 0,
                    limit.isPresent() ? limit.get() : 100), HttpStatus.OK);
        }else{
           return new ResponseEntity<List<Review>>(reviewService.findAllForSender(
                   user.getId(),offset.isPresent() ? offset.get() : 0,
                   limit.isPresent() ? limit.get() : 100), HttpStatus.OK);
        }

    }

    @DeleteMapping("/{id}")
    public String deleteCar(@PathVariable(value = "id") Integer id, HttpServletResponse response) throws ExecutionException, InterruptedException {
        User user = userService.findBySSO(SecurityContextHolder.getContext().getAuthentication().getName());
        Review review = reviewService.findById(id);
        if (review != null && review.getSender().getId().equals(user.getId())) {
            reviewService.deleteReviewById(review.getId());
            response.setStatus(200);
            return "";
        } else {
            response.setStatus(500);
            response.setContentType("application/json");
            return "{ \"error\": \"Review not found\" }";
        }
    }

}
