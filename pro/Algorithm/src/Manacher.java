import java.util.Scanner;

public class Manacher {

    /**
     * 算法经典解决问题：
     * 判断一个字符串的最长回文子串长度
     */

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        String str = sc.nextLine();

        ClassicalWay cw = new ClassicalWay();
        ManacherWay mw = new ManacherWay();

        System.out.println(cw.mainMethod(str));
        System.out.println(mw.mainMethod(str));

        sc.close();
    }

    //经典算法（以下使用时简称暴力过程
    public static class ClassicalWay{
        /**
         * 算法简述：
         * 遍历字符串中每个元素，从每个元素向外扩（两个指针向外移动，碰到边界或冲边界结束
         * 得出每个元素的最长回文串
         *
         * 存在的问题：
         * 如果回文串是偶数，
         * 明显的，存在的问题是如果对称轴是虚轴，则会导致无法从虚轴开始向外扩
         * 解决方法：再每个可能存在虚轴的位置加入一个相同元素代替虚轴，进行上述过程
         *
         * 明显的，对于最差情况1111111111111111111，每个字符的时间复杂度都是N，导致总时间复杂度为O(N2)
         * 这好吗，这不好
         */

        public char[] changeString(String str){

            char[] chars = str.toCharArray();

            char[] result = new char[chars.length*2+1];

            result[0] = '#';

            for(int i=0; i<chars.length; i++){

                result[2*i +1 ] = chars[i];
                result[2 *i+2 ] = '#';
            }

            return result;
        }

        public int mainMethod(String str){

            char[] chars = changeString(str);

            int maxLength = 0;

            for(int i=0; i<chars.length; i++){

                int p1,p2;
                p1 = p2 = i;
                int r = 0;//回文半径
                while(p1>=0 && p2<chars.length && chars[p1] == chars[p2]){

                    if(chars[p1]!='#'){

                        r++;
                    }
                    p1 --;
                    p2++;

                }

                if(chars[i] == '#'){

                    r *=2;
                }else{

                    r = (r-1)*2 +1;//长度
                }

                if(r>maxLength){

                    maxLength = r;
                }
            }

            return maxLength;
        }

    }

    public static class ManacherWay{
        /**
         * 算法简述
         * 现在我们先明确几个概念：
         *
         * 回文串长度length，也可叫做回文直径
         *
         * 回文半径r
         *
         * 最长回文串访问边界R
         * 例如：在分析第index位置的字符时，回文串较长，回文边界就访问到了很后面，代表目前回文串已经访问过哪里了
         *
         * 回文中心center，
         * 该概念与R伴生，当R发生变动时，center也会跟着发生改变，代表这个回文边界对应的回文串的中心（对称轴
         *
         * 遍历字符串，并对每一index进行分类讨论
         * 分类1：
         *  index在R外面
         *      这种情况下，无法进行加速，只能像经典暴力算法对该情况进行分析
         * 分类2：
         *  index在R里
         *  这时候，可以根据index的位置以及center的位置找到对称点的位置index'
         *  算法的一定程度加速可以通过 index' 的位置的情况来分析index的位置情况
         *  以下index' 简称为i
         *  分类2.1：
         *      i位置对应的回文串完全在R_center回文串内部
         *          在这种情况下，index位置与i位置的回文串情况完全相同，可以直接赋值
         *  分类2.2：
         *      i位置的回文串一部分超过了R_center回文串
         *          在这种情况下，index位置的回文串就是index到R边界的举例，原因如下：
         *          例如情况：
         *             |                 |
         *          OOO|###OOO~~~~~OOO###|未遍历区域（设为x
         *             | i      c      a |
         *          目前遍历到了a位置，对称点是i，根据之前计算的记录，i的回文串OOO###OOO超过了R_center的范围
         *          此时我们可以i推导：i回文串中 O == O，但是R_center在包括的时候并没有包括x区域
         *          说明 x！=O（i左侧的
         *          由于c回文，所以 a左侧==i右侧==i左侧!=a右侧，得到a左侧!=a右侧
         *          所以a回文串的长度就是index位置到边界的距离
         *  分类2.3：
         *      i位置的回文串刚好与R_center边界重合
         *          在此情况下，需要调用经典暴力方法，但是开始比较的点是外面的点（还是简化了时间
         * 方法时间复杂度分析：
         *  方法在循环的时候其实就只有两种情况：
         *      需要调用经典算法的情况：
         *          调用经典算法，则R边界一定往外走，最多走n个距离 ，所以最差情况时间复杂度O(N)
         *      不需要调用经典算法的情况：
         *          不调用经典算法，则每次赋值的时候都是直接调用已有数值，时间复杂度O(1)
         *          所以就算所有index都走这条分支，导致的时间复杂度都是O(1)
         * 最坏的情况就是两种时间加起来，但是很不幸的是，总时间不可能等于两个加起来，
         * 况且，就算加起来，时间复杂度还是O(N)，所以时间复杂度就是O(N)
         *
         * 处理添加好了#的回文半径：
         * 通过规律可以发现，任何一个情况的回文半径-1就是删除#后原串的长度，也就是我们需要的答案
         * 例如：#1#2#1#，回文半径#1#2，长度4，原串的长度3
         * #1#1#回文半径#1#吗，长度3，原串长度2
         * 原理分析：
         *  每一种字符串的的开头和结尾一定是#（为什么要在changeString的时候前后加入#的原因
         *  其他的原理很简单，可以简答理解为找规律
         *
         */

        public int mainMethod(String str){

            if(str == null || str.isEmpty()){

                return 0;
            }

            ClassicalWay cw = new ClassicalWay();
            char[] chars = cw.changeString(str);

            int[] r = new int[chars.length];

            int center = -1;
            int R = -1;
            int maxLength = 0;

            for(int index=0; index<chars.length; index++){

                r[index] = ( R > index ? Math.min(r[2*center - index] , R - index) : 1 );
                //最少啊不用计算的半径长度
                //翻译：i在R的范围内吗
                //在：选取对称结果中和到边界距离小的那个 为什么要分析边界：情况2.2
                //不在：只有一个字符构成回文字符串，长度1

                while( index + r[index] < chars.length && index - r[index] >= 0 ){

                    if(chars[index + r[index]] == chars[index - r[index]]){

                        r[index] ++;
                    }else {

                        break;
                    }

                    if(index + r[index] > R){

                        R = index + r[index];
                        center = index;
                    }

                }//当然可以使用if else 对程序进行构建
                //这种写法只是为了代码看起来短，对于时间复杂度而言
                //不管是那种情况，都会进行一次比较if else 会进行比较，这种把所有情况统合的写法在本来不需要比较的时候比较
                //不管哪种写法都会进行一次比较，所以时间复杂度是一样的

                maxLength = Math.max(maxLength , r[index]);
            }

            return maxLength-1;
        }

    }
}
