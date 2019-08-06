package fileVisitors.visitor;
import fileVisitors.visitor.VisitorI;
import fileVisitors.visitor.Node;
import java.lang.StringBuilder;

// Capitalize palindrom words over length 3
// :q

public class PalindromeHighlight implements VisitorI{
	
	//visit of visitor pattern, 
	public void visit(Treebuilder tree){
		Node root = tree.getRoot();
			checkPalindromes(root);	
	}

	//search the tree for palindromes if a word is a palindrome capitalize first letter 
	public void checkPalindromes(Node root){
		//recurse left child 
		if(root.getLeft() != null){
			checkPalindromes(root.getLeft());	
		}	
		//check if a palindrome 
		if(isPalindrome(root)){
			//root.setWord(root.getWord().toUpperCase());
			//TODO   following lines should create a string word capitalized then assign set the word equal to it.
			//but this causes a problem and the program exits...... 
			String word = root.getWord().substring(0,1).toUpperCase() + root.getWord().substring(1);
			//System.out.println("Word to uppercase checking !!!!  "+ word);
			root.setWord(word);
		}
		//recurse right child 
		if(root.getRight() != null){
			checkPalindromes(root.getRight());
		}
	}



	public boolean isPalindrome(Node node_in){
		//compare the string of node to the reverse of the string 	
		if(node_in.getWord().length() > 3){
			String reverse= new StringBuilder(node_in.getWord()).reverse().toString();
			if(node_in.getWord().equalsIgnoreCase(reverse)){
				return true;
			}
		}

		return false;
	}
	
	



}

/*


 76     //adds to the results string arraylist for final output and traverses in order
 77     public Results printNodes(Results out, Node root){
 78         MyLogger.writeMessage("printNodes(Results, Node)", MyLogger.DebugLevel.TREE);
 79         if(root.getLeft() != null){
 80             out = printNodes(out, root.getLeft());
 81         }
 82         //used for formatting the string to add to the array
 83         String p = root.getWord();
 84         p = p + root.getWordCount();;
 85         MyLogger.writeMessage(p, MyLogger.DebugLevel.CHECKW);
 86         if(root.getWordCount() > 0){
 87             out.output.add(root.getWord());
 88         }
 89         if(root.getRight() != null){
 90         out = printNodes(out, root.getRight());
 91         }
 92         return out;
 93     }
 94 
*/
