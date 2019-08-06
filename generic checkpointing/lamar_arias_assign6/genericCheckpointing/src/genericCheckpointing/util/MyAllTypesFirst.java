package genericCheckpointing.util;

import genericCheckpointing.util.SerializableObject;

public class MyAllTypesFirst extends SerializableObject {
	private int myInt;
	private int myOtherInt;
	private long myLong;
	private long myOtherLong;
	private String myString;
	private boolean myBool;

	public MyAllTypesFirst(){
		setMyInt(0);
		setMyOtherInt(0);
		setMyLong(0);
		setMyOtherLong(0);
		setMyString("");
		setMyBool(false);
	
	}
	public MyAllTypesFirst(int in, int in2, long l1, long l2, String s, boolean boo){
		setMyInt(in);
		setMyOtherInt(in2);
		setMyLong(l1);
		setMyOtherLong(l2);
		setMyString(s);
		setMyBool(boo);
	}
	
	public void setMyInt(int num){
		myInt = num;
	}
	
	public void setMyOtherInt(int num){
		myOtherInt = num;
	}
	
	public void setMyLong(long num){
		myLong = num;
	}
	
	public void setMyOtherLong(long num){
		myOtherLong = num;
	}
	
	public void setMyString(String s){
		myString = s;
	}
	
	public void setMyBool(boolean boo){
		myBool = boo;
	}
	
	public int getMyInt(){
		return myInt;
	}
	
	public int getMyOtherInt(){
		return myOtherInt;
	}
	
	public long getMyLong(){
		return myLong;
	}
	
	public long getMyOtherLong(){
		return myOtherLong;
	}
	
	public String getMyString(){
		return myString;
	}
	
	public boolean getMyBool(){
		return myBool;
	}
	
	public String toString(){
		return ("MyAllTypesFirst" + "\nmyInt = " + getMyInt() + "\nmyOtherInt = " + getMyOtherInt() + "\nmyLong = " + getMyLong() + "\nmyOtherLong = " + getMyOtherLong() + "\nmyString = " + getMyString() + "\nmyBool = " + getMyBool());
	}
	@Override
	public int hashCode(){ 
		return (1 * getMyInt()) + (3 * getMyOtherInt()) + (5 * (int)getMyLong()) + (7 * (int)getMyOtherLong()) + (11 * getMyString().hashCode()) + (13 * (getMyBool()? 1:0));
	}
	@Override
	public boolean equals(Object other){
		//System.out.println("Test equals");
		//Integer oth = 
		return (this.hashCode() == other.hashCode());
	}	
	
	
	
	


}
