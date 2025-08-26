package com.firstClub.repository;

import com.firstClub.constants.PlanPeriod;
import com.firstClub.constants.Tier;
import com.firstClub.entity.PlanPricing;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PlanPricingRepo extends JpaRepository<PlanPricing,Long> {

   List<PlanPricing> findAllByOrderByPeriodAscTierAsc();
   Optional<PlanPricing> findByPeriodAndTier(PlanPeriod period, Tier tier);
}
