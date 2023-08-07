public class Register {
    private String name="";
    private String qI="0";
    private double value=-1;
    public Register(String name){
        this.name=name;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getqI() {
        return qI;
    }

    public void setqI(String qI) {
        this.qI = qI;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }
    public String toString() {
        return "register Name :"+ this.name +" , QI: "+this.qI+" , value: "+this.value;
    }

}
