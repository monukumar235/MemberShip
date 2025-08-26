package com.firstClub.controller;

import com.firstClub.dto.*;
import com.firstClub.service.PlanService;
import com.firstClub.service.SubcriptionService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/membership")
public class MemberShipController {

    @Autowired
    private PlanService planService;

    @Autowired
    private SubcriptionService subcriptionService;

    @GetMapping("/plans")
    public ResponseEntity<List<PlanDto>> plans(){
        List<PlanDto> plans = planService.listPlans();
        return ResponseEntity.ok(plans);
    }

    @PostMapping("/subscribe")
    public ResponseEntity<SubscriptionView> subscribe(@RequestBody @Valid SubscriptionRequest request){
        SubscriptionView subscriptionView = subcriptionService.subscribe(request);
        return  ResponseEntity.ok(subscriptionView);
    }

    @PostMapping("/subscribe/{subscriptionId}/upgrade")
    public ResponseEntity<SubscriptionView> upgrade(@PathVariable Long subscriptionId, @RequestBody @Valid UpdateTierRequest request){
        SubscriptionView upgrade = subcriptionService.upgrade(subscriptionId, request);
        return ResponseEntity.ok(upgrade);
    }

    @PostMapping("/subscribe/{subscriptionId}/downgrade")
    public ResponseEntity<SubscriptionView> downgrade(@PathVariable Long subscriptionId,@RequestBody @Valid UpdateTierRequest request){
        return ResponseEntity.ok(subcriptionService.downgrade(subscriptionId,request));
    }

    @PostMapping("/subscribe/{subscriptionId}/cancel")
    public ResponseEntity<SubscriptionView> cancel(@PathVariable Long subscriptionId){
        return ResponseEntity.ok(subcriptionService.cancel(subscriptionId));
    }

    @GetMapping("/users/{userId}/current")
    public ResponseEntity<MemberShipSnapShot> current(@PathVariable Long userId){
        return ResponseEntity.ok(subcriptionService.activeSubs(userId));
    }

    @PostMapping("/uses/{userId}/stats")
    public ResponseEntity<Void> insertStats(@PathVariable Long userId,
                                            @RequestParam(defaultValue = "0") int monthlyOrders,
                                            @RequestParam(defaultValue = "5000")BigDecimal monthlyValue,
                                            @RequestParam(required = false) String cohort){
        subcriptionService.insertStats(userId,monthlyOrders,monthlyValue,cohort);
        return ResponseEntity.ok(null);
    }

    @GetMapping("/users/{userId}/recommended-tier")
    public ResponseEntity<Map<String,Object>> recommended(@PathVariable Long userId){
        Map<String, Object> recommended = subcriptionService.recommended(userId);
        return ResponseEntity.ok(recommended);
    }
}
