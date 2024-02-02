public class BasicThread {
    public static void main(String[] args){
        Threads thread = new Threads();
        DaemonThreading daemon = new DaemonThreading();
        System.out.println("This is : " + Thread.currentThread().getName());
        thread.setName("UserThread");
        thread.start();
        // try {
        //     Thread.sleep(1000);
        // } catch (InterruptedException e) {
        //     // TODO Auto-generated catch block
        //     e.printStackTrace();
        // }
        daemon.setDaemon(true);
        daemon.setName("Daemon");
        daemon.start();
        System.out.println("This is" + Thread.currentThread().getName());
    }
}
