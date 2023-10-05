import java.util.ArrayList;
import java.util.Random;

public class SimProcessor {
    private SimProcess currentProcess;
    private int r1;
    private int r2;
    private int r3;
    private int r4;
    private int currInstruction;


    public ProcessState executeNextInstruction(){
        ProcessState result = currentProcess.execute(currInstruction);
        currInstruction++;
        setR1(new Random().nextInt());
        setR2(new Random().nextInt());
        setR3(new Random().nextInt());
        setR4(new Random().nextInt());
        return result;
    }

    public int getR1() {
        return r1;
    }

    public void setR1(int r1) {
        this.r1 = r1;
    }

    public int getR2() {
        return r2;
    }

    public void setR2(int r2) {
        this.r2 = r2;
    }

    public int getR3() {
        return r3;
    }

    public void setR3(int r3) {
        this.r3 = r3;
    }

    public int getR4() {
        return r4;
    }

    public void setR4(int r4) {
        this.r4 = r4;
    }

    public SimProcess getCurrentProcess() {
        return currentProcess;
    }

    public void setCurrentProcess(SimProcess currentProcess) {
        this.currentProcess = currentProcess;
    }


    public int getCurrInstruction() {
        return currInstruction;
    }

    public void setCurrInstruction(int currInstruction) {
        this.currInstruction = currInstruction;
    }

}
