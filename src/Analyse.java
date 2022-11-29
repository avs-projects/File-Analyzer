
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.LinkedHashMap;
import java.util.Map;

/*
 * This class explores folders and subfolders
 */

public class Analyse {
	
	/*
	 * We take the path of the file (or folder) as the key of the hash map.
	 * We use LinkedHashMap to keep the order of entries when displaying.
	 */
	
	private LinkedHashMap<String, Information> informations = new LinkedHashMap<String, Information>();

	public LinkedHashMap<String, Information> getInformations() {
		return informations;
	}

	/** 
	 *	Add information in a Hash Map.
	 *	@param path The file path
	 *	@param information The name, mime, extension, size and verification of a file
	 */
	
	public void add (String path,Information information) {
		informations.put(path, information);
	}
	
	/**
	 *	Find out if a file is empty.
	 * @param taille The file size.
	 * @return whether the file is empty or not.
	 */
	
	public boolean estVide (long taille) {
		return taille == 0;
	}
	
	/**
	 * Find the mime type of a file from the extension.
	 * @param file The file.
	 * @return The Mime type of the file.
	 * @throws IOException
	 */
	
	public String searchMimeType (File file) throws IOException {
		Path path = file.toPath();
		String mimeType = Files.probeContentType(path);
		return mimeType;
	}
	
	/**
	 * Take information from the file.
	 * Check the file.
	 * Add the information in a Hash Map.
	 * @param path File path.
	 * @throws IOException
	 */
	
	public void file (String path) throws IOException {
		Verification verif = new Verification ();
		String mimeType, ext, name;
		long size;
		File file = new File(path);
		if (file.isFile()) {
			mimeType = searchMimeType (file);
			ext = file.getName().substring(file.getName().lastIndexOf('.')+1, file.getName().length());
			name = file.getName();
			size = file.length();
			Information info = new Information (name,mimeType,ext,size,verif.check(file,ext));
			add (path,info);
		}
	}
	
	/**
	 * Take the information of each file and folder.
	 * Explore a folder and its subfolders and check each file.
	 * @param path File path
	 * @throws IOException
	 */
	
	public void list (String path) throws IOException {
		Verification verif = new Verification ();
		String mimeType, ext, name;
		long size;
		File file = new File(path);
		File[] files = file.listFiles();
		if (files != null) {
			for (int i = 0; i < files.length; i++) { 
				if (files[i].isDirectory() == true) {
					name = files[i].getName();
					Information info = new Information (name,null,null,0,null);
					add (files[i].getAbsolutePath(),info);
				}
				else {	
					mimeType = searchMimeType(files[i]);
					ext = files[i].getName().substring(files[i].getName().lastIndexOf('.')+1, files[i].getName().length());
					name = files[i].getName();
					size = files[i].length();
					Information info = new Information (name,mimeType,ext,size,verif.check(files[i], ext));
					add (files[i].getAbsolutePath(),info);
                }
				if (files[i].isDirectory() == true) {
					this.list(files[i].getAbsolutePath());
				}
			}
		}
	}       	
	
	public String toString () {
		String result = "";
		for (Map.Entry<String,Information> entry : informations.entrySet()) {
			result += "\n" + entry.getKey() + "\n" + entry.getValue() ;
			if (estVide(entry.getValue().getSize()) && entry.getKey().contains("Fichier")) {
				result += "Le fichier est vide \n"; 
			}
		}
		return result;
	}
}

