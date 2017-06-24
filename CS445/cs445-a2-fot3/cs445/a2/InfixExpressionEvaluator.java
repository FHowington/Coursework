package cs445.a2;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StreamTokenizer;

/**
 * This class uses two stacks to evaluate an infix arithmetic expression from an
 * InputStream. It should not create a full postfix expression along the way; it
 * should convert and evaluate in a pipelined fashion, in a single pass.
 */
public class InfixExpressionEvaluator {
    // Tokenizer to break up our input into tokens
    StreamTokenizer tokenizer;

    // Stacks for operators (for converting to postfix) and operands (for
    // evaluating)
    StackInterface<Character> operatorStack;
    StackInterface<Double> operandStack;

    /**
     * Initializes the evaluator to read an infix expression from an input
     * stream.
     *
     * @param input the input stream from which to read the expression
     */
    public InfixExpressionEvaluator(InputStream input) {
        // Initialize the tokenizer to read from the given InputStream
        tokenizer = new StreamTokenizer(new BufferedReader(
                new InputStreamReader(input)));

        // StreamTokenizer likes to consider - and / to have special meaning.
        // Tell it that these are regular characters, so that they can be parsed
        // as operators
        tokenizer.ordinaryChar('-');
        tokenizer.ordinaryChar('/');

        // Allow the tokenizer to recognize end-of-line, which marks the end of
        // the expression
        tokenizer.eolIsSignificant(true);

        // Initialize the stacks
        operatorStack = new ArrayStack<Character>();
        operandStack = new ArrayStack<Double>();
    }

