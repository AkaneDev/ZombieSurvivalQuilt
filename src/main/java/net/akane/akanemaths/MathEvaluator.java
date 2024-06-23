package net.akane.akanemaths;

import java.util.Stack;

public class MathEvaluator {
	public static double evaluate(String expression) {
		char[] tokens = expression.toCharArray();

		// Stack for numbers
		Stack<Double> values = new Stack<>();

		// Stack for operators
		Stack<Character> operators = new Stack<>();

		for (int i = 0; i < tokens.length; i++) {
			// Current token is a whitespace, skip it
			if (tokens[i] == ' ')
				continue;

			// Current token is a number, push it to values stack
			if (tokens[i] >= '0' && tokens[i] <= '9') {
				StringBuilder sb = new StringBuilder();
				// There may be more than one digits in number
				while (i < tokens.length && (tokens[i] >= '0' && tokens[i] <= '9' || tokens[i] == '.')) {
					sb.append(tokens[i++]);
				}
				values.push(Double.parseDouble(sb.toString()));
				i--;
			} else if (tokens[i] == '(') {
				operators.push(tokens[i]);
			} else if (tokens[i] == ')') {
				while (operators.peek() != '(')
					values.push(applyOperator(operators.pop(), values.pop(), values.pop()));
				operators.pop();
			} else if (tokens[i] == '+' || tokens[i] == '-' ||
				tokens[i] == '*' || tokens[i] == '/' || tokens[i] == '^') {
				while (!operators.empty() && hasPrecedence(tokens[i], operators.peek()))
					values.push(applyOperator(operators.pop(), values.pop(), values.pop()));
				operators.push(tokens[i]);
			}
		}

		// Entire expression has been parsed, apply remaining operators to remaining values
		while (!operators.empty())
			values.push(applyOperator(operators.pop(), values.pop(), values.pop()));

		// Top of 'values' contains result, return it
		return values.pop();
	}

	// Returns true if 'op2' has higher or same precedence as 'op1',
	// otherwise returns false.
	public static boolean hasPrecedence(char op1, char op2) {
		if (op2 == '(' || op2 == ')')
			return false;
		if ((op1 == '^' && op2 != '^') ||
			((op1 == '*' || op1 == '/') && (op2 == '+' || op2 == '-')))
			return false;
		return true;
	}

	// A utility method to apply an operator 'op' on operands 'a'
	// and 'b'. Return the result.
	public static double applyOperator(char op, double b, double a) {
		switch (op) {
			case '+':
				return a + b;
			case '-':
				return a - b;
			case '*':
				return a * b;
			case '/':
				if (b == 0)
					throw new ArithmeticException("Cannot divide by zero");
				return a / b;
			case '^':
				return Math.pow(a, b);
		}
		return 0;
	}

	public static void main(String[] args) {
		String expression = "1^(65*43)/2";
		try {
			double result = evaluate(expression);
			System.out.println("Result: " + result);
		} catch (ArithmeticException e) {
			System.out.println("Error evaluating expression: " + e.getMessage());
		}
	}
}
