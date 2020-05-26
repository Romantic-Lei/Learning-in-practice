package com.luojia.principle.inversion;

public class DependecyInversion {

    public static void main(String[] args){

        BasePersion basePersion = new BasePersion();
        basePersion.receive(new BaseEmail());
        basePersion.receive(new WeiXin());

//        Persion persion = new Persion();
//        persion.receive(new Email());
    }
}

// ==================================================================
// 方式二
// 定义接口
interface IReceiver{
    public String getInfo();
}

class BaseEmail implements IReceiver{

    @Override
    public String getInfo() {
        return "使用基础邮件包发送信息：hello，Interface";
    }
}

class WeiXin implements  IReceiver{

    @Override
    public String getInfo() {
        return "使用微信发送消息";
    }
}

class BasePersion {
    public void receive(IReceiver iReceiver) {
        System.out.println("使用" + iReceiver.getInfo());
    }
}


// ==================================================================
// 方式一
class Email{
    public String getInfo(){
        return "电子邮件信息：hello， world";
    }
}

// 完成接受消息的功能
// 此写法简单，比较容易想到
// 如果我们获取对象是 微信，短信等等，则新增类，同时Person也要增加相应的接受方法
// 解决思路：引入一个抽象的接口IReceiver，表示接收者，这样Person类与接口IReceiver发生依赖
// 因为Email， Weixin 等属于接收的范围，他们各自实现IReceiver接口就OK，这样我们就符合依赖倒转原则
class Persion{
    public void receive(Email email){
        System.out.println(email.getInfo());
    }
}
