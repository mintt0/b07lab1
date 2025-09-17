public class Polynomial{
	// i. fields
	double [] coeffs;
	
	// ii. constructor
	public Polynomial(){
		coeffs = new double[]{0};
	} 
	
	// iii. customised constructor
	public Polynomial(double [] coeffs){
		this.coeffs = coeffs.clone();
	}
	
	// iv. add method um help
	public Polynomial add(Polynomial f){
		// g = this.coeffs + f
		int n_coeffs = coeffs.length;
		int n_f = f.coeffs.length;
		
		int n = Math.max(n_coeffs, n_f);
		
		double g[] = new double[n];
		
		for (int i=0; i<n; i++){
			g[i] = (i < n_coeffs ? coeffs[i] : 0) + (i < n_f ? f.coeffs[i] : 0);
		}
		
		return new Polynomial(g); 

	}
	
	// v. evaluate
	public float evaluate(double x){
		int n = coeffs.length;
		float result = 0;
		
		for (int i = 0; i < n; i++){
			result += coeffs[i] * Math.pow(x,i);
		}
		
		return result;
	}
	
	// vi. has root method
	public boolean hasRoot(double x){
		return evaluate(x) == 0;
	}
}
	
			
		
	
	
	
	
	