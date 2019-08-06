package fileVisitors.visitor;
import fileVisitors.visitor.VisitorI;
import fileVisitors.visitor.Node;

public class PrimeLength implements VisitorI{

	public void visit(Treebuilder tree){
		Node root = tree.getRoot();
		checkPrime(root);
	}
	//recursively checks tree for prime numbers and changes word to have -PRIME at the end if prime
	public void checkPrime(Node root){
		//recurse left child 
		if(root.getLeft() != null){
			checkPrime(root.getLeft());	
		}
		//check if Prime
		if(isPrime(root.getWord())){
			String change = root.getWord() + "-PRIME";
			root.setWord(change);
		}
		//recurse right child 
		if(root.getRight() != null){
			checkPrime(root.getRight());
		}
	
	}
	
	//checks if word is prime
	public boolean isPrime(String word){
		for(int i=2;i<word.length();i++) {
        		if(word.length()%i==0) return false;
   		}
    		return true;
	}

}
