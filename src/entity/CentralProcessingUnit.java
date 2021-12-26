package entity;

public class CentralProcessingUnit {
    public void execute(Runnable code) {
        Thread thread = new Thread(code);
        thread.run();
    }
}
