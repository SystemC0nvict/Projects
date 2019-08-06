package fileVisitors.visitor;
import fileVisitors.visitor.VisitorI;
import fileVisitors.visitor.Treebuilder;
import fileVisitors.util.FileProcessor;
import fileVisitors.util.MyLogger;

public class PopulateVisitor implements VisitorI{
	//public Treebuilder tr;
	public FileProcessor fi;
	public String fname;
	

	//constructor 
	public PopulateVisitor( String name_in){
	//	tr = tree;
		fname = name_in; 
		fi = new FileProcessor();
		MyLogger.writeMessage("PopulateVisitor(String)", MyLogger.DebugLevel.CONSTRUCTOR);
	}


	//visit treebuilder 
	//reads input from file and inserts intro tree using treebuilders methods .
	public void visit(Treebuilder tree){
		fi.op(fname);
		String line;
		while((line = fi.re()) != null){
		//MyLogger.writeMessage(line, MyLogger.DebugLevel.CHECKW);
			String [] w = parseline(line);
			for(int i = 0; i < w.length; i++){
 				tree.insertS(w[i]);
 			}
		}
		fi.cl();
	}
	
	//spits lines by spaces and returns string array of the split
	public String [] parseline(String line){
		String ldelim = "[ ]+";
		String [] w = line.split(ldelim);
		
		return w;
		
	}
}
