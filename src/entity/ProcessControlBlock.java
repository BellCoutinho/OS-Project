package entity;

public class ProcessControlBlock implements Comparable <ProcessControlBlock> {
    private int processId;
    private ProcessState processState;
    private int priority;
    private long arrivalTime;
    private long burstTime;
    private long processingTime;
    private long completionTime;
    private long turnAroundTime;
    private long waitingTime;
    private long responseTime;
    private boolean assignedResponseTime;
    private Process process;
    private Thread thread;

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

    public long getProcessingTime() {
        return this.processingTime;
    }
    public void setProcessingTime(long processingTime) {
        this.processingTime = processingTime;
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

    public long getResponseTime() {
        return this.responseTime;
    }
    public void setResponseTime(long responseTime) {
        this.responseTime = responseTime;
    }

    public boolean getAssignedResponseTime() {
        return this.assignedResponseTime;
    }
    public void setAssignedResponseTime(boolean assignedResponseTime) {
        this.assignedResponseTime = assignedResponseTime;
    }

    public Process getProcess() {
        return this.process;
    }
    public void setProcess(Process process) {
        this.process = process;
    }

    public Thread getThread() {
        return this.thread;
    }
    public void setThread(Thread thread) {
        this.thread = thread;
    }

    @Override
    public int compareTo(ProcessControlBlock pcb) {
        if (this.processId < pcb.getProcessId()) {
            return -1;
        }
        if (this.processId > pcb.getProcessId()) {
            return 1;
        }
        return 0;
    }


    @Override
    public String toString() {
        return "PID - " + this.processId + "=" + this.processingTime;
    }

}
