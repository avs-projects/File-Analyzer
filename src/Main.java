
import java.io.File;
import java.io.IOException;

public class Main {
	
	 public static void main(String[] args) throws IOException {
		try {
			/*
			 * If a path is indicated, the analysis is launched
			 */
			if (new File(args[1]).isDirectory()||new File(args[1]).isFile()) {
				AnalyseSaveInterface explore = new AnalyseSave();
				if (args.length == 2 || args.length == 4) {
					/*
					 * If the first argument is -f, we launch the analysis of the file
					 */
					if (args[0].equals("-f")) {
						explore.file(args[1]);
						System.out.println(explore.toString()); 
						try {
							/*
							 * If the third argument is -s, save the analysis
							 */
							if(args[2].equals("-s") && args[3]!="") {
								explore.textSave(args[3]+".txt");
								explore.serializationSave(args[3]+".ser");
							}
						} catch (ArrayIndexOutOfBoundsException e)  {}
					}
					/*
					 * If the first argument is -d, we launch the analysis of the file
					 */
					if (args[0].equals("-d")) {
						explore.list(args[1]);
						System.out.println(explore.toString()); 
						try {
							if(args[2].equals("-s") && args[3]!="") {
								explore.textSave(args[3]+".txt");
								explore.serializationSave(args[3]+".ser");
							}
						} catch (ArrayIndexOutOfBoundsException e) {}
					}
				}
			}
		}
			catch(ArrayIndexOutOfBoundsException e) {
				System.err.println("Wrong parameter\n-d for a directory, -f for a file or put a path");
		}
	}
}