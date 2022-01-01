package main;

import java.util.Queue;
import java.util.LinkedList;
import java.util.concurrent.ConcurrentLinkedQueue;

import entity.ProcessControlBlock;
import entity.Process;
import entity.CentralProcessingUnit;
import scheduling.Scheduler;
import scheduling.FCFSScheduler;
import scheduling.RoundRobinScheduler;

public class OperatingSystem {
    private static Queue<ProcessControlBlock> readyQueue = new LinkedList<>();
    private static final CentralProcessingUnit cpu = new CentralProcessingUnit();
    private static RoundRobinScheduler schedulerRR;
    private static FCFSScheduler schedulerFCFS;

    public static void main(String[] args) {
        schedulerFCFS = new FCFSScheduler(cpu, readyQueue);
        schedulerRR = new RoundRobinScheduler(cpu, readyQueue, 3);
        Thread cpuSchedulerRR = new Thread(() -> {
            schedulerRR.run();
        }, "CPU scheduler");

        Thread cpuSchedulerFCFS = new Thread(() -> {
            schedulerFCFS.run();
        }, "CPU scheduler");

        FCFSAlgorithm(cpuSchedulerFCFS);
        //RRAlgorithm(cpuSchedulerRR);

        System.out.println("Shutting down the operating system");
    }

    public static void FCFSAlgorithm(Thread cpuScheduler) {
        schedulerFCFS.admitted(new Process(new Runnable() {
            @Override
            public void run() {
                System.out.println("Processing...");
            }
        }), 0, 2);

        schedulerFCFS.admitted(new Process(() -> {
                System.out.println("Processing...");
            }
        ), 1, 6);

        schedulerFCFS.admitted(new Process(() -> {
                System.out.println("Processing...");
            }
        ), 2, 4);

        schedulerFCFS.admitted(new Process(() -> {
                System.out.println("Processing...");
            }
        ), 3, 9);

        schedulerFCFS.admitted(new Process(() -> {
                System.out.println("Processing...");
            }
        ), 6, 12);

        cpuScheduler.start();
    }

    public static void RRAlgorithm(Thread cpuScheduler) {
        schedulerRR.admitted(new Process(new Runnable() {
            @Override
            public void run() {
                System.out.println("Processing...");
            }
        }), 0, 8);

        schedulerRR.admitted(new Process(() -> {
                System.out.println("Processing...");
            }
        ), 1, 7);

        schedulerRR.admitted(new Process(() -> {
                System.out.println("Processing...");
            }
        ), 5, 2);

        schedulerRR.admitted(new Process(() -> {
                System.out.println("Processing...");
            }
        ), 6, 3);

        schedulerRR.admitted(new Process(() -> {
                System.out.println("Processing...");
            }
        ), 8, 5);

        /*
        schedulerRR.admitted(new Process(() -> {
                System.out.println("Processing...");
            }
        ), 6, 3);
        */

        cpuScheduler.start();
    }
}
