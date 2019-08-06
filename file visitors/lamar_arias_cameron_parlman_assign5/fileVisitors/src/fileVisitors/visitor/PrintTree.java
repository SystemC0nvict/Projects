package fileVisitors.visitor;
import fileVisitors.visitor.VisitorI;
import fileVisitors.visitor.Node;
import fileVisitors.util.Results;
import fileVisitors.util.MyLogger;
import java.util.ArrayList;
import java.util.Collections;

public class PrintTree implements VisitorI{
	//arrayList for sorting the words in alphanumeric order
	ArrayList<Node> nlist;
	Results out;
	//name of output file
	String oname;
	
	//sets arrayList and Results object as well as string for output file
	public PrintTree(String name){
		nlist = new ArrayList<Node>();
		out = new Results();
		oname = name;
		MyLogger.writeMessage("PrintTree(String)", MyLogger.DebugLevel.CONSTRUCTOR);
	}
	
	public void visit(Treebuilder tree){
		Node root = tree.getRoot();
		getNodes(root);
		Collections.sort(nlist);
		addList();
		out.writeToFile(oname);
		
	}
	
	public void getNodes(Node root){
	
		if(root.getLeft() != null){
			getNodes(root.getLeft());	
		}
		nlist.add(root);
		
		if(root.getRight() != null){
			getNodes(root.getRight());
		}
	}
	//adds words from sorted arrayList to Results object
	public void addList(){
		for(int i = 0; i < nlist.size(); i++){
			out.output.add(nlist.get(i).getWord());
		}
	}
	

}
