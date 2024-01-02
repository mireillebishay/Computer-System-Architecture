import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;

public class CPU {
	private static Memory memory;
	private RegisterFile registerFile;
	private ZeroRegister zeroReg;
	private  PC pc;
	private Register Rd;
	private Register Rs;
	private Register Rt;

	private Register R1;
	private Register R2;
	private Register R3;
	private Register R4;
	private Register R5;
	private Register R6;
	private Register R7;
	private Register R8;
	private Register R9;
	private Register R10;
	private Register R11;
	private Register R12;
	private Register R13;
	private Register R14;
	private Register R15;
	private Register R16;
	private Register R17;
	private Register R18;
	private Register R19;
	private Register R20;
	private Register R21;
	private Register R22;
	private Register R23;
	private Register R24;
	private Register R25;
	private Register R26;
	private Register R27;
	private Register R28;
	private Register R29;
	private Register R30;
	private Register R31;
	private int clockcycles;
	private int instructionCount;
	private static Queue<Instruction> fetchQueue;
	private Queue<Instruction> decodeQueue1;
	private Queue<Instruction> decodeQueue2;
	private Queue<Instruction> executeQueue1;
	private Queue<Instruction> executeQueue2;
	private Queue<Instruction> memoryQueue;
	private Queue<Instruction> writebackQueue;
	private Instruction instruction;
	private static final Map<String, String> registerMap = new HashMap<>();

	private static final Map<String, String> opcodeMap = new HashMap<>();
	static {
		opcodeMap.put("ADD", "0000");
		opcodeMap.put("SUB", "0001");
		opcodeMap.put("MULI", "0010");
		opcodeMap.put("ADDI", "0011");
		opcodeMap.put("BNE", "0100");
		opcodeMap.put("ANDI", "0101");
		opcodeMap.put("ORI", "0110");
		opcodeMap.put("J", "0111");
		opcodeMap.put("SLL", "1000");
		opcodeMap.put("SRL", "1001");
		opcodeMap.put("LW", "1010");
		opcodeMap.put("SW", "1011");
		for(int i=0; i<32; i++) {
            registerMap.put("$R"+i, String.format("%05d", Integer.parseInt(Integer.toBinaryString(i))));
            registerMap.put("$zero", "00000");
			RegisterFile.getInstance().setIndex(i, new Register());
        }
	}

	

	public CPU() {
		registerFile = RegisterFile.getInstance();
		zeroReg = ZeroRegister.getInstance();
		instruction = new Instruction(0);
		memory = new Memory();
		registerFile.setIndex(0, zeroReg);
		registerFile.setIndex(32, pc);
		clockcycles = 0;
		instructionCount = 0;
		pc =new PC();
		fetchQueue = new LinkedList<>();
		decodeQueue1 = new LinkedList<>();
		executeQueue1 = new LinkedList<>();
		decodeQueue2 = new LinkedList<>();
		executeQueue2 = new LinkedList<>();
		memoryQueue = new LinkedList<>();
		writebackQueue = new LinkedList<>();
		R1=new Register();
		registerFile.setIndex(1, R1);
		R2=new Register();
		registerFile.setIndex(2, R2);
		R3=new Register();
		registerFile.setIndex(3, R3);
		R4=new Register();
		registerFile.setIndex(4, R4);
		R5=new Register();
		registerFile.setIndex(5, R5);
		R6=new Register();
		registerFile.setIndex(6, R6);
		R7=new Register();
		registerFile.setIndex(7, R7);
		R8=new Register();
		registerFile.setIndex(8, R8);
		R9=new Register();
		registerFile.setIndex(9, R9);
		R10=new Register();
		registerFile.setIndex(10, R10);
		R11=new Register();
		registerFile.setIndex(11, R11);
		R12=new Register();
		registerFile.setIndex(12, R12);
		R13=new Register();
		registerFile.setIndex(13, R13);
		R14=new Register();
		registerFile.setIndex(14, R14);
		R15=new Register();
		registerFile.setIndex(15, R15);
		R16=new Register();
		registerFile.setIndex(16, R16);
		R17=new Register();
		registerFile.setIndex(17, R17);
		R18=new Register();
		registerFile.setIndex(18, R18);
		R19=new Register();
		registerFile.setIndex(19, R19);
		R20=new Register();
		registerFile.setIndex(20, R20);
		R21=new Register();
		registerFile.setIndex(21, R21);
		R22=new Register();
		registerFile.setIndex(22, R22);
		R23=new Register();
		registerFile.setIndex(23, R23);
		R24=new Register();
		registerFile.setIndex(24, R24);
		R25=new Register();
		registerFile.setIndex(25, R25);
		R26=new Register();
		registerFile.setIndex(26, R26);
		R27=new Register();
		registerFile.setIndex(27, R27);
		R28=new Register();
		registerFile.setIndex(28, R28);
		R29=new Register();
		registerFile.setIndex(29, R29);
		R30=new Register();
		registerFile.setIndex(30, R30);
		R31=new Register();
		registerFile.setIndex(31, R31);


		
	}
	public Queue<Instruction> getFetchQueue(){
		return fetchQueue;
	}
public static void addtoFetchQueue(Instruction o){
	fetchQueue.add(o);}


