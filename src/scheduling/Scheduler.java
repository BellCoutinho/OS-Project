package scheduling;

import java.util.Queue;

import entity.ProcessControlBlock;
import entity.CentralProcessingUnit;

public interface Scheduler {
    public void admitted(Queue<ProcessControlBlock> readyQueue, Runnable code);
    public void dispatch(Queue<ProcessControlBlock> readyQueue, CentralProcessingUnit cpu);
    public void interrupt(ProcessControlBlock pcb);
    public void exit(ProcessControlBlock pcb);
    //public void execute(ProcessControlBlock pcb);
}
