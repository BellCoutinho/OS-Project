package scheduling;

import java.util.Queue;
import java.util.LinkedList;
import java.util.Collections;
import java.util.concurrent.atomic.AtomicInteger;

import entity.ProcessControlBlock;
import entity.ProcessState;
import entity.Process;
import entity.CentralProcessingUnit;

public class FCFSScheduler implements Scheduler, Runnable {
    private static final AtomicInteger identifier = new AtomicInteger();
    private Queue<ProcessControlBlock> readyQueue;
    private static Queue<ProcessControlBlock> jobQueue = new LinkedList<>();
    private static LinkedList<ProcessControlBlock> terminatedQueue = new LinkedList<>();
    private CentralProcessingUnit cpu;
    private static long clock;

    public FCFSScheduler(CentralProcessingUnit cpu, Queue<ProcessControlBlock> readyQueue) {
        this.readyQueue = readyQueue;
        this.cpu = cpu;
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
        pcb.setProcessingTime(pcb.getBurstTime());

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
    }

    @Override
    public void exit() {
        ProcessControlBlock pcb = readyQueue.remove();
        terminatedQueue.add(pcb);
        pcb.setState(ProcessState.TERMINATED);
        pcb.setCompletionTime(clock);
        pcb.setTurnAroundTime(pcb.getCompletionTime() - pcb.getArrivalTime());
        pcb.setWaitingTime(pcb.getTurnAroundTime() - pcb.getBurstTime());
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
            System.out.println("Executando o processo - " + pcb.getProcessId());
            while (pcb.getProcessingTime() > 0) {
                pcb.getProcess().run();
                pcb.setProcessingTime(pcb.getProcessingTime() - 1);
            }
            clock += pcb.getBurstTime();
            exit();
        }
        displayStatistics();
    }

    public static int nextValue() {
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
