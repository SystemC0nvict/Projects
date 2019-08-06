package genericCheckpointing.xmlStoreRestore;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.lang.reflect.Field;
import java.lang.reflect.Constructor;
import genericCheckpointing.util.Results;
import genericCheckpointing.util.FileProcessor;
import genericCheckpointing.util.SerializableObject;
import  genericCheckpointing.xmlStoreRestore.XMLSerialization;

public class StoreRestoreHandler implements InvocationHandler {
	public Results out = new Results();
	public FileProcessor proc = new FileProcessor();
	
	//meth.getName() gives whether it is write or readobj
	//objs[0].getClass().getName() gives full class name
	//objs[i] gives value of variables
	public Object invoke(Object obj, Method meth, Object[] objs){
		if(meth.getName().equals("writeObj")){
			
			if(objs[2] == "XML"){
				serializeData((SerializableObject)objs[0], new XMLSerialization(), out);
			}
		
		}
		
		else if(meth.getName().equals("readObj")){
			if(objs[0] == "XML"){
				return deserialize( new XMLDeserialization(), proc);
			}
		}
		
		else if(meth.getName().equals("opobj")){
			proc.op((String) objs[0]);
			//System.out.println(objs[0]);
		}
		else if(meth.getName().equals("closeobj")){
			proc.cl();
			
		}
		
		
		return null;
	}
	
public void serializeData(SerializableObject sObject, SerStrategy sStrategy, Results res) {
        sStrategy.processInput(sObject, res);
    }
    
public Object deserialize(DeserStrategy dStrategy, FileProcessor fp){
	return dStrategy.makeOutput(fp);
}    

}
