public class question13 {

    /**
     * 给定一个未排序的数组
     * 判断对哪个最小连续区间进行排序可以使这个数组有序
     * 
     * 思路：
     * 先从左向右遍历，确定最右边不用排序部分（已经有序部分
     * 再从右向左遍历，确定最左边不用排序部分（已经有序部分
     * 
     * 实现：
     * 从左往右遍历时，记录一个数max，指的是遍历过的部分的最大值
     * 确定最后一个不合规的位置
     * 不合规的位置：
     *  小于max的位置
     * 从右往左遍历时同理
     */
    
    public static Information getUnsortedRange(int[] nums) {
        
        int max = nums[0];
        int indexLeft = 0;
        int min = nums[nums.length - 1];
        int indexRight = nums.length - 1;
        
        for( int i = 1; i < nums.length; i++) {
            
            if(nums[i] > max) {

                max = nums[i];
                indexLeft = i;
            }
        }


        for( int i = nums.length - 2; i >= 0; i--){

            if(nums[i] < min) {

                min = nums[i];
                indexRight = i;
            }
        }

        return new Information(indexLeft, indexRight);
    }
    
    static class Information{
        
        int leftIndex;
        int rightIndex;
        
        
        public Information(int leftIndex, int rightIndex) {
            
            this.leftIndex = leftIndex;
            
            this.rightIndex = rightIndex;
        }
    }
}
