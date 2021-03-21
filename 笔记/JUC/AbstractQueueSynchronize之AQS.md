## AbstractQueueSynchronize之AQS

#### 前置知识

- 公平锁和非公平锁
- 可重入锁
- LockSupport
- 自旋锁
- 数据结构之链表
- 设计模式之模板设计模式

#### AQS是什么

字面意思 抽象队列同步器(AbstractQueueSynchronize)

源代码

![20210321140111](images\20210321140111.png)·

AbstractOwnableSynchronizer
AbstractQueuedLongSynchronizer
AbstractQueuedSynchronizer

**通常地：AbstractQueuedSynchronizer简称为AQS**

技术解释

是用来构建锁或者其他同步器组件的**重量级框架及整个JUC体系的基石**，通过内置的FIFO**队列**来完成资源获取线程的队列工作，并通过一个**int类型变量**表示持有锁的状态。

![1616307605](images\1616307605.jpg)

#### AQS为什么是JUC内容中最重要的基石

和AQS有关的



- **ReentrantLock**

![image-20210321144932732](images\image-20210321144932732.png)

- **CountDownLatch**

![1616309492](images\1616309492.jpg)

- **ReentrantReadWriteLock**

![1616309656](images\1616309656.jpg)

- **Semaphore**

![1616309711](images\1616309711.jpg)






