import java.util.Random;

public class SimProcess {
    private int pid;
    private String procName;
    private int totalInstructions;

    public SimProcess(int pid, String procName, int totalInstructions) {
        this.pid = pid;
        this.procName = procName;
        this.totalInstructions = totalInstructions;
    }

    public ProcessState execute(int i) {
        System.out.println("Proc " + procName + ", PID: " + pid + " executing instruction: " + i);
        if (i >= totalInstructions) {
            return ProcessState.FINISHED;
        }
        if (Math.random() < .15) {
            return ProcessState.BLOCKED;
        }
        return ProcessState.READY;
    }

    public int getPid() {
        return pid;
    }

    public void setPid(int pid) {
        this.pid = pid;
    }

    public String getProcName() {
        return procName;
    }

    public void setProcName(String procName) {
        this.procName = procName;
    }

    public int getTotalInstructions() {
        return totalInstructions;
    }

    public void setTotalInstructions(int totalInstructions) {
        this.totalInstructions = totalInstructions;
    }
}
