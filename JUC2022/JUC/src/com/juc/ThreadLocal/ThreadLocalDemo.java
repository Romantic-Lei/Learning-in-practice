package com.juc.ThreadLocal;

import java.util.Random;
import java.util.concurrent.TimeUnit;

class House {
    int saleCount = 0;
    ThreadLocal<Integer> saleVolume = ThreadLocal.withInitial(() -> 0);
    public void saleVolumeByThreadLocal() {
        saleVolume.set(1 + saleVolume.get());
    }
}


public class ThreadLocalDemo {
    public static void main(String[] args) throws InterruptedException {
        House house = new House();

        for (int i = 0; i <= 5; i++) {
            new Thread(() -> {
                int size = new Random().nextInt(5) + 1;
                try {
                    for (int j = 0; j <= size; j++) {
                        house.saleVolumeByThreadLocal();
                    }
                    System.out.println(Thread.currentThread().getName()+"\t"+"号销售卖出："+house.saleVolume.get());
                    house.saleCount = house.saleCount + house.saleVolume.get();
                } finally {
                    house.saleVolume.remove();
                }
            }, String.valueOf(i)).start();
        }

        TimeUnit.MILLISECONDS.sleep(300);
        System.out.println(Thread.currentThread().getName()+"\t共计卖出套数：" + house.saleCount);
    }
}
