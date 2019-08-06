package genericCheckpointing.xmlStoreRestore;

import genericCheckpointing.util.FileProcessor;
import genericCheckpointing.xmlStoreRestore.DeserStrategy;
import genericCheckpointing.util.SerializableObject;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.lang.reflect.Field;
import java.lang.reflect.Constructor;

public class XMLDeserialization implements DeserStrategy{
	public Object makeOutput(FileProcessor proc){
   	//System.out.println(objs[0]);
			String line = proc.re();//getDPSerialization
			String classn = proc.re();// get complex type line
			//System.out.println(classn);
			Class<?> cl = null;
			Constructor<?> cons = null;
			Object ob = null;
			try{
				/*
				if(classn.contains("MyAllTypesFirst")){
					cl = Class.forName("genericCheckpointing.util.MyAllTypesFirst");
					cons = cl.getConstructor();
					ob = cl.newInstance();
				}
				else{
					cl = Class.forName("genericCheckpointing.util.MyAllTypesSecond");
					cons = cl.getConstructor();
					ob = cl.newInstance();
				}
				*/
				String [] cla = classn.split("\"+");
				cl = Class.forName(cla[1].substring(0,cla[1].length() ));
				//System.out.println(cla[1].substring(0,cla[1].length() ));
				
				cons = cl.getConstructor();
				ob = cl.newInstance();
				
				
				
				while(!(line = proc.re()).equals(" </ComplexType>")){
					//System.out.println(line);
					String [] split = line.split(" +");
					//System.out.println(split[1].substring(1, split[1].length()));
					Field fi = cl.getDeclaredField(split[1].substring(1, split[1].length()));
					fi.setAccessible(true);
					String [] typeS = split[2].split("\"+");
					//System.out.println(typeS[1]);
					String type = typeS[1].substring(4,typeS[1].length() );
					//System.out.println(type);
					String [] val = typeS[2].split("<");
					String v = val[0].substring(1, val[0].length() );
					//System.out.println(v);
					String mname = "set" + fi.getName().substring(0, 1).toUpperCase() + fi.getName().substring(1);
					//System.out.println(ob.getClass());
					//Method m = ob.getClass().getMethod(mname);
					
					if(type.equals("String")){
						fi.set(ob,(Object)v);
						//m.invoke(ob,(Object)v );
					}
					else if(type.equals("int")){
						fi.setInt(ob, (Integer.parseInt(v)));
						//m.invoke(ob,(Object)(Integer.parseInt(v)) );
					}
					else if(type.equals("float")){
						fi.setFloat(ob, (Float.parseFloat(v)));
						//m.invoke(ob,(Object)(Float.parseFloat(v)) );
					}
					else if(type.equals("boolean")){
						fi.setBoolean(ob, (Boolean.parseBoolean(v)));
						//m.invoke(ob,(Object) (Boolean.parseBoolean(v)));
					}
					else if(type.equals("char")){
						fi.setChar(ob, v.charAt(0));
						//m.invoke(ob, (Object)v.charAt(0));
					}
					else if(type.equals("long")){
						fi.setLong(ob, (Long.parseLong(v)));
						//m.invoke(ob,(Object) (Long.parseLong(v)));
					}
					else if(type.equals("short")){
						fi.setShort(ob, (Short.parseShort(v)));
						//m.invoke(ob,(Object) (Short.parseShort(v)));
					}
					else if(type.equals("double")){
						fi.setDouble(ob, (Double.parseDouble(v)));
						//m.invoke(ob, (Object)(Double.parseDouble(v)));
					}
				}
				proc.re();
				
			}catch(Exception e){
				System.out.println(e);
			}
			return ob;
   }

}
