package com.luojia.command;

/**
 * @Author Romantic-Lei
 * @Date 2020/6/2
 * @description 操作用户开关灯
 */
public class LightOnCommand implements Command {

    // 聚合LightReceiver
    LightReceiver lightReceiver;

    public LightOnCommand(LightReceiver lightReceiver) {
        this.lightReceiver = lightReceiver;
    }

    @Override
    public void execute() {
        // 调用接受者的方法
        lightReceiver.on();
    }

    @Override
    public void undo() {
        // 调用接受者的方法
        lightReceiver.off();
    }
}
