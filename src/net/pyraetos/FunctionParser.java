package net.pyraetos;

public abstract class FunctionParser{

	private static double numberBuffer = 0.0d;
	
	public static Function parse(String input){
		Function result = new Function.Multiplication(null, new Function.Constant(1d));
		String left =
	}
	
	private static boolean isNumberNext(String input){
		char c = input.charAt(0);
		return c == '-' || c == '.' || isDigit(c);
	}
	
	private static String parseNumber(String input){
		String number = "" + input.charAt(0);
		input = input.substring(1);
		char c = input.charAt(0);
		boolean periodUsed = false;
		while(isDigit(c) || c == '.'){
			if(c == '.'){
				if(periodUsed)
					return null;
				periodUsed = true;
			}
			number += c;
			input = input.substring(1);
		}
		numberBuffer = Double.parseDouble(number);
		return input;
	}
	
	private static boolean isDigit(char c){
		return c == 0 || c == 1 || c == 2 || c == 3 || c == 4
				|| c == 5 || c == 6 || c == 7 || c == 8 || c == 9;
	}
	
}