    /**completed=true;
     * Parses and evaluates the expression read from the provided input stream,
     * then returns the resulting value
     *
     * @return the value of the infix expression that was parsed
     */
    public Double evaluate() throws ExpressionError {
        int previousToken=-1;
        // Get the first token. If an IO exception occurs, replace it with a
        // runtime exception, causing an immediate crash.
        try {
            tokenizer.nextToken();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        // Continue processing tokens until we find end-of-line
        while (tokenizer.ttype != StreamTokenizer.TT_EOL) {
            // Consider possible token types
            switch (tokenizer.ttype) {
                case StreamTokenizer.TT_NUMBER:
                    // If the token is a number, process it as a double-valued
                    // operand
                    if(previousToken!=2) {
                        previousToken=2;
                        processOperand((double) tokenizer.nval);
                        break;
                    }

                    else
                        throw new ExpressionError("Two consecutive operands input.");

                case '+':
                case '-':
                case '*':
                case '/':
                case '^':
                    // If the token is any of the above characters, process it
                    // is an operator
                    if(previousToken==-1)
                        throw new ExpressionError("Expression cannot start with operator");
                    if(previousToken!= 1) {
                        previousToken=1;
                        processOperator((char) tokenizer.ttype);
                        break;
                    }

                    else
                        throw new ExpressionError("Two consecutive operators input.");

                case '(':
                case '[':
                    // If the token is open bracket, process it as such. Forms
                    // of bracket are interchangeable but must nest properly.
                    processOpenBracket((char) tokenizer.ttype);
                    break;
                case ')':
                case ']':
                    // If the token is close bracket, process it as such. Forms
                    // of bracket are interchangeable but must nest properly.
                    processCloseBracket((char) tokenizer.ttype);
                    break;
                case StreamTokenizer.TT_WORD:
                    // If the token is a "word", throw an expression error
                    throw new ExpressionError("Unrecognized token: " +
                            tokenizer.sval);
                default:
                    // If the token is any other type or value, throw an
                    // expression error
                    throw new ExpressionError("Unrecognized token: " +
                            String.valueOf((char) tokenizer.ttype));
            }

            // Read the next token, again converting any potential IO exception
            try {
                tokenizer.nextToken();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        // Almost done now, but we may have to process remaining operators in
        // the operators stack
        processRemainingOperators();

        if(!operandStack.isEmpty())
            return operandStack.pop();
        else
            return 0.0;
    }

    /**
     * This method is called when the evaluator encounters an operand. It
     * manipulates operatorStack and/or operandStack to process the operand
     * according to the Infix-to-Postfix and Postfix-evaluation algorithms.
     *
     * @param operand the operand token that was encountered
     */
    void processOperand(double operand) {
        operandStack.push(operand);
    }

    /**
     * This method is called when the evaluator encounters an operator. It
     * manipulates operatorStack and/or operandStack to process the operator
     * according to the Infix-to-Postfix and Postfix-evaluation algorithms.
     *
     * @param operator the operator token that was encountered
     */
    void processOperator(char operator) {
        int value = 0, topValue = 0;
        boolean completed = false;
        char nextOperation;
        switch (operator) {
            case '+':
                value = 1;
                break;
            case '-':
                value = 1;
                break;
            case '*':
                value = 2;
                break;
            case '/':
                value = 2;
                break;
            case '^':
                operatorStack.push(operator);
                completed = true;
        }

        while (!completed) {
            if (operatorStack.isEmpty()) {
                operatorStack.push(operator);
                completed = true;
            } else {
                switch (operatorStack.peek()) {
                    case '+':
                        topValue = 1;
                        break;
                    case '-':
                        topValue = 1;
                        break;
                    case '*':
                        topValue = 2;
                        break;
                    case '/':
                        topValue = 2;
                        break;
                    case '^':
                        topValue = 3;
                }
            }

            if (value > topValue && !completed) {
                completed = true;
                operatorStack.push(operator);
            } else if(value<=topValue && ! completed){
                nextOperation = operatorStack.pop();

                switch (nextOperation) {
                    case '+':
                        operandStack.push(operandStack.pop() + operandStack.pop());
                        break;
                    case '-':
                        operandStack.push((-1 * operandStack.pop()) + operandStack.pop());
                        break;
                    case '*':
                        operandStack.push(operandStack.pop() * operandStack.pop());
                        break;
                    case '/':
                        operandStack.push((1 / operandStack.pop()) * operandStack.pop());
                        break;
                    case '^':
                        double tempPow = operandStack.pop();
                        double tempOperand = operandStack.pop();
                        operandStack.push(Math.pow(tempOperand, tempPow));
                }
            }
        }
    }

    /**
     * This method is called when the evaluator encounters an open bracket. It
     * manipulates operatorStack and/or operandStack to process the open bracket
     * according to the Infix-to-Postfix and Postfix-evaluation algorithms.
     *
     * @param openBracket the open bracket token that was encountered
     */
    void processOpenBracket(char openBracket) {
        operatorStack.push(openBracket);
    }

    /**
     * This method is called when the evaluator encounters a close bracket. It
     * manipulates operatorStack and/or operandStack to process the close
     * bracket according to the Infix-to-Postfix and Postfix-evaluation
     * algorithms.
     *
     * @param closeBracket the close bracket token that was encountered
     */
    void processCloseBracket(char closeBracket) throws ExpressionError {
        int type;
        if (closeBracket==')')
            type=1;
        else
            type=2;

        boolean completed = false;
        char nextOperation;
        while (!completed) {
            nextOperation = operatorStack.pop();
            switch (nextOperation) {
                case '(':
                    if(type != 1)
                        throw new ExpressionError("Error: Parenthesised in incorrect order");
                    else completed=true;
                    break;
                case '[':
                    if(type != 2)
                        throw new ExpressionError("Error: Parenthesised in incorrect order");
                    else completed=true;
                    break;
                case '+':
                    operandStack.push(operandStack.pop() + operandStack.pop());
                    break;
                case '-':
                    operandStack.push((-1 * operandStack.pop()) + operandStack.pop());
                    break;
                case '*':
                    operandStack.push(operandStack.pop() * operandStack.pop());
                    break;
                case '/':
                    operandStack.push((1 / operandStack.pop()) * operandStack.pop());
                    break;
                case '^':
                    double tempPow = operandStack.pop();
                    double tempOperand = operandStack.pop();
                    operandStack.push(Math.pow(tempOperand, tempPow));
            }
        }
    }

    /**
     * This method is called when the evaluator encounters the end of an
     * expression. It manipulates operatorStack and/or operandStack to process
     * the operators that remain on the stack, according to the Infix-to-Postfix
     * and Postfix-evaluation algorithms.
     */
    void processRemainingOperators() {
        double tempPop1,tempPop2;
        while(!operatorStack.isEmpty()){
            char nextOperation;

            nextOperation = operatorStack.pop();

            if(!operandStack.isEmpty())
                tempPop1=operandStack.pop();
            else
                throw new ExpressionError("Expression contains too few operands");
            if(!operandStack.isEmpty())
                tempPop2=operandStack.pop();
            else
                throw new ExpressionError("Expression contains too few operands");


            switch (nextOperation) {
                case '+':
                    operandStack.push(tempPop1 + tempPop2);
                    break;
                case '-':
                    operandStack.push((-1 * tempPop1) +tempPop2);
                    break;
                case '*':
                    operandStack.push(tempPop1 * tempPop2);
                    break;
                case '/':
                    operandStack.push((1 / tempPop1) * tempPop2);
                    break;
                case '^':
                    operandStack.push(Math.pow(tempPop2, tempPop1));
            }
        }
    }


    /**
     * Creates an InfixExpressionEvaluator object to read from System.in, then
     * evaluates its input and prints the result.
     *
     * @param args not used
     */
    public static void main(String[] args) {
        System.out.println("Infix expression:");
        InfixExpressionEvaluator evaluator =
                new InfixExpressionEvaluator(System.in);
        Double value = null;
        try {
            value = evaluator.evaluate();
        } catch (ExpressionError e) {
            System.out.println("ExpressionError: " + e.getMessage());
        }
        if (value != null) {
            System.out.println(value);
        } else {
            System.out.println("Evaluator returned null");
        }
    }

}

