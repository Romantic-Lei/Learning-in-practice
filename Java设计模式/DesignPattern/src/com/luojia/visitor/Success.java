package com.luojia.visitor;

/**
 * @Author Romantic-Lei
 * @Date 2020/6/2
 * @description
 */
public class Success extends Action {
    @Override
    public void getManResult(Man man) {
        System.out.println(" 男人给的评价很成功 ");
    }

    @Override
    public void getManResult(Woman woman) {
        System.out.println(" 女人给的评价很成功 ");
    }
}
