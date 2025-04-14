package com.fawry.order_api.sevices;

public interface CouponService {
    Boolean validateCoupon(String couponCode);
    Double calcPriceWithCoupon(double price,String couponCode);
}