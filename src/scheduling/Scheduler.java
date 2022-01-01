package scheduling;

import entity.ProcessControlBlock;
import entity.Process;

public interface Scheduler {
    public void admitted(Process code, long arrivalTime, long burstTime);
    public void dispatch();
    public void interrupt();
    public void exit();
    //public void execute(ProcessControlBlock pcb);
}
