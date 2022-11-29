
/*
 * This class uses the methods of AnalyseInterface
 */

public interface AnalyseSaveInterface extends AnalyseInterface{

	public final static String SEPARATOR = "#";

	void serializationSave(String fileName);

	void textSave(String fileName);

	void removeAll();

}