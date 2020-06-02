package com.luojia.visitor;

/**
 * @Author Romantic-Lei
 * @Date 2020/6/2
 * @description
 */
public class Fail extends Action {
    @Override
    public void getManResult(Man man) {
        System.out.println(" 男人给的评价很失败 ");
    }

    @Override
    public void getManResult(Woman woman) {
        System.out.println(" 男人给的评价很失败 ");
    }
}
