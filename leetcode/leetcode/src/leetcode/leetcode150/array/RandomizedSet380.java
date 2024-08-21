package leetcode.leetcode150.array;

import java.util.*;

public class RandomizedSet380 {

    /**
     * https://leetcode.cn/problems/insert-delete-getrandom-o1/description/?envType=study-plan-v2&envId=top-interview-150
     * 380. O(1) 时间插入、删除和获取随机元素
     *
     * 实现RandomizedSet 类：
     *
     * RandomizedSet() 初始化 RandomizedSet 对象
     * bool insert(int val) 当元素 val 不存在时，向集合中插入该项，并返回 true ；否则，返回 false 。
     * bool remove(int val) 当元素 val 存在时，从集合中移除该项，并返回 true ；否则，返回 false 。
     * int getRandom() 随机返回现有集合中的一项（测试用例保证调用此方法时集合中至少存在一个元素）。每个元素应该有 相同的概率 被返回。
     * 你必须实现类的所有函数，并满足每个函数的 平均 时间复杂度为 O(1)
     */

    List<Integer> nums;
    Map<Integer, Integer> indices;
    Random random;

    public RandomizedSet380() {
        nums = new ArrayList<>();
        indices = new HashMap<>();
        random = new Random();
    }

    public boolean insert(int val) {
        // 新增数据，如果判断在Map中不存在，则数据需要新增
        if (!indices.containsKey(val)) {
            nums.add(val);
            // key - val
            // value - 数组下标
            indices.put(val, nums.size() - 1);
            return true;
        }
        return false;
    }

    public boolean remove(int val) {
        if (indices.containsKey(val)) {
            int index = indices.get(val);
            // 不能直接 remove，如果val不在最后，移除后下标全乱了
            // nums.remove(index);
            // indices.remove(val);

            // 将最后一个元素替换到需要删除元素的位置,并修改Map下标
            int last = nums.get(nums.size() - 1);
            nums.set(index, last);
            indices.put(last, index);
            nums.remove(nums.size() - 1);
            indices.remove(val);
            return true;
        }
        return false;
    }

    public int getRandom() {
        int i = random.nextInt(nums.size() );
        Integer num = nums.get(i);
        return num;
    }

    public static void main(String[] args) {
        RandomizedSet380 randomizedSet380 = new RandomizedSet380();

        // ["RandomizedSet","insert","remove","insert","getRandom","remove","insert","getRandom"]
        // [[],[1],[2],[2],[],[1],[2],[]]
        // [null,true,false,true,2,true,false,2]
        System.out.println(randomizedSet380.insert(1));
        System.out.println(randomizedSet380.remove(2));
        System.out.println(randomizedSet380.insert(2));
        System.out.println(randomizedSet380.getRandom());
        System.out.println(randomizedSet380.remove(1));
        System.out.println(randomizedSet380.insert(2));
        System.out.println(randomizedSet380.getRandom());

    }
}
