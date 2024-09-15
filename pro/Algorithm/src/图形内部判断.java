public class 图形内部判断 {

    /**
     * 题目背景：
     * 给定一个矩形的四个顶点，（可能不与坐标轴平行
     * 再给定一个点，判断这个点是否在矩形内部
     */


    /**
     * 题目二：
     * 如果给定一个三角形的三个点
     * 再给定一个点，判断这个点是否在三角新内部
     */



    /**
     * 思路解析：
     * 如果矩形是平行于坐标轴的，判断其实是非常简单的
     * 只需要比对x，y与边界x，y的关系就可以了，所以我们可以考虑使用坐标轴旋转将矩形转成平行于坐标轴的就可以进行判断了
     */

    //保证给定的点的顺序是顺时针旋转得到的点
    class Soution{

        //x y
        public boolean isInside(double[][] rectangle, double[] point) {


            double h = rectangle[1][1] - rectangle[0][1];
            double l = rectangle[1][0] - rectangle[0][0];

            double sqrt = Math.sqrt(h * h + l * l);
            double sin = h / sqrt;
            double cos = l / sqrt;

            //方法：
            //x后 = x原*cos + y原*sin
            //y后 = -x原*sin + y原*cos

            double x0 = rectangle[0][0] * cos + rectangle[0][1] * sin;
            double y0 = -rectangle[0][0] * sin + rectangle[0][1] * cos;

            double x2 = rectangle[2][0] * cos + rectangle[2][1] * sin;
            double y2 = -rectangle[2][0] * sin + rectangle[2][1] * cos;

            double x = point[0] * cos + point[1] * sin;
            double y = -point[0] * sin + point[1] * cos;


            return x >= x0 && x <= x2 && y >= y0 && y <= y2;
        }
    }


    /**
     * 思路解析：
     * 普安段一个点在三角形内部的原有方式：
     * 如果点在三角形内部
     * 则AOB + BOC + AOC 的面积等于 ABC的面积，否则在外面
     * 但是问题四，double类型有精度耗损，导致面积常常不相等
     * 所以需要考虑其他方法
     * 其他方法：
     * 向量AO AB  BO BC    CO CA 叉乘结果的正负性应该一样
     */

    class Solution2{

        class Point{

            public double x;
            public double y;


            public Point(double x, double y) {

                this.x = x;
                this.y = y;
            }

        }

        public int symbolForkMultiplicationResult(Point a , Point b){

            int result = (int)((a.x * b.y) - (a.y * b.x));

            if(result > 0){

                return 1;
            }else if(result < 0){

                return -1;
            }

            return 0;

        }


        public boolean isInside(Point[] points, Point aimPoint) {

            //向量ab
            Point a_b = new Point(points[0].x - points[1].x, points[0].y - points[1].y);
            Point a_o = new Point(points[0].x - aimPoint.x, points[0].y - aimPoint.y);

            //正数为正，负数为负
            boolean key;

            int res = symbolForkMultiplicationResult(a_b, a_o);

            if(res == 0){

                return true;
            }


            key = res > 0;

            Point b_c = new Point(points[1].x - points[2].x, points[1].y - points[2].y);
            Point b_o = new Point(points[1].x - aimPoint.x, points[1].y - aimPoint.y);

            res = symbolForkMultiplicationResult(b_c, b_o);

            if(res == 0){
                return true;
            }


            if(key != (res > 0)){

                return false;
            }


            Point c_a = new Point(points[2].x - points[0].x, points[2].y - points[0].y);
            Point c_o = new Point(points[2].x - aimPoint.x, points[2].y - aimPoint.y);

            res = symbolForkMultiplicationResult(c_a , c_o);


            if(res == 0){

                return true;
            }


            return key == (res > 0);


        }
    }

}
