package com.luojia.command;

/**
 * @Author Romantic-Lei
 * @Date 2020/6/2
 * @description 命令模式，创建命令接口
 */
public interface Command {

    // 执行动作（操作）
    public void execute();
    // 撤销动作（操作）
    public void undo();

}
