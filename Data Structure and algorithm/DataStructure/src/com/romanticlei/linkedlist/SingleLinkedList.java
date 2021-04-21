package com.romanticlei.linkedlist;

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