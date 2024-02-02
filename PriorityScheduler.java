import java.util.*;

class Process {
    int at, bt, pri, pno;
    Process(int pno, int at, int bt, int pri) {
        this.pno = pno;
        this.pri = pri;
        this.at = at;
        this.bt = bt;
    }
}

class GChart {
    int pno, stime, ctime, wtime, ttime;
}

class MyComparator implements Comparator<Process> {
    public int compare(Process p1, Process p2) {
        if (p1.at < p2.at)
            return -1;
        else if (p1.at == p2.at && p1.pri > p2.pri)
            return -1;
        else
            return 1;
    }
}

class FindGantChart {
    void findGc(LinkedList<Process> queue) {
        int time = 0;
        TreeSet<Process> prique = new TreeSet<>(new MyComparator());
        LinkedList<GChart> result = new LinkedList<>();

        while (!queue.isEmpty())
            prique.add(queue.removeFirst());

        Iterator<Process> it = prique.iterator();
        time = prique.first().at;

        while (it.hasNext()) {
            Process obj = it.next();

            GChart gc1 = new GChart();
            gc1.pno = obj.pno;
            gc1.stime = time;
            time += obj.bt;
            gc1.ctime = time;
            gc1.ttime = gc1.ctime - obj.at;
            gc1.wtime = gc1.ttime - obj.bt;

            result.add(gc1);
        }

        new ResultOutput(result);
    }
}

class ResultOutput {
    ResultOutput(LinkedList<GChart> result) {
        int totalWaitingTime = 0;
        int totalTurnaroundTime = 0;

        System.out.println("\nProcess execution details:");
        System.out.println("Process_no\tTurn_Around_Time\tWaiting_Time");

        for (GChart gc : result) {
            totalWaitingTime += gc.wtime;
            totalTurnaroundTime += gc.ttime;
            System.out.println(gc.pno + "\t\t" + gc.ttime + "\t\t\t" + gc.wtime);
        }

        double averageWaitingTime = (double) totalWaitingTime / result.size();
        double averageTurnaroundTime = (double) totalTurnaroundTime / result.size();

        System.out.println("\nAverage Waiting Time is: " + averageWaitingTime);
        System.out.println("Average Turnaround Time is: " + averageTurnaroundTime);
    }
}

public class PriorityScheduler {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        LinkedList<Process> processes = new LinkedList<>();
        
        System.out.println("Enter the no of processes");
        int n = sc.nextInt();
        
        for(int i = 0; i < n; i++) {
            System.out.println("Enter the id, arrival time, burst time and priority for process " + i);
            int pid = sc.nextInt();
            int at = sc.nextInt();
            int bt = sc.nextInt();
            int pri = sc.nextInt();
            processes.add(new Process(pid, at, bt, pri));
        }
        
        FindGantChart obj = new FindGantChart();
        obj.findGc(processes);
    }
}
