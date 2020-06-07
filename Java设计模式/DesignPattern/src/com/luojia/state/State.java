package com.luojia.state;

/**
 * @Author Romantic-Lei
 * @Date 2020/6/7
 * @description 状态抽象类
 */
public abstract class State {

    // 扣除积分
    public abstract void deductMoney();

    // 是否抽中奖品
    public abstract boolean raffle();

    // 发放奖品
    public abstract void dispensePrize();
}
