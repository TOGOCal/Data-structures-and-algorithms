import java.util.HashSet;

public class 图算法_拼矩形 {
    /**
     * 问题背景
     * 一个矩形可以由左下角和右上角进行代表
     * 给定一众矩形
     * 判断这个这些矩形是否构成了一个完美矩形
     * 给定完美矩形定义：
     *  内部没有重叠，没有凹陷，没有突出，所有矩形正好拼成一块
     *
     *  算法介绍：
     *  完美矩形满足下面两个条件：
     *  设定四个值
     *  大矩形能够到达的最大x，最小x，最大y，最小y
     *  1.在没有重叠的情况下，所有小长方形的面积正好等于大长方形的面积（通过最大x，y计算
     *  2.除了大长方形的四个顶点出现一次之外，其余的点一定出现偶数次
     */

    class Solution{

        public boolean isPerfectRectangle(Rectangle[] rectangles){

            int maxX = Integer.MIN_VALUE;
            int minX = Integer.MAX_VALUE;

            int maxY = Integer.MIN_VALUE;
            int minY = Integer.MAX_VALUE;

            int area = 0;

            //用于验证是否所有点只出现了偶数次（除了顶点
            HashSet<String> set = new HashSet<>();

            //处理所有的点
            for(Rectangle rectangle:rectangles){

                maxX = Math.max(maxX,rectangle.rightUp.x);
                minX = Math.min(minX,rectangle.leftDown.x);

                maxY = Math.max(maxY,rectangle.rightUp.y);
                minY = Math.min(minY,rectangle.leftDown.y);

                area += rectangle.getSize();

                if(set.contains(rectangle.leftDown.toString())){

                    set.remove(rectangle.leftDown.toString());
                }else{

                    set.add(rectangle.leftDown.toString());
                }


            }

            if(!set.contains(new Point(minX,minY).toString()) || !set.contains(new Point(minX,maxY).toString()) || !set.contains(new Point(maxX,minY).toString()) || !set.contains(new Point(maxX,maxY).toString() )
                    || set.size()!=4
                    || area != (maxX - minX)*(maxY - minY)){

                return false;
            }


            return true;

        }

    }


    class Rectangle{

        Point leftDown;
        Point rightUp;


        Rectangle(Point leftDown,Point rightUp){

            this.leftDown = leftDown;
            this.rightUp = rightUp;
        }


        public int getSize(){

            return (rightUp.x - leftDown.x)*(rightUp.y - leftDown.y);
        }
    }

    class Point{

        int x;
        int y;


        Point(int x,int y){

            this.x = x;
            this.y = y;
        }

        @Override
        public String toString() {
            return x + ","+y;
        }
    }

}
