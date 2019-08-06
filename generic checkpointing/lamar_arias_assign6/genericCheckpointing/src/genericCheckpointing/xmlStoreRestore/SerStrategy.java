package genericCheckpointing.xmlStoreRestore;

import genericCheckpointing.util.SerializableObject;
import genericCheckpointing.util.Results;
import genericCheckpointing.util.FileProcessor;

public interface SerStrategy {
   public void processInput(SerializableObject sObject, Results obj);
   

}
