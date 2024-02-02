 import java.util.concurrent.*;

class SleepingBarberProblem extends Thread{
    public static Semaphore barber = new Semaphore(0);
    public static Semaphore customers = new Semaphore(0);
    public static Semaphore mutex = new Semaphore(1);
    static final int CHAIRS = 5;
    static int noOfEmptyChairs = CHAIRS;
    static final int CUSTOMER_MAX = 7;

    public static void main(String args[]) {
        SleepingBarberProblem barbershop = new SleepingBarberProblem();
        barbershop.start();
    }

    public void run() {
        SleepingBarber barberA = new SleepingBarber();
        barberA.start();

        for (int i = 1; i <= CUSTOMER_MAX; i++) {
            Customer newCustomer = new Customer(i);
            newCustomer.start();
        }

        try {
            Thread.sleep(1000);
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
    }
}

class SleepingBarber extends SleepingBarberProblem implements Runnable {
    public void run() {
        while (true) {
            try {
                customers.acquire();
                mutex.acquire();
                noOfEmptyChairs++;
                barber.release();
                mutex.release();
                this.cutHair();
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
        }
    }

    public void cutHair() {
        System.out.println("The barber is cutting hair");
        try {
            Thread.sleep(1000);
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
    }
}

class Customer extends SleepingBarberProblem implements Runnable {
    int A;
    boolean notCut = true;

    public Customer(int u) {
        A = u;
    }

    public void run() {
        while (notCut) {
            try {
                mutex.acquire();
                if (noOfEmptyChairs > 0) {
                    System.out.println("Customer " + this.A + " simply took a seat.");
                    noOfEmptyChairs--;
                    customers.release();
                    mutex.release();
                    try {
                        barber.acquire();
                        notCut = false;
                        this.getHaircut();
                    } catch (InterruptedException ex) {
                        ex.printStackTrace();
                    }
                } else {
                    System.out.println("There are no free seats. Customer " + this.A + " has left the barbershop.");
                    mutex.release();
                    notCut = false;
                }
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
        }
    }

    public void getHaircut() {
        System.out.println("Customer " + this.A + " is getting his hair cut");
        try {
            Thread.sleep(1000);
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
    }
}
