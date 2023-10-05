import java.util.ArrayList;

public class Main {
    private static SimProcessor processor = new SimProcessor();
    final static int QUANTUM = 5;
    private static ArrayList<ProcessControlBlock> readyList = new ArrayList<>();
    private static ArrayList<ProcessControlBlock> blockedList = new ArrayList<>();
    private static int numOfProcesses = 10;
    private final static int totalNumOfIterations = 3000;
    private static int currentStep = 0;
    private static int numFinishedProcesses = 0;
    private static ProcessControlBlock runningPCB;
    private static int numOfCyclesThisProcessHasRunConsecutively = 0;

    public static void main(String[] args) {
        processor.setCurrInstruction(1);
        for (int i = 0; i < numOfProcesses; i++) {
            SimProcess process = new SimProcess(i + 1, "Proc" + (i + 1), (200 + (i * 10)));
            ProcessControlBlock pcb = new ProcessControlBlock(process);
            pcb.setCurrentInstruction(1);
            readyList.add(pcb);
        }
        //set up the first process to be executed
        runningPCB = readyList.remove(0);
        for (int i = 0; i < totalNumOfIterations; i++) {
            currentStep++;
            processor.setCurrentProcess(runningPCB.getProcess());
            System.out.print("Step " + currentStep + " ");
            ProcessState result = processor.executeNextInstruction();


            if (result == ProcessState.READY) {
                numOfCyclesThisProcessHasRunConsecutively++;
                //Check if quantum was reached and if it was perform a context switch
                if (numOfCyclesThisProcessHasRunConsecutively >= QUANTUM) {
                    System.out.println("*** Quantum expired ***");
                    numOfCyclesThisProcessHasRunConsecutively = 0;
                    updateAndPersistValues();
                    readyList.add(runningPCB);
                    runningPCB = readyList.remove(0);
                    reloadData();
                }
            }
            //if it is blocked add it to block list and do a context switch
            if (result == ProcessState.BLOCKED) {
                System.out.println("*** Process Blocked ***");
                updateAndPersistValues();
                blockedList.add(runningPCB);
                //now switching process so reset quantum "clock"
                numOfCyclesThisProcessHasRunConsecutively = 0;
                checkReadyListSizeAndRepopulateItIfNeeded();
                runningPCB = readyList.remove(0);
                reloadData();
            }

            if (result == ProcessState.FINISHED) {
                numFinishedProcesses++;
                System.out.println("*** Process Finished ***");
                if (numFinishedProcesses >= numOfProcesses) {
                    System.out.println("*** All processes are finished ***");
                    break;
                }
                checkReadyListSizeAndRepopulateItIfNeeded();
                runningPCB = readyList.remove(0);
                reloadData();
            }
            wakeUpBlockedProcesses();
        }
    }

    private static void checkReadyListSizeAndRepopulateItIfNeeded() {
        if (readyList.isEmpty()) {
            System.out.println("Processor is idling. Waiting for processes to be unblocked");
            while (readyList.isEmpty()) {
                wakeUpBlockedProcesses();
            }
        }
    }


    private static void updateAndPersistValues() {
        runningPCB.setR1(processor.getR1());
        runningPCB.setR2(processor.getR2());
        runningPCB.setR3(processor.getR3());
        runningPCB.setR4(processor.getR4());
        runningPCB.setCurrentInstruction(processor.getCurrInstruction());
        currentStep++;
        System.out.println("Step " + currentStep + " Context Switch: Saving process: " + runningPCB.getProcess().getPid());
        System.out.println("\t\tInstruction: " + runningPCB.getCurrentInstruction() + " - R1: " + runningPCB.getR1() + ", R2: " + runningPCB.getR2() + ", R3: " + runningPCB.getR3() + ", R4 " + runningPCB.getR4());
    }

    private static void reloadData() {
        processor.setR1(runningPCB.getR1());
        processor.setR2(runningPCB.getR2());
        processor.setR3(runningPCB.getR3());
        processor.setR4(runningPCB.getR4());
        processor.setCurrInstruction(runningPCB.getCurrentInstruction());
        currentStep++;
        System.out.println("Step " + currentStep + " Context Switch Restoring process: " + runningPCB.getProcess().getPid());
        System.out.println("\t\tInstruction: " + runningPCB.getCurrentInstruction() + " - R1: " + runningPCB.getR1() + ", R2: " + runningPCB.getR2() + ", R3: " + runningPCB.getR3() + ", R4 " + runningPCB.getR4());
    }

    private static void wakeUpBlockedProcesses() {
        for (int i = 0; i < blockedList.size(); i++) {
            if (Math.random() < .3) {
                readyList.add(blockedList.remove(i));
                //since I am removing one from the list, and it automatically adds one it would skip the
                // next one, so I offset it by subtracting one now
                i--;
            }
        }
    }
}