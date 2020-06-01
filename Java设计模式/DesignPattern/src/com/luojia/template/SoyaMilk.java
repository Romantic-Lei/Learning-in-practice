package com.luojia.template;

/**
 * @Author Romantic-Lei
 * @Date 2020/6/1
 * @description 模板方法模式
 */
public abstract class SoyaMilk {

    // 模板方法，make， 模板方法可以做成final， 不让子类去覆盖
    final void make() {
        select();
        addCondiments();
        soak();
        beat();
    }

    // 选材料
    void select() {
        System.out.println("第一步：选择好的新鲜黄豆");
    }

    // 添加不同的配料，抽象方法，子类具体实现
    abstract void addCondiments();

    // 浸泡
    void soak() {
        System.out.println("第三步， 换头和配料开始浸泡，需要3小时");
    }

    void beat() {
        System.out.println("第四部：黄豆和配料使用豆浆机打碎");
    }

}
