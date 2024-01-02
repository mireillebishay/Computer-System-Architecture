public class Instruction {
    private int value;
    private String inst;
    private int op;
    private int rs;
    private int rt;
    private int rd;
    private int shamt;
	private int immediate;
	private int address;
    private int ALUvalue;
    private int loadValue;
    public Instruction(int value){
        this.value = value;
        this.inst =inst;
        this.op = op;
        this.rs = rs;
        this.rt = rt;
        this.rd = rd;
        this.shamt = shamt;
        this.immediate = immediate;
        this.address = address;
        this.ALUvalue = ALUvalue;
        this.loadValue = loadValue;

        // String inst = Integer.toBinaryString(value);
		// inst = String.format("%32s", inst).replace(' ', '0');
		// op = Integer.parseInt(inst.substring(0, 4), 2); // bits31:28
		// rs = Integer.parseInt(inst.substring(4, 9), 2); // bits27:23
		// rt = Integer.parseInt(inst.substring(9, 14), 2); // bit22:18
		//  rd = Integer.parseInt(inst.substring(14, 19), 2); // bits17:13
		// shamt = Integer.parseInt(inst.substring(19, 32), 2); // bits12:0
		// immediate = Integer.parseInt(inst.substring(14, 32), 2); // bits17:0
		// address = Integer.parseInt(inst.substring(4, 32), 2); // bits27:0
    }
    //generate setters and getters
    public int getValue(){
        return value;
    }

    public void setValue(int value){
        this.value = value;
    }

    public String getInst(){
        return inst;
    }
    public void setInst(String inst){
        this.inst = inst;
    }
    public int getOp(){
        return op;
    }
    public void setOp(int op){
        this.op = op;
    }
    public int getRs(){
        return rs;
    }
    public void setRs(int rs){
        this.rs = rs;
    }
    public int getRt(){
        return rt;
    }
    public void setRt(int rt){
        this.rt = rt;
    }
    public int getRd(){
        return rd;
    }
    public void setRd(int rd){
        this.rd = rd;
    }
    public int getShamt(){
        return shamt;
    }
    public void setShamt(int shamt){
        this.shamt = shamt;
    }
    public int getImmediate(){
        return immediate;
    }
    public void setImmediate(int immediate){
        this.immediate = immediate;
    }
    public int getAddress(){
        return address;
    }
    public void setAddress(int address){
        this.address = address;
    }
    public int getALUvalue(){
        return ALUvalue;
    }
    public void setALUvalue(int ALUvalue){
        this.ALUvalue = ALUvalue;
    }
    public int getLoadValue(){
        return loadValue;
    }
    public void setLoadValue(int loadValue){
        this.loadValue = loadValue;
    }

    
}
