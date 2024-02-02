import java.util.*;

class Process {
    int id;
    int arrivalTime;
    int burstTime;
    int priority;
    int startTime;
    int waitingTime;
    int turnaroundTime;
    
    public Process(int id, int arrivalTime, int burstTime, int priority) {
        this.id = id;
        this.arrivalTime = arrivalTime;
        this.burstTime = burstTime;
        this.priority = priority;
        this.startTime = -1;
        this.waitingTime = 0;
        this.turnaroundTime = 0;
    }
}

public class PriorityScheduling {
    public static void main(String[] args) {
        List<Process> processes = new ArrayList<>();
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter the no of processes");
        int n = sc.nextInt();
        // Add processes with their arrival time, burst time, and priority
        for(int i=0;i<n;i++){
            System.out.println("Enter the id,arrival time, burst time and priority for process" + i);
            int pid = sc.nextInt();
            int at = sc.nextInt();
            int bt = sc.nextInt();
            int pri = sc.nextInt();
            processes.add(new Process(pid,at,bt,pri));
        }
        
        // Sort processes by arrival time
        processes.sort(Comparator.comparingInt(p -> p.arrivalTime));
        List<Process> processesCopy = new ArrayList<>(processes);
        PriorityQueue<Process> priorityQueue = new PriorityQueue<>(Comparator.comparingInt(p -> p.priority));
        int currentTime = 0;
        Process currentProcess = null;
        
        System.out.println("Process execution order:");
        
        while (!processes.isEmpty() || currentProcess != null || !priorityQueue.isEmpty()) {
            while (!processes.isEmpty() && processes.get(0).arrivalTime <= currentTime) {
                priorityQueue.add(processes.remove(0));
            }
            
            if (currentProcess != null && !priorityQueue.isEmpty() && priorityQueue.peek().priority < currentProcess.priority) {
                priorityQueue.add(currentProcess);
                currentProcess = priorityQueue.poll();
                currentProcess.waitingTime += currentTime - currentProcess.startTime;
                System.out.println("Preemptively executing Process " + currentProcess.id + " at time " + currentTime);
            } else if (currentProcess == null && !priorityQueue.isEmpty()) {
                currentProcess = priorityQueue.poll();
                currentProcess.startTime = currentTime;
                System.out.println("Executing Process " + currentProcess.id + " at time " + currentTime);
            }
            
            if (currentProcess != null) {
                currentProcess.burstTime--;
                currentTime++;
                
                if (currentProcess.burstTime == 0) {
                    currentProcess.turnaroundTime = currentTime - currentProcess.arrivalTime;
                    currentProcess = null;
                }
            } else {
                currentTime++;
            }
        }
        
        // Print process details
        System.out.println("\nProcess execution details:");
        System.out.println("Process_no\tArrivalTime\tBurst_time\tTurn_Around_Time\tWaiting_Time");
        for (Process process : processesCopy) {
            System.out.println(process.id + "\t\t" + process.arrivalTime + "\t\t" + process.burstTime + "\t\t" + process.turnaroundTime + "\t\t" + process.waitingTime);
        }
        
        // Calculate and print average waiting time and average turnaround time
        int totalWaitingTime = 0;
        int totalTurnaroundTime = 0;
        for (Process process : processesCopy) {
            totalWaitingTime += process.waitingTime;
            totalTurnaroundTime += process.turnaroundTime;
        }
        double averageWaitingTime = (double) totalWaitingTime / processes.size();
        double averageTurnaroundTime = (double) totalTurnaroundTime / processes.size();
        
        System.out.println("\nAverage Waiting Time is: " + averageWaitingTime);
        System.out.println("Average Turnaround Time is: " + averageTurnaroundTime);
    }
}
