package com.romanticlei.linkedlist;

public class DoubleLinkedList {

    public static void main(String[] args) {
        System.out.println("双向链表的测试");
        HeroNode2 hero1 = new HeroNode2(1, "宋江", "及时雨");
        HeroNode2 hero2 = new HeroNode2(2, "卢俊义", "玉麒麟");
        HeroNode2 hero3 = new HeroNode2(5, "吴用", "智多星");
        HeroNode2 hero4 = new HeroNode2(6, "林冲", "豹子头");
        //创建一个双向链表
        DoubleLinkedListDemo doubleLinkedList = new DoubleLinkedListDemo();
        doubleLinkedList.add(hero1);
        doubleLinkedList.add(hero2);
        doubleLinkedList.add(hero3);
        doubleLinkedList.add(hero4);
        doubleLinkedList.list();

        // 修改
        System.out.println("修改后的链表~~~");
        HeroNode2 hero5 = new HeroNode2(3, "公孙胜", "入云龙");
        doubleLinkedList.update(hero5);
        doubleLinkedList.list();

        // 删除
        System.out.println("删除指定编号的链表为~~~");
        doubleLinkedList.delete(3);
        doubleLinkedList.list();

        // 有序加入链表
        System.out.println("按序加入到双向链表");
        HeroNode2 hero6 = new HeroNode2(7, "公孙胜", "入云龙");
        doubleLinkedList.addByOrder(hero6);
        doubleLinkedList.list();
    }

}

class DoubleLinkedListDemo {
    // 先初始化一个头节点，头节点不要懂，不存储任何具体的数据
    HeroNode2 head = new HeroNode2(0, "", "");

    // 获取到头节点
    public HeroNode2 getHead() {
        return head;
    }

    // 遍历显示双向链表
    public void list() {
        // 判断链表是否为空
        if (head.next == null) {
            System.out.println("链表为空");
            return;
        }

        // 因为头节点不能动，所以仍旧需要一个临时节点
        HeroNode2 temp = head;
        while (true) {
            if (temp.next == null) {
                System.out.println("遍历完毕");
                break;
            }

            System.out.println(temp.next + "->");
            temp = temp.next;
        }
    }

    // 添加一个数据到双向链表的尾部
    public void add(HeroNode2 heroNode) {
        // 头节点不能动，我们需要一个临时变量来存储头节点
        HeroNode2 temp = head;
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
        heroNode.pre = temp;

    }

    public void addByOrder(HeroNode2 node) {
        HeroNode2 cur = head;
        boolean flag = false;

        while (true){
            if (cur.next == null){
                flag = true;
                break;
            }

            if (cur.next.no > node.no){
                break;
            }

            if (cur.next.no == node.no){
                update(node);
                return;
            }

            cur = cur.next;
        }

        if (flag){
            node.pre = cur;
            cur.next = node;
        } else {
            node.next = cur.next;
            cur.next.pre = node;
            cur.next = node;
            node.pre = cur;
        }

    }

    public void update(HeroNode2 newHeroNode) {
        if (head == null) {
            System.out.println("链表为空");
            return;
        }

        // 找到需要修改的结点，根据编号no
        HeroNode2 temp = head.next;
        boolean flag = false;
        while (true) {
            if (temp == null) {
                break;
            }

            if (temp.no == newHeroNode.no) {
                flag = true;
                break;
            }
            temp = temp.next;
        }

        if (flag) {
            temp.name = newHeroNode.name;
            temp.nickName = newHeroNode.nickName;
        } else {
            System.out.println("没有找到编号为" + newHeroNode.no + "的节点");
        }
    }

    // 删除节点
    public void delete(int no) {
        if (head.next == null) {
            System.out.println("链表为空，无法删除");
            return;
        }

        HeroNode2 temp = head.next;
        while (true) {
            if (temp == null) {
                System.out.println("删除数据不存在，删除失败");
                break;
            }
            if (temp.no == no) {
                temp.pre.next = temp.next;
                // 如果是最后一个节点，就不需要执行这句话
                if (temp.next != null) {
                    temp.next.pre = temp.pre;
                }
                temp.next = null;
                temp.pre = null;
                break;
            }
            temp = temp.next;
        }
    }
}

// 定义 HeroNode，每个 HeroNode 对象就是一个节点
class HeroNode2 {
    public int no;
    public String name;
    public String nickName;
    public HeroNode2 next; // 指向下一个节点
    public HeroNode2 pre; // 指向前一个节点

    public HeroNode2(int no, String name, String nickName) {
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
