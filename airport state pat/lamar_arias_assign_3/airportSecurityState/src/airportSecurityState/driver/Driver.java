package airportSecurityState.driver;
import airportSecurityState.util.FileProcessor;
import airportSecurityState.util.Results;
import airportSecurityState.util.MyLogger;
import airportSecurityState.airportStates.Context;
import java.lang.System;
import java.io.IOException;


public class Driver{
 
 //run command ant -buildfile src/build.xml run -Darg0=src/input.txt -Darg1=src/output.txt -Darg2=0
 public static void main(String [] args){
 	//checks for number input in debug level
 	try{
        Integer.parseInt(args[2]);
        }catch(NumberFormatException b){
        	System.err.println("number not given for debug level.");
        	b.printStackTrace();
        	System.exit(1);
        }
 	if(args.length == 3 && Integer.parseInt(args[2]) <= 4 && Integer.parseInt(args[2]) >= 0){// must be 5 args
 		
 		int dbg = Integer.parseInt(args[2]);
 		
 		MyLogger.setDebugValue(dbg);
 		
 		//reads input file
 		FileProcessor inp = new FileProcessor();
 		inp.op(args[0]);
 		//inp.readLine(args[0]);
 		
 		//makes all results objects
 		Results o = new Results();
 		
 		Context airport = new Context();
 		
 		String banana;
 		//splits the string so the number and the course can be taken
 		//for(int i = 0; i < inp.lines.size(); i++){
 		while((banana = inp.re()) != null){
 			String delim = "[:]+";
 			String delimsemi = "[;]+";
 			String [] sp = banana.split(delim);
        		//String [] sp = inp.lines.get(i).split(delim);
        		MyLogger.writeMessage(sp[1], MyLogger.DebugLevel.STRPAR);
        		String [] tmpsp = sp[1].split(delimsemi);
        		MyLogger.writeMessage(tmpsp[0], MyLogger.DebugLevel.STRPAR);
        		//makes sure day is a number
        		try{
        		 Integer.parseInt(tmpsp[0]);
        		}catch(NumberFormatException b){
        			System.err.println("number not given for day.");
        			b.printStackTrace();
        			System.exit(1);
        		}
        		int day = Integer.parseInt(tmpsp[0]);
        		//makes sure day is not 0
        		if(day < 1){
        			System.err.println("invalid number for days, it should be positive and greater than 0.");
        			System.exit(1);
        		}
        		//populates results objects 
        		o = airport.getState().tightenOrLoosenSecurity(day, sp[5], o,airport);
 		}
 		inp.cl();
 		
 		
 		
 		//write to output file
 		o.writeToFile(args[1]);
 	}
 	else{
 		System.err.println("improper number of args, need 3 and debug value must be lees than 4 but greater than or equal to 0.");
 		System.exit(1);
 	}
 }
 
 }
