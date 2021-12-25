package scheduling;

import java.util.Queue;

import entity.ProcessControlBlock;

public interface Scheduler {
    public void admitted(Queue<ProcessControlBlock> readyQueue, Runnable code);
    public void dispatch(ProcessControlBlock pcb);
    public void interrupt(ProcessControlBlock pcb);
    public void exit(ProcessControlBlock pcb);
    //public void execute(ProcessControlBlock pcb);
}
