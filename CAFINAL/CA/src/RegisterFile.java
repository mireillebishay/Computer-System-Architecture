public class RegisterFile {
	private Register[] registerFile;
	private int noOfRegisters;
	private static RegisterFile instance;

	private RegisterFile() {
		registerFile = new Register[33];
		noOfRegisters = 0;
	}

	public static RegisterFile getInstance() {
		if (instance == null) {
			instance = new RegisterFile();
		}
		return instance;
	}

	public void setIndex(int index, Register register) {
		registerFile[index] = register;
		noOfRegisters++;
	}

	public Register getRegister(int index) {
		return registerFile[index];
	}
public int getRegisterFileSize (){
	return registerFile.length;
}
public int getNoOfRegisters(){
	return noOfRegisters;}

}
