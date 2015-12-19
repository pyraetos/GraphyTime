package net.pyraetos;

public abstract class FunctionParser{

	private static double numberBuffer = 0.0d;
	private static char itemBuffer = 0;
	private static final byte NOP = 0;
	private static final byte MUL = 1;
	private static final byte DIV = 2;
	private static final byte ADD = 3;
	private static final byte SUB = 4;
	
	public static Function parse(String input){
		String left = "";
		String right = "";
		byte currentOp = NOP;
		while(!input.isEmpty()){
			if(isNumberNext(input)){
				input = parseNumber(input);
				if(currentOp == NOP)
					left += numberBuffer;
				else
					right += numberBuffer;
			}
			
			if(isXNext(input)){
				input = parseItem(input);
				if(currentOp == NOP)
					left += 'x';
				else
					right += 'x';
			}
			
			if(isOpNext(input)){
				input = parseItem(input);
				switch(itemBuffer){
				case '*': {
					if(currentOp < MUL)
						currentOp = MUL;
					else
						right += '*';
					break;
				}
				case '/': {
					if(currentOp < MUL)
						currentOp = DIV;
					else
						right += '/';
					break;
				}
				case '+': {
					if(currentOp < ADD)
						currentOp = ADD;
					else
						right += '+';
					break;
				}
				case '-': {
					if(currentOp < ADD)
						currentOp = SUB;
					else
						right += '-';
					break;
				}
				}
			}
		}
		Function function = null;
		if(currentOp == NOP)
			function = left.equals("x") ? new Function.IndependentVariable() : new Function.Constant(Double.parseDouble(left));
		else if(currentOp == MUL)
			function = new Function.Multiplication(parse(left), parse(right));
		else if(currentOp == DIV)
			function = new Function.Division(parse(left), parse(right));
		else if(currentOp == ADD)
			function = new Function.Addition(parse(left), parse(right));
		else if(currentOp == SUB)
			function = new Function.Subtraction(parse(left), parse(right));
		return function;
	}
	
	private static boolean isOpNext(String input){
		if(input.isEmpty()) return false;
		char c = input.charAt(0);
		switch(c){
		case '*': return true;
		case '/': return true;
		case '+': return true;
		case '-': return true;
		default: return false;
		}
	}
	
	private static boolean isXNext(String input){
		if(input.isEmpty()) return false;
		char c = input.charAt(0);
		return c == 'x';
	}
	
	private static String parseItem(String input){
		itemBuffer = input.charAt(0);
		return input.substring(1);
	}
	
	private static boolean isNumberNext(String input){
		if(input.isEmpty()) return false;
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
