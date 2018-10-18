import java.util.LinkedList;

public class Buffer<T> {

	private int capacidad;
	
	private LinkedList<T> elementos;
	
	public Buffer(int capacidad){
		this.capacidad = capacidad;
		this.initialize();
	}
	
	private void initialize() {
		this.elementos = new LinkedList<T>();
	}
	
	public synchronized void add(T element) {
		while(elementos.size() == capacidad) {
			try {
				this.wait();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		this.elementos.add(element);
		notifyAll();
	}
	
	public synchronized T pop() {
		while(this.elementos.isEmpty()) {
			try {
				this.wait();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		T result = this.elementos.pop();
		notifyAll();
		return result;
	}
	
	public synchronized int size() {
		return capacidad;
		
	}
}
