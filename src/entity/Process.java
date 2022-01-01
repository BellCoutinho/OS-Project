package entity;

public class Process implements Runnable {
    private Runnable code;
    private CentralProcessingUnit cpu;

    public Process(Runnable code) {
        this.code = code;
    }

    @Override
    public void run() {
        code.run();
    }

    public Runnable getCode() {
        return this.code;
    }
    public void setCode(Runnable code) {
        this.code = code;
    }

    public CentralProcessingUnit getCPU() {
        return this.cpu;
    }

    public void setCPU(CentralProcessingUnit cpu) {
        this.cpu = cpu;
    }

}
