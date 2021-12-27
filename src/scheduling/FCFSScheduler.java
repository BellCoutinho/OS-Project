package scheduling;

import java.util.Queue;
import java.util.LinkedList;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.TimeUnit;

import entity.ProcessControlBlock;
import entity.ProcessState;
import entity.CentralProcessingUnit;

public class FCFSScheduler implements Scheduler {
    private static final AtomicInteger identifier = new AtomicInteger();

    @Override
    public void admitted(Queue<ProcessControlBlock> readyQueue, Runnable code) {
        final int processId = nextValue();
        ProcessControlBlock pcb = new ProcessControlBlock();
        pcb.setState(ProcessState.NEW);
        pcb.setProcessId(processId);

        pcb.setPriority(0);
        pcb.setCode(code);
        pcb.setState(ProcessState.READY);
        pcb.setArrivalTime(
                TimeUnit.MILLISECONDS.toSeconds(System.currentTimeMillis()));

        readyQueue.add(pcb);
    }

    @Override
    public void dispatch(Queue<ProcessControlBlock> readyQueue, CentralProcessingUnit cpu) {
        ProcessControlBlock pcb = readyQueue.remove();
        long startTime = 0;
        long endTime = 0;

        pcb.setState(ProcessState.RUNNING);
        startTime = TimeUnit.MILLISECONDS.toSeconds(System.currentTimeMillis());
        cpu.execute(pcb.getCode());
        endTime = TimeUnit.MILLISECONDS.toSeconds(System.currentTimeMillis());
        pcb.setCompletionTime(endTime);
        pcb.setBurstTime(startTime - endTime);

        exit(pcb);
    }

    @Override
    public void interrupt(ProcessControlBlock pcb) {
    }

    @Override
    public void exit(ProcessControlBlock pcb) {
        pcb.setState(ProcessState.TERMINATED);
        pcb.setTurnAroundTime(pcb.getCompletionTime() - pcb.getArrivalTime());
        pcb.setWaitingTime(pcb.getTurnAroundTime() - pcb.getBurstTime());
    }

    public static int nextValue() {
        return identifier.getAndIncrement();
    }
}
