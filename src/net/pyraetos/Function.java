package net.pyraetos;

public abstract class Function{
	
	protected Function inner;
	
	public void compose(Function inner){
		this.inner = inner;
	}
	
	public double evaluate(double x){
		return inner.evaluate(x);
	}
	
	static class Constant extends Function{
		
		private double value;
		
		public Constant(double value){
			this.value = value;
		}
		
		@Override
		public double evaluate(double x){
			return value;
		}
		
	}
	
	static class IndependentVariable extends Function{

		@Override
		public double evaluate(double x){
			return x;
		}

	}

	static class Multiplication extends Function{

		private Function right;

		public Multiplication(Function left, Function right){
			this.inner = left;
			this.right = right;
		}

		@Override
		public double evaluate(double x){
			return inner.evaluate(x) * right.evaluate(x);
		}

	}
	
	static class Division extends Function{

		private Function right;

		public Division(Function left, Function right){
			this.inner = left;
			this.right = right;
		}

		@Override
		public double evaluate(double x){
			return inner.evaluate(x) / right.evaluate(x);
		}

	}
	
	static class Addition extends Function{
		
		private Function right;
		
		public Addition(Function left, Function right){
			this.inner = left;
			this.right = right;
		}
		
		@Override
		public double evaluate(double x){
			return inner.evaluate(x) + right.evaluate(x);
		}
		
	}
	
	static class Subtraction extends Function{
		
		private Function right;
		
		public Subtraction(Function left, Function right){
			this.inner = left;
			this.right = right;
		}
		
		@Override
		public double evaluate(double x){
			return inner.evaluate(x) - right.evaluate(x);
		}
		
	}
}
