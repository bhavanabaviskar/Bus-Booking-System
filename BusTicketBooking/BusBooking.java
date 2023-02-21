package BusTicketBooking;
import java.time.LocalDate;
import java.util.LinkedList;
import java.util.Scanner;
import java.util.HashMap;

public class BusBooking {

	public void mainMethod(){
		int n;
		do{
		System.out.println("\t\t\t\tx-x-x-x-x-x-x-x-x-x-x-x-x-x-x");
		System.out.println("\t\t\t\tx                           x");
		System.out.println("\t\t\t\tx\t1: Log In           x"
				+ "\n\t\t\t\tx\t2: Register         x"+ "\n\t\t\t\tx\t3: Exit             x");
		System.out.println("\t\t\t\tx                           x");

		System.out.println("\t\t\t\tx-x-x-x-x-x-x-x-x-x-x-x-x-x-x\n");

		System.out.print("\t\t\t\tEnter Your Choice: ");

		Scanner sc = new Scanner(System.in);
		n = sc.nextInt();

		Customer customer = new Customer();
		switch(n){
		case 1: customer.Login(); break;
		case 2: customer.Register(); break;
		default: System.out.println("Please enter appropriate number...!");
		}
		}while(n!=3);

	}
	public static void main(String[] args) {

		System.out.println("\n\t\t\t-:-:-:-: Welcome to Tours & Travel :-:-:-:-\n");
		BusBooking busBooking = new BusBooking();
		busBooking.mainMethod();

	}

}
class BookingDatabase {
	int busNo;
	int billAmt;
	String username;
	String date;
	int seats;
	public void getBookingInfo(){
		System.out.println(busNo + "\t" + username + "\t" + seats + "\t" + billAmt + "\t" + date);
	}
	
	public void setBookingInfo(int busNo, int billAmt, String username, String date, int seats){
		this.busNo = busNo;
		this.billAmt = billAmt;
		this.username = username;
		this.date = date;
		this.seats = seats;

	}
}
 class Bus {
	Scanner s;
	Buses paradise = new Buses();
	Buses volvo = new Buses();
	Buses imperia  = new Buses();
	Buses safedrive = new Buses();
	Buses dreamland = new Buses(); 
	Buses quantum = new Buses();
	static LinkedList<Buses> busObject = new LinkedList<>();
	static LinkedList<BookingDatabase> busDatabase = new LinkedList<>();
	String username;
	public Bus() {
		
	}
	Bus(String username){
		this.username = username;
		
	}
	public void addBusDetails(){
		Buses b1 = new Buses();
		s = new Scanner(System.in);
		System.out.print("Enter bus no: ");
		int bno = s.nextInt();
		System.out.print("Enter bus name: ");
		String busName = s.next();
		System.out.print("Enter bus source: ");
		String source = s.next();
		System.out.print("Enter bus destination: ");
		String destination = s.next();
		System.out.print("Enter ticket price: ");
		int ticketPrice = s.nextInt();
		System.out.print("Enter available seats: ");
		int availableSeats = s.nextInt();
		b1.setBusData(bno, busName, source, destination, ticketPrice, availableSeats);
		busObject.add(b1);
		System.out.println("Details added Successfully....!!!");
	}
	public void setBusDetails(){
		paradise.setBusData(102425, "Paradise", "Pune", "Goa", 1400, 50);
		 volvo.setBusData(158725, "Volvo Exp", "Pune", "Mumbai", 400, 150);
		 imperia.setBusData(895425, "Imperia sea", "Pune", "Punjab", 3400, 50);
		 dreamland.setBusData(987562, "Dreamland", "Pune", "Noida", 4400, 50);
		 quantum.setBusData(874685, "Quantum bayer", "Pune", "Mathura", 1400, 30);
		 busObject.add(paradise);
		 busObject.add(volvo);
		 busObject.add(imperia);
		 busObject.add(dreamland);
		 busObject.add(quantum);

	}
	public void displayBusDetails(){
		System.out.println("x-----------------------------------------------------------------------x");
		System.out.println("|\t\t\t\tAvailable buses\t\t\t\t|");
		System.out.println("x-----------------------------------------------------------------------x");
		System.out.println("|\tNo\tBus no\t Bus Name \tSource\tDestiny\tPrice\tSeats\t|");
		System.out.println("|\t\t\t\t\t\t\t\t\t|");
		for(int i=0;i<busObject.size();i++){
			System.out.print("|\t"+ (i+1));
			busObject.get(i).getBusData();
		}
		System.out.println("x-----------------------------------------------------------------------x");

	}
	
	public void chooseBus(int no,int tickets, String username){
		if(busObject.get(no-1).availableSeats > tickets && tickets > 0){
			busObject.get(no-1).bookTickets(tickets);
			billGenerate(no-1,tickets,username);
			BookingDatabase bd = new BookingDatabase();
			String date = LocalDate.now().toString();
			bd.setBookingInfo(busObject.get(no-1).bno, busObject.get(no-1).ticketPrice * tickets, username, date, tickets);
			busDatabase.add(bd);
		}else{
			System.out.println();
			System.out.println("Seats are unavailable...!");
			System.out.println("=========================================");
		}
	}
	
	public void cancelBooking(int busNo, int cancelTicket, String username){
		boolean flag = false;
		for(int i=0;i<busDatabase.size();i++){
			
			if(busDatabase.get(i).busNo == busObject.get(busNo-1).bno && busDatabase.get(i).username.equals(username)){
				if(busDatabase.get(i).seats >= cancelTicket ){
					busObject.get(busNo-1).availableSeats +=  cancelTicket;
					busDatabase.get(i).seats -= cancelTicket;
					System.out.println("Ticket Cancellation Succesful...!");
					flag = true;
					break;
					
				}else{
					System.out.println("You haven't book that much tickets...!");
				}
			}else{
				flag = false;

			}
			
		}
		

		if(flag == false)
			System.out.println("Please book a ticket first...!");
	}
	
	public void billGenerate(int no, int tickets, String username){
		System.out.println();
		String date = LocalDate.now().toString();
		System.out.println("*********************************************************************");
		System.out.println("\tBill No: "+ 1021 + "\t\tTours & Travel Bill\t" + date );
		System.out.println("*********************************************************************");
		System.out.println("\tName: "+ username + "\t\t\t\t"+ "Bus No: " + busObject.get(no).bno);
		System.out.println("\tBus Name: "+
		busObject.get(no).busName + "\n");
		System.out.println("\tTotal No of seats: \t\t"+ tickets + " x "+ busObject.get(no).ticketPrice);
		
		System.out.println("\tTotal amount: \t\t\tRs. "+ busObject.get(no).ticketPrice * tickets);
		System.out.println("*********************************************************************");

	}
	
	public void displayBookingTransactions(){
		System.out.println("*********************************************************************");
		System.out.println("\tBus No \tUsername \tSeats  \tTotal Amount" );
		System.out.println("*********************************************************************");

		for (int i = 0; i < busDatabase.size(); i++) {
			System.out.println("\t"+busDatabase.get(i).busNo + "\t" + busDatabase.get(i).username 
					+ "\t\t" + busDatabase.get(i).seats +"\t" + busDatabase.get(i).billAmt);
		}
	}
}
 class Buses {
 	
 	public int bno = 0;
 	public String busName = "";
 	public String source = "";
 	public String destination = "";
 	public int ticketPrice = 0;
 	public int availableSeats = 0;
 	public void setBusData(int bno, String busName, String source, String destination,int ticketPrice, int availableSeats){
 		this.bno = bno;
 		this.busName = busName;
 		this.source = source;
 		this.destination = destination;
 		this.ticketPrice = ticketPrice;
 		this.availableSeats = availableSeats;
 	}
 	public void getBusData(){
 		System.out.println("\t"+bno+ "\t"+ busName + "\t"+ source +"\t" 
 	+destination + "\t"+ ticketPrice +"\t"+ availableSeats + "\t|" );
 	}
 	public void bookTickets(int tickets){
 		availableSeats = availableSeats - tickets;
 		System.out.println("Ticket Booked Succesfully....!");
 	}
 	
 }
  class Customer {
 	static HashMap<String, String> user = new HashMap<>();
 	Scanner sc;
 	
 	static String username;
 	Bus bus = new Bus();
 	public void Login(){
 		sc= new Scanner(System.in);
 		System.out.println("\n=====================================================================================================");

 		System.out.print("Please enter Username: ");
 		username = sc.nextLine();
 		System.out.print("Please enter Password: ");
 		String password = sc.nextLine();
 		if(username.equals("admin")&& password.equals("admin")){
 			displayScreen(username);
 			menu();	
 		}
 		
 		else if(user.containsKey(username)){
 				if(user.get(username).equals(password)){
 				System.out.println("Login Succesfull...!");
 				displayScreen(username);
 				menuCustomer();
 				}
 			
 			else{
 				System.out.println("Invalid Credentials...!");
 				BusBooking busBooking = new BusBooking();
 				busBooking.mainMethod();
 			}
 		}else{
 			System.out.println("User not Found...");
 			BusBooking busBooking = new BusBooking();
 			busBooking.mainMethod();

 		}
 		
 	}
 	public void displayScreen(String username){
 		System.out.println("\t\t\t\t******************************");
 		System.out.println("\t\t\t\t*                            *");
 		System.out.println("\t\t\t\t* Welcome to Bus Booking App *");
 		System.out.println("\t\t\t\t*                            *");
 		System.out.println("\t\t\t\t******************************");
 		System.out.println("\t\t\t\t\tHello "+ username);
 		System.out.println("=====================================================================================================");
 		System.out.println();
 	}
 	public void menu(){
 		int n;
 		do{
 		System.out.println("[1] Transactions");
 		System.out.println("[2] Add Bus");
 		System.out.println("[3] Display Customers Name");
 		System.out.println("[4] Display Buses");
 		System.out.print("Enter your choice: ");
 		n = sc.nextInt();
 		
 			switch(n){
 			case 1 :
 			bus.displayBookingTransactions();
 				break;
 			case 2 : 
 					bus.addBusDetails();
 					bus.displayBusDetails();
 					break;
 			case 3 : 
 				System.out.println("------------------x Users x----------------- ");
 				for(String keys : user.keySet()){
 				    System.out.println( keys );
 				}
 				break;
 			case 4 : bus.displayBusDetails();
 				break;
 			
 			default: System.out.println("Enter valid no...! ");

 			}
 		}while(n!=5);
 	}
 	
 	public void menuCustomer(){
 		int ch;
 		bus.setBusDetails();
 		do{
 		System.out.println("[1] Book a Ticket");
 		System.out.println("[2] Cancel Ticket");
 		System.out.println("[3] Exit");
 		System.out.print("Enter your choice: ");
 		ch = sc.nextInt();
 		switch(ch){
 		case 1: 
 			bus.displayBusDetails();
 			System.out.print("Enter no which bus you want to book: ");
 			int no = sc.nextInt();
 			System.out.print("Enter no of tickets: ");
 			int tickets = sc.nextInt();
 			bus.chooseBus(no, tickets, username);
 			break;
 		case 2: 
 			bus.displayBusDetails();
 			System.out.print("Enter no which bus you want to cancel: ");
 			int busno = sc.nextInt();
 			System.out.print("Enter how many tickets you want to cancel: ");
 			int cancelTickets = sc.nextInt();
 			bus.cancelBooking(busno, cancelTickets, username); 
 			break;
 		default: System.out.println("Please enter appropriate number...!");
 		}
 		
 		}while(ch!=3);
 	}
 	
 	public void Register(){
 		

 		sc = new Scanner(System.in);
 		System.out.print("Please enter Username: ");
 		String username = sc.nextLine();
 		System.out.print("Please enter Password: ");
 		String password = sc.nextLine();
 		user.put(username, password);
 		System.out.println("Registeration Succesfull...!");
 		BusBooking busBooking = new BusBooking();
 		busBooking.mainMethod();
 		
 	}
 	
 }