	public void instructionFetch(int address) throws Exception {
		instruction.setValue((int) memory.read(address));
		//	System.out.println("PC: " + pc.readRegister());
			pc.incrementPC();
	
	}

	public void instructionDecode(Instruction instruction) throws Exception {
		String inst = Integer.toBinaryString(instruction.getValue());
		inst=String.format("%32s", inst).replace(' ', '0');
		instruction.setInst(String.format("%32s", inst).replace(' ', '0'));
		instruction.setOp(Integer.parseInt(inst.substring(0, 4), 2));
		instruction.setRd(Integer.parseInt(inst.substring(4, 9), 2)); 
		//System.out.println(instruction.getRd());// bits27:23
	    instruction.setRs(Integer.parseInt(inst.substring(9, 14), 2));
		//System.out.println(instruction.getRs());
		instruction.setRt(Integer.parseInt(inst.substring(14, 19), 2));
		//System.out.println(instruction.getRt());
		instruction.setShamt(Integer.parseInt(inst.substring(19, 32), 2));
		instruction.setImmediate(Integer.parseInt(inst.substring(14, 32), 2));
		instruction.setAddress(Integer.parseInt(inst.substring(4, 32), 2));

		 Rd = registerFile.getRegister(instruction.getRd());
		 Rs = registerFile.getRegister(instruction.getRs());
		 Rt = registerFile.getRegister(instruction.getRt());
	}

	public void instructionExecute(Instruction instruction) throws Exception {
		Rd = registerFile.getRegister(instruction.getRd());
		Rs = registerFile.getRegister(instruction.getRs());
		Rt = registerFile.getRegister(instruction.getRt());
		switch (instruction.getOp()) {
		case 0:
		//System.out.println(R2.readRegister()+R3.readRegister()+"lllll");
		instruction.setALUvalue(Rs.readRegister()+Rt.readRegister());
		//System.out.println("ALU: wnaby " + instruction.getALUvalue());
			break;
		case 1:
		instruction.setALUvalue( Rs.readRegister() - Rt.readRegister());
			break;
		case 2:
		instruction.setALUvalue( Rs.readRegister() * instruction.getImmediate());

			break;
		case 3:
		instruction.setALUvalue( Rs.readRegister() +  instruction.getImmediate());

			break;
		case 4:

			bne(R1, Rs,  instruction.getImmediate());
			break;
		case 5:
		instruction.setALUvalue( Rs.readRegister() &  instruction.getImmediate());
			break;
		case 6:
		instruction.setALUvalue( Rs.readRegister() | instruction.getImmediate());
			break;
		case 7:
			jump(instruction.getAddress());
			break;
		case 8:
		instruction.setALUvalue( Rs.readRegister() << instruction.getShamt());
			break;
		case 9:
		instruction.setALUvalue( Rs.readRegister() >>> instruction.getShamt());

			break;
		case 10:
			break;
		case 11:
			break;
		default:
			break;
		}

	}

	public void instructionMem(Instruction instruction) throws Exception {
		Rd = registerFile.getRegister(instruction.getRd());
		Rs = registerFile.getRegister(instruction.getRs());
	    Rt = registerFile.getRegister(instruction.getRt());
		if (instruction.getOp() == 10) {
			int x=(Rs.readRegister() + instruction.getImmediate())+1024;
			instruction.setLoadValue((int) memory.read(x));
		}
		if (instruction.getOp() == 11) {
			sw(Rd, Rs, instruction.getImmediate());

		}

	}

	public void instructionWriteBack(Instruction instruction) throws Exception {
		Rd = registerFile.getRegister(instruction.getRd());
		Rs = registerFile.getRegister(instruction.getRs());
		Rt = registerFile.getRegister(instruction.getRt());
		switch (instruction.getOp()) {
			
		case 0:
			Rd.writeRegister(instruction.getALUvalue());
			System.out.println("In the Write Back stage:Value of Destination Register was updated to " + instruction.getALUvalue());
		//	System.out.println(instruction.getALUvalue());
		//	System.out.println("R1:ana hena " + R1.readRegister());
			break;
		case 1:
			Rd.writeRegister(instruction.getALUvalue());
			System.out.println("In the Write Back stage:Value of Destination Register  was updated to " + instruction.getALUvalue());

			break;
		case 2:
			Rd.writeRegister(instruction.getALUvalue());
			System.out.println("In the Write Back stage:Value of Destination Register  was updated to " + instruction.getALUvalue());

			break;
		case 3:
			Rd.writeRegister(instruction.getALUvalue());
			System.out.println("In the Write Back stage:Value of Destination Register  was updated to " + instruction.getALUvalue());

			break;
		case 4:
			break;
		case 5:
			Rd.writeRegister(instruction.getALUvalue());
			System.out.println("In the Write Back stage:Value of Destination Register  was updated to " + instruction.getALUvalue());

			break;
		case 6:
			Rd.writeRegister(instruction.getALUvalue());
			System.out.println("In the Write Back stage:Value of Destination Register  was updated to " + instruction.getALUvalue());

			break;
		case 7:

			break;
		case 8:
			Rd.writeRegister(instruction.getALUvalue());
			System.out.println("In the Write Back stage:Value of Destination Register  was updated to " + instruction.getALUvalue());

			break;
		case 9:
			Rd.writeRegister(instruction.getALUvalue());
			System.out.println("In the Write Back stage:Value of Destination Register  was updated to " + instruction.getALUvalue());

			break;
		case 10:
			Rd.writeRegister(instruction.getLoadValue());
			System.out.println("In the Write Back stage:Value of Destination Register  was updated to " + instruction.getLoadValue());

			break;
		case 11:
			break;
		default:
			break;
		}

	}
	public void add(Register R1, Register R2, Register R3) {
		R1.writeRegister(R2.readRegister() + R3.readRegister());
	}

	public void sub(Register R1, Register R2, Register R3) {
		R1.writeRegister(R2.readRegister() - R3.readRegister());
	}

	public void muli(Register R1, Register R2, int immediate) {
		R1.writeRegister(R2.readRegister() * immediate);
	}

	public void addi(Register R1, Register R2, int immediate) {
		R1.writeRegister(R2.readRegister() + immediate);
	}

	public void bne(Register R1, Register R2, int immediate) {
		if (R1.readRegister() != R2.readRegister()) {
			pc.writeRegister(pc.readRegister() + 1 + immediate);
		}
	}

	public void andi(Register R1, Register R2, int immediate) {
		R1.writeRegister(R2.readRegister() & immediate);
	}

	public void ori(Register R1, Register R2, int immediate) {
		R1.writeRegister(R2.readRegister() | immediate);
	}

	public void jump(int ads) {
		String pcValue = Integer.toBinaryString(pc.readRegister());
		System.out.println(pcValue+"ana el pcValue");
		pcValue=String.format("%32s", pcValue).replace(' ', '0');
		int tmp = Integer.parseInt(pcValue.substring(28, 32), 2);
		String tmp2=Integer.toString(tmp);
		String ad = Integer.toString(ads);
		tmp2 = tmp2 + ad;
		int val = Integer.parseInt(tmp2);
		System.out.println(val+"ana el val");
		pc.writeRegister(val);
		System.out.println(pc.readRegister() + "ana el pc");
	}

	public void sll(Register R1, Register R2, int shamt) {
		R1.writeRegister(R2.readRegister() << shamt);
	}

	public void srl(Register R1, Register R2, int shamt) {
		R1.writeRegister(R2.readRegister() >>> shamt);
	}

	public void lw(Register R1, Register R2, int immediate) throws Exception {
		R1.writeRegister((int) memory.read(R2.readRegister() + immediate+1024));
	}

	public void sw(Register R1, Register R2, int immediate) throws Exception {
		int x=R2.readRegister() + immediate+ 1024;
		memory.storeData( R1.readRegister(),x);
		System.out.println("In the Memory Stage:A value was updated in memory upon storing"  + memory.read(x));
	}

	public  void runCycle() throws Exception {
		clockcycles++;	
        if (!writebackQueue.isEmpty()) {
			instructionWriteBack(writebackQueue.peek());
            System.out.println("Clock " + clockcycles + ": WB instruction " + writebackQueue.remove().getValue());   
	     }

        if (!memoryQueue.isEmpty() && clockcycles % 2 == 0) {
		    instructionMem(memoryQueue.peek());
            writebackQueue.add(memoryQueue.remove());
            System.out.println("Clock " + clockcycles + ": MEM instruction " + writebackQueue.peek().getValue());
		}
        

        if (!executeQueue2.isEmpty()) {
			instructionExecute(executeQueue2.peek());
            memoryQueue.add(executeQueue2.remove());
            System.out.println("Clock " + clockcycles + ": EX instruction " + memoryQueue.peek().getValue());
        }

        if (!executeQueue1.isEmpty()) {
		//	instructionExecute(executeQueue1.peek());
            executeQueue2.add(executeQueue1.remove());
			System.out.println("Clock " + clockcycles + ": EX instruction " + executeQueue2.peek().getValue());
        }
        if (!decodeQueue2.isEmpty()) {
		    instructionDecode(decodeQueue2.peek());
            executeQueue1.add(decodeQueue2.remove());
            System.out.println("Clock " + clockcycles + ": ID instruction " + executeQueue1.peek().getValue());
        }

        if (!decodeQueue1.isEmpty()) {
			//instructionDecode(decodeQueue1.peek());
             decodeQueue2.add(decodeQueue1.remove());
		 	System.out.println("Clock " + clockcycles + ": ID instruction " + decodeQueue2.peek().getValue());

         }
		
		if (!fetchQueue.isEmpty()&&clockcycles%2!=0 ) {
			instructionFetch(pc.readRegister());
			decodeQueue1.add(fetchQueue.peek());
			System.out.println("Clock " + clockcycles + ": IF instruction " + decodeQueue1.peek().getValue());
			fetchQueue.remove();}
		// } else if (memoryQueue.isEmpty()){
		// 	instructionFetch(pc.readRegister());
		// }
		
    }
	private static int calculateClockCycles(int instructionCount) {
	   return 7 + ((instructionCount - 1) * 2);
	}

