package com.luojia.state;

/**
 * @Author Romantic-Lei
 * @Date 2020/6/7
 * @description
 */
public class Client {

    public static void main(String[] args) {
        // 创建活动对象，奖品只有一个
        RaffleActivity activity = new RaffleActivity(1);

        // 连续抽奖300次
        for (int i = 0; i < 30; i++){
            System.out.println("-------第" + (i + 1) + "次抽奖");
            // 参加抽奖，第一步点击扣除积分
            activity.debuctMoney();

            // 第二部抽象
            activity.raffle();
        }
    }
}
