## LockSupport

https://www.apiref.com/java11-zh/java.base/module-summary.html

用于创建锁和其他同步类的基本线程阻塞原语。



LockSupport在哪看？

https://www.apiref.com/java11-zh/java.base/java/util/concurrent/locks/package-summary.html

![1616120530(1)](images\1616120530(1).jpg)```

是什么？（https://www.apiref.com/java11-zh/java.base/java/util/concurrent/locks/LockSupport.html）

![1616122249](images\1616122249.jpg)

**用于创建锁和其他同步类的基本线程阻塞原语。**

LockSupport中的park()和unpark()的作用分别是阻塞线程和解除阻塞线程



## 线程等待唤醒机制(wait/notify)

##### 传统的线程等待和唤醒的方法

![16161226211](images\16161226211.jpg)

##### 3种让线程等待和唤醒的方法

1. 方式1：使用Object中的wait()方法让线程等待，使用Object中的Notify()方法唤醒线程
2. 方式2：使用JUC包中Condition的await()方法让线程等待，使用signal()方法唤醒线程
3. 方式3：LockSupport类可以阻塞当前线程以及唤醒指定被阻塞的线程









