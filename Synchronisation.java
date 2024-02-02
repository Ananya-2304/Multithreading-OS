public class Synchronisation {
    public static void main(String[] args){
        System.out.println("Main thread");

        Stack stack = new Stack(5);
        Thread pushthread = new Thread(()->{
            int i;
            for(i =0;i< 10;i++){
                System.out.println("Pushed" + stack.push(i));
            }
        });
        pushthread.start();
        Thread popthread = new Thread(()->{
            int i = 0;
            for(i=0;i<10;i++){
                System.out.println("Popped" + stack.pop());
            }
        });
        popthread.start();
        System.out.println(Thread.currentThread().getName());
    }
}
