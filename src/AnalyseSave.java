
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectOutputStream;

/*
 * This class is used to save the analysis information in a file created
 */

public class AnalyseSave extends Analyse implements AnalyseSaveInterface {
	
	/**
	 * This function makes it possible to serialize the information
	 */
	
	public void serializationSave (String fileName) {
		ObjectOutputStream stream;
		try {
				stream = new ObjectOutputStream (new FileOutputStream(fileName));
				for (Information information : getInformations().values()) {
					stream.writeObject(information);
				}
				stream.close();
		} catch (FileNotFoundException e) {
			System.err.println(e.getMessage());
		} catch (IOException e) {
			System.err.println(e.getMessage());
		}
	}
	
	/**
	 * This function allows to put the information in a text file
	 */
	
	public void textSave(String fileName) {
		try {
			BufferedWriter writer = new BufferedWriter(new FileWriter(fileName));
			for (Information information : getInformations().values()) {
				writer.write(information.getName() + SEPARATOR + information.getMime() + SEPARATOR + information.getExt() + SEPARATOR + information.getSize() + SEPARATOR + information.getInfo() + "\n");
				writer.newLine();
			}
			writer.close();
		} catch (IOException e) {
			System.err.println(e.getMessage());
		}
	}
	
	public void removeAll() {
		getInformations().clear();
	}

}
