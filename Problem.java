import java.util.concurrent.Semaphore;

public class Problem {
    public Problem() {
        currentState = TABLE_EMPTY;
        //So that agent places the items first
        mutex = new Semaphore(1);
        
        Thread[] thread = new Thread[4];

        thread[0] = new Agent(this, 0);

        // Second parameter to Smoker constructor is the item the smoker _has_.

        thread[1] = new Smoker(this, PAPER, 1);
        thread[2] = new Smoker(this, TOBACCO, 2);
        thread[3] = new Smoker(this, MATCHES, 3);

        for (int i = 0; i < 4; i++)
            thread[i].start();
    }

    // Ignore the unused id parameters to these methods.  They were needed
    // in the busy-wait-free version from which this was ported.

    public void dispenseItems(int id) {
        int itemNotOnTable;

        while (true) {
            try {
                mutex.acquire();
                if (currentState == TABLE_EMPTY) {
                    itemNotOnTable = 1 + (int)(Math.random() * 3.0);
                    if (itemNotOnTable == 1) {
                        itemOnTable1 = 2;
                        itemOnTable2 = 3;
                    }
                    else if (itemNotOnTable == 2) {
                        itemOnTable1 = 1;
                        itemOnTable2 = 3;
                    }
                    else {
                        itemOnTable1 = 1;
                        itemOnTable2 = 2;
                    }

                    System.out.println("Agent puts items " + itemOnTable1
                               + " and " + itemOnTable2 + " on table");

                    currentState = ITEM_AVAILABLE;
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            } finally {
                mutex.release();
                Thread.yield(); // these are necessary!
            }
        }
    }

    public void waitForItem(int item, int id) {
        boolean gotItem = false;

        while (!gotItem) {
            try {
                mutex.acquire();
                if (currentState == ITEM_AVAILABLE && (itemOnTable1 != item
                                                       && itemOnTable2 != item)) {
                    currentState = ITEM_IN_USE;
                    gotItem = true;
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            } finally {
                mutex.release();
                Thread.yield();
            }
        }
    }

    public void finishSmoking(int id) {
        try {
            mutex.acquire();
            currentState = TABLE_EMPTY;
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        } finally {
            mutex.release();
        }
    }

    public static void main(String[] args) {
        Problem p = new Problem();
    }

    protected Semaphore mutex;

    protected int currentState;
    public final int ITEM_AVAILABLE = 1;
    public final int ITEM_IN_USE = 2;
    public final int TABLE_EMPTY = 3;

    protected int itemOnTable1;
    protected int itemOnTable2;
    public final int PAPER = 1;
    public final int TOBACCO = 2;
    public final int MATCHES = 3;
}
class Agent extends Thread {
    private Problem problem;
    private int id;

    public Agent(Problem problem, int id) {
        this.problem = problem;
        this.id = id;
    }

    @Override
    public void run() {
        while (true) {
            try {
                Thread.sleep(1000); // Simulate some time before the agent dispenses items
                problem.dispenseItems(id);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }
}

class Smoker extends Thread {
    private Problem problem;
    private int item;
    private int id;

    public Smoker(Problem problem, int item, int id) {
        this.problem = problem;
        this.item = item;
        this.id = id;
    }

    @Override
    public void run() {
        while (true) {
            try {
                problem.waitForItem(item, id);
                System.out.println("Smoker " + id + " is now smoking.");
                Thread.sleep(2000); // Simulate smoking
                problem.finishSmoking(id);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }
}
