public class Threads extends Thread  {
    public void run(){
        System.out.println("This is inside function" + Thread.currentThread().getName());
        for(int i = 0;i< 5;i++)
            System.out.println(i);
    }
}
