package com.romanticlei.linkedlist;

public class Josephu {

    public static void main(String[] args) {

        CircleSingleLinkedList circleSingleLinkedList = new CircleSingleLinkedList();
        circleSingleLinkedList.addBoy(5);
        circleSingleLinkedList.showBoy();

        System.out.println("测试约瑟夫出圈问题");
        circleSingleLinkedList.countBoy(1, 2, 5);
    }
}

// 创建一个环形的单向链表
class CircleSingleLinkedList {
    // 创建一个first 节点，没有编号
    private Boy first = null;

    public void addBoy(int nums) {
        if (nums < 1) {
            System.out.println("nums 的值不正确");
            return;
        }

        // 创建一个辅助指针，帮助构建环形链表
        Boy curBoy = null;
        // 使用for 循环来创建我们的唤醒链表
        for (int i = 1; i <= nums; i++) {
            // 根据编号，创建小孩节点
            Boy boy = new Boy(i);
            if (i == 1) {
                first = boy;
                // 构成环
                first.setNext(first);
                // 让curBoy 指向第一个节点
                curBoy = boy;
            } else {
                curBoy.setNext(boy);
                boy.setNext(first);
                curBoy = boy;
            }
        }
    }

    // 遍历当前环形链表
    public void showBoy() {
        // 判断链表是否为空
        if (first == null) {
            System.out.println("没有任何节点可以遍历");
            return;
        }

        // 因为first 不能动，因此需要一个辅助指针完成遍历
        Boy curBoy = first;
        while (true) {
            System.out.println("当前小孩出队编号为：" + curBoy.getNo());
            if (curBoy.getNext() == first) {
                break;
            }

            curBoy = curBoy.getNext();
        }
    }

    /**
     * 根据用户的输入，计算出小孩出圈的顺序
     *
     * @param startNo  表示从第多少个小孩开始数数
     * @param countNum 表示数几下
     * @param nums     表示最初有多少小孩在圈中
     */
    public void countBoy(int startNo, int countNum, int nums) {
        // 对数据进行校验
        if (first == null || startNo < 1 || startNo > nums) {
            System.out.println("输入参数有误，请重新输入");
            return;
        }

        // 创建一个辅助指针
        Boy helper = first;
        // 创建的这个辅助指针，应该遍历指向环形链表的最后一个节点
        while (helper.getNext() != first) {
            helper = helper.getNext();
        }

        // 出圈遍历前，先让first 和 helper 移动 k-1次（因为当前值也算一个数）
        for (int i = 0; i < startNo - 1; i++) {
            first = first.getNext();
            helper = helper.getNext();
        }
        // 找到出圈节点，让 first 和 helper 移动 countNum-1次
        while (first != helper){
            // 让 first 和 helper 指针同时移动 countNum - 1
            for (int i = 0; i < countNum - 1; i++) {
                first = first.getNext();
                helper = helper.getNext();
            }
            // 这时 first 指向的节点，就是要出圈的节点
            System.out.println("出圈的节点编号为 " + first.getNo());
            first = first.getNext();
            helper.setNext(first);
        }

        System.out.println("最后留在圈中的编号为 " + first.getNo());
    }
}

// 创建一个Boy类，表示一个节点
class Boy {
    private int no;
    private Boy next;

    public Boy(int no) {
        this.no = no;
    }

    public int getNo() {
        return no;
    }

    public void setNo(int no) {
        this.no = no;
    }

    public Boy getNext() {
        return next;
    }

    public void setNext(Boy next) {
        this.next = next;
    }
}
