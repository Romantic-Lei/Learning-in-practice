package leetcode.leetcode150.array;

public class Gas134 {

    /**
     * https://leetcode.cn/problems/gas-station/?envType=study-plan-v2&envId=top-interview-150
     * 在一条环路上有 n 个加油站，其中第 i 个加油站有汽油 gas[i] 升。
     *
     * 你有一辆油箱容量无限的的汽车，从第 i 个加油站开往第 i+1 个加油站需要消耗汽油 cost[i] 升。你从其中的一个加油站出发，开始时油箱为空。
     *
     * 给定两个整数数组 gas 和 cost ，如果你可以按顺序绕环路行驶一周，则返回出发时加油站的编号，否则返回 -1 。如果存在解，则 保证 它是 唯一 的。
     * @param gas
     * @param cost
     * @return
     */
    public int canCompleteCircuit(int[] gas, int[] cost) {
        int sum = 0;
        int min = 0;
        int start = 0;
        for (int i = 0; i < gas.length; i++) {
            sum = gas[i] - cost[i] + sum;
            // 寻找却油量最多的地点
            if (sum < min) {
                min = sum;
                start = i + 1;
            }
        }

        if (sum < 0) {
            // 总耗油量大于总补给量，一定无法到达终点
            return -1;
        }

        // 取模目的是为了方式 start正常是数据长度，然后出发点会类似越界
        return start % gas.length;
    }

}
