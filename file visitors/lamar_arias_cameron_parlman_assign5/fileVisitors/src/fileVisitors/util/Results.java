package fileVisitors.util;
import fileVisitors.util.StdoutDisplayInterface;
import fileVisitors.util.FileDisplayInterface;
import java.util.ArrayList;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import fileVisitors.util.MyLogger;

	/*

	*/
 public class Results implements StdoutDisplayInterface, FileDisplayInterface{
 	public ArrayList <String> output;
 	
	/*

	*/
 	public Results(){
		MyLogger.writeMessage("Results()", MyLogger.DebugLevel.CONSTRUCTOR);
 		output = new ArrayList<String>();
 	}
 	
	/*

	*/
 	public void writeToStdout(String o){
		System.out.println(o);
	}
	
	/*
	
	*/
	public void writeToFile(String out){
		BufferedWriter wr = null;
		FileWriter fw = null;
		try{
			fw = new FileWriter(out);
			wr = new BufferedWriter(fw);
			for(int i = 0; i < output.size(); i++){
				String tmp =  output.get(i) + "\n";
				wr.write(tmp);
			}
		}catch(IOException banana){
			banana.printStackTrace(); 
		}finally{
			try{
				if(wr != null)wr.close();
				
				if(fw != null)fw.close();
			
			}
			catch(IOException ban2){
				ban2.printStackTrace();
			}
		
		
		}
	}
	public void writeToScreen(String in){

	}	
	
	/*
		writes whatever is ArrayList output to screen
	*/
	public void writeToScreen(){
		System.out.println(output);
	}
	
	/*
	
	*/
	public void writeSchedulesToFile(String name){
	
	}

	public void insertResult(String in){
		output.add(in);	
	}
	public void reset(){output = new ArrayList<String>();}
	
	public ArrayList<String> getResults(){
		return output;
	}	

 }
