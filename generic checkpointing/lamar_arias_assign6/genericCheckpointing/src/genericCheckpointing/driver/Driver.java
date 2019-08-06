
package genericCheckpointing.driver;

import java.util.Vector;
import genericCheckpointing.util.ProxyCreator;
import genericCheckpointing.util.SerializableObject;
import genericCheckpointing.util.MyAllTypesFirst;
import genericCheckpointing.util.MyAllTypesSecond;
import genericCheckpointing.server.StoreRestoreI;
import genericCheckpointing.server.RestoreI;
import genericCheckpointing.server.StoreI;
import genericCheckpointing.xmlStoreRestore.StoreRestoreHandler;
// import the other types used in this file

//ant -buildfile src/build.xml run -Dargs='2 src/file.txt' test commmand
public class Driver {
    
    public static void main(String[] args) {
	
	// FIXME: read the value of checkpointFile from the command line
	int NUM_OF_OBJECTS = Integer.parseInt(args[1]);
	ProxyCreator pc = new ProxyCreator();
	
	// create an instance of StoreRestoreHandler (which implements
	// the InvocationHandler
	StoreRestoreHandler banana =  new StoreRestoreHandler();
	//banana.proc.op(args[1]);
	// create a proxy
	/*
	StoreRestoreI cpointRef = (StoreRestoreI) pc.createProxy(
								 new Class[] {
								     StoreI.class, RestoreI.class
								 }, 
								 new StoreRestoreHandler()
								 );
	*/	
		
	StoreRestoreI cpointRef = (StoreRestoreI) pc.createProxy(
								 new Class[] {
								     StoreI.class, RestoreI.class
								 }, 
								 banana
								 );
	// FIXME: invoke a method on the handler instance to set the file name for checkpointFile and open the file

	MyAllTypesFirst myFirst;
	MyAllTypesSecond  mySecond;

	// Use an if/switch to proceed according to the command line argument
	// For deser, just deserliaze the input file into the data structure and then print the objects
	// The code below is for "serdeser" mode
	// For "serdeser" mode, both the serialize and deserialize functionality should be called.

	// create a data structure to store the objects being serialized
        // NUM_OF_OBJECTS refers to the count for each of MyAllTypesFirst and MyAllTypesSecond
        Vector <SerializableObject> mysv = null;
        if(args[0].equals("serdeser")){
        	mysv = new Vector<SerializableObject>();
		for (int i=0; i<NUM_OF_OBJECTS; i++) {

	   	 // create these object instances correctly using an explicit value constructor
	    	// use the index variable of this loop to change the values of the arguments to these constructors
	   	 boolean in = false;
	   	 if(i%2 == 0)in = true;
	   	 myFirst = new MyAllTypesFirst(i+10, i+17, i*1000, i*2000, ("String" + i), in);
	   	 mySecond = new MyAllTypesSecond(i+0.5, i*2.5, (float)(i * 5.5), (short)(i / 2), (short)(i / 4), (char)(i + 70) );

	   	 // store myFirst and mySecond in the data structure
	    	((StoreI) cpointRef).writeObj(myFirst, 7,  "XML", args[2]);
	    	((StoreI) cpointRef).writeObj(mySecond, 9,  "XML", args[2]);
	   
	   	mysv.add(myFirst);
	   	mysv.add(mySecond);

		}
		banana.out.writeToFile(args[2]);
	
	}

	SerializableObject myRecordRet;

	((RestoreI) cpointRef).opobj(args[2]);
	// create a data structure to store the returned ojects
	Vector <SerializableObject> sList = new Vector<SerializableObject>();
	for (int j=0; j<2*NUM_OF_OBJECTS; j++) {
		
	     	myRecordRet = ((RestoreI) cpointRef).readObj("XML");
	     	//System.out.println("myRecordRet int " + ((MyAllTypesFirst)myRecordRet).getMyInt());
	     	//System.out.println("myRecordRet String " + ((MyAllTypesFirst)myRecordRet).getMyString());
	     	System.out.println(myRecordRet.toString() + "\n");
	     	sList.add(myRecordRet);
	    // FIXME: store myRecordRet in the vector
	}

	// FIXME: invoke a method on the handler to close the file (if it hasn't already been closed)
	((RestoreI) cpointRef).closeobj();
	// FIXME: compare and confirm that the serialized and deserialzed objects are equal. 
	//System.out.println(mysv.equals(sList));
	int fcount = 0;
	if(args[0].equals("serdeser")){
	
		for(int v = 0; v < 2*NUM_OF_OBJECTS; v++){
			if(!mysv.get(v).equals(sList.get(v)))fcount++;
			//System.out.println(" mysv hash = " + mysv.get(v).hashCode() + " sList hash = " + sList.get(v).hashCode() + " t or f = " +  mysv.get(v).equals(sList.get(v)));
		}
	}
	System.out.println("\nNumber mismatched = " + fcount);

	// The comparison should use the equals and hashCode methods. Note that hashCode 
	// is used for key-value based data structures
    
    }
}
