package leetcode.leetcode150.array;

public class Jieyushui42 {

    public static int trap(int[] height) {
        int length = height.length;
        int[] preMax = new int[length];
        int[] sufMax = new int[length];
        int ans = 0;
        preMax[0] = height[0];
        sufMax[length-1] = height[length-1];
        for (int i = 1; i < length; i++) {
            preMax[i] = Math.max(height[i], preMax[i-1]);
        }

        for (int i = length-2; i >= 0; i--) {
            sufMax[i] = Math.max(height[i], sufMax[i+1]);
        }

        for (int i = 0; i < length; i++) {
            ans += Math.min(preMax[i], sufMax[i]) - height[i];
        }

        return ans;
    }

    public static void main(String[] args) {
        int[] r = new int[]{4,2,0,3,2,5};
        System.out.println(trap(r));
    }

}
