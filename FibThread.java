import java.util.Scanner;

class Fibonacci extends Thread{
    int n;
    int series[];
    public Fibonacci(int n){
        this.n = n;
        series = new int[n];
    }
public void run(){
    series[0] = 0;
    series[1] = 1;
    for(int i =2;i<n;i++){
        series[i] = series[i-1] + series[i-2];
    }
}
void printSeries(){
    for(int i =0;i<n;i++)
        System.out.print(series[i] + " ");
    }
}
public class FibThread {
    public static void main(String[] args) {
    // TODO Auto-generated method stub
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter the number");
        int n = sc.nextInt();
        Fibonacci fib = new Fibonacci(n);
        fib.start();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
        // TODO Auto-generated catch block
            e.printStackTrace();
        }
        fib.printSeries();
    }

}