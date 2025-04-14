package com.fawry.order_api.sevices.impl;

import java.time.LocalDate;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

import com.fawry.order_api.entity.Order;
import com.fawry.order_api.repos.OrderRepository;
import com.fawry.order_api.sevices.CouponService;
import com.fawry.order_api.sevices.OrderService;
import com.fawry.order_api.sevices.StoreService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;
    private final CouponService couponService;
    private final StoreService storeService;
    @Override
    public String saveOrder(Order order) {
        log.info("Creating order : "+order);
        try {
            //Coupon Validation (if applicable)
            if (order.getCouponCode()==null) {
                validateCoupon(order.getCouponCode());
                log.info("Coupon is valid");
            }

            //Product Stock Validation
            validateAvailability(order.getProductCode());
            log.info(order.getProductCode()+" is available in the stock");

            //Price Calculation with Discount (if applicable)
            Double finalPrice=order.getPrice();
            if (order.getCouponCode()!=null) {
                finalPrice=couponService.calcPriceWithCoupon(order.getPrice(),order.getCouponCode());
                log.info("Price after applying : "+order.getCouponCode());
            }
            //Financial Transactions
            withDrawFromCustomer(finalPrice,"WITHDRAW");
            depositForMerchant(finalPrice);

            //Stock Consumption
            ResponseEntity<String>storeResponse=storeService.consume(order.getProductCode());
            log.info("Stock consumed successfully and the response is : "+storeResponse);

            //Order Entity Creation & Persistence
            order.setCreationDate(LocalDate.now());
            orderRepository.save(order);

            //Notification Emails
            
            //Success Response
            return "success";
        } catch (HttpClientErrorException e) {
            log.error("Error occurred during order creation: {}", e.getMessage());
            return "error: " + e.getResponseBodyAsString();
        }
    }
    
    private void validateCoupon(String couponCode){
        if (!couponService.validateCoupon(couponCode)) {
            log.error(couponCode+" is not valid");
            throw new Error(couponCode+" not valid. Order creation aborted.");
        }
    }

    private void validateAvailability(String productCode){
        if (!storeService.checkAvailability(productCode)) {
            log.info(productCode+" is not available in the stock");
            throw new Error(productCode+" is not available");
        }
    }

    private void withDrawFromCustomer(double finalPrice,String operation){

    }
    private void depositForMerchant(double finalPrice){
        
    }
    @Override
    public List<Order> getOrderByCustomerEmail(String email) {
        return this.orderRepository.findAllByEmail(email);
    }
    @Override
    public List<Order> getOrdersInPeriod(LocalDate start, LocalDate end) {
        return this.orderRepository.findAllByStartAndEndDate(start, end);
    }
}