
public class Poc {

	private static final int vectorSize = 400;
	
	public static void main(String[] args) {
		
		ConcurVector concurVector = new ConcurVector(vectorSize);
		for (int i = 0; i < vectorSize; i++) {
			concurVector.set(i, i);
		}
		
		double result = concurVector.sum();
	}
}
