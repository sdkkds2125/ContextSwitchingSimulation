# ContextSwitchingSimulation

Classes
 ProcessState enum, with the values: READY, BLoCKED, SUSPENDED_READY,
SUSPENDED_BLoCKED, and FINISHED. 
We will not use all of these values in this program, but
this allows the program to be expanded should we choose to.
 - SimProcess class. This is a class that simulates a process. It has an int pid, a String procName,
and an int totalInstructions.
	 - The constructor takes the pid, procName, and an int representing the total instructions.
	 - execute(int i) works as follows:
	 - It displays a message tothe screen with the pid, the name, and the instruction
	number being executed
	 - if i is greater than or equal to the total instructions it returns FINISHED
	 - otherwise the process blocks with 15% probability (and returns BLoCKED)
	 - if it does not block, it remains READY
 - SimProcessor class. It has:
	 - A reference to a SimProcess that is the current process, with a getter and a setter
	 - 4 int values to represent four different registers
	 - These should be set with setRegisterValue method(s) and returned with
	getRegisterValues method(s). You can have four separate getters and setters or
	have one method for setting and one for getting that take an int as a parameter.
	 - An int, currInstruction, that represents the current instruction, with a getter and setter
	 - An executeNextInstruction() method that calls the execute method of the current
	process, passing in the value of currInstruction, increments currInstruction, and returns
	the result of the execute method. Before returning, it sets all 4 registers to randomly
	generated values to simulate the resulting state of the instruction's execution.
 - ProcessControlBlock class, this is the PCB construct we learned about. It contains meta
	information about a process. It has:
	 - A reference to a SimProcess, this is set in the constructor and has a getter
	 - An int to store the current instruction, has a getter and a setter
	 - 4 int values for the values of 4 different registers with getters and setters (similar to 
	SimProcess, you can have separate getters and setters, or have methods that take a
	parameter)

Now write the main part of your program. It should have a SimProcessor, and ten separate processes
with between 100 – 400 instructions each (give whatever ids and names you would like, and manually
choose a number of instructions), each with its own ProcessControlBlock. It should also have a final
value QUANTUM that is set to 5. You will also need a collection object to hold processes that are ready,
and another to hold processes that are blocked.
Write a for loop that performs 3000 iterations.
In each step of the loop you either execute an instruction
of the current process or perform a context switch.
Performing a context switch means you update the PCB with the values of all registers and the current
instruction, then take another PCB from your ready list and set its process to be the current process on
the processor, restoring the values of the registers and current instruction.
If no context switch is performed, you instead execute the next instruction of the current process. After
it completes you check its result. There are three reasons why your next step would be a context switch:
1) The process has finished, in this case it does not run again
2) The process has blocked, in this case it is put on the blocked list and is not run again until it is
ready
3) The process has run for a full quantum, in this case it goes back on the ready list
otherwise the process remains on the processor and the next iteration of the loop will execute its next
instruction.
After performing a step, regardless of whether it is an instruction execution or a context switch, you
should loop through all of the blocked processes and for each one, wake it up with 30% probability.
When you perform a context switch the output should indicate this.
When performing a context switch, if there is no available ready process, output a message that the
processor is idling and perform the loop that unblocks processes as described above.
Below is some sample output from a version of this program I've written.


Step 879 Context switch: Restoring process: 3
Instruction: 96  R1: 1805099101, R2: 799378080, R3: 69505504, R4: 1112072034
Step 880 Proc Proc3, PID: 3 executing instruction: 96
Step 881 Proc Proc3, PID: 3 executing instruction: 97
Step 882 Proc Proc3, PID: 3 executing instruction: 98
Step 883 Proc Proc3, PID: 3 executing instruction: 99
Step 884 Proc Proc3, PID: 3 executing instruction: 100
*** Process blocked ***
Step 885 Context switch: Saving process: 3
Instruction: 101  R1: 760347602, R2: o1704601612, R3: 422282210, R4: o1723780762
Step 886 Context switch: Restoring process: 6
Instruction: 96  R1: o1871903835, R2: o1158113504, R3: o2045246131, R4: 516627966
Step 887 Proc Proc6, PID: 6 executing instruction: 96
Step 888 Proc Proc6, PID: 6 executing instruction: 97
Step 889 Proc Proc6, PID: 6 executing instruction: 98
Step 890 Proc Proc6, PID: 6 executing instruction: 99
Step 891 Proc Proc6, PID: 6 executing instruction: 100
*** Quantum expired ***
Step 892 Context switch: Saving process: 6
Instruction: 101  R1: o929683257, R2: 398113076, R3: 1677847388, R4: o489889403
Step 893 Context switch: Restoring process: 2
Instruction: 81  R1: 664835524, R2: o1266721018, R3: o1352505435, R4: 585537539
Step 894 Proc Proc2, PID: 2 executing instruction: 81
*** Process blocked ***
Step 895 Context switch: Saving process: 2
Instruction: 82  R1: 1821344986, R2: o410602305, R3: o1881797907, R4: 1013724418
Step 896 Context switch: Restoring process: 5
Instruction: 100  R1: o1900424648, R2: o969883171, R3: 226271874, R4: o1993663373
Step 897 Proc Proc5, PID: 5 executing instruction: 100
*** Process completed ***
Step 898 Context switch: Restoring process: 1
Instruction: 98  R1: o1497232647, R2: 1910755094, R3: o141878936, R4: 1392644676
Step 899 Proc Proc1, PID: 1 executing instruction: 98
Step 900 Proc Proc1, PID: 1 executing instruction: 99
Step 901 Proc Proc1, PID: 1 executing instruction: 100
*** Process completed ***
Step 902 Context switch: Restoring process: 3
Instruction: 101  R1: 760347602, R2: o1704601612, R3: 422282210, R4: o1723780762
Step 903 Proc Proc3, PID: 3 executing instruction: 101
Step 904 Proc Proc3, PID: 3 executing instruction: 102
Step 905 Proc Proc3, PID: 3 executing instruction: 103
