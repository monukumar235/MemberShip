package com.firstClub.repository;

import com.firstClub.entity.OrderStats;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OrderStatsRepo extends JpaRepository<OrderStats,Long> {
    Optional<OrderStats> findByUserId(Long userId);
}
