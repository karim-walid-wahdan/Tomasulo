public class reservationStation {
    private boolean busy = false;
    private String tag = "";
    private String op ="";
    private double vJ=0.0;
    private double vK=0.0;
    private String qJ = "0";
    private String qK = "0";
    private int address =0;
    private int cyclesRemaining =-2;
    public  reservationStation (String tag){
        this.tag =tag;
    }
    public boolean isBusy() {
        return busy;
    }
    public void setBusy(boolean busy) {
        this.busy = busy;
    }
    public String getTag() {
        return tag;
    }
    public void setTag(String tag) {
        this.tag = tag;
    }
    public String getOp() {
        return op;
    }
    public void setOp(String op) {
        this.op = op;
    }
    public double getvJ() {
        return vJ;
    }

    public void setvJ(double vJ) {
        this.vJ = vJ;
    }

    public double getvK() {
        return vK;
    }

    public void setvK(double vK) {
        this.vK = vK;
    }

    public String getqJ() {
        return qJ;
    }

    public void setqJ(String qJ) {
        this.qJ = qJ;
    }

    public String getqK() {
        return qK;
    }

    public void setqK(String qK) {
        this.qK = qK;
    }

    public int getAddress() {
        return address;
    }

    public void setAddress(int address) {
        this.address = address;
    }
    public int getCyclesRemaining() {
        return cyclesRemaining;
    }
    public void setCyclesRemaining(int cyclesRemaining) {
        this.cyclesRemaining = cyclesRemaining;
    }
    @Override
    public String toString() {
        if(op.equalsIgnoreCase("LD")){
            return "reservationStationName :"+ this.tag +", busyState: "+this.busy+",Operation :"+this.op+" load Address: "+this.address+" , Cycles Remaining : "+ cyclesRemaining;
        }
        if(op.equalsIgnoreCase("SD")){
            return "reservationStationName :"+ this.tag +", busyState: "+this.busy+",Operation :"+this.op+", VJ: "+this.vJ+", QJ: "+this.qJ+" Store Address: "+this.address+" , Cycles Remaining : "+ cyclesRemaining;
        }
        return "reservationStationName :"+ this.tag +", busyState: "+this.busy+",Operation :"+this.op+", VJ: "+this.vJ+", VK: "+this.vK +", QJ: "+this.qJ+", QK: "+this.qK+", A: "+this.address+" , Cycles Remaining : "+ cyclesRemaining;
    }
}
