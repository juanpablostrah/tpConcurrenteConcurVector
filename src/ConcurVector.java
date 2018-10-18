import java.util.List;

public class ConcurVector { 
	
	private final static int WORKERS_AMOUNT = 10;
	
	private final static int BUFFER_LENGTH = 10;
	
	// El array con los elementos del vector
	private double[] elements;
	
	private double[] processedElements;
	
	/** Constructor del ConcurVector.
	 * @param size, la longitud del vector.
	 * @precondition size > 0. */
	public ConcurVector(int size) {
		elements = new double[size];
	}
	
	/** Retorna la longitud del vector; es decir, su dimension. */
	public int dimension() {
		return elements.length;
	}
	
	/** Retorna el elemento del vector en la posicion i.
	 * @param i, la posicion del elemento a ser retornado.
	 * @precondition 0 <= i < dimension(). */
	public double get(int i) {
		return elements[i];
	}
	
	/** Pone el valor d en todas las posiciones del vector. 
	 * @param d, el valor a ser asignado. */
	public void set(double d) {
		for (int i = 0; i < dimension(); ++i)
			elements[i] = d;
	}
	
	/** Pone el valor d en la posicion i del vector. 
	 * @param i, la posicion a setear.
	 * @param d, el valor a ser asignado en la posicion i.
	 * @precondition 0 <= i < dimension. */
	public void set(int i, double d) {
		elements[i] = d;
	}
	
	/** Suma los valores de este ConcurVector con los de otro (uno a uno).
	 * @param c, el ConcurVector con los valores a sumar.
	 * @precondition dimension() == v.dimension(). */
	public synchronized void add(ConcurVector c) {
		Buffer<Object> buffer = new Buffer<>(this.BUFFER_LENGTH);
		ThreadPool threadPool = new ThreadPool(this.WORKERS_AMOUNT, buffer);

		threadPool.startToWork(); //comienza a trabajar cada uno de los workers del ThreadPool
		
		for (double element : elements) {
			buffer.add(element); // agrego cada uno de los elementos de elements al buffer, sino hay mas espacio dentro del buffer, se queda lockeado			
		}
	}
	
	public synchronized double sum() {
		Buffer<Object> buffer = new Buffer<>(this.BUFFER_LENGTH);
		ThreadPool threadPool = new ThreadPool(this.WORKERS_AMOUNT, buffer);

		threadPool.startToWork(); //comienza a trabajar cada uno de los workers del ThreadPool
		
		for (double element : elements) {
			buffer.add(element); // agrego cada uno de los elementos de elements al buffer, sino hay mas espacio dentro del buffer, se queda lockeado			
		}
		
		
		//tengo que leer elementos del buffer 
		
		return 0;
	}

	/* 
	 * calculo los tamanios de las unidades de trabajo en funcion de la cantidad de elementos 
	 * cantidad de elementos | cantidad de workers | unidades de trabajo
	 * 		200							10					10b * 20u
	 * 		202							10					8b * 20u + 2b * 21u
	 * 		400							5					5b * 80u
	 * 		20							20					10b * 2u
	 * 		40							100					20b * 2u
	 * 		50							25					25b * 2u
	 * 		49							25					23b * 2u + 1b * 3u
	 * 		10							4					2b * 2u + 2b * 3u  
	 * 		23							4					1b * 5u + 3b * 6u  
	 */
	public WorkUnitSizeDTO getSize(int cantElement, int cantWorkers){
		int cantBloques = 0; //asume redondeo hacia abajo
		int cantUnidades = 0;
		if(cantWorkers > cantElement / 2) {
			cantUnidades = 2;
		}
		else {
			cantUnidades = cantElement / cantWorkers;
		}
		cantBloques = cantElement / cantUnidades; //asume redondeo hacia abajo
		int bloquesTamanioExtra = cantElement - cantBloques * cantUnidades;
		int bloquesTamanioNormal = cantBloques - bloquesTamanioExtra;
		//[(bloquesTamanioNormal,cantUnidades),(bloquesTamanioExtra,cantUnidades + 1)]
		
		return new WorkUnitSizeDTO(bloquesTamanioNormal, cantUnidades, bloquesTamanioExtra, cantUnidades + 1);
	}
	
}
