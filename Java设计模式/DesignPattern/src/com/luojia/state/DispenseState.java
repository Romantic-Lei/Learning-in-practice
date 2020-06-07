package com.luojia.state;

/**
 * @Author Romantic-Lei
 * @Date 2020/6/7
 * @description
 */
public class DispenseState extends State {

    // 初始化时传入活动引用，发放奖品后改变其状态
    RaffleActivity activity;

    public DispenseState(RaffleActivity activity) {
        this.activity = activity;
    }

    @Override
    public void deductMoney() {
        System.out.println("不能扣除积分");
    }

    @Override
    public boolean raffle() {
        System.out.println("不能抽奖");
        return false;
    }

    @Override
    public void dispensePrize() {
        if (activity.getCount() > 0) {
            System.out.println("恭喜中奖了");
            activity.setState(activity.getNoRaffleState());
        } else {
            System.out.println("很遗憾，奖品发放完毕");
            activity.setState(activity.getDispenseOutState());
        }
    }
}
