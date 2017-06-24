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
