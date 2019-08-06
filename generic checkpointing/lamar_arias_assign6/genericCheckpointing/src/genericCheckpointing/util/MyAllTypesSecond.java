package genericCheckpointing.util;

import genericCheckpointing.util.SerializableObject;

public class MyAllTypesSecond extends SerializableObject {
	private double myDouble;
	private double myOtherDouble;
	private float myFloat;
	private short myOtherShort;
	private short myShort;
	private char myChar;

	public MyAllTypesSecond(){}
	public MyAllTypesSecond(double d1, double d2, float f, short s1, short s2, char c){
		setMyDouble(d1);
		setMyOtherDouble(d2);
		setMyFloat(f);
		setMyShort(s1);
		setMyOtherShort(s2);
		setMyChar(c);
		
	}
	
	public void setMyDouble(double num){
		myDouble = num;
	}
	
	public void setMyOtherDouble(double num){
		myOtherDouble = num;
	}
	
	public void setMyFloat(float num){
		myFloat = num;
	}
	
	public void setMyShort(short num){
		myShort = num;
	}
	
	public void setMyOtherShort(short num){
		myOtherShort = num;
	}
	
	public void setMyChar(char c){
		myChar = c;
	}
	
	
	public double getMyDouble(){
		return myDouble;
	}
	
	public double getMyOtherDouble(){
		return myOtherDouble;
	}
	
	public float getMyFloat(){
		return myFloat;
	}
	
	public short getMyShort(){
		return myShort;
	}
	
	public short getMyOtherShort(){
		return myOtherShort;
	}
	
	public char getMyChar(){
		return myChar;
	}
	
	
	
	public String toString(){
		return ("MyAllTypesSecond" + "\nmyDouble = " + getMyDouble() + "\nmyOtherDouble = " + getMyOtherDouble() + "\nmyFloat = " + getMyFloat() + "\nmyShort = " + getMyShort() + "\nmyOtherShort = " + getMyOtherShort() + "\nmyChar = " + getMyChar() );
	}
	@Override
	public int hashCode(){ 
		return (int)(1 * getMyDouble()) + (int)(3 * getMyOtherDouble()) + (5 * (int)getMyShort()) + (7 * (int)getMyOtherShort()) + (int)(11 * getMyFloat()) + (13 * (getMyChar()));
	}
	@Override
	public boolean equals(Object other){
		return this.hashCode() == other.hashCode();
	}	

}
