package genericCheckpointing.server;

import genericCheckpointing.server.StoreRestoreI;
import genericCheckpointing.util.MyAllTypesFirst;
import genericCheckpointing.util.MyAllTypesSecond;

public interface StoreI extends StoreRestoreI {
      void writeObj(MyAllTypesFirst aRecord, int authID, String wireFormat, String fname);
      void writeObj(MyAllTypesSecond bRecord, int authID, String wireFormat, String fname);
}
