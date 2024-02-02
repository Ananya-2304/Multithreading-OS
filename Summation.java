import java.util.Scanner;

class Sum extends Thread{
    int n,sum;
    public Sum(int n){
        this.n = n;
        sum =0;
    }
    public void run(){
        for(int i=0;i<=n;i++)
            sum += i;
    }
    public void printSum(){
        Thread.currentThread().setName("Summation Thread");
        System.out.println("Executing in Thread :" + Thread.currentThread().getName());
        System.out.println("The sum of all non negative numbers upto "+ n + " is " + sum);
    }
}
class Summation {

    public static void main(String[] args) {
    // TODO Auto-generated method stub
        Thread.currentThread().setName("Main");
        System.out.println("Executing in Thread :" + Thread.currentThread().getName());
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter the number");
        int n = sc.nextInt();
        Sum obj = new Sum(n);
        obj.start();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
        // TODO Auto-generated catch block
            e.printStackTrace();
        }
        obj.printSum();
    }

}

