package com.juc.cf;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

public class CompletableFutureMallDemo {
    static List<NetMall> list = Arrays.asList(
            new NetMall("jd"),
            new NetMall("dangdang"),
            new NetMall("taobao")
    );

    public static List<String> getPrice(List<NetMall> list, String productName) {
        return list.stream()
                .map(netMall ->
                        String.format(productName+" in %s price is %.2f",
                                netMall.getNetMallName(),
                                netMall.calcPrice(productName)))
                .collect(Collectors.toList());
    }

    public static List<String> getPriceByCompletableFuture(List<NetMall> list, String productName) {
        return list.stream().map(netMall ->
                CompletableFuture.supplyAsync(() ->
                        String.format(productName+" in %s price is %.2f",
                        netMall.getNetMallName(),
                        netMall.calcPrice(productName))))
                .collect(Collectors.toList())
                .stream()
                .map(s -> s.join())
                .collect(Collectors.toList());
    }

    public static void main(String[] args) {
        long startTime = System.currentTimeMillis();
        List<String> list1 = getPrice(list, "mysql");
        for (String element: list1) {
            System.out.println(element);
        }
        long endTime = System.currentTimeMillis();
        // 串行编程调用需要耗时3秒以上
        System.out.println("-----costTime: " + (endTime - startTime) + "毫秒");

        System.out.println("-------------------");

        // 如果需要使用并发线程里面的结果，他还是会阻塞后面的逻辑
        long startTime2 = System.currentTimeMillis();
        List<String> list2 = getPriceByCompletableFuture(list, "mysql");
        for (String element: list2) {
            System.out.println(element);
        }
        long endTime2 = System.currentTimeMillis();
        // 并行编程调用需要耗时仅需要1秒以上
        System.out.println("-----costTime2: " + (endTime2 - startTime2) + "毫秒");


    }

}

class NetMall{

    private String netMallName;

    public NetMall(String netMallName) {
        this.netMallName = netMallName;
    }

    public String getNetMallName() {
        return netMallName;
    }

    public double calcPrice(String productName) {
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return ThreadLocalRandom.current().nextDouble() * 2 + productName.charAt(0);
    }
}
