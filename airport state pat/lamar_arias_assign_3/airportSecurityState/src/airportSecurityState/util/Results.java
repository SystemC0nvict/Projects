package airportSecurityState.util;
import java.util.ArrayList;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
 public class Results implements StdoutDisplayInterface, FileDisplayInterface{
 	public ArrayList <String> output;
 	
 	public Results(){
 		output = new ArrayList<String>();
 	}
 	
 	public void writeToStdout(String o){
		System.out.println(o);
	}
	
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
 }
