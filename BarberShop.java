import java.util.concurrent.*;

class BarberShop {
    static final int CHAIRS = 4;
    static Semaphore barber = new Semaphore(0);
    static Semaphore customer = new Semaphore(0);
    static Semaphore mutex = new Semaphore(1);
    static int waitingCustomers = 0;

    static class Barber implements Runnable {
        public void run() {
            while (true) {
                try {
                    customer.acquire(); // Wait for a customer
                    mutex.acquire();
                    waitingCustomers--;
                    barber.release(); // Wake up the barber
                    mutex.release();
                    cutHair(); // Barber cuts hair
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }
            }
        }

        public void cutHair() {
            System.out.println("Barber is cutting hair");
            try {
                Thread.sleep(2000);
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
        }
    }

    static class Customer implements Runnable {
        private int id;

        public Customer(int id) {
            this.id = id;
        }

        public void run() {
            try {
                mutex.acquire();
                if (waitingCustomers < CHAIRS) {
                    waitingCustomers++;
                    System.out.println("Customer " + id + " is waiting.");
                    customer.release(); // Wake up the barber if sleeping
                    mutex.release();
                    barber.acquire(); // Wait for the barber to finish
                    getHaircut(); // Get haircut
                } else {
                    mutex.release(); // No available chairs, leave
                    System.out.println("Customer " + id + " left due to no available chairs.");
                }
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
        }

        public void getHaircut() {
            System.out.println("Customer " + id + " is getting a haircut.");
            try {
                Thread.sleep(3000);
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        Thread barberThread = new Thread(new Barber());
        barberThread.start();

        for (int i = 1; i <= 8; i++) {
            Thread customerThread = new Thread(new Customer(i));
            customerThread.start();

            try {
                Thread.sleep(1000); // Time between customer arrivals
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
        }
    }
}

