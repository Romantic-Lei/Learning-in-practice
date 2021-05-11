package com.romanticlei.hashTable;

public class HashTableDemo {
}

// 创建一个 HashTable 管理多个链表
class HashTable{
    private EmpLinkedList[] empLinkedListArray;
    private int size; // 集合的个数

    // 构造器
    public HashTable(int size) {
        this.size = size;
        empLinkedListArray = new EmpLinkedList[size];
    }

    // 添加雇员
    public void add(Emp emp) {
        // 根据员工id， 得到该员工添加到那条链表
        int empLinkedListNo = hashFun(emp.id);
        empLinkedListArray[empLinkedListNo].add(emp);
    }

    public void list(){
        for (int i = 0; i < size; i++) {
            empLinkedListArray[i].list();
        }
    }

    // 编写散列函数，使用一个简单取模算法
    public int hashFun(int id) {
        return id % size;
    }
}

class Emp{
    public int id;
    public String name;
    public Emp next; // 下一个员工信息，默认为null

    public Emp(int id, String name) {
        this.id = id;
        this.name = name;
    }
}

// 创建 EmpLinkedList， 表示链表
class EmpLinkedList{
    // 头指针，执行第一个Emp，因此我们这个链表的 head 是直接指向第一个Emp
    private Emp head;

    public void add(Emp emp) {
        if (head == null) {
            head = emp;
            return;
        }

        // 由于头结点不能动，所以需要一个辅助指针，帮助定位
        Emp curEmp = head;
        // 找到链表的最后
        while (curEmp.next != null) {
            curEmp = curEmp.next;
        }

        curEmp.next = emp;
    }

    // 遍历链表
    public void list(){
        if (head == null) {
            System.out.println("链表为空");
            return;
        }

        Emp curEmp = head;
        while (true) {
            System.out.println("id = " + curEmp.id + "\t name = " + curEmp.name);
            if (curEmp.next == null) {
                break;
            }

            curEmp = curEmp.next;
        }
    }
}
