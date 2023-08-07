public class Instruction {
    private String operation="";
    private String  dest="";
    private int memoryLocation=0;
    private String operand1="";
    private String operand2="";
    public Instruction(String operation, String dest, String operand1, String operand2) {
        this.operation = operation;
        this.dest = dest;
        this.operand1 = operand1;
        this.operand2 = operand2;
    }
    public Instruction(String operation, String operand1, int memoryLocation) {
        this.operation = operation;
        this.memoryLocation = memoryLocation;
        this.operand1 = operand1;
    }
    public String getOperation() {
        return operation;
    }

    public void setOperation(String operation) {
        this.operation = operation;
    }

    public String getDest() {
        return dest;
    }

    public void setDest(String dest) {
        this.dest = dest;
    }

    public String getOperand1() {
        return operand1;
    }

    public void setOperand1(String operand1) {
        this.operand1 = operand1;
    }

    public String getOperand2() {
        return operand2;
    }

    public void setOperand2(String operand2) {
        this.operand2 = operand2;
    }

    public int getMemoryLocation() {
        return this.memoryLocation;
    }

    public void setMemoryLocation(int memoryDest) {
        this.memoryLocation = memoryDest;
    }

    @Override
    public String toString(){
        if(!operand2.equals(""))
        {
            return "Operation : "+operation+", Dest : "+dest+" ,Operand 1 :"+operand1+" ,Operand 2 : "+operand2 ;
        }
        else
        {
            return "Operation : "+operation+", memory Location : "+memoryLocation+" ,register : "+operand1;
        }

    }
}
