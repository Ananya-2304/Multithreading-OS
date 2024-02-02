import java.util.Scanner;
class Process{
    int pid,bt,art;
    Process(int pid,int bt,int art){
        this.pid = pid;
        this.bt = bt;
        this.art = art;
    }
}
public class SRTF {
    public static void main(String[] args){
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter the no of process");
        int n = sc.nextInt();
        Process[] process = new Process[n];
        int wt[] = new int[n];
        int tat[] = new int[n];
        int totalWaitingTime =0, totalTAT= 0;
        System.out.println("Enter the Process id , burst time and Arrival Time");
        for(int i=0;i<n;i++){
            int pid = sc.nextInt();
            int bt = sc.nextInt();
            int art = sc.nextInt();
            process[i] = new Process(pid,bt,art);
        }
        findWaitingTime(process,wt,n);
        findTurnAroundTime(process, wt,tat, n);
        System.out.printf("Processes BurstTime ArrivalTime WaitingTime TurnAroundTime\n");

		// Calculate total waiting time and total turn
		// around time
		for (int i = 0; i < n; i++) {
			totalWaitingTime += wt[i];
            totalTAT += tat[i];
			System.out.printf(" %d ", (i + 1));
			System.out.printf("\t   %d ", process[i].bt);
            System.out.printf("\t   %d ", process[i].art);
			System.out.printf("\t\t%d", wt[i]);
			System.out.printf("\t\t%d\n", tat[i]);
		}
		float s = (float)totalWaitingTime /(float) n;
		float t = (float)totalTAT / (float)n;
		System.out.printf("Average waiting time = %.2fms", s);
		System.out.printf("\n");
		System.out.printf("Average turn around time = %.2fms ", t);
}

    public static void findTurnAroundTime(Process[] process, int[] wt,int[] tat,int n){
        for(int i=0;i<n;i++)
            tat[i] = process[i].bt +wt[i];
    }
    private static void findWaitingTime(Process[] process, int[] wt,int n) {
        boolean check = false;
        int finish_time , shortest =0 ,minm = Integer.MAX_VALUE,t=0,complete =0;
        //To store the remaining time
        int [] rt = new int[n];
        for(int i =0;i<n;i++)
            rt[i] = process[i].bt ;
        while(complete != n){
            //To find the shortest process out of all the processes that have arrived
            for(int i=0;i<n;i++){
                if(process[i].art<=t && rt[i]<minm && rt[i] >0){
                    minm = rt[i];
                    shortest = i;
                    check = true;
                }
            }
            //If no process has arrived at the given time
            if(check == false){
                t++;
                continue;
            }
            //Update remaining time and minmalprocess
            rt[shortest]--;
            minm = rt[shortest];
            if(minm == 0){
                minm = Integer.MAX_VALUE;
            }
            //remaining time of given process is zero,so process complete
            if(rt[shortest]==0){
                complete++;
                check = false;
                finish_time = t+1;
                wt[shortest] = finish_time - process[shortest].bt - process[shortest].art;
                if(wt[shortest]<0)
                    wt[shortest] =0;
            }
            t++;
        }
    }
}
