package entity;

public enum ProcessState {
    NEW(0),
    READY(1),
    RUNNING(2),
    WAITING(3),
    TERMINATED(4);

    private int processValue;

    private ProcessState(int processValue) {
        this.processValue = processValue;
    }

    public int getStateValue() {
        return this.processValue;
    }
}
