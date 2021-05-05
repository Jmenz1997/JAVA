import java.util.Random;

public class Customer {
	private int arrivalTime;
	private int initialNumberOfItems;
	private int numberOfItems;
	private int MAX_NUM_ITEMS;
	public Object queue;
	
	public Customer(int timeArrival) {
		this.arrivalTime=timeArrival;
		numberOfItems=15;
		MAX_NUM_ITEMS=15;
		Random generator;
		
		generator=new Random();
		int numItems;
		numItems=generator.nextInt(MAX_NUM_ITEMS-1)+1;
		
		
		
		initialNumberOfItems=numItems;
		

	}
	public int getArrivalTime() {
		return arrivalTime;
	}
	public int getNumberOfItems() {
		return numberOfItems;
		
	}
	public int getNumberOfServedItems() {
		int servedItem;
		servedItem=MAX_NUM_ITEMS-numberOfItems;
		return servedItem; //servis (traité)
	}
	public void serve() {
		numberOfItems--;
	}
	public int getInitialNumberOfItems() {
		return initialNumberOfItems;
	}
	
		
		
	

}
