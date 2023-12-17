package com.luojia.demo.Service;

import com.luojia.demo.entity.UserEntity;
import com.luojia.demo.event.LoginSuccessEvent;
import org.springframework.context.event.EventListener;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Service;

@Service
public class CouponService {

    @Order(1)
    @EventListener
    public void onEvent(LoginSuccessEvent loginSuccessEvent) {
        System.out.println("===== sendCoupon 感知到事件 =====" + loginSuccessEvent);
        UserEntity source = (UserEntity) loginSuccessEvent.getSource();
        sendCoupon(source.getUsername());
    }

    public void sendCoupon(String username) {
        System.out.println(username + " 得到一张随机优惠券");
    }
}
