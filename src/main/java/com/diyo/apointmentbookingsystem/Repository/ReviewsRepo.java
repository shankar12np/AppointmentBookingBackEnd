package com.diyo.apointmentbookingsystem.Repository;

import com.diyo.apointmentbookingsystem.Entity.Reviews;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReviewsRepo extends JpaRepository<Reviews, Long> {
}
