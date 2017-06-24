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
Project 5
*/

public class Fraction
{
	private int numer;
	private int denom;

	// ACCESSORS
	public int getNumer()
	{
		return numer;
	}
	public int getDenom()
	{
		return denom;
	}
	public String toString()
	{
		return numer + "/" + denom;
	}

	// MUTATORS
	public void setNumer( int n )
	{
		numer = n;
	}
	public void setDenom( int d )
	{
		if (d!=0)
			denom=d;
		else
		{
			// error msg OR exception OR exit etc.
		}
	}

	// DEFAULT CONSTRUCTOR - no args passed in
	public Fraction(  )
	{
		this( 0, 1 ); // "this" means call a fellow constructor
	}

	// 1 arg CONSTRUCTOR - 1 arg passed in
	// assume user wants whole number
	public Fraction( int n )
	{
		this( n, 1 ); // "this" means call a fellow constructor

	}

	// FULL CONSTRUCTOR - an arg for each class data member
	public Fraction( int n, int d )
	{
		// you could calc the gcd(n,d) here and then divide both by gcd in the setter calls
		setNumer(n); // i.e. setNumer( n/gcd );
		setDenom(d); // same here
		reduce();
	}

	// COPY CONSTRUCTOR - takes ref to some already initialized Fraction object
	public Fraction( Fraction other )
	{
		this( other.numer, other.denom ); // call my full C'Tor with other Fraction's data
	}

	private void reduce(){
		for (int i= numer; i>1;i--)
			if(numer % i == 0 && denom % i ==0){
				this.numer = numer / i;
				this.denom = denom / i;
				break;
			}
	}
	public Fraction add(Fraction other){
		Fraction temporary = new Fraction(0,0);
		temporary.numer=(other.numer*this.denom + this.numer * other.denom);
		temporary.denom=(this.denom * other.denom);
		temporary.reduce();
		return temporary;
	}
	public Fraction subtract(Fraction other){
		Fraction temporary = new Fraction(other.numer * -1,other.denom);
		return this.add(temporary);
	}
	public Fraction multiply(Fraction other){
		Fraction temporary=new Fraction(0,0);
		temporary.numer=this.numer*other.numer;
		temporary.denom=this.denom*other.denom;
		temporary.reduce();
		return temporary;
	}
	public Fraction reciprocal(){
		int temp;
		Fraction temporary = new Fraction(0,0);
		temp=this.numer;
		temporary.numer=this.denom;
		temporary.denom=temp;
		return temporary;
	}
	public Fraction divide(Fraction other){
		int temp;
		Fraction temporary = new Fraction(0,0);
		temp=this.numer*other.denom;
		temporary.denom=this.denom*other.numer;
		temporary.numer=temp;
		temporary.reduce();
		return temporary;
	}
	
}
