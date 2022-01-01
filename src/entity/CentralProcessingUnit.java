package entity;

public class CentralProcessingUnit {
    public void execute(ProcessControlBlock pcb) {
        long burst = pcb.getBurstTime();
        while (burst > 0) {
            --burst;
        }
    }

}
