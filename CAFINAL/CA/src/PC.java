public class PC extends Register {
	private static PC instance;
	
	public PC() {
		super();
	}
	

	
	public void incrementPC() {
	//	System.out.println("ana incrementedddd");
		value++;
		//System.out.println("wrabna incrementedddd");
	}

}
