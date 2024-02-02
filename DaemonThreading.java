public class DaemonThreading extends Thread{
    public void run(){
        System.out.println(Thread.currentThread().getName());
        for(int i = 5;i>=0;i--)
            System.out.println(i);
    }
}
