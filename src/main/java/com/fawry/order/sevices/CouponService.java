package com.fawry.order.sevices;

public interface CouponService {
    Boolean validateCoupon(String couponCode);

    Double calcPriceWithCoupon(double price, String couponCode);
}