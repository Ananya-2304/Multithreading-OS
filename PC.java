import java.util.concurrent.Semaphore;
import java.util.Random;
class SharedBuffer {
    private int buffersize;
    private int[] buffer;
    private int front,rear;

    public SharedBuffer(int size){
        buffersize = size;
        buffer = new int[size];
        front = rear = 0;
    }
    public void produce(int item){
        //Locks into the critical section and adds item into the buffer
        //instance of the curent class is used as the lock
        synchronized(this){
            buffer[front] = item;
            rear = (rear+1)%buffersize;
        }
        System.out.println("The produced item is :" + item);
    }
    public int consume(){
        int item ;
        synchronized(this){
            item = buffer[rear];
            front = (front+1)%buffersize;
        }
        System.out.println("The consumed item is :"+ item);
        return item;
    }
}
class Producer implements Runnable{
    private SharedBuffer buffer;
    private Semaphore producerSemaphore;
    private Semaphore consumerSemaphore;

    public Producer(SharedBuffer buffer , Semaphore producerSemaphore , Semaphore consumerSemaphore){
        this.buffer = buffer;
        this.producerSemaphore = producerSemaphore;
        this.consumerSemaphore = consumerSemaphore;
    }
    @Override
    public void run(){
        try{
            while(true){
                int item = produceItem();
                //blocks a permit(item) available
                producerSemaphore.acquire();
                buffer.produce(item);
                //increases the consumer permits
                consumerSemaphore.release();
                Thread.sleep(1000);
            }
        }
        catch(InterruptedException e){
            Thread.currentThread().interrupt();
        }
    }
    private int produceItem() {
        Random rand = new Random();
        return rand.nextInt(100); // Generate a random item
    }
}
class Consumer implements Runnable {
    private SharedBuffer buffer;
    private Semaphore producerSemaphore;
    private Semaphore consumerSemaphore;

    public Consumer(SharedBuffer buffer, Semaphore producerSemaphore, Semaphore consumerSemaphore) {
        this.buffer = buffer;
        this.producerSemaphore = producerSemaphore;
        this.consumerSemaphore = consumerSemaphore;
    }

    @Override
    public void run() {
        try {
            while (true) {
                consumerSemaphore.acquire();
                buffer.consume();
                producerSemaphore.release();
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
public class PC {
    public static void main(String[] args){
        int buffersize = 5;
        SharedBuffer buffer = new SharedBuffer(buffersize);
    
        //to ensure that producer produces first
        Semaphore producerSemaphore = new Semaphore(buffersize);
        Semaphore consumerSemaphore = new Semaphore(0);
    
        Thread producerThread  = new Thread(new Producer(buffer,producerSemaphore,consumerSemaphore));
        Thread consumerThread  = new Thread(new Consumer(buffer,producerSemaphore,consumerSemaphore));
        
        producerThread.start();
        consumerThread.start();
    }
}
