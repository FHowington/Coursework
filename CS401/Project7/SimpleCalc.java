/**
Copyright (c) 2017, Forbes Turley
All rights reserved.
Redistribution and use in source and binary forms, with or without
modification, are permitted provided that the following conditions are met:
    * Redistributions of source code must retain the above copyright
      notice, this list of conditions and the following disclaimer.
    * Redistributions in binary form must reproduce the above copyright
      notice, this list of conditions and the following disclaimer in the
      documentation and/or other materials provided with the distribution.
    * Neither the name of the <organization> nor the
      names of its contributors may be used to endorse or promote products
      derived from this software without specific prior written permission.
THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
DISCLAIMED. IN NO EVENT SHALL <COPYRIGHT HOLDER> BE LIABLE FOR ANY
DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
(INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
(INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
University of Pittsburgh
CS401
Project 7
*/


// Demonstrates JPanel, GridLayout and a few additional useful graphical features.
// adapted from an example by john ramirez (cs prof univ pgh)
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.StringTokenizer;
import javax.swing.*;
import javax.swing.text.BadLocationException;

public class SimpleCalc
{
	JFrame window;  // the main window which contains everything
	Container content ;
	JButton[] digits = new JButton[12]; 
	JButton[] ops = new JButton[4];
	JTextField expression;
	JButton equals;
	JTextField result;
    String[] opCodes = { "+", "-", "*", "/" };

    public SimpleCalc()
	{
		window = new JFrame( "Simple Calc");
		content = window.getContentPane();
		content.setLayout(new GridLayout(2,1)); // 2 row, 1 col
		ButtonListener listener = new ButtonListener();
		keyListen keyListen = new keyListen();
		// top panel holds expression field, equals sign and result field
		// [4+3/2-(5/3.5)+3]  =   [3.456]
		
		JPanel topPanel = new JPanel();
		topPanel.setLayout(new GridLayout(1,3)); // 1 row, 3 col
		
		expression = new JTextField();
		expression.setFont(new Font("verdana", Font.BOLD, 16));
		expression.setText("");
		
		equals = new JButton("=");
		equals.setFont(new Font("verdana", Font.BOLD, 20 ));
		equals.addActionListener( listener ); 
		
		result = new JTextField();
		result.setFont(new Font("verdana", Font.BOLD, 16));
		result.setText("");
		
		topPanel.add(expression);
		expression.addKeyListener(keyListen);
		topPanel.add(equals);
		topPanel.add(result);
						
		// bottom panel holds the digit buttons in the left sub panel and the operators in the right sub panel
		JPanel bottomPanel = new JPanel();
		bottomPanel.setLayout(new GridLayout(1,2)); // 1 row, 2 col
	
		JPanel  digitsPanel = new JPanel();
		digitsPanel.setLayout(new GridLayout(4,3));	
		
		for (int i=0 ; i<10 ; i++ )
		{
			digits[i] = new JButton( ""+i );
			digitsPanel.add( digits[i] );
			digits[i].addActionListener( listener ); 
		}
		digits[10] = new JButton( "C" );
		digitsPanel.add( digits[10] );
		digits[10].addActionListener( listener ); 

		digits[11] = new JButton( "CE" );
		digitsPanel.add( digits[11] );
		digits[11].addActionListener( listener ); 		
	
		JPanel opsPanel = new JPanel();
		opsPanel.setLayout(new GridLayout(4,1));
		for (int i=0 ; i<4 ; i++ )
		{
			ops[i] = new JButton( opCodes[i] );
			opsPanel.add( ops[i] );
			ops[i].addActionListener( listener ); 
		}
		bottomPanel.add( digitsPanel );
		bottomPanel.add( opsPanel );
		
		content.add( topPanel );
		content.add( bottomPanel );
	
		window.setSize( 640,480);
		window.setVisible( true );
	}

	// We are again using an inner class here so that we can access
	// components from within the listener.  Note the different ways
	// of getting the int counts into the String of the label
	
	class ButtonListener implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{
		    double resultDouble;
			Component whichButton = (Component) e.getSource();
			// how to test for which button?
			// this is why our widgets are 'global' class members
			// so we can refer to them in here

			for (int i=0 ; i<10 ; i++ )
				if (whichButton == digits[i])
					expression.setText( expression.getText() + i );
			if(whichButton==digits[10])
				expression.setText("");
			if(whichButton==digits[11])
			    try {
                    expression.setText(expression.getText(0, expression.getText().length() - 1));
                }
                catch(Exception BadLocationException) {
			    //This should be impossible
                }
            for (int i =0; i<4; i++)
                if(whichButton==ops[i])
                    expression.setText(expression.getText() + opCodes[i]);

			// need to add tests for other controls that may have been
			// click that got us in here. Write code to handle those
			if(whichButton==equals) {
                evaluator(expression);

            }
			
		}
	}
    private  void evaluator(JTextField expression){
        ArrayList<String> operatorList = new ArrayList<String>();
        ArrayList<String> operandList = new ArrayList<String>();
        // StringTokenizer is like an infile and calling .hasNext() that splits on + - / or *
        StringTokenizer st = new StringTokenizer(expression.getText(),"+-*/", true );
        while (st.hasMoreTokens())
        {
            String token = st.nextToken();
            if ("+-/*".contains(token))
                operatorList.add(token);
            else
                operandList.add(token);
        }
        String toEval=expression.getText();
        Scanner input= new Scanner(toEval);
        input.useDelimiter("[/*+-]+");

        ArrayList<Double> operandListConv = new ArrayList<Double>();
        for(int i =0; i<operandList.size();i++)
            operandListConv.add(Double.parseDouble(operandList.get(i)));

		if(operandList.size() != operatorList.size()+1) {
			result.setText("ERROR");
			return;
		}

        for(int i=0;i<operatorList.size();i++)
            if(operatorList.get(i).equals("*")) {
                operatorList.remove(i);
                Double result=operandListConv.get(i)*operandListConv.get(i+1);
                operandListConv.remove(i);
                operandListConv.remove(i);
                operandListConv.add(i,result);
                i--;
            }
            else if(operatorList.get(i).equals("/")) {
                operatorList.remove(i);
                Double resultant=operandListConv.get(i)/operandListConv.get(i+1);
                if(operandListConv.get(i+1)==0){
                	result.setText("ERROR");
                	return;
				}

                operandListConv.remove(i);
                operandListConv.remove(i);
                operandListConv.add(i,resultant);
                i--;
            }


        for(int i=0;i<operatorList.size();i++)
            if (operatorList.get(i).equals("+")) {
                operatorList.remove(i);
                Double result = operandListConv.get(i) + operandListConv.get(i+1);
                operandListConv.remove(i);
                operandListConv.remove(i);
                operandListConv.add(i, result);
                i--;
            } else if (operatorList.get(i).equals("-")) {
                operatorList.remove(i);
                Double result = operandListConv.get(i) - operandListConv.get(i+1);
                operandListConv.remove(i);
                operandListConv.remove(i);
                operandListConv.add(i, result);
                i--;
            }
		if(operandListConv.size()>1)
			result.setText("ERROR");
        if(operatorList.size()!=0)
        	result.setText("ERROR");


		result.setText(""+operandListConv.get(0));
    }
    class keyListen implements KeyListener{
    	public void keyPressed(KeyEvent e){
			if(e.getKeyCode()==10)
				evaluator(expression);
		}
		public void keyReleased(KeyEvent e){

		}
		public void keyTyped(KeyEvent e){

		}
	}
	public static void main(String [] args)
	{
		new SimpleCalc();
	}
}
