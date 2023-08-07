import java.util.*;
public class Tomasulo {
    //Global variables
    //----------------
    //Operation Latency variables declaration
    static int addLatency=0,subLatency=0,mulLatency=0,divLatency=0,loadLatency=0,storeLatency=0,numberOfCycles =0;
    //Main Memory and Register File declaration i.e: Architecture memory elements
    static Double[] Memory = new Double[8*1024];
    static Register [] registerFile = new Register [32];
    /*Various reservationStations declaration and initialization as AddRS,MultiRS,LoadRS,StoreRS for
    the Addition-Reservation-Stations,Multiplication-Reservation-Stations,Load-Reservation-Stations, At the Store-Buffers respectively */
    static reservationStation[] AddRS = {new reservationStation("A1"),new reservationStation("A2"),new reservationStation("A3")};
    static reservationStation[] MultiRS = {new reservationStation("M1"),new reservationStation("M2")};
    static reservationStation[] LoadRS = {new reservationStation("L1"),new reservationStation("L2"),new reservationStation("L3"),new reservationStation("L4"),new reservationStation("L5")};
    static reservationStation[] StoreRS = {new reservationStation("S1"),new reservationStation("S2"),new reservationStation("S3"),new reservationStation("S4"),new reservationStation("S5")};
    //The Program instruction declaration in the form of an arraylist
    static ArrayList<Instruction>instructions = new ArrayList<Instruction>();
    //The result of the most recent executed operation
    static double result=0;
    //A method to initialize  the architecture memory elements to initial values of -1.0
    public static void initializeStorage(){
        Arrays.fill(Memory,-1.0);
        for(int i =0; i<registerFile.length;i++) {
            registerFile[i]= new Register("F"+i);
        }
    }
    /*4 Methods that return the index of the first available reservation Station in one of
     the architecture reservation Station if non are available return -1*/
    public static int checkAdditionRSFull(){
        for(int i =0 ;i<AddRS.length;i++){
            if(!AddRS[i].isBusy()){
                return i;
            }
        }
        return -1;
    }
    public static int checkMultiRSFull(){
        for(int i =0 ;i<MultiRS.length;i++){
            if(!MultiRS[i].isBusy()){
                return i;
            }
        }
        return -1;
    }
    public static int checkLoadRSFull(){
        for(int i =0 ;i<LoadRS.length;i++){
            if(!(LoadRS[i].isBusy())){
                return i;
            }
        }
        return -1;
    }
    public static int checkStoreRSFull(){
        for(int i =0 ;i<StoreRS.length;i++){
            if(!StoreRS[i].isBusy()){
                return i;
            }
        }
        return -1;
    }
    /*A method that queries the architecture registerFile to find the value of
     the given register name if possible else return the value of its computing operation*/
    public static String queryRegisterFile(String RegisterName) {
        for(Register i: registerFile ) {
            if(i.getName().equalsIgnoreCase(RegisterName))
            {
                if(i.getqI().equals("0")) {
                    return i.getValue()+"";
                }
                else {
                    return i.getqI();
                }
            }
        }
        return"-1";
    }
    /*A method that updates the architecture registerFile to update the QI field of the given register name
     with the given tag to announce that the register value is now unusable till further updates*/
    public static void setQi(String tag,String registerName) {
        for(Register i: registerFile)
        {
            if(i.getName().equalsIgnoreCase(registerName))
            {
                i.setqI(tag);
            }
        }
    }
    //A method that executes all executable instructions in all the architecture reservation Station
    public static void execute(){
        //checking for executable instructions in the AddRS
        for(int i =0; i< AddRS.length;i++){
            reservationStation rs = AddRS[i];
            if(rs.getCyclesRemaining()==-1){
                rs = new reservationStation(rs.getTag());
                for(reservationStation j:AddRS) {
                    if(j.getqJ().equalsIgnoreCase(rs.getTag())) {
                        j.setvJ(result);
                        j.setqJ("0");
                    }
                    if(j.getqK().equalsIgnoreCase(rs.getTag())) {
                        j.setvK(result);
                        j.setqK("0");
                    }
                }
                for(reservationStation j:MultiRS) {
                    if(j.getqJ().equalsIgnoreCase(rs.getTag())) {
                        j.setvJ(result);
                        j.setqJ("0");
                    }
                    if(j.getqK().equalsIgnoreCase(rs.getTag())) {
                        j.setvK(result);
                        j.setqK("0");

                    }
                }
                for(reservationStation j:StoreRS) {
                    if(j.getqJ().equalsIgnoreCase(rs.getTag())) {
                        j.setvJ(result);
                        j.setqJ("0");
                    }
                }
                for(Register j:registerFile){
                    if(j.getqI().equalsIgnoreCase(rs.getTag())) {
                        j.setValue(result);
                        j.setqI("0");
                    }
                }
                result=0;
                AddRS[i]=rs;
                continue;
            }
            if(rs.getqJ().equals("0")&&rs.getqK().equals("0")){
                if(rs.getCyclesRemaining()==0){
                    switch (rs.getOp().toUpperCase()) {
                        case "ADD.D": result= rs.getvJ()+rs.getvK();; break;
                        case "SUB.D": result= rs.getvJ()-rs.getvK();
                    }
                }
                AddRS[i].setCyclesRemaining(rs.getCyclesRemaining()-1);
            }
        }
        //checking for executable instructions in the MultiRS
        for(int i =0; i< MultiRS.length;i++) {
            reservationStation rs = MultiRS[i];
            if(rs.getCyclesRemaining()==-1){
                rs = new reservationStation(rs.getTag());
                for(reservationStation j:AddRS) {
                    if(j.getqJ().equalsIgnoreCase(rs.getTag())) {
                    j.setvJ(result);
                    j.setqJ("0");
                }
                    if(j.getqK().equalsIgnoreCase(rs.getTag())) {
                    j.setvK(result);
                    j.setqK("0");
                }
                }
                for(reservationStation j:MultiRS) {
                    if(j.getqJ().equalsIgnoreCase(rs.getTag())) {
                    j.setvJ(result);
                    j.setqJ("0");
                }
                    if(j.getqK().equalsIgnoreCase(rs.getTag())) {
                    j.setvK(result);
                    j.setqK("0");

                }
                }
                for(reservationStation j:StoreRS) {
                    if(j.getqJ().equalsIgnoreCase(rs.getTag())) {
                        j.setvJ(result);
                        j.setqJ("0");
                    }
                }
                for(Register j:registerFile){
                    if(j.getqI().equalsIgnoreCase(rs.getTag())) {
                    j.setValue(result);
                    j.setqI("0");
                }
                }
                result=0;
                MultiRS[i]=rs;
                continue;
            }
            if(rs.getqJ().equals("0")&&rs.getqK().equals("0")){
                if(rs.getCyclesRemaining()==0) {
                    switch (rs.getOp().toUpperCase()) {
                        case "MUL.D": result= rs.getvJ()*rs.getvK(); break;
                        case "DIV.D": result= rs.getvJ()/rs.getvK();
                    }
                }
                MultiRS[i].setCyclesRemaining(rs.getCyclesRemaining()-1);
            }
        }
        //checking for executable instructions in the LoadRS
        for(int i =0; i< LoadRS.length;i++) {
            reservationStation rs = LoadRS[i];
            if(rs.getCyclesRemaining()==-1){
                rs = new reservationStation(rs.getTag());
                for(reservationStation j:AddRS) {
                    if(j.getqJ().equalsIgnoreCase(rs.getTag())) {
                    j.setvJ(result);
                    j.setqJ("0");
                }
                    if(j.getqK().equalsIgnoreCase(rs.getTag())) {
                    j.setvK(result);
                    j.setqK("0");
                }
                }
                for(reservationStation j:MultiRS) {
                    if(j.getqJ().equalsIgnoreCase(rs.getTag())) {
                    j.setvJ(result);
                    j.setqJ("0");
                }
                    if(j.getqK().equalsIgnoreCase(rs.getTag())) {
                    j.setvK(result);
                    j.setqK("0");

                }
                }
                for(reservationStation j:StoreRS) {
                    if(j.getqJ().equalsIgnoreCase(rs.getTag())) {
                        j.setvJ(result);
                        j.setqJ("0");
                    }
                }
                for(Register j:registerFile){
                    if(j.getqI().equalsIgnoreCase(rs.getTag())){
                    j.setValue(result);
                    j.setqI("0");
                }
                }
                result=0;
                LoadRS[i]=rs;
                continue;
            }
            if(rs.getCyclesRemaining()==0) {
                result =Memory[rs.getAddress()];
            }
            LoadRS[i].setCyclesRemaining(rs.getCyclesRemaining()-1);
        }
        //checking for executable instructions in the StoreRS
        for(int i =0 ; i<StoreRS.length;i++){
            reservationStation rs = StoreRS[i];
            if(rs.getqJ().equalsIgnoreCase("0")) {

                if(rs.getCyclesRemaining()==-1) {
                    StoreRS[i]=new reservationStation(rs.getTag());
                    Memory[rs.getAddress()]= rs.getvJ();
                }
                StoreRS[i].setCyclesRemaining(rs.getCyclesRemaining()-1);
            }
        }
    }
    //A method that issues all issuable instructions in architecture the instructions arrayList
    public static void issue(ArrayList<Instruction> instructions) {
         loop:for(int i=0;i<instructions.size();i++){
                Instruction instruction= instructions.get(i);
                switch (instruction.getOperation().toUpperCase()){
                    case "ADD.D":
                    case "SUB.D":
                        int index = checkAdditionRSFull();
                        if(index!=-1){
                            AddRS[index].setOp(instruction.getOperation());
                            AddRS[index].setBusy(true);
                            if(instruction.getOperation().equalsIgnoreCase("ADD.D")) {
                                AddRS[index].setCyclesRemaining(addLatency);
                            }
                            else{
                                AddRS[index].setCyclesRemaining(subLatency);
                            }
                            try{
                                double vJ= Double.parseDouble(queryRegisterFile(instruction.getOperand1()));
                                AddRS[index].setvJ(vJ);
                                AddRS[index].setqJ("0");
                            }
                            catch(Exception exception) {
                                AddRS[index].setqJ(queryRegisterFile(instruction.getOperand1()));
                            }
                            try {
                                double vK= Double.parseDouble(queryRegisterFile(instruction.getOperand2()));
                                AddRS[index].setvK(vK);
                                AddRS[index].setqK("0");
                            }
                            catch(Exception exception) {
                                AddRS[index].setqK(queryRegisterFile(instruction.getOperand2()));
                            }
                            setQi(AddRS[index].getTag(),instruction.getDest());
                            instructions.remove(instruction);
                            break loop;
                        }
                        break;
                    case"MUL.D":
                    case"DIV.D":
                        index = checkMultiRSFull();
                        if(index!=-1){
                            MultiRS[index].setOp(instruction.getOperation());
                            MultiRS[index].setBusy(true);
                            if(instruction.getOperation().equalsIgnoreCase("MUL.D")) {
                                MultiRS[index].setCyclesRemaining(mulLatency);
                            }
                            else{
                            MultiRS[index].setCyclesRemaining(divLatency);
                            }
                            try {
                                double vJ= Double.parseDouble(queryRegisterFile(instruction.getOperand1()));
                                MultiRS[index].setvJ(vJ);
                                MultiRS[index].setqJ("0");
                            }
                            catch(Exception exception) {
                                MultiRS[index].setqJ(queryRegisterFile(instruction.getOperand1()));
                            }
                            try{
                                double vK= Double.parseDouble(queryRegisterFile(instruction.getOperand2()));
                                MultiRS[index].setvK(vK);
                                MultiRS[index].setqK("0");
                            }
                            catch(Exception exception){
                                MultiRS[index].setqK(queryRegisterFile(instruction.getOperand2()));
                            }
                            setQi(MultiRS[index].getTag(),instruction.getDest());
                            instructions.remove(instruction);
                            break loop;
                        }
                        break;
                    case "LD":
                        index = checkLoadRSFull();
                        if(index!=-1) {
                            LoadRS[index].setOp(instruction.getOperation());
                            LoadRS[index].setBusy(true);
                            LoadRS[index].setCyclesRemaining(loadLatency);
                            int address= instruction.getMemoryLocation();
                            LoadRS[index].setAddress(address);
                            setQi(LoadRS[index].getTag(),instruction.getOperand1()+"");
                            instructions.remove(instruction);
                            break loop;
                        }
                        break;
                    case "SD":
                        index = checkStoreRSFull();
                        if(index!=-1) {
                            StoreRS[index].setOp(instruction.getOperation());
                            StoreRS[index].setBusy(true);
                            StoreRS[index].setCyclesRemaining(storeLatency);
                            try{
                                double vJ= Double.parseDouble(queryRegisterFile(instruction.getOperand1()));
                                StoreRS[index].setvJ(vJ);
                                StoreRS[index].setqJ("0");
                            }
                            catch(Exception exception) {
                                StoreRS[index].setqJ(queryRegisterFile(instruction.getOperand1()));
                            }
                            int address= instruction.getMemoryLocation();
                            StoreRS[index].setAddress(address);
                            instructions.remove(instruction);
                            break loop;
                        }
                        break;
                }
        }
    }
    //A method to check if the tomasulo architecture completed execution or not
    public static boolean checkComplete() {
        for(reservationStation i: AddRS) {
            if(i.isBusy()){
                return false;
            }
        }
        for(reservationStation i: MultiRS) {
            if(i.isBusy()){
                return false;
            }
        }
        for(reservationStation i: LoadRS) {
            if(i.isBusy()){
                return false;
            }
        }
        for(reservationStation i: StoreRS) {
            if(i.isBusy()){
                return false;
            }
        }
        return instructions.isEmpty();
    }
    public static void main(String[] args) {
        //initialization of the architecture memory elements
        initializeStorage();
        Memory[100]=100.0;
        registerFile[5].setValue(2.1);
        //taking inputs from the user
        Scanner sc = new Scanner(System.in);
        //taking operation's latency while maintaining that all of them are integers
        System.out.println("Welcome to Tomasulo please enter the latency of the following operation");
        while(true){
            try{
                System.out.print("Addition operation : ");
                addLatency = sc.nextInt();
                break;
            }
            catch(Exception exception){
                System.out.println("the value is not a integer please try again");
                sc.nextLine();
            }
        }
        while(true){
            try{

                System.out.print("Subtract operation : ");
                subLatency = sc.nextInt();
                break;
            }
            catch(Exception exception){
                System.out.println("the value is not a integer please try again");
                sc.nextLine();
            }
        }
        while(true) {
            try {
                System.out.print("Multiplication operation : ");
                mulLatency = sc.nextInt();
                break;
            }
            catch(Exception exception){
                System.out.println("the value is not a integer please try again");
                sc.nextLine();
            }
        }
        while(true) {
            try {
                System.out.print("Division operation : ");
                divLatency = sc.nextInt();
                break;
            }
            catch(Exception exception){
                System.out.println("the value is not a integer please try again");
                sc.nextLine();
            }
        }
        while(true) {
            try {
                System.out.print("Load operation : ");
                loadLatency = sc.nextInt();
                break;
            }
            catch(Exception exception){
                System.out.println("the value is not a integer please try again");
                sc.nextLine();
            }
        }
        while(true) {
            try {
                System.out.print("Store operation : ");
                storeLatency = sc.nextInt();
                break;
            }
            catch(Exception exception){
                System.out.println("the value is not a integer please try again");
                sc.nextLine();
            }
        }
        /*taking program instructions while checking each instruction
        has the proper structure from operation name to the proper operands
        and ending the program in either exit or space*/
        System.out.println("please enter your program instructions and at the end of the program write exit");
        sc.nextLine();
        int count = 1;
        while(true) {
            System.out.print("instruction #"+ count++ +" : ");
            String instruction = sc.nextLine();
            if(instruction == null || instruction.isEmpty() || instruction.equalsIgnoreCase("exit")) {
                break;
            }
            if(instruction.charAt(0) == '#') {
                continue;
            }
            String[] instelements = instruction.split(" ");
            if(instelements.length!=2){
                System.out.println("instruction Structure is un recognizable please reenter it");
                count--;
                continue;
            }
            String operation = instelements[0];
            String[] operands = instelements[1].split(",");
            if(operands.length<2||operands.length>3){
                System.out.println("instruction Structure is un recognizable please reenter it");
                count--;
                continue;
            }
            switch (operation.toUpperCase()) {
                case "ADD.D", "SUB.D", "MUL.D", "DIV.D" -> {
                    if (operands.length != 3) {
                        System.out.println("the operation at line " + count + " structure is incorrect please try again");
                        count --;
                        continue;
                    }
                    instructions.add(new Instruction(operation, operands[0], operands[1], operands[2]));
                }
                case "LD","SD" -> {
                    //checks the structure of the load and store operation e.g: LD destination,loadMemoryLocation
                    if (operands.length != 2) {
                        System.out.println("the operation at line " + count + " structure is incorrect please try again");
                        count --;
                        continue;
                    }
                    try {
                        instructions.add(new Instruction(operation, operands[0], Integer.parseInt(operands[1])));
                    }
                    catch(Error error) {
                        System.out.println("the load or store operation at line " + count + " structure is incorrect please try again");
                        count --;
                    }
                }
                default -> {
                    System.out.println("instruction not found please reenter it");
                    count--;
                }
            }
        }
        /*executing the tomasulo issuing and execution methods every one cycle
        and printing the program state and active memory elements*/
        while(!checkComplete()) {
            execute();
            issue(instructions);
            System.out.println("At cycle #"+numberOfCycles++ );
            System.out.println("At the Addition-Reservation-Station");
            for(reservationStation rs :AddRS)
            {
                if(rs.isBusy())
                {
                    System.out.println(rs);
                }
            }
            System.out.println("<-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+->");
            System.out.println("At the Multiplication-Reservation-Station");
            for(reservationStation rs :MultiRS)
            {
                if(rs.isBusy())
                {
                    System.out.println(rs);
                }
            }
            System.out.println("<-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+->");
            System.out.println("At the Load-Reservation-Station");
            for(reservationStation rs :LoadRS)
            {
                if(rs.isBusy())
                {
                    System.out.println(rs);
                }
            }
            System.out.println("<-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+->");
            System.out.println("At the Store-Buffers");
            for(reservationStation rs :StoreRS)
            {
                if(rs.isBusy())
                {
                    System.out.println(rs);
                }
            }
            System.out.println("<-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+->");
            System.out.println("At the Register-File");
            for(Register r :registerFile)
            {
                if(r.getValue()!=-1.0)
                {
                    System.out.println(r);
                }
            }
            System.out.println("<-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+->");
            System.out.println("At the Main-Memory");
            for(int j=0;j<Memory.length;j++)
            {
                double m = Memory[j];
                if(m!=-1.0)
                {
                    System.out.println("At location : " +j+" , the value : "+m +" is stored");
                }
            }
            System.out.println("<-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+->");
        }
    }
}