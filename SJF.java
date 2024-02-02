import java.util.*;

 // Compiler version JDK 11.0.2
class Process{
  int id,bt, art;
  public Process(int id, int bt, int art){
    this.id= id;
    this.bt= bt;
    this.art = art;
  }
}
 class SJF
 {
   public static void main(String args[])
   { 
    Scanner sc = new Scanner(System.in);
    System.out.println("Enter the no of processes! ");
    int n = sc. nextInt() ;
    Process process[] = new Process[n];
    int wt[] = new int[n];
    int tat[] = new int[n];
    System.out.println(" Enter the process no, burst time and arrival time");
    for(int i=0;i<n;i++){
      int id= sc.nextInt();
      int bt = sc.nextInt();
      int art = sc.nextInt();
      process[i]= new Process(id, bt, art);
    }
    findWaitingTime(process,n,wt);
    findtat(process,n,wt,tat);
    int totalWaitingTime =0, totalTAT= 0;
    System.out.printf("Process BurstTime ArrivalTime WaitingTime TurnAroundTime\n");
    for (int i = 0; i < n; i++) {
      totalWaitingTime += wt[i];
            totalTAT += tat[i];
      System.out.printf(" %d\t", (i + 1));
      System.out.printf("   %d ", process[i].bt);
      System.out.printf("\t\t%d ", process[i].art);
      System.out.printf("\t\t%d", wt[i]);
      System.out.printf("\t\t%d\n", tat[i]);
    }
    float s = (float)totalWaitingTime /(float) n;
    float t = (float)totalTAT / (float)n;
    System.out.printf("Average waiting time = %.2fms", s);
    System.out.printf("\n");
    System.out.printf("Average turn around time = %.2fms ", t);
    
   }
   public static void findWaitingTime(Process[] process, int n, int[] wt){
     int [] rt = new int[n];
     boolean check = false;
     int min= Integer.MAX_VALUE,shortest=0,t=0,finishTime,complete=0;
     for(int i=0;i<n;i++){
       rt[i]= process[i].bt;
     }
    while(complete != n){
     for(int i=0;i<n;i++){
       if(rt[i] <= min && rt[i]>0 && process[i].art <=t){
         check=true;
         min = rt[i];
         shortest = i;
       }
     }
       if(check == false){
         t++;
         continue;
       }
       finishTime = t+ rt[shortest];
       complete++;
       wt[shortest]= finishTime - process[shortest].bt - process[shortest].art;
       System.out.println(wt[shortest] + " " + finishTime);
       rt[shortest]=0;
       check = false;
       min = Integer.MAX_VALUE;
       t = finishTime;
     }
   }
   public static void findtat(Process[] process, int n, int[] wt,int[] tat){
     for(int i=0;i<n;i++){
       tat[i] = wt[i]+ process[i].bt;
     }
   }
 }
