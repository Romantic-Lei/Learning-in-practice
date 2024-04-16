package com.luojia.bean;

import com.luojia.anotation.Bean;
import com.luojia.anotation.Di;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.net.URLDecoder;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class AnnotationApplicationContext implements MyApplicationContext{

    // 创建 map 集合，模拟spring bean的创建，该map 存放 bean对象
    private Map<Class, Object> beanFactory = new HashMap<>();
    private static String rootPath;

    // 返回对象
    @Override
    public Object getBean(Class clazz) {
        return beanFactory.get(clazz);
    }

    // 创建有参构造器，传递包路径，设置包的扫描规则
    // 当前包及其子包，哪个类有 @Bean 注解，把这个类通过反射实例化
    public AnnotationApplicationContext(String basePackage) {
        try {
            // java 代码里面的层级都是.为分隔符  com.luojia
            // 1.把.替换成\
            String packagePath = basePackage.replaceAll("\\.", "\\\\");
            // 2.获取包的绝对路径
            Enumeration<URL> urls = Thread.currentThread().getContextClassLoader().getResources(packagePath);
            while (urls.hasMoreElements()) {
                URL url = urls.nextElement();
                String filePath = URLDecoder.decode(url.getFile(), "utf-8");
// /F:/workspace/数据结构和算法/Learning-in-practice/springBoot3/spring6/spring6-reflect/target/classes/com\luojia
                System.out.println(filePath);

                // 获取包前面部分，字符串截取
                rootPath = filePath.substring(0, filePath.length() - basePackage.length());
                // 包扫描
                loadBean(new File(filePath));
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } catch (InvocationTargetException e) {
            throw new RuntimeException(e);
        } catch (NoSuchMethodException e) {
            throw new RuntimeException(e);
        } catch (InstantiationException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }

        // 属性注入
        loadDi();
    }

    // 包扫描过程，实例化
    public void loadBean(File file) throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        // 1.判断当前是否文件夹
        if (file.isDirectory()) {
            // 2.判断文件夹里面所有内容
            File[] childrenFiles = file.listFiles();
            // 3.判断文件夹里面为空，直接返回
            if (childrenFiles == null || childrenFiles.length == 0) {
                return;
            }
            // 4.如果文件夹里面不为空，遍历文件夹所有内容
            for (File child : childrenFiles) {
                // 4.1 遍历得到每个File对象，继续判断，如果还是文件夹，递归
                if (child.isDirectory()) {
                    loadBean(child);
                } else {
                    // 4.2遍历得到File 对象不是文件夹，是文件
                    // 4.3得到包路径+类名称
                    String pathWithClass = child.getAbsolutePath().substring(rootPath.length() - 1);
                    // 4.4判断当前文件类型是否是.class
                    if (pathWithClass.contains(".class")) {
                        // 4.5如果是.class类型，把路径\替换成. 把.class去掉
                        // com.luojia.service.UserServiceImpl
                        String allName = pathWithClass.replaceAll("\\\\", ".").replace(".class", "");
                        // 4.6判断类上面是否有注解@Bean，如果有实例化过程
                        // 4.6.1获取类的Class 对象
                        Class<?> clazz = Class.forName(allName);
                        // 4.6.2判断不是接口(我们注解都是写在实现上，所以此处排除接口)
                        if (!clazz.isInterface()) {
                            // 4.6.3判断类上面是否有注解 @Bean
                            Bean annotation = clazz.getAnnotation(Bean.class);
                            if (annotation != null) {
                                // 4.6.4实例化
                                Object instance = clazz.getConstructor().newInstance();
                                // 4.7把对象实例化之后，放到map集合beanFactory
                                // 4.7.1 判断当前类如果有接口，让接口class作为map的key
                                if (clazz.getInterfaces().length > 0) {
                                    beanFactory.put(clazz.getInterfaces()[0], instance);
                                } else {
                                    beanFactory.put(clazz, instance);
                                }

                            }
                        }
                    }
                }
            }
        }
    }

    // 属性注入
    private void loadDi() {
        // 实例化对象在 beanFactory 的map 集合里面
        // 1.遍历 beanFactory 的map集合
        Set<Map.Entry<Class, Object>> entries = beanFactory.entrySet();
        for (Map.Entry<Class, Object> entry : entries) {
            // 2.获取 map 集合每个对象（value），每个对象的属性都获取到
            Object obj = entry.getValue();
            Class<?> clazz = obj.getClass();

            // 获取每个对象属性
            Field[] declaredFields = clazz.getDeclaredFields();
            // 3.遍历得到每个对象属性数组，得到每个属性
            for (Field field : declaredFields) {
                // 4.判断属性上是否有 @Di注解
                Di annotation = field.getAnnotation(Di.class);
                if (null != annotation) {
                    // 如果私有属性，设置为可以设置值
                    field.setAccessible(true);
                    // 5.如果有 @Di 注解，把对象进行设置（注入）
                    try {
                        // set第一个参数是设置的对象，第二个事值，
                        // 将字段类型取出，比如是service还是dao
                        field.set(obj, beanFactory.get(field.getType()));
                    } catch (IllegalAccessException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        }
    }
}
