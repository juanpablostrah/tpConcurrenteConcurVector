
public class Worker<T> extends Thread{

	private boolean stopped;
	
	private int indice;
	
	private Buffer<T> buffer;
	
	public Worker(Buffer buffer, int indice) {
		this.buffer = buffer;
		this.indice = indice;
		this.stopped = false;
	}
	
	public void run() {
		System.out.println("worker " + indice + "is alive");
		while(!stopped) {
			T element = this.buffer.pop();
			hang();
			System.out.println(this.indice + " : " + element);
		}
	}
	
	private void hang() {
		try {
			double period = Math.random() * 750;
			sleep((new Double(period)).longValue());
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void stopped () {
		this.stopped = true;
	}
	
}
