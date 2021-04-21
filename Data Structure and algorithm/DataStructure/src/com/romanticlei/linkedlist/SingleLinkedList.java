package com.romanticlei.linkedlist;

import java.util.Stack;

public class SingleLinkedList {

    public static void main(String[] args) {
        HeroNode hero1 = new HeroNode(1, "宋江", "及时雨");
        HeroNode hero2 = new HeroNode(2, "卢俊义", "玉麒麟");
        HeroNode hero3 = new HeroNode(3, "吴用", "智多星");
        HeroNode hero4 = new HeroNode(4, "林冲", "豹子头");
        SingleLinkedListDemo linkedListDemo = new SingleLinkedListDemo();
        // linkedListDemo.add(hero1);
        // linkedListDemo.add(hero4);
        // linkedListDemo.add(hero3);
        // linkedListDemo.add(hero2);

        HeroNode hero3_1 = new HeroNode(3, "小吴", "智多星");
        linkedListDemo.addByOrder(hero1);
        linkedListDemo.addByOrder(hero4);
        linkedListDemo.addByOrder(hero3);
        linkedListDemo.addByOrder(hero2);
        linkedListDemo.addByOrder(hero3_1);
        linkedListDemo.list();

        // linkedListDemo.delete(1);
        // linkedListDemo.delete(4);
        // linkedListDemo.delete(5);
        linkedListDemo.list();

        System.out.println("有效节点总个数为：" + getLength(linkedListDemo.getHead()));

        int k = 3;
        System.out.println("倒数第" + k + "个数据是" + findLastIndexNode(linkedListDemo.getHead(), k));

        System.out.println("单链表反转之后~");
        reversetList(linkedListDemo.getHead());
        linkedListDemo.list();

        System.out.println("利用栈逆序打印链表数据，不破坏原来链表结构~~~");
        reversePrint(linkedListDemo.getHead());

        System.out.println("~~~~~~~~~~~~~~两个有序单向链表合并成一个有序单向链表~~~~~~~~~~~~~~");
        HeroNode hero01 = new HeroNode(1, "宋江", "及时雨");
        HeroNode hero02 = new HeroNode(3, "卢俊义", "玉麒麟");
        HeroNode hero03 = new HeroNode(6, "吴用", "智多星");
        HeroNode hero04 = new HeroNode(9, "林冲", "豹子头");

        HeroNode hero10 = new HeroNode(2, "三国演义", "三国演义");
        HeroNode hero20 = new HeroNode(5, "水浒传", "水浒传");
        HeroNode hero30 = new HeroNode(7, "红楼梦", "红楼梦");
        HeroNode hero40 = new HeroNode(8, "西游记", "西游记");

        SingleLinkedListDemo mergeNode1 = new SingleLinkedListDemo();
        mergeNode1.addByOrder(hero01);
        mergeNode1.addByOrder(hero02);
        mergeNode1.addByOrder(hero03);
        mergeNode1.addByOrder(hero04);

        SingleLinkedListDemo mergeNode2 = new SingleLinkedListDemo();
        mergeNode2.addByOrder(hero10);
        mergeNode2.addByOrder(hero20);
        mergeNode2.addByOrder(hero30);
        mergeNode2.addByOrder(hero40);

        mergeNode1.list();
        mergeNode2.list();
        SingleLinkedListDemo mergeNode3 = new SingleLinkedListDemo();
        HeroNode heroNode = mergeSingleLinkedList(mergeNode1.getHead(), mergeNode2.getHead());
        mergeNode3.add(heroNode);
        mergeNode3.list();


    }

    // 合并两个有序单链表
    public static HeroNode mergeSingleLinkedList(HeroNode node1, HeroNode node2){
        // 判断有没有链表是空
        if (node1.next == null && node2.next == null){
            return null;
        }

        if (node1.next == null){
            return node2.next;
        }

        if (node2.next == null){
            return node1.next;
        }

        HeroNode newNode = new HeroNode(0, "", "");
        HeroNode cur;

        // 比较第一个有效值的大小
        if (node1.next.no > node2.next.no){
            // 将小值放在新链表后面
            newNode = node2.next;
            // 头结点后移一位，即移到第一个有效值
            node1 = node1.next;
            // 头结点后移两位，即移动到第二个值
            node2 = node2.next.next;

            // 声明一个临时变量
            cur = newNode;
            cur.next = null;

        }else {
            newNode = node1.next;
            node1 = node1.next.next;
            node2 = node2.next;

            cur = newNode;
            cur.next = null;
        }

        // node1 node2 都已指向了实际的数据
        while (node1 != null && node2 != null){
            if (node1.no > node2.no){
                cur.next = node2;
                cur = cur.next;
                node2 = node2.next;

            } else {
                cur.next = node1;
                cur = cur.next;
                node1 = node1.next;
            }
        }

        if (node1 == null){
            cur.next = node2;
        }else {
            cur.next = node1;
        }

        return newNode;
    }

