package com.diyo.apointmentbookingsystem.Service;

import com.diyo.apointmentbookingsystem.Entity.Appointments;
import com.diyo.apointmentbookingsystem.Entity.Reviews;
import com.diyo.apointmentbookingsystem.Repository.ReviewsRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.Optional;

@Service
public class ReviewsService {
    private final ReviewsRepo reviewsRepo;

    public ReviewsService(ReviewsRepo reviewsRepo) {
        this.reviewsRepo = reviewsRepo;
    }

    public Reviews createReviews(Reviews reviews) {
        return reviewsRepo.save(reviews);
    }


    public List<Reviews> getAllReviews() {
        return reviewsRepo.findAll();
    }


    public ResponseEntity<String> likeReview(@PathVariable Long id) {
        Optional<Reviews> optionalReview = reviewsRepo.findById(id);
        if (optionalReview.isPresent()) {
            Reviews review = optionalReview.get();
            review.setLikeCount(review.getLikeCount() + 1);
            reviewsRepo.save(review);
            return ResponseEntity.ok("Like saved");
        } else {
            return ResponseEntity.notFound().build();
        }
    }


    public Long getReviewCount() {
        return reviewsRepo.count();
    }
}
