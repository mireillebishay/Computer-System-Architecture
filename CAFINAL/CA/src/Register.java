public class Register {

	protected int value;

	public Register() {
	//	value = 0;
	}

	public int readRegister() {
		return value;
	}

	public void writeRegister(int value) {
		this.value = value;
	}
}
