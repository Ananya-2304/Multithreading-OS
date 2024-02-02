public class Stack{
    int stk[];
    int top;
    Object lock;
    public Stack(int capacity){
        stk = new int[capacity];
        top = -1;
        lock = new Object();
    }
    public boolean push(int element){
        synchronized(lock){
            if(isFull()){
            return false;
        }
        top++;
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        stk[top] = element;
        return true;
        }
        
    }
    public int pop(){
        synchronized(lock){
            if(isEmpty())
                return Integer.MIN_VALUE;
            int ele = stk[top];
            stk[top] = Integer.MIN_VALUE;
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            top--;
            return ele;
        }
    }
    public boolean isFull(){
        if(top == stk.length - 1)
            return true;
        return false;
    }
    public boolean isEmpty(){
        if(top <= -1)
            return true;
        return false;
    }
}