package fileVisitors.visitor;
import java.util.ArrayList;
import fileVisitors.util.MyLogger;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/** a node that contains a word, the number of times the word appears, the number
	of characters in the word, and references to other tree Nodes.
*/
public class Node implements Cloneable, Comparable{
	private String word;//word inserted into node(key)
	private int wcount;//number of time word encounterd 
	private int ccount;//number of characters 
	private  Node left, right;//children
	

	/**Constructor
		@param wd a String word 
	*/
	public Node( String wd){
		MyLogger.writeMessage("Node(String)", MyLogger.DebugLevel.CONSTRUCTOR);
		word = wd;
		wcount = 1;
		ccount = wd.length();
		left = null;
		right = null;
		
	}


	/**
		@return the left child node of this node 
	*/
	public Node getLeft(){
		return left;
	}
	

	/** 
		@return the right child node of this node 
	*/
	public Node getRight(){
		return right;
	}


	/** set the left child node
		@param in the Node to assign as left child 
	*/
	public synchronized void setLeft(Node in){
		left = in;	
	}


	/**	set the right child node 
		@param in the Node to assign as right child 
	*/
	public synchronized void setRight(Node in){
		right = in;	
	}
	
	/** increment the word count
	*/
	public synchronized void increment(){
		wcount++;
	}
	
	/** decrement the word count
	*/
	public synchronized void decriment(){
	//	System.out.println("NEED DEBUG word deleted but none exists\n");//TODO 
		if(wcount>0){wcount--;}
	}
	
	/** set the word count 
	*/
	public synchronized void setCount(int int_in){
		this.wcount = int_in;
	}
	
	/**	get the wordCount
		@return wcount number of times a word encounterd		
	*/
	public int getWordCount(){
		return wcount;
	}


	/** get the characterCount
	@return character count int.
	*/
	public int getCharCount(){
		return ccount;
	}

	/**get the word the Node has (key)
		@return the key of Node, a string word.
	*/
	public String getWord(){
		return this.word;	
	}


	/** set the word 
		@param word_in the word to assign as key
	*/
	public void setWord(String word_in){
		this.word = word_in;	
		ccount = this.word.length();
	}	

	/**
		Objects comapre to method
	*/
	@Override 
	public int compareTo(Object other){
		/*
		if(! (other instanceof Node)){
			return 0;	
		}
		return this.word.compareTo(((Node)other).getWord());
		*/
		int totalVal = 0;
		if(! (other instanceof Node)){
			return 0;	
		}
		String word = (((Node)other).getWord());
		for(int i = 0; i < word.length(); i++){
			totalVal += (int) word.charAt(i);
		}
		
		int mVal = 0;
		for(int i = 0; i < this.getWord().length(); i++){
			mVal += (int) this.getWord().charAt(i);
		}
		
	
		String tmp = ((Node)other).getWord() + " "  + totalVal + "\n";
		MyLogger.writeMessage(tmp, MyLogger.DebugLevel.CHECKS);
		return (mVal - totalVal);
	}


	//makes a copy of the node and the important parameters not including the sub and observers when used
	/*
	public Node clone(){
		//Node tmp = new Node(bnum,course.get(0));
		//tmp.course = (ArrayList<String>)course.clone();
		
		return tmp;
	}
	*/



}
