package org.example;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
              
        while(true){
        System.out.println("1-Non-Preemptive Priority Scheduling");
        System.out.println("2-Non-Preemptive Shortest-Job First");
        System.out.println("3-Exit");
        System.out.println("which scheduler you want to apply: ");

        Scanner scanner = new Scanner(System.in);
        int scheduler = scanner.nextInt();
        
       
        if (scheduler == 1) {
            NonPreemptivePriorityScheduling();
        }
        else if (scheduler == 2){
            NonPreemptiveSJF();
        }
        else if (scheduler == 3){
            System.out.println("Exiting....\n\n");
            System.exit(0);
        }

    }
}   
   
private static void NonPreemptiveSJF() { 
    int[] at = new int[10]; 
    int[] bt = new int[10]; 
    int[] waitTime = new int[10];
    int[] turnaroundTime = new int[10];
    int n;
    
    Scanner scanner = new Scanner(System.in);
    System.out.print("Enter number of Processes: ");
    n = scanner.nextInt();
    for (int i = 0; i < n; i++) {
        System.out.print("Enter arrival time for Process P" + (i + 1) + ": ");
        at[i] = scanner.nextInt();
        System.out.print("Enter burst time for Process P" + (i + 1) + ": ");
        bt[i] = scanner.nextInt();
    }
    
    int[] originalOrder = new int[n];
    for (int i = 0; i < n; i++) {
        originalOrder[i] = i;
    }

// Sort based on burst time 
for (int i = 0; i < n - 1; i++) {
    for (int j = 0; j < n - i - 1; j++) {
        if (bt[j] > bt[j + 1] && at[j] != 0) {       
            int tempBt = bt[j];
            bt[j] = bt[j + 1];
            bt[j + 1] = tempBt;
          
            int tempAt = at[j];
            at[j] = at[j + 1];
            at[j + 1] = tempAt;
        
            int tempOrder = originalOrder[j];
            originalOrder[j] = originalOrder[j + 1];
            originalOrder[j + 1] = tempOrder;
        }
    }
}

    // Check if a process with arrival time 0 
    int processWithArrivalZero = -1; 
    for (int i = 0; i < n; i++) {
        if (at[i] == 0) {
            processWithArrivalZero = i;
            break;
        }
    }

    // Swap the process with arrival time 0 
    if (processWithArrivalZero != -1 && processWithArrivalZero != 0) {
        int tempAt = at[0];
        at[0] = at[processWithArrivalZero];
        at[processWithArrivalZero] = tempAt;

        int tempBt = bt[0];
        bt[0] = bt[processWithArrivalZero];
        bt[processWithArrivalZero] = tempBt;

        int tempOrder = originalOrder[0];
        originalOrder[0] = originalOrder[processWithArrivalZero];
        originalOrder[processWithArrivalZero] = tempOrder;
    }

    // wt
for (int i = 1; i < n; i++) {
    waitTime[i] = 0;
    for (int j = 0; j < i; j++) {
        waitTime[i] += bt[j];
    }
    waitTime[i] -= at[i]; 
    if (waitTime[i] < 0) {
        waitTime[i] = 0; 
    }
}

   
    for (int i = 0; i < n; i++) {
        turnaroundTime[i] = bt[i] + waitTime[i];
    }

    System.out.println("\nProcess's order, waiting time, and turnaround time of each process: \n");
    int totalWaitingTime = 0;
    int totalTurnaroundTime = 0;

    for (int i = 0; i < n; i++) {
        totalWaitingTime += waitTime[i];
        totalTurnaroundTime += turnaroundTime[i];

        System.out.println("Process P" + (originalOrder[i] + 1) + " - Waiting Time: " + waitTime[i]
                + ", Turnaround Time: " + turnaroundTime[i]);
    }
    double averageWaitingTime = (double) totalWaitingTime / n;
    double averageTurnaroundTime = (double) totalTurnaroundTime / n;

    System.out.println("\nAverage Waiting Time: " + averageWaitingTime + " msec");
    System.out.println("Average Turnaround Time: " + averageTurnaroundTime + " msec\n\n");

}
                                                                                                               
private static void NonPreemptivePriorityScheduling() {
    int[] priority = new int[10];
    int[] bt = new int[10]; 
    int[] waitTime = new int[10];      
    int[] turnaroundTime = new int[10];
    int n;
    Scanner scanner = new Scanner(System.in);
    System.out.print("Enter number of Processes: ");
    n = scanner.nextInt();
    for (int i = 0; i < n; i++) {
        System.out.print("Enter priority num for Process P" + (i + 1) + ": ");
        priority[i] = scanner.nextInt();
        System.out.print("Enter burst time for Process P" + (i + 1) + ": ");
        bt[i] = scanner.nextInt();   
    }

    int[] originalOrder = new int[n];
    for (int i = 0; i < n; i++) {
        originalOrder[i] = i;
    }

    for (int i = 0; i < n - 1; i++) {
        for (int j = 0; j < n - i - 1; j++) {
            if (priority[j] > priority[j + 1]) {
                int tempPriority = priority[j];
                priority[j] = priority[j + 1];
                priority[j + 1] = tempPriority;

                int tempBt = bt[j];
                bt[j] = bt[j + 1];
                bt[j + 1] = tempBt;
             
                int tempOrder = originalOrder[j];
                originalOrder[j] = originalOrder[j + 1];
                originalOrder[j + 1] = tempOrder;
            }
        }
    }

    waitTime[0] = 0; 
    for (int i = 1; i < n; i++) {
        waitTime[i] = 0;
        for (int j = 0; j < i; j++) {
            waitTime[i] += bt[j];
        }
    }
    for (int i = 0; i < n; i++) {
        turnaroundTime[i] = bt[i] + waitTime[i];
    }

    System.out.println("\nProcess's order, waiting time, and turnaround time of each process: \n");
    int totalWaitingTime = 0;
    int totalTurnaroundTime = 0;

    for (int i = 0; i < n; i++) {
        totalWaitingTime += waitTime[i];
        totalTurnaroundTime += turnaroundTime[i];
        System.out.println("Process P" + (originalOrder[i] + 1) + " - Waiting Time: " + waitTime[i] +
                " - Turnaround Time: " + turnaroundTime[i]);
    }

    double averageWaitingTime = (double) totalWaitingTime / n;
    double averageTurnaroundTime = (double) totalTurnaroundTime / n;
    
    System.out.println("\nAverage Waiting Time: " + averageWaitingTime + " msec");
    System.out.println("Average Turnaround Time: " + averageTurnaroundTime + " msec\n\n");
}
}



