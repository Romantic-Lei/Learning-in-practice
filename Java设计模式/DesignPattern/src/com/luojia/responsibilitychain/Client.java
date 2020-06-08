package com.luojia.responsibilitychain;

/**
 * @Author Romantic-Lei
 * @Date 2020/6/8
 * @description
 */
public class Client {

    public static void main(String[] args) {
        // 创建一个请求
        PurchaseRequest purchaseRequest = new PurchaseRequest(1, 70000, 1);

        // 创建相关的审批人
        DepartmentApprover departmentApprover = new DepartmentApprover("张主任");
        CollegeApprover collegeApprover = new CollegeApprover("李院长");
        ViceSchoolMasterApprover viceSchoolMasterApprover = new ViceSchoolMasterApprover("王副校");
        SchoolMasterApprover schoolMasterApprover = new SchoolMasterApprover("赵校长");

        // 设置审批流程的下一个级别
        departmentApprover.setApprover(collegeApprover);
        collegeApprover.setApprover(viceSchoolMasterApprover);
        viceSchoolMasterApprover.setApprover(schoolMasterApprover);

        departmentApprover.processRequest(purchaseRequest);

    }

}
