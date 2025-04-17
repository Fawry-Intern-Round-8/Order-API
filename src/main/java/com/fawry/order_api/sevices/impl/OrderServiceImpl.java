package com.fawry.order_api.sevices.impl;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

import com.fawry.order_api.entity.CouponConsumptionRequest;
import com.fawry.order_api.entity.CouponValidationResponse;
import com.fawry.order_api.entity.DepositRequest;
import com.fawry.order_api.entity.Order;
import com.fawry.order_api.entity.StockConsumeRequestDTO;
import com.fawry.order_api.entity.WithdrawRequest;
import com.fawry.order_api.repos.OrderRepository;
import com.fawry.order_api.sevices.BankService;
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
    private final BankService bankService;
    @Override
    public String saveOrder(Order order) {
        log.info("Creating order : "+order);
        try {
            //Coupon Validation (if applicable)
            System.out.println(order.getCouponCode());
            validateCoupon(order.getCouponCode());
            log.info("Coupon is valid");


            //Product Stock Validation
            validateAvailability(order.getProductId(),order.getQuantity());
            log.info(order.getProductId()+" is available in the stock with "+order.getQuantity()+" quantity");

            //Price Calculation with Discount (if applicable)
            Double finalPrice = order.getPrice() * order.getQuantity();

            if (order.getCouponCode() != null && !order.getCouponCode().isEmpty()) {
                Optional<CouponValidationResponse> couponResponse = couponService.validateCoupon(order.getCouponCode());
                if (couponResponse.isPresent() && couponResponse.get().isValid()) {
                    CouponValidationResponse coupon = couponResponse.get();
                    if ("FIXED".equals(coupon.getDiscountType())) {
                        finalPrice = Math.max(0, finalPrice - coupon.getValue());
                    } else if ("PERCENTAGE".equals(coupon.getDiscountType())) {
                        finalPrice = finalPrice * (100 - coupon.getValue()) / 100;
                    }
                    log.info("Price after applying coupon {}: {}", order.getCouponCode(), finalPrice);
                }
            }
            
            //Financial Transactions
            withDrawFromCustomer(order.getCardNumber(),finalPrice);
            depositForMerchant(finalPrice);
            log.info("Bank process completed successfully");

            //Stock Consumption
            stockConsumption(order.getProductId(),order.getQuantity(),order.getCustomerEmail(),order.getLongitude(),order.getLatitude());
            log.info("Stock consumed successfully");

            //Order Entity Creation & Persistence
            order.setCreationDate(LocalDate.now());
            Order savedOrder = orderRepository.save(order);
            
            //consume coupon
            consumeCoupon(order.getCouponCode(),String.valueOf(savedOrder.getId()));
            log.info("Successfully created order with ID: " + savedOrder.getId());
            
            //Notification Emails
            
            //Success Response
            return "success";
        } catch (Error e) {
            log.error("Error occurred during order creation: {}", e.getMessage());
            return "error: " + e.getMessage();
        }
    }

    private void validateCoupon(String couponCode) {
        if (couponCode == null || couponCode.isEmpty()) {
            return; 
        }
        
        Optional<CouponValidationResponse> validationResponse = couponService.validateCoupon(couponCode);
        
        if (validationResponse.isEmpty()) {
            log.error("Coupon validation failed for code: {}", couponCode);
            throw new Error("Coupon validation failed for code: " + couponCode);
        }
        
        CouponValidationResponse coupon = validationResponse.get();
        if (!coupon.isValid()) {
            log.error("Invalid coupon code: {}", couponCode);
            throw new Error("Invalid coupon code: " + couponCode);
        }
        
        log.info("Coupon {} is valid with {}% discount", couponCode, coupon.getValue());
    }

    private void validateAvailability(Long productId, int quantity) {
        boolean isAvailable = storeService.checkProductAvailability(productId, quantity);
        if (!isAvailable) {
            throw new Error(productId + " is not available with that quantity");
        }
    }
    private void stockConsumption(Long productId,int quantity,String customerEmail,double longitude,double latitude){
        StockConsumeRequestDTO stockConsumeRequestDTO =new StockConsumeRequestDTO();
        stockConsumeRequestDTO.setCustomerEmail(customerEmail);
        stockConsumeRequestDTO.setProductId(productId);
        stockConsumeRequestDTO.setQuantity(quantity);
        stockConsumeRequestDTO.setLongitude(longitude);
        stockConsumeRequestDTO.setLatitude(latitude);
        storeService.consumeStock(stockConsumeRequestDTO);
    }
    private void withDrawFromCustomer(String cardNumber,double finalPrice){
        log.info(cardNumber,finalPrice);
        WithdrawRequest withdrawRequest=new WithdrawRequest();
        withdrawRequest.setCardNumber(cardNumber);
        withdrawRequest.setAmount(finalPrice);
        withdrawRequest.setNotes("Order funds withdrawal");
        bankService.withdraw(withdrawRequest);
    }
    private void depositForMerchant(double finalPrice){
        DepositRequest depositRequest=new DepositRequest();
        depositRequest.setCardNumber("1234567898765432");
        depositRequest.setAmount(finalPrice);
        depositRequest.setNotes("Order funds deposit");
        bankService.deposit(depositRequest);
    }
    private void consumeCoupon(String couponCode,String orderId){
        CouponConsumptionRequest consumptionRequest=new CouponConsumptionRequest();
        consumptionRequest.setCode(couponCode);
        consumptionRequest.setOrderId(orderId);
        couponService.consume(consumptionRequest);
    }
    @Override
    public List<Order> getOrderByCustomerEmail(String email) {
        return this.orderRepository.findAllByCustomerEmail(email);
    }
    @Override
    public List<Order> getOrdersInPeriod(LocalDate start, LocalDate end) {
        return this.orderRepository.findAllByCreationDateBetween(start, end);
    }

    @Override
    public List<Order> getOrders() {
        return orderRepository.findAll();
    }
}