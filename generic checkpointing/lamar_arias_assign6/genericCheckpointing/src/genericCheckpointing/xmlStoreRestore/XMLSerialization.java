package genericCheckpointing.xmlStoreRestore;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.lang.reflect.Field;
import java.lang.reflect.Constructor;
import genericCheckpointing.xmlStoreRestore.SerStrategy;
import genericCheckpointing.util.SerializableObject;
import genericCheckpointing.util.Results;
import genericCheckpointing.util.FileProcessor;

public class XMLSerialization implements SerStrategy {
    
     public void processInput(SerializableObject sObject, Results out) {

      		
			
			out.output.add("<DPSerialization>");
			out.output.add(" <complexType xsi:type=\"" + sObject.getClass().getName() + "\">");
			
			for(Field field : sObject.getClass().getDeclaredFields()){
				field.setAccessible(true);
				
				try{
					//Class<?> ctype = field.getType();
					//Constructor<?> con = ctype.getConstructor(cls);
					//Object tmp = ctype.newInstance();
					Object o = field.get(sObject);
					String mname = "get" + field.getName().substring(0, 1).toUpperCase() + field.getName().substring(1);
					//System.out.println(field.getName());
					Method m = sObject.getClass().getMethod(mname);
					//System.out.println(m.invoke(sObject));
					//System.out.println(m);
					//System.out.println("  <" + field.getName() + " " + "xsi:type=\"xsd:" + field.getType().getName().replace("java.lang.", "") + "\"" + ">" + o  + "</" + field.getName() + ">");
					
					boolean comp = true;
					if(o instanceof Integer){
						//System.out.println("Integer " + o);
						//if((Integer)o < 10)comp = false;
						if((Integer)m.invoke(sObject) < 10)comp = false;
					}
					else if(o instanceof Float){
						//if((Float)o < 10)comp = false;
						if((Float)m.invoke(sObject) < 10)comp = false;
					}
					else if(o instanceof Short){
						//if((Short)o < 10)comp = false;
						if((Short)m.invoke(sObject) < 10)comp = false;
					}
					else if(o instanceof Long){
						//if((Long)o < 10)comp = false;
						if((Long)m.invoke(sObject) < 10)comp = false;
						
					}
					else if(o instanceof Double){
						//if((Double)o < 10)comp = false;
						if((Double)m.invoke(sObject) < 10)comp = false;
					}
					//System.out.println("comp = " + comp);
					if(comp == true){
						out.output.add("  <" + field.getName() + " " + "xsi:type=\"xsd:" + field.getType().getName().replace("java.lang.", "") + "\"" + ">" + m.invoke(sObject)  + "</" + field.getName() + ">");
					}
					
					
						//out.output.add("  <" + field.getName() + " " + "xsi:type=\"xsd:" + field.getType().getName().replace("java.lang.", "") + "\"" + ">" + o  + "</" + field.getName() + ">");
					
				}catch(Exception e){
					System.out.println("instance of " + e);
				}
				/*catch(InstantiationException e){
					System.out.println("InstantiationException");
				}
				catch(IllegalAccessException e){
					System.out.println("IllegalAccessException");
				}*/
				
			}
			//System.out.println(" </ComplexType>");
			out.output.add(" </ComplexType>");
			//System.out.println("</DPSerialization>");
			out.output.add("</DPSerialization>");

   }
   
   
}

