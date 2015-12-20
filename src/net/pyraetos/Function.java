package net.pyraetos;

public abstract class Function{
	
	protected Function inner;
	
	public void compose(Function inner){
		this.inner = inner;
	}
	
	public double evaluate(double x){
		return inner.evaluate(x);
	}
	
	public Point[] evaluate(double xMin, double xMax, double xStep){
		int n = (int) ((xMax - xMin) / xStep);
		Point[] points = new Point[n+1];
		for(int i = 0; i <= n; i++){
			double x = xMin + i * xStep;
			points[i] = new Point(x, evaluate(x));
		}
		return points;
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
