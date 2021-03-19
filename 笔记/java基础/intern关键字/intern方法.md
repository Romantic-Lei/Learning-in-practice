## inter()方法

inter是直接操作jvm底层的方法

![Image](images\Image.bmp)



#### 方法区和运行时常量池溢出

​	**由于运行时常量池是方法的一部份**，所以这两个区域的溢出测试可以放到一起进行。前面曾经提到HotSpot从JDK7开始逐步“去永久代”的计划，**并在JDK8中完全使用元空间来代替永久代**的背景故事，在此我们就以测试代码来观察一下，使用“永久代”还是“元空间”来实现方法区，对程序有什么实际的影响。

​	**String::inter()是一个本地方法，他的作用是如果字符串常量池中已经包含一个等于此String对象的字符串，则返回代表池中这个字符串的String对象的引用；否则，会将此String对象包含的字符串添加到常量池中，并且返回此String对象的引用。**在JDK 6或更早之前的HotSpot虚拟机中，常量池都是分配在永久代中，我们可以通过-XX:PermSize和 -XX: MaxPermSize限制永久代的大小，即可间接限制其中常量池的容量

```java
String str1 = new StringBuilder("Hello").append("RomanticLei").toString();
        // HelloRomanticLei
        System.out.println(str1);
        // HelloRomanticLei
        System.out.println(str1.intern());
        // true
        System.out.println(str1 == str1.intern());

        System.out.println();

        String str2 = new StringBuilder("ja").append("va").toString();
        // java
        System.out.println(str2);
        // java
        System.out.println(str2.intern());
        // false
        System.out.println(str2 == str2.intern());
```

**为什么会出现这样的情况？**

​	按照代码结果，java字符串答案为false，必然是两个不同的java，那另外一个java字符串是如何加载进来的？

​	有一个初始化的java字符串（JDK出娘胎自带的），在加载sun.misc.Version这个类的时候进入常量池。

​	OpenJDK8底层源码说明

​		System代码解析

​			System -> initializeSystemClass -> Version

![1615814220](images\1615814220.jpg)

​			

![1615814364](images\1615814364.jpg)

OpenJDK8源码

http://openjdk.java.net/

路径：openjdk8\jdk\src\share\classes\sun\misc



## 总结

```java
String str1 = new StringBuilder("Hello").append("RomanticLei").toString();
        System.out.println(str1 == str1.intern());
        System.out.println();

        String str2 = new StringBuilder("ja").append("va").toString();
        System.out.println(str2.intern());
```

![1615815592](images\1615815592.jpg)



sun.misc.Version 类会在JDK类库的初始化过程中被加载并初始化，而在初始化时它需要对静态常量字段根据指定的常量值（ConstantValue）做默认初始化，此时被sun.misc.Version.launcher静态常量字段所引用的“java”字符串字面量被intern到HotSpot VM的字符串常量池--StringTable里了。

