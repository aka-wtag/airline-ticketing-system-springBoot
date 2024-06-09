package com.ats.repository;

import com.ats.model.booking.Booking;
import com.ats.model.user.Passenger;
import com.ats.model.user.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {
    User findByUserEmail(String username);

    Page<User> findAllByOrderByUserIdAsc(Pageable pageable);
}
