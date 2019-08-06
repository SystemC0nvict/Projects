package genericCheckpointing.xmlStoreRestore;


import genericCheckpointing.util.FileProcessor;
public interface DeserStrategy{
	public Object makeOutput(FileProcessor fp);
}
