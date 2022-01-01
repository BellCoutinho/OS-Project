package scheduling;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.TimeUnit;
import java.util.Queue;
import java.util.LinkedList;
import java.util.Collections;

import entity.CentralProcessingUnit;
import entity.ProcessControlBlock;
import entity.Process;
import entity.ProcessState;

public class RoundRobinScheduler implements Scheduler, Runnable {
    private static final AtomicInteger identifier = new AtomicInteger();
    private CentralProcessingUnit cpu;
    private Queue<ProcessControlBlock> readyQueue;
    private static Queue<ProcessControlBlock> jobQueue = new LinkedList<>();
    private static LinkedList<ProcessControlBlock> terminatedQueue = new LinkedList<>();
    private static long clock;
    private int quantum;

    public RoundRobinScheduler(CentralProcessingUnit cpu, Queue<ProcessControlBlock> readyQueue, int quantum) {
        this.cpu = cpu;
        this.readyQueue = readyQueue;
        this.quantum = quantum;
    }

    @Override
    public void admitted(Process process, long arrivalTime, long burstTime) {
        final int processId = nextValue();
        ProcessControlBlock pcb = new ProcessControlBlock();
        pcb.setState(ProcessState.NEW);
        pcb.setProcessId(processId);

        pcb.setProcess(process);
        pcb.setArrivalTime(arrivalTime);
        pcb.setBurstTime(burstTime);
        pcb.setProcessingTime(burstTime);

        jobQueue.add(pcb);
    }

    @Override
    public void dispatch() {
        while (!jobQueue.isEmpty() && jobQueue.peek().getArrivalTime() <= clock) {
            ProcessControlBlock task = jobQueue.remove();
            task.setState(ProcessState.READY);
            readyQueue.add(task);
        }

        System.out.println("Relógio - " + clock);
        System.out.println("Ready Queue -> " + readyQueue);
        if (!readyQueue.isEmpty()) {
            ProcessControlBlock pcb = readyQueue.peek();
            if (!pcb.getAssignedResponseTime()) {
                pcb.setResponseTime(clock - pcb.getArrivalTime());
                pcb.setAssignedResponseTime(true);
            }
            pcb.setState(ProcessState.RUNNING);
        }
    }

    @Override
    public void interrupt() {
        while (!jobQueue.isEmpty() && jobQueue.peek().getArrivalTime() <= clock) {
            ProcessControlBlock task = jobQueue.remove();
            task.setState(ProcessState.READY);
            readyQueue.add(task);
        }

        ProcessControlBlock pcb = readyQueue.remove();
        pcb.setState(ProcessState.READY);
        pcb.setProcessingTime(pcb.getProcessingTime() - this.quantum);
        readyQueue.add(pcb);
    }

    @Override
    public void exit() {
         ProcessControlBlock pcb = readyQueue.remove();
         pcb.setState(ProcessState.TERMINATED);
         pcb.setCompletionTime(clock);
         pcb.setTurnAroundTime(pcb.getCompletionTime() - pcb.getArrivalTime());
         pcb.setWaitingTime(pcb.getTurnAroundTime() - pcb.getBurstTime());
         terminatedQueue.add(pcb);
    }

    @Override
    public void run() {
        while (true) {
            if (readyQueue.isEmpty() && jobQueue.isEmpty()) {
                break;
            }
            if (readyQueue.isEmpty() && !jobQueue.isEmpty()) {
                while (jobQueue.peek().getArrivalTime() > clock) {
                    ++clock;
                }
            }
            dispatch();
            ProcessControlBlock pcb = readyQueue.peek();
            if (pcb.getProcessingTime() <= this.quantum) {
                clock += pcb.getProcessingTime();
                System.out.println("Executando o processo - " + pcb.getProcessId());
                pcb.getProcess().run();
                exit();
                continue;
            } else {
                int timer = this.quantum;
                clock += this.quantum;
                System.out.println("Executando o processo - " + pcb.getProcessId());
                while (timer > 0) {
                    pcb.getProcess().run();
                    --timer;
                    //Thread.sleep(TimeUnit.SECONDS.toMillis(quantum));
                }
            }
            interrupt();
        }
        displayStatistics();
    }

    private static int nextValue() {
        return identifier.getAndIncrement();
    }

    private static void displayStatistics() {
        Collections.sort(terminatedQueue);
        float averageTurnAroundTime = averageTurnAroundTime();
        float averageWaitingTime = averageWaitingTime();
        float averageResponseTime = averageResponseTime();
        while (!terminatedQueue.isEmpty()) {
            ProcessControlBlock pcb = terminatedQueue.remove();
            System.out.println("PID  ArrivalTime  BurstTime  CompletionTime  TurnAroundTime  WaitingTime  ResponseTime");
            System.out.printf( " %2d           %2d         %2d              %2d              %2d           %2d           %2d\n", 
                    pcb.getProcessId(),
                    pcb.getArrivalTime(),
                    pcb.getBurstTime(),
                    pcb.getCompletionTime(),
                    pcb.getTurnAroundTime(),
                    pcb.getWaitingTime(),
                    pcb.getResponseTime());
        }

        System.out.println("===========================================================");
        System.out.printf("Average Turn Around Time is: %.2f\n", averageTurnAroundTime);
        System.out.printf("Average Waiting Time is: %.2f\n", averageWaitingTime);
        System.out.printf("Average Response Time is: %.2f\n", averageResponseTime);
    }

    private static float averageTurnAroundTime() {
        float average = 0;
        for (ProcessControlBlock pcb : terminatedQueue) {
            average += pcb.getTurnAroundTime();
        }
        return average/terminatedQueue.size();
    }

    private static float averageWaitingTime() {
        float average = 0;
        for (ProcessControlBlock pcb : terminatedQueue) {
            average += pcb.getWaitingTime();
        }
        return average/terminatedQueue.size();
    }

    private static float averageResponseTime() {
        float average = 0;
        for (ProcessControlBlock pcb : terminatedQueue) {
            average += pcb.getResponseTime();
        }
        return average/terminatedQueue.size();
    }
}
