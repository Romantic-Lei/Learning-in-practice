package com.luojia.state;

/**
 * @Author Romantic-Lei
 * @Date 2020/6/7
 * @description 不能抽奖的状态
 */
public class NoRaffleState extends State {

    // 初始化时传入活动引用，发放奖品后改变其状态
    RaffleActivity activity;

    public NoRaffleState(RaffleActivity activity) {
        this.activity = activity;
    }

    @Override
    public void deductMoney() {
        System.out.println("扣除50积分成功， 您可以抽奖了");
        activity.setState(activity.getCanRaffleState());
    }

    @Override
    public boolean raffle() {
        System.out.println("扣除积分才能抽奖");
        return false;
    }

    @Override
    public void dispensePrize() {
        System.out.println("不能发放奖品");
    }
}
