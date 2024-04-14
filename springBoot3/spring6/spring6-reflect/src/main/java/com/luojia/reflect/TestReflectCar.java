package com.luojia.reflect;

import org.junit.jupiter.api.Test;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class TestReflectCar {

    // 1.获取 Class 对象多种方式
    @Test
    public void test01() throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        // 1.类名.class
        Class<Car> clazz1 = Car.class;
        // 2.对象.getClass()
        Class clazz2 = new Car().getClass();
        // 3.Class.forName("全路径")
        Class<?> clazz3 = Class.forName("com.luojia.reflect.Car");
        // 实例化
        Car car = (Car)clazz3.getDeclaredConstructor().newInstance();
        // Car(name=null, age=0, color=null)
        System.out.println(car);
    }

    // 2.获取构造方法
    @Test
    public void test02() throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        Class<Car> clazz = Car.class;
        // getConstructors() 获取所有 public 的构造方法
        // Constructor<?>[] constructors = clazz.getConstructors();

        // 获取所有的构造方法 包括 public、private、protected
        Constructor<?>[] constructors = clazz.getDeclaredConstructors();
        for (Constructor c : constructors) {
            // 方法名称：com.luojia.reflect.Car 参数个数：0
            // 方法名称：com.luojia.reflect.Car 参数个数：3
            System.out.println("方法名称：" + c.getName() + " 参数个数：" + c.getParameterCount());
        }

        // 指定有参数构造创建对象
        // 1.构造 public
        // Constructor c1 = clazz.getConstructor(String.class, int.class, String.class);
        // Car car = (Car)c1.newInstance("奔驰", 10, "白色");
        // System.out.println(car);

        // 2. 构造 private
        Constructor c2 = clazz.getDeclaredConstructor(String.class, int.class, String.class);
        c2.setAccessible(true);
        Car car2 = (Car)c2.newInstance("宝马", 15, "红色");
        // Car(name=宝马, age=15, color=红色)
        System.out.println(car2);
    }

    // 3.获取属性
    @Test
    public void test03() throws Exception {
        Class<Car> clazz = Car.class;
        Car car = clazz.getDeclaredConstructor().newInstance();
        // 获取所有 public 属性
        Field[] fields = clazz.getFields();
        Field[] declaredFields = clazz.getDeclaredFields();
        for (Field field : declaredFields) {
            // 属性名称：name
            // 属性名称：age
            // 属性名称：color
            System.out.println("属性名称：" + field.getName());
            if (field.getName().equals("name")) {
                // 设置允许访问
                field.setAccessible(true);
                field.set(car, "五菱宏光");
            }
        }
        // Car(name=五菱宏光, age=0, color=null)
        System.out.println(car);
    }

    // 4.获取方法
    @Test
    public void test04() throws Exception {
        Car car = new Car("奥迪", 5, "黑色");
        Class clazz = car.getClass();
        // 1.public 方法
        Method[] methods = clazz.getMethods();
        for (Method m : methods) {
            // System.out.println("方法名：" + m.getName());
            // 执行方法
            if (m.getName().equals("toString")) {
                String invoke = (String)m.invoke(car);
                // toString 方法执行了：Car(name=奥迪, age=5, color=黑色)
                System.out.println("toString 方法执行了：" + invoke);
            }
        }

        // 2.private 方法
        Method[] methodAll = clazz.getDeclaredMethods();
        for (Method m1 : methodAll) {
            // 执行方法 run()
            if (m1.getName().equals("run")) {
                m1.setAccessible(true);
                // 私有方法-run
                m1.invoke(car);
            }
        }
    }
}
