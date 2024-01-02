public class ZeroRegister extends Register{
	
	private final int value;
	private static ZeroRegister instance;
	
	private ZeroRegister() {
		value = 0;
	}
	
	public static ZeroRegister getInstance() {
		if (instance == null) { 
			instance = new ZeroRegister();
		}
		return instance;
	}

	public void writeRegister(int value) {
		
	}
}
