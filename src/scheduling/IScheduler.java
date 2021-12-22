package scheduling;

import entity.ProcessControlBlock;

public interface IScheduler {
    public void execute(ProcessControlBlock pcb);
}
