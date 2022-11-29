

import java.io.File;
import java.io.IOException;
import java.util.LinkedHashMap;

/*
 * This class contains all the methods of the Analysis class
 */

public interface AnalyseInterface {
	
	public LinkedHashMap<String, Information> getInformations();

	public void add (String path,Information information);
	
	public boolean estVide (long taille);
	
	public String searchMimeType (File file) throws IOException;
	
	public void file (String path) throws IOException;
	
	public void list (String path) throws IOException;
	
	public String toString ();

}
