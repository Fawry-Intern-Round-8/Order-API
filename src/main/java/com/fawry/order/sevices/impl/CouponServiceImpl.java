package com.fawry.order.sevices.impl;

import com.fawry.order.sevices.CouponService;
import org.springframework.stereotype.Service;

@Service
public class CouponServiceImpl implements CouponService {
    public CouponServiceImpl() {
        // TODO Auto-generated method stub
    }

    @Override
    public Boolean validateCoupon(String couponCode) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'validateCoupon'");
    }

    @Override
    public Double calcPriceWithCoupon(double price, String couponCode) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'calcPriceWithCoupon'");
    }
}