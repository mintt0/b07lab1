import java.util.Arrays;
import java.io.FileReader;
import java.io.IOException;
import java.io.FileNotFoundException;

public class Driver {
	public static void main(String [] args) {
		
		//construct new polynomial and evaluate at 0
		Polynomial p = new Polynomial();
		System.out.println(p.evaluate(3)); // expect 0.0
		
		// p1
		double [] coeff1 = {6,4,-2};
		int [] expon1 = {0,3,5};
		Polynomial p1 = new Polynomial(coeff1,expon1);
		
		// p2
		double [] coeff2 = {5,1,-3,6};
		int [] expon2 = {0,1,2,3};
		Polynomial p2 = new Polynomial(coeff2,expon2);
		
		// add p1 and p2
		Polynomial s = p1.add(p2);
		System.out.println("the coefficients of p1 is " + Arrays.toString(s.coeffs)); // expect [11,1,-3,10,-2]
		System.out.println("the coefficients of p2 is " + Arrays.toString(s.exponents)); //expect [0,1,2,3,5]
		
		// evaluate p1 at 1
		System.out.print("p1 evaluated at 0 is " + p1.evaluate(0)+'\n'); // expect 6
		System.out.print("p2 evaluated at 1 is " + p2.evaluate(1)+'\n'); // expect 9
		
		// testing has root
		if(p1.hasRoot(1.63791)) {
			System.out.println("1.63791 is a root of p1, as expected");} // expect this
		else {
			System.out.println("1.63791 is not a root of p1, which is not true");}
	
		if(p2.hasRoot(-0.74)) {
			System.out.println("-0.74 is a root of p2, which is not true"); }
		else {
			System.out.println("-0.74 is not a root of p2, as expected");} // expect this

		// testing multiply
		Polynomial f = p1.multiply(p2);
		System.out.println("the coefficients of f is " + Arrays.toString(f.coeffs));
		System.out.println("the exponents of f is " + Arrays.toString(f.exponents));
		
		f.saveToFile("b07lab2polynom");
		try{
			FileReader file = new FileReader("b07lab2polynom");		
			Polynomial q = new Polynomial(file);
			System.out.println("the coefficients of q is " + Arrays.toString(q.coeffs));
			System.out.println("the exponents of q is " + Arrays.toString(q.exponents));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}	
		
}