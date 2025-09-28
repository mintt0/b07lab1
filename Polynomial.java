import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintStream;
import java.io.Reader;
import java.util.ArrayList;
import java.util.*;


public class Polynomial{
	// i. fields
	double [] coeffs; //non-zero coefficients
	int [] exponents; //exponents
	
	// ii. constructor
	public Polynomial(){
		coeffs = new double[]{0};
		exponents = new int[]{0};
	} 
	
	// iii. customised constructor
	public Polynomial(double [] coeffs, int [] exponents){
		this.coeffs = coeffs.clone();
		this.exponents = exponents.clone();
	}
	
	// iv. add method
	public Polynomial add(Polynomial f){
				
		Map<Integer, Double> p_map = new TreeMap<>(); // map of this polynomial
		Map<Integer, Double> f_map = new TreeMap<>(); // map of argument polynomial f
		Map<Integer, Double> h_map = new TreeMap<>(); // map of output polynomial h
		
		for (int i=0; i<this.coeffs.length; i++) {
			p_map.put(this.exponents[i], this.coeffs[i]);
		}
		
		for (int i=0; i<f.coeffs.length; i++) {
			f_map.put(f.exponents[i], f.coeffs[i]);
		}
		
		Set<Integer> h_key = new TreeSet<>();
		h_key.addAll(f_map.keySet());
		h_key.addAll(p_map.keySet());
		
		for (Integer exponent : h_key) {
			if (p_map.containsKey(exponent) && f_map.containsKey(exponent) && p_map.get(exponent) + f_map.get(exponent) != 0) {
				h_map.put(exponent, p_map.get(exponent) + f_map.get(exponent));
			}
			else if (p_map.containsKey(exponent)) {
				h_map.put(exponent,  p_map.get(exponent));
			}
			else {
				h_map.put(exponent, f_map.get(exponent));
			}
		}
		
		double[] coeffs = new double[h_map.size()];
		int[] exponents = new int[h_map.size()];
			
		int i = 0;
		for (Map.Entry<Integer, Double> term : h_map.entrySet()) {
			coeffs[i] = term.getValue();
			exponents[i] = term.getKey();
			i++;
		}
		
		return new Polynomial(coeffs, exponents);
	}
	
	// v. evaluate
	public double evaluate(double x){
		int n = coeffs.length;
		double result = 0.0;
		
		for (int i = 0; i < n; i++){
			result += coeffs[i] * Math.pow(x,exponents[i]); // this should probably work
		}
		
		return result;
	}
	
	// vi. has root method
	public boolean hasRoot(double x){
		double result = evaluate(x);
		double epsilon = 1e-3;
		return Math.abs(result)<epsilon;
	}
	
	// c. multiply
	public Polynomial multiply(Polynomial f){
		
		Map<Integer, Double> h_map = new TreeMap<>(); // map of output polynomial h
		
		for (int i = 0; i < this.exponents.length; i++) {
			Integer p_ex = this.exponents[i];
			Double p_co = this.coeffs[i];
			
			for (int j = 0; j < f.exponents.length; j++) {
				Integer f_ex = f.exponents[j];
				Double f_co = f.coeffs[j];
				
				if (!(h_map.containsKey(p_ex + f_ex))) {
					h_map.put(p_ex+f_ex, p_co * f_co);
				}
				else {
					h_map.put(p_ex+f_ex, h_map.get(p_ex+f_ex)+p_co*f_co);
					if (h_map.get(p_ex+f_ex)==0) {
						h_map.remove(p_ex+f_ex);
					}
				}
			}
		}
		
		double[] coeffs = new double[h_map.size()];
		int[] exponents = new int[h_map.size()];
			
		int i = 0;
		for (Map.Entry<Integer, Double> term : h_map.entrySet()) {
			coeffs[i] = term.getValue();
			exponents[i] = term.getKey();
			i++;
		}
		
		return new Polynomial(coeffs, exponents);
	}
	
	// d. constructor
	public Polynomial(Reader file) {
		try {
			// read the file 
			BufferedReader input = new BufferedReader(file);
			String line = input.readLine();
			
			ArrayList<Double> file_coeffs = new ArrayList<Double>();
			ArrayList<Integer> file_exponents = new ArrayList<Integer>();

			
			String[] terms = line.split("(?=[+-])");
			
			for (int i = 0; i<terms.length; i++) {
				String[] coeff_exponent = terms[i].split("(?=x)");
				
				//parse the coefficient
				if (coeff_exponent[0].equals("+")||coeff_exponent[0].equals("")) {
					file_coeffs.add(1.0);
				}
				else if (coeff_exponent[0].equals("-")) {
					file_coeffs.add(-1.0);
				}
				else {
					file_coeffs.add(Double.valueOf(coeff_exponent[0]));
				}
				
				//parse the exponent
				if (coeff_exponent.length==1) {
					if (!(coeff_exponent[0].contains("x"))) {
						file_exponents.add(0);
					}
					else {
						file_exponents.add(1);
					}
				}
				else if (coeff_exponent.length==2) {
					if (coeff_exponent[1].equals("x")) {
						file_exponents.add(1);
					}
					else {
						file_exponents.add(Integer.valueOf(coeff_exponent[1].substring(1)));
					}
				}	
			}
			
			double [] file_coeffs_arr = new double[file_coeffs.size()];
			int [] file_exponents_arr = new int[file_exponents.size()];
			
			for (int i = 0; i < file_coeffs.size(); i++) {
				file_coeffs_arr[i] = file_coeffs.get(i);
			}
			
			for (int i = 0; i < file_exponents.size(); i++) {
				file_exponents_arr[i] = file_exponents.get(i);
			}
			
			this.coeffs = file_coeffs_arr;
			this.exponents = file_exponents_arr;
			
		} catch (IOException e) {
			e.printStackTrace();
		
		}	
		
	}
	
	
	public void saveToFile(String file_name){
		// saves the polynomial in textual format in the file named file_name
		String line = new String("");
		
		for (int i=0; i<coeffs.length; i++) {			
			if (i>0 && coeffs[i]>0) {
				line+= '+';
			}
			
			if (coeffs[i]%1==0) {
				line += Integer.toString((int) coeffs[i]);
			}
			else {
				line += Double.toString(coeffs[i]);
			}
			
		
			if (exponents[i]==1) {
				line += 'x';
			}
			else if (exponents[i]!=0){
				line+='x'+ Integer.toString(exponents[i]);
			}
		}
		
		try {
			PrintStream output = new PrintStream(file_name);
			output.print(line);
			output.flush();
			output.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
}
	
			
		
	
	
	
	
	