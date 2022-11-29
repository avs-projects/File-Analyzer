
import java.awt.Image;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.FileNameMap;
import java.net.URLConnection;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import javax.imageio.ImageIO;

public class Verification {
	
	String c = null;
	
	/**
	 * Check the integrity of a file.
	 * @param file The file.
	 * @param ext The file extension.
	 * @return If the mime type of the file is the correct one
	 */
	
	public String check (File file, String ext) {
		BufferedReader tmp = null;
    	try {
    		if (file.length()!=0) {
    			if (ext.matches("jpg|jpeg|png|gif|ico|svg|tif|tiff|webp")) {
    				return c=checkImage(file);
    			}
    			if (ext.matches("htm|html")) {
    				return c=checkHTML (file,tmp);
    			}
    			if (ext.equals("sh")) {
    				return c=checkScript (file,tmp);
    			}
    			if (ext.matches("zip|docx|xlsx|pptx|odt|ods|odp")) {
    				return c=checkZip (file) ;
    			}
    			else {
    					return "No_Check ";
    			}
    		}
    		else {
    			return "No_Check ";
    		}
    	} catch (IOException e) {
    		return "No_Check ";
    	}
	}
	
	/**
	 * Allows you to take the dimensions of an image
	 * @param file The file
	 * @return true and the dimensions if it is an image, false if the file is not an image
	 * @throws IOException
	 */
	
	public String checkImage (File file) throws IOException {
		Image img;
		try {
			img = ImageIO.read(file);
			return true + "\n\t  [dim=" + img.getWidth(null) + " x " + img.getHeight(null) + "]\n\t  Ce fichier est bien une image";
		} catch (NullPointerException e) {
			return false + "\n\t  Ce fichier n'est pas une image";
		}         
	}
	
	/**
	 * Allows to read the first line in an HTML file
	 * @param file The file
	 * @param tmp Bufferedreader
	 * @return true if it's an HTML file, false otherwise
	 * @throws IOException
	 */
	
	public String checkHTML (File file, BufferedReader tmp) throws IOException  {
	    tmp = new BufferedReader(new FileReader(file));
		String l = tmp.readLine();
		try {
			if (l.equals("<!DOCTYPE html>")) {
				return true + "\n\t  Ce fichier est bien un fichier html";
			} 
			else {
				return false + "\n\t  Ce fichier n'est pas un fichier html";
			}
		} finally { 
	    	if (tmp != null) {
	    			tmp.close();
	    	}
		}
	}
	
	/**
	 * Allows to read the first line in an sh file
	 * @param file The file.
	 * @param tmp BufferedReader
	 * @return true if it is a schell file, false if not
	 * @throws IOException
	 */
	
	public String checkScript (File file, BufferedReader tmp) throws IOException  {
    	tmp = new BufferedReader(new FileReader(file));
		String l = tmp.readLine();
		try {
			if (l.equals("#!/bin/")) {
				return true + "\n\t  Ce fichier est un fichier script shell";
			} 
			else {
				return false + "\n\t  Ce fichier n'est pas un fichier script shell";
			}
		} finally { 
    		if (tmp != null) {
    			tmp.close();
    		}
		}
    }
	
	/**
	 * Allows to unzip a zip file
	 * @param file the file
	 * @return true if it is a zip file and the files that compose it, false if it is not a zip file
	 */
	
	public String checkZip(File file) {
        try {
        
            ZipFile zipFile = new ZipFile(file);
            String zip = "" ;
            
            Enumeration<? extends ZipEntry> entries = zipFile.entries();
            
            while (entries.hasMoreElements()) {
                ZipEntry entry = entries.nextElement();
                String name = entry.getName();
                long compressedSize = entry.getCompressedSize();
                long normalSize = entry.getSize();
                FileNameMap fileNameMap = URLConnection.getFileNameMap();
                String mimeType = fileNameMap.getContentTypeFor(entry.getName());
                	if (entry.isDirectory()) {
                		zip += "\n\t  Dossier : "+ name  ;
                	}
                	else {
                		zip += "\n\t  Fichier : "+ name +"\n\t  Taille compress� : "+ compressedSize +"\n\t  Taille  : "+ normalSize+"\n\t  Type Mime : "+ mimeType;

                	}
            }
            zipFile.close();
            return true + zip;
                
		} catch (IOException e) {
			return false + "\n\t  Ce n'est pas un fichier ZIP";
		}
	}
}