public class Memory {

	private int[] memory;
	private int instructionIndex;
	private int dataIndex;
	private int instructionCount;
	private int dataCount;
	public Memory() {
		memory = new int[2048];
		instructionIndex = 0;
		dataIndex = 1024;
		instructionCount = 0;
		dataCount = 0;

	}

	public int getInstructionCount() {
		return instructionCount;
	}

	public int read(int address) throws Exception {
		if (address < 0 || address > 2047)
			throw new Exception("Invalid address!");
		return memory[address]; // consider 2047 - address
	}

	public void writeInstruction(Instruction instruction) throws Exception {
		if (instructionIndex > 1023)
			throw new Exception("Instruction memory is full");
		memory[instructionIndex] = instruction.getValue();
		instructionIndex++;
		instructionCount++;
		CPU.addtoFetchQueue(instruction);
	
	}

	public void writeData(int data) throws Exception {
		if (dataIndex > 2047)
			throw new Exception("Data memory is full");
		memory[dataIndex++] = data;
		dataCount++;
	}
	public void storeData(int data,int address) throws Exception {
		if (address < 1024 || address > 2047)
			throw new Exception("Invalid address!");
		memory[address] = data;
		dataCount++;
	}
	public int getMemomrySize(){
		return memory.length;
	}	
	public int getDataCount(){
		return dataCount;
	}


}
