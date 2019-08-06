package fileVisitors.driver;
import fileVisitors.util.FileProcessor;
import fileVisitors.visitor.Treebuilder;
import fileVisitors.visitor.PopulateVisitor;
import fileVisitors.visitor.PalindromeHighlight;
import fileVisitors.visitor.PrimeLength;
import fileVisitors.visitor.PrintTree;
import fileVisitors.util.Results;
import fileVisitors.util.MyLogger;
//import airportSecurityState.airportStates.Context;
import java.lang.System;
import java.io.IOException;


/** 
@author lamar arias 
@author	cameron parlman
@version 1.0 
@since 0.0
*/
public class Driver{
 

//run command
//ant -buildfile src/build.xml run -Dargs='src/input.txt src/output.txt 1'
 public static void main(String [] args){
		String inputName="";
		String outputName="";
		int debugLevel = 1;

		/*	VALIDATION: command line  
			catch incorrect number of command line arguments 	
		*/
		if(args.length < 3){
			usage(); 
			System.exit(1);
		}

		/*	ASSIGNMENTS: default
			assign input and output names 
			try to assign numThreads and debugLevel. 
		*/
		inputName = args[0];	
		outputName = args[1];	
		try{
			debugLevel = Integer.parseInt(args[args.length-1]);//debug level
		}catch(NumberFormatException b){
				System.err.println("number not given for debug level.");//TODO
				b.printStackTrace();
				System.exit(1);
		}
	for(int i = 0 ; i < args.length; i++){
		System.out.println(args[i] + ", ");
	}
		System.out.println("");
		

		//VALIDATION check DebugVal in range
		if(debugLevel <0 || debugLevel > 4){System.err.println("DebugLevel out of range (0-4)");}
		/*END VALIDATION:*/
		
		
		/* 	create Declare Treebuilder, FileProcessor, Results 
			set debug level 
			pass input file to Fileprocessor 	
		*/
		MyLogger.setDebugValue(Integer.parseInt(args[2]));
		Treebuilder tree = new Treebuilder();
		
		
		PopulateVisitor popy = new PopulateVisitor(inputName);
		tree.accept(popy);
	
		PalindromeHighlight paly = new PalindromeHighlight();
		tree.accept(paly);
		
		PrimeLength prim = new PrimeLength();
		tree.accept(prim);
		
		PrintTree prin = new PrintTree(outputName);
		tree.accept(prin);

		/*
			write results to file 
		*/
		//out = tree.printNodes(out, tree.root);
		//out.writeToFile(outputName);	 	


		/*
			close input file.		
		*/
		//inp.cl();
 
	}/* END MAIN*/



	/** Display print the command line usage 
	*/
	public static void usage(){
		System.err.println("fileVisitors input output 1 banana 0");
	} 
 
}



/* 
	COMMENTED CODE 
*/

 //run command ant -buildfile src/build.xml run -Darg0=src/input.txt -Darg1=src/output.txt -Darg2=3 -Darg3=a,as,fox -Darg4=0

//String wdelim = "[,]+";
		//String ldelim = "[ ]+";
		//String [] wds = args[3].split(wdelim); 


/*
 	String line;
 	while((line = inp.re()) != null){
 		String [] w = line.split(ldelim);
 		for(int i = 0; i < w.length; i++){
 			tree.insertS(w[i]);
 		}
 	}
 	*/
 	
//creator.out = tree.printNodes(creator.out, tree.root);
//creator.out.writeToFile(args[1]);
