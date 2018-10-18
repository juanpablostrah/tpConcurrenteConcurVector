import java.util.LinkedList;

public class ThreadPool {

	private LinkedList<Worker> workers;
	private int cantidadWorker;
	
	
	public ThreadPool (int cantidadWorker, Buffer<Object> buffer) {
		this.cantidadWorker = cantidadWorker;
		this.initialize(buffer);
	}
	
	private void initialize(Buffer buffer){
		this.workers = new LinkedList<Worker>();
		for (int i = 0; i < this.cantidadWorker; i++) {
			this.workers.add(new Worker(buffer, i));
		}
	}
	
	public synchronized void startToWork() {
		for (Worker worker : workers) {
			System.out.println("worker fork");
			worker.start();
		}
	}

	
	
	
}