    // 可以利用这个数据结构，将各个节点压入到栈中，然后利用栈的先进后出的特点实现逆序打印
    public static void reversePrint(HeroNode head){
        if (head.next == null){
            return; // 空链表，不能打印
        }

        // 创建一个栈，将各个节点压入栈中
        Stack<HeroNode> stack = new Stack<>();
        HeroNode  cur = head.next;
        while (cur != null){
            stack.push(cur);
            cur = cur.next;
        }

        while (stack.size() > 0){
            System.out.println(stack.pop());
        }
    }

    // 单链表的反转【腾讯面试题，有点难度】
    public static void reversetList(HeroNode head) {
        // 如果当前链表为空或者只有一个结点，那么无需反转直接返回即可
        if (head == null || head.next == null) {
            return ;
        }

        // 创建一个新头节点
        HeroNode newNode = new HeroNode(0, "", "");
        // 定义一个辅助变量，即原结点的第一个值
        HeroNode cur = head.next;

        while (cur != null) {
            // 取出原节点的当前值值,然后将当前值后移
            HeroNode oldCur = cur;
            cur = cur.next;

            oldCur.next = newNode.next;
            newNode.next = oldCur;
        }

        // 将 head.next 指向 newNode.next实现反转
        head.next = newNode.next;
    }

    // 查找单链表中的倒数第k个结点
    // 编写一个方法，接受head节点，同时接受一个index，index指的是倒数第几个节点
    // 先遍历一遍链表，获得到链表总长度
    // 得到size 后，我们从链表开始遍历（size - index）个就可以得到
    // 如果找到了，就返回，否则返回null
    public static HeroNode findLastIndexNode(HeroNode head, int index) {
        if (head == null) {
            return null; // 没有找到
        }

        // 第一遍遍历得到的链表长度
        int size = getLength(head);
        // 第二遍遍历链表到 size - index位置。就是我们倒数的第k个
        HeroNode cur = head.next;
        if (index < 0 || index > size) {
            return null;
        }

        for (int i = 0; i < size - index; i++) {
            cur = cur.next;
        }

        return cur;
    }

    /**
     * 获取单链表的个数
     *
     * @param head 头节点
     * @return 节点总个数
     */
    public static int getLength(HeroNode head) {
        int length = 0;
        HeroNode cur = head;

        while (true) {
            if (cur.next == null) {
                return length;
            }
            length++;
            cur = cur.next;
        }
    }
}

// 定义 SingleLinkedList 管理我们的英雄
class SingleLinkedListDemo {
    // 先初始化一个头节点，头节点不要懂，不存储任何具体的数据
    HeroNode head = new HeroNode(0, "", "");

    // 获取到头节点
    public HeroNode getHead() {
        return head;
    }

    // 添加节点到单向链表
    // 思路，当不考虑编号顺序时
    // 1.找到当前链表的最后一个结点
    // 2.将最后这个结点的next 指向新的节点
    public void add(HeroNode heroNode) {
        // 头节点不能动，我们需要一个临时变量来存储头节点
        HeroNode temp = head;
        while (true) {
            if (null == temp.next) {
                break;
            }

            // 存在后一个节点，临时节点后移
            temp = temp.next;
        }

        // 当退出while循环时，temp就指向了链表的最后
        // 并将最后这个节点的 next 指向新的节点
        temp.next = heroNode;
    }

    // 对插入的数据进行排序插入到链表
    public void addByOrder(HeroNode heroNode) {
        HeroNode temp = head;
        boolean flag = false;
        while (true) {
            if (temp.next == null) {
                break;
            }

            if (temp.next.no > heroNode.no) {
                break;
            }

            if (temp.next.no == heroNode.no) {
                flag = true;
                break;
            }

            temp = temp.next;
        }

        // 如果排序编号相同，那么我们就覆盖原来的值
        if (flag) {
            heroNode.next = temp.next.next;
            temp.next.next = null;
        } else {
            // 如果编号不相同，那么我们直接插入到链表
            heroNode.next = temp.next;
        }

        temp.next = heroNode;
    }

    public void delete(int no) {
        HeroNode temp = head;
        while (true) {
            if (temp.next == null) {
                System.out.println("删除数据不存在，删除失败");
                break;
            }
            if (temp.next.no == no) {
                temp.next = temp.next.next;
                break;
            }
            temp = temp.next;
        }
    }

    // 显示链表
    public void list() {
        // 判断链表是否为空
        if (head.next == null) {
            System.out.println("链表为空");
            return;
        }

        // 因为头节点不能动，所以仍旧需要一个临时节点
        HeroNode temp = head;
        while (true) {
            if (temp.next == null) {
                System.out.println("遍历完毕");
                break;
            }

            System.out.println(temp.next + "->");
            temp = temp.next;
        }
    }
}

// 定义 HeroNode，每个 HeroNode 对象就是一个节点
class HeroNode {
    public int no;
    public String name;
    public String nickName;
    public HeroNode next;

    public HeroNode(int no, String name, String nickName) {
        this.no = no;
        this.name = name;
        this.nickName = nickName;
    }

    //为了显示方便，重写toString方法
    @Override
    public String toString() {
        return "HeroNode{" +
                "no=" + no +
                ", name='" + name + '\'' +
                ", nickName='" + nickName + '\'' +
                '}';
    }

}