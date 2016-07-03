package math_similarity;

public class CosineSimilarityCalculator {
	
	public double calculateCosineSimilarity(double[] vector1, double[] vector2) {
		double firstElement = 0;
		double secondElement = 0;
		double thirdElement = 0;
		
		for (int i = 0; i < vector1.length; i++) {
			firstElement = firstElement + vector1[i] * vector2[i];
			secondElement = secondElement + vector1[i] * vector1[i];
			thirdElement = thirdElement + vector2[i] * vector2[i];
		}
		
		if(Math.sqrt(secondElement) * Math.sqrt(thirdElement) == 0 || Double.isNaN(Math.sqrt(secondElement) * Math.sqrt(thirdElement))) {
			return 0;
		}
		double result = firstElement / (Math.sqrt(secondElement) * Math.sqrt(thirdElement));
		
		return result;
	}
	
}
