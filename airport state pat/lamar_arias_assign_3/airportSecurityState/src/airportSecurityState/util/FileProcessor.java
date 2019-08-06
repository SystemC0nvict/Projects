package airportSecurityState.util;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.lang.System;

public class FileProcessor{
	public ArrayList <String> lines;
	public File fi;
	public FileReader re;
	public BufferedReader reader;
	
	public FileProcessor(){
		lines = new ArrayList<String>();
	}
	public void op(String inf){
		try{
			fi = new File(inf);
			re = new FileReader(fi);
			reader = new BufferedReader(new FileReader(inf));
		}catch(IOException p){
        		System.err.println("improper file name given.");
        		p.printStackTrace();
        		System.exit(1);
        	}
	}
	public String re(){
	String line = null; 
	try{
		
		if((line = reader.readLine()) != null){
			String delim = "[:]+";
        		String [] sp = line.split(delim);
        		if(sp.length != 6){
        			System.err.println("improper formatting of input, file line cant be read");
        			System.exit(1);
        		}
        		//lines.add(line);
		}
	}
	catch(IOException p){
        		System.err.println("improper file name given.");
        		p.printStackTrace();
        		System.exit(1);
        	}
		return line;
	}
	
	public void cl(){
		try{
			re.close();
		}
		catch(IOException p){
        	System.err.println("File cant be closed");
        	p.printStackTrace();
        	System.exit(1);
        }
	}
	

}