	public void executeProgram(int instructionCount) throws Exception {
	for(int i=0;i<calculateClockCycles(instructionCount);i++){
		runCycle();	}

		System.out.println("Program complete");
		System.out.println("The Total no of Clock cycles: " + clockcycles);
		System.out.println("Instructions executed: " + instructionCount);
		System.out.println("CPI: " + (double) clockcycles / instructionCount);
		for(int i=0; i<32;i++){
			System.out.println(" The content of Register R" + i + " is : " + registerFile.getRegister(i).readRegister());
		}
		for(int i=0;i<memory.getInstructionCount();i++){
			System.out.println(" The content of the Instruction at Memory address " + i + " is : " + memory.read(i));
		}
		for(int i=1024;i<memory.getDataCount()+1024;i++){
			System.out.println(" The content of the Data at Memory address " + i + " is : " + memory.read(i));
		}

		System.out.println("The content of the pc ready to fetch the next instruction is : " + pc.readRegister());
		
	}
	public void loadInstructionsFromFile(String fileName) throws Exception {
        try (
			BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line = reader.readLine();
            while ((line) != null) {
                if (!line.isEmpty() && !line.startsWith("#")) {
                    instructionToBinary(line.trim());
                }
				line = reader.readLine();

            }
			reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
	public static void instructionToBinary(String instruction) throws Exception {
        String binaryInstruction = instructionToBinaryString(instruction);
        int x= Integer.parseUnsignedInt(binaryInstruction, 2);
		memory.writeInstruction(new Instruction(x));
    }


	private static String instructionToBinaryString(String instruction) {
        String[] parts = instruction.replace(",", "").split("\\s+");
        if (parts.length < 3) {
            throw new IllegalArgumentException("Instruction format is incorrect");
        }

        String opcode = parts[0].toUpperCase();
        String opcodeBinary = opcodeMap.get(opcode);
        if (opcodeBinary == null) {
            throw new IllegalArgumentException("Unknown opcode: " + opcode);
        }

        String rd = registerMap.get(parts[1]);
        if (rd == null) {
            throw new IllegalArgumentException("Unknown register: " + parts[1]);
        }

        String rs = registerMap.get(parts[2]);
        if (rs == null) {
            throw new IllegalArgumentException("Unknown register: " + parts[2]);
        }

        switch (opcode) {
            case "ADD":
            case "SUB":
            case "SLL":
            case "SRL":
                String rt = registerMap.get(parts[3]);
                if (rt == null) {
                    throw new IllegalArgumentException("Unknown register: " + parts[3]);
                }
				String shamt = String.format("%013d", Integer.parseInt(Integer.toBinaryString(Integer.parseInt(parts[4]))));
                return opcodeBinary + rd + rs + rt + shamt;

            case "MULI":
            case "ADDI":
            case "BNE":
            case "ANDI":
            case "ORI":
            case "LW":
            case "SW":
			String immediate = String.format("%018d", Integer.parseInt(Integer.toBinaryString(Integer.parseInt(parts[3]))));
			return opcodeBinary + rd + rs + immediate;

            case "J":
			String address = String.format("%028d", Integer.parseInt(Integer.toBinaryString(Integer.parseInt(parts[1]))));
			return opcodeBinary + address;

            default:
                throw new IllegalArgumentException("Unknown format for opcode: " + opcode);
        }
    }
    
	public static void main(String[] args) throws Exception {
		CPU cpu = new CPU();
		cpu.memory.writeData(12222);
		cpu.memory.writeData(234);
		cpu.loadInstructionsFromFile("assembly.txt");
        cpu.executeProgram(memory.getInstructionCount());
		// System.out.println(cpu.memory.read(1027));
        // Register r1=new Register();
		// r1.writeRegister(77);
		// cpu.registerFile.setIndex(1, r1);
		// Register r2=new Register();
		// r2.writeRegister(0);
		// cpu.registerFile.setIndex(2, r2);
		// Register r3=new Register();
		// r3.writeRegister(7);
		// cpu.registerFile.setIndex(3, r3);
		//System.out.println(cpu.registerFile.getRegister(1).readRegister());
		//cpu.memory.writeInstruction(new Instruction(545783810));
		//cpu.memory.writeInstruction(new Instruction(-1601699840));
	//	cpu.memory.writeInstruction(new Instruction(8938496));
		//cpu.memory.writeInstruction(new Instruction(-1601698816));
	//	cpu.memory.writeInstruction(new Instruction(1879048195));
	 //   cpu.executeProgram(1);
	//	System.out.println(cpu.pc.readRegister());
		//System.out.println(cpu.memory.read(1024));

		//System.out.println(cpu.memory.read(r2.readRegister()));
		// cpu.memory.writeInstruction(new Instruction(1));
		// cpu.memory.writeInstruction(new Instruction(2));
		// cpu.memory.writeInstruction(new Instruction(3));
		// cpu.memory.writeInstruction(new Instruction(4)); 
		// cpu.memory.writeInstruction(new Instruction(5)); 
		// cpu.memory.writeInstruction(new Instruction(6)); 
		// cpu.memory.writeInstruction(new Instruction(7)); 
		//cpu.memory.writeInstruction(new Instruction(8));
	//	cpu.memory.writeInstruction(new Instruction(8938496));
	// cpu.memory.writeData(234);
	// 	cpu.memory.writeInstruction(new Instruction(-1601699840));
		// registerMap.keySet().forEach(System.out::println);
		//cpu.executeProgram(memory.getInstructionCount());
//cpu.memory.writeData(234);
//cpu.memory.writeInstruction(new Instruction(-1601699840));
// cpu.memory.writeInstruction(new Instruction(-1333264384));

// cpu.executeProgram(1);
// cpu.memory.writeData(345);
 //System.out.println(cpu.memory.read(1024));
//  System.out.println(r1.readRegister());
// System.out.println(cpu.memory.read(1025));


	}
	
}	