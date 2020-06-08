package com.luojia.responsibilitychain;

/**
 * @Author Romantic-Lei
 * @Date 2020/6/8
 * @description
 */
public abstract class Approver {

    Approver approver; // 下一个处理者
    String name; // 名字

    public Approver(String name) {
        this.name = name;
    }

    // 下一个处理者
    public void setApprover(Approver approver) {
        this.approver = approver;
    }

    // 处理审批请求的方法，得到一个请求，处理是子类完成，因此该方法要作成抽象
    public abstract void processRequest(PurchaseRequest purchaseRequest);

}
