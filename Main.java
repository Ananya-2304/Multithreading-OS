public class Main{
    public static void main(String[] args){
        Thread thread = new Thread(new Runnable(){
            public void run(){
                System.out.println("We are in thread" + Thread.currentThread().getName());
                System.out.println("We are in thread" + Thread.currentThread().getPriority());
            }
        });
        thread.setName("hello");
        thread.setPriority(Thread.MAX_PRIORITY);
        System.out.println("We are in thread" + Thread.currentThread().getName() + " before starting");
        thread.start();
        System.out.println("We are in thread" + Thread.currentThread().getName() + " after starting");
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}