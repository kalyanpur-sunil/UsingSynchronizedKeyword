package org.sunil.thread;
/**
 * 
 * @author Sunil Kalyanpur
 *
 */
class Website{
	
	private int counter;

	public int getCounter() {
		return counter;
	}

	//i am making this method synchronized coz i want thread to obtain lock
	//on the object before it increments counter. every object in Java has
	//intrensic lock also called monitor lock a.k.a mutex. If you want to call
	//synchronized method of an object you have to acquire intrensic lock 
	//on the object. If a thread already has intresic lock on the object,
	//then other thread has to wait until other thread releases lock on the 
	//object
	public synchronized void incrementCounter(){
		counter++;
	}

}
class AppThread implements Runnable{
	
	private Website site;
	
	public AppThread(Website site){
		this.site = site;
	}
	@Override 
	public void run(){
		//increment counter by 10000
		for(int i=0;i<10000;i++)
			site.incrementCounter();
	}
}

public class Application1 {
	public static void main(String []args){

		//create a website object
		Website site = new Website();

		//create thread t1
		Thread t1 = new Thread(new AppThread(site));

		//create thread t2
		Thread t2 = new Thread(new AppThread(site));

		//start thread t1 and t2
		t1.start();
		t2.start();

		//wait till t1 and t2 die
		try{
			t1.join();
			t2.join();
		}catch(InterruptedException ex){
			ex.printStackTrace();
		}

		//print out website counter value
		System.out.println("Counter value is "+site.getCounter());
	}
}
