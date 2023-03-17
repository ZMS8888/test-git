package com.zm.algorithm;

import org.junit.Test;

public class AlgorithmTest {

    private static int search(int[] nums, int target) {
        int low = 0;
        int length = nums.length-1;
        int result = -1;
        while (low <= length) {
            int mid = (low + length) / 2;
            if (nums[mid] > target) {
                length = mid - 1;
            } else if (nums[mid] < target) {
                low = mid + 1;
            } else {
                return mid;
                //low = length+1;
            }
        }
        return result;
    }

    /**
     *  查找指定升序数组中是否有目标数
     *  二分法
     *
     */
    @Test
    public void dichotomySearch() {
        int[] nums = new int[]{2,4,8};
        int target = 5;
        int search = search(nums, target);
        System.out.println(search);
    }
}
