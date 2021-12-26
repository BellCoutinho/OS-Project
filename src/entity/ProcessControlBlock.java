package entity;

public class ProcessControlBlock {
    private int processId;
    private ProcessState processState;
    private int priority;
    private long arrivalTime;
    private long burstTime;
    private long completionTime;
    private long turnAroundTime;
    private long waitingTime;
    private Runnable code;

    public int getProcessId() {
        return this.processId;
    }

    public void setProcessId(int processId) {
        this.processId = processId;
    }

    public ProcessState getState() {
        return this.processState;
    }
    public void setState(ProcessState processState) {
        this.processState = processState; 
    }

    public int getPriority() {
        return this.priority;
    }
    public void setPriority(int priority) {
        this.priority = priority;
    }

    public long getArrivalTime() {
        return this.arrivalTime;
    }
    public void setArrivalTime(long arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

    public long getBurstTime() {
        return this.burstTime;
    }
    public void setBurstTime(long burstTime) {
        this.burstTime = burstTime;
    }

    public long getCompletionTime() {
        return this.completionTime;
    }
    public void setCompletionTime(long completionTime) {
        this.completionTime = completionTime;
    }

    public long getTurnAroundTime() {
        return this.turnAroundTime;
    }
    public void setTurnAroundTime(long turnAroundTime) {
        this.turnAroundTime = turnAroundTime;
    }

    public long getWaitingTime() {
        return this.waitingTime;
    }
    public void setWaitingTime(long waitingTime) {
        this.waitingTime = waitingTime;
    }

    public Runnable getCode() {
        return this.code;
    }
    public void setCode(Runnable code) {
        this.code = code;
    }
}
