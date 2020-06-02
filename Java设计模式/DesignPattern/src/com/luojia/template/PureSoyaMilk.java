package com.luojia.template;

/**
 * @Author Romantic-Lei
 * @Date 2020/6/1
 * @description 纯豆浆，不加任何调料
 */
public class PureSoyaMilk extends SoyaMilk {
    @Override
    void addCondiments() {
        //空实现
    }

    @Override
    boolean customerWantCondiments() {
        return false;
    }
}
