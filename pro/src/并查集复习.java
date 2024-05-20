import java.util.HashMap;
import java.util.Scanner;
import java.util.Stack;


public class 并查集复习 {

    public static void main(String[] args) {

        Scanner s = new Scanner(System.in);

        System.out.println("请输入你需要的元素个数");
        int n = s.nextInt();

        System.out.println("请输入所有元素");

        Integer[] arr = new Integer[n];
        HashMap<Integer, Integer> map = new HashMap<>();
        //（测试时的使用，通过数字查找他所在的Integer   仅在这个地方测试使用，重要！！！

        for(int i = 0; i < n; i++) {

            int a = s.nextInt();
            arr[i] = a;
            map.put(a , arr[i]);
        }//完成输入
        s.nextLine();

        Union<Integer> union = new Union<>(arr);//构成集合

        boolean key = true;

        while(key){

            System.out.println("请输入你需要进行的操作：");
            System.out.println("1.合并两个节点");
            System.out.println("2.检查两个节点时候在同一集合中");
            System.out.println("3.退出");

            char c = s.nextLine().charAt(0);

            switch(c){

                case '1':
                    System.out.println("输入两个点：");

                    union.toUnion(map.get(s.nextInt()) , map.get(s.nextInt()));

                    s.nextLine();
                    break;

                case '2':
                    System.out.println("输入两个点：");

                    System.out.println(union.checkSameUnion(map.get(s.nextInt()) , map.get(s.nextInt())));

                    s.nextLine();
                    break;

                case '3':
                default:
                    key = false;
            }


        }

        s.close();
    }

    public static class Element<T>{
        //包装类
        T value;//集合中元素

        T symbol;//集合代表点
        
        int size;

        Element(T value){

            this.value = value;
            this.symbol = value;
            size = 1;
        }//最初所有集合的包装点都是本身//这个包装类相当于就是一个集合

    }

    public static class Union<T>{

        HashMap<T , Element<T>> foundElement;

        Union(T[] values){

            foundElement = new HashMap<>();

            for(T value : values){

                Element<T> element = new Element<>(value);
                foundElement.put(value , element);
            }
        }

        public void toUnion(T a , T b){
            //合并两个集合

            Element<T> aElement = foundElement.get(a);//找到对应的集合
            Element<T> bElement = foundElement.get(b);

            if(aElement.size > bElement.size){

                bElement.symbol = a;
                aElement.size += bElement.size;
                bElement.size = aElement.size;
            }else{

                aElement.symbol = b;
                bElement.size +=aElement.size;
                aElement.size = bElement.size;
            }//将小的集合挂在大的集合下面，并将这小的集合的代表点挂在大的集合下
        }

        public boolean checkSameUnion(T a , T b){
            //检查两个点是否是相同的集合
            /**
             * 规定在查询操作的时候对链上的数据进行处理
             * 例如 1 -> 2 -> 3 -> 4（在合并集合挂点的时候挂成这样了
             * 1 -> 4   2 -> 4  3 -> 4 将这些点都挂在代表点上，为了使得后面的查找的时间复杂度为1
             *
             */

            Element<T> aElement = foundElement.get(a);
            Element<T> bElement = foundElement.get(b);

            Stack<Element<T> > help = new Stack<>();

            T t = a;
            Element<T> e = foundElement.get(a);
            while(e.symbol != t){

                help.push(e);
                t = e.symbol;
                e = foundElement.get(e.symbol);
            }

            //出循环之后，说明e.symbol == t，找到了最终的集合代表点
            while(!help.isEmpty()){

                e = help.pop();
                e.symbol = t;//将集合代表点挂好
            }//将a集合代表点挂好


            t = b;
            e = foundElement.get(b);

            while(e.symbol != t){

                help.push(e);
                t = e.symbol;
                e = foundElement.get(e.symbol);

            }

            while(!help.isEmpty()){

                e = help.pop();
                e.symbol = t;
            }//将b集合代表点挂好


            return aElement.symbol == bElement.symbol;
        }
        
    }

}
