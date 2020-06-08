package com.luojia.responsibilitychain;

/**
 * @Author Romantic-Lei
 * @Date 2020/6/8
 * @description
 */
public class SchoolMasterApprover extends Approver {

    public SchoolMasterApprover(String name) {
        super(name);
    }

    @Override
    public void processRequest(PurchaseRequest purchaseRequest) {
        if (purchaseRequest.getPrice() > 30000) {
            System.out.println(" 请求编号 id =  " + purchaseRequest.getId() + "被" + this.name + "处理");
        }
    }

}
