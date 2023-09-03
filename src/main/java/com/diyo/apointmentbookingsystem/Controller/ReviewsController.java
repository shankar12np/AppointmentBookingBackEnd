package com.diyo.apointmentbookingsystem.Controller;

import com.diyo.apointmentbookingsystem.Entity.Reviews;
import com.diyo.apointmentbookingsystem.Service.ReviewsService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin("http://localhost:4200")
@RequestMapping("/reviews")
public class ReviewsController {

    private final ReviewsService reviewsService;

    public ReviewsController(ReviewsService reviewsService) {
        this.reviewsService = reviewsService;
    }
    @PostMapping
    public ResponseEntity<String>createReviews(@RequestBody Reviews reviews){
        reviewsService.createReviews(reviews);
        return ResponseEntity.ok("Reviews Created");
    }
@GetMapping("/get-reviews")
    public ResponseEntity<List<Reviews>> getAllReviews(){
        List<Reviews> allReviews = reviewsService.getAllReviews();
        return ResponseEntity.ok(allReviews);
    }
@PostMapping("/{id}/like")
    public ResponseEntity<String> likeReview(@PathVariable Long id ){
        reviewsService.likeReview(id);
        return ResponseEntity.ok("Like saved");
    }
    @GetMapping("/count")
    public ResponseEntity<Long> getReviewCount() {
        Long count = reviewsService.getReviewCount();
        return ResponseEntity.ok(count);
    }


}
