package scheduling;

import entity.ProcessControlBlock;

public interface Scheduler {
    public void execute(ProcessControlBlock pcb);
}
