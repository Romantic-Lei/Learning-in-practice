/**
 * @Author Romantic-Lei
 * @Date 2020/5/27
 * @description
 */
public class singletonTest07 {
    public static void main(String[] args) {
        Singleton instance = Singleton.INSTANCE;
        Singleton instance1 = Singleton.INSTANCE;
        System.out.println(instance == instance1);

        System.out.println(instance.hashCode());
        System.out.println(instance1.hashCode());

        instance.enumOk();
    }
}

enum Singleton {
    INSTANCE;

    public void enumOk() {
        System.out.println("枚举类，懒汉安全");
    }
}