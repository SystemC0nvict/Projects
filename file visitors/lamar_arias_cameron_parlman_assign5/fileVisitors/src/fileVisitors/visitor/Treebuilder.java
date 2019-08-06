package fileVisitors.visitor;

import fileVisitors.visitor.Node;
import fileVisitors.visitor.VisitorI;
import fileVisitors.util.MyLogger;
import fileVisitors.util.Results;

import java.util.ArrayList;

/** Treebuilder. creates a BST of fileVisitors.visitor.Nodes 
*/
 public class Treebuilder{
 	public Node root;
 	
 	public Treebuilder(){
		MyLogger.writeMessage("Treebuilder()", MyLogger.DebugLevel.CONSTRUCTOR);
 		root = null;
 	}

 	//insert method for main tree that takes in the bnum and class to be added
 	public synchronized void insertS( String wd){
		MyLogger.writeMessage("insertS(String)", MyLogger.DebugLevel.TREE);
 		root = insertSR(root, wd);
 	}

 	//recursive insert method that inserts node into the main tree but also creates the clones of the node and inserts those clones into the backup trees. takes in the start node, bnum, and class to be added 
 	public synchronized Node insertSR(Node root, String wd){
		MyLogger.writeMessage("insertSR(Node, String)", MyLogger.DebugLevel.TREE);
 		if(root == null){
 			root = new Node(wd);
 			
 			//MyLogger.writeMessage(root.word, MyLogger.DebugLevel.CHECKW);
 			return root;
 		}
 		if(wd.equals(root.getWord())){
			root.increment();
		}
 		else if(wd.compareTo( root.getWord()) < 0){
			//root.left = insertSR(root.left, wd);
			root.setLeft(insertSR(root.getLeft(), wd));	
		}
 		else if(wd.compareTo(root.getWord()) >= 0){
			//root.right = insertSR(root.right, wd);
			root.setRight(insertSR(root.getRight(), wd));
		}
 		return root;
 	}


 	//delete function to delete which takes the bnum and class to delete. calls recursive delete function
 	public void delete(String c){
		MyLogger.writeMessage("delete(String)", MyLogger.DebugLevel.TREE);
 		root = deleteR(root, c);
 	}
 	//recursive delete function takes starting node, and the word to delete
 	//the word isnt deleted so much as the count for the word becomes 0
 	
 	public synchronized Node deleteR(Node root, String c){
		MyLogger.writeMessage("deleteR(Node, String)", MyLogger.DebugLevel.TREE);
 		//if(root.word == null)return root;
 		
 		if(root.getWord().equals(c)){
 			root.setCount(0);
 			return root;
 		}
 		if(root.getLeft() != null && c.compareTo(root.getWord()) < 0  ){
 			return deleteR(root.getLeft(), c);
 		}
 		if(root.getRight() != null && c.compareTo(root.getWord()) >= 0){
 			return deleteR(root.getRight(), c);
 		}
 		return null;
 	}
 	

 	//adds to the results string arraylist for final output and traverses in order
 	public Results printNodes(Results out, Node root){
		MyLogger.writeMessage("printNodes(Results, Node)", MyLogger.DebugLevel.TREE);
 		if(root.getLeft() != null){
 			out = printNodes(out, root.getLeft());
 		}
 		//used for formatting the string to add to the array
 		String p = root.getWord(); 
 		p = p + root.getWordCount();;
 		//MyLogger.writeMessage(p, MyLogger.DebugLevel.CHECKW);
 		if(root.getWordCount() > 0){
 			out.output.add(root.getWord());
		}
 		if(root.getRight() != null){
 		out = printNodes(out, root.getRight());
 		}
 		return out;
 	}


 	//counts the number of words in the tree
 	public int countW( Node root){
		MyLogger.writeMessage("countW(Node)", MyLogger.DebugLevel.TREE);
 		int Ws = root.getWordCount();
 		if(root.getLeft() != null){
 			Ws += countW( root.getLeft());
 		}
 		

 		if(root.getRight() != null){
 			Ws += countW(root.getRight());
 		}
 		return Ws;
 	}


 	//gets number of distinct words
 	public int countDW( Node root){
		MyLogger.writeMessage("countDW(Node)", MyLogger.DebugLevel.TREE);
 		int Ws = 0;
 		if(root.getWordCount() > 0)Ws +=1;
 		if(root.getLeft() != null){
 			Ws += countDW( root.getLeft());
 		}
		//MyLogger.writeMessage(root.word, MyLogger.DebugLevel.CHECKW);
 		if(root.getRight() != null){
 			Ws += countDW(root.getRight());
 		}
 		return Ws;
 	}


 	//counts number of characters not including spaces
 	public int countChar( Node root){
		MyLogger.writeMessage("countChar(Node)", MyLogger.DebugLevel.TREE);
 		int chars = root.getCharCount() * root.getWordCount() ;
 		if(root.getLeft() != null){
 			chars += countChar(root.getLeft());
 		}
 		
 		//used for formatting the string to add to the array
 		String p = root.getWord() + " "; 
 		p = p + root.getCharCount();
 		//MyLogger.writeMessage(p, MyLogger.DebugLevel.CHECKW);
 		//out.output.add(p);

 		if(root.getRight() != null){
 			chars += countChar(root.getRight());
 		}
 		return chars;
 	}
 	
	/*
		Delete function, recursively search for Node with String w as key
		decriment the Nodes wcount. 
	*/
 	public synchronized void tmpDel(Node root, String w){
		MyLogger.writeMessage("tmpDel(Node, String)", MyLogger.DebugLevel.TREE);
 		if(w.equals(root.getWord())){
 			root.decriment();
 		}
		else if(root.getLeft() != null && w.compareTo(root.getWord()) < 0){
 			 tmpDel(root.getLeft(), w);
			
 		}
 		else if(root.getRight() != null && w.compareTo(root.getWord()) >= 0){
 			tmpDel(root.getRight(), w);
 		}
 		return ;	
 		
 	}


 	/*	writes the word count, disitnct word count, and number fo characters 
		to the results object and returns it
	*/
 	public Results writeResults(Results out, Node root){
		MyLogger.writeMessage("writeResults(Results, Node)", MyLogger.DebugLevel.TREE);
 		String line = "The total number of words: " + Integer.toString(countW(root));
 		out.output.add(line);
 		line = "The total number of characters: " + countChar(root);
 		out.output.add(line);
 		line = "The total number of distinct words: " + countDW(root);
 		out.output.add(line);
 	
 		return out;
 	}
 	
 	public Node getRoot(){
		MyLogger.writeMessage("getRoot()", MyLogger.DebugLevel.TREE);
		return root;
	}	
	
	public void accept(VisitorI visi){
		visi.visit(this);
	}
 }

 	//insert function for backup trees. Takes in the bnum, class, and Node reference for the clone of the node to be inserted
 	
 	//function to search for node based on root and bnumber
 	/*
 	public Node search(Node root,String wd){
 		if(root == null || root.bnum == num)return root;
 		
 		if(root.bnum > num)return search(root.left, num);
 		
 		return search(root.right, num);
 	}
 	*/

