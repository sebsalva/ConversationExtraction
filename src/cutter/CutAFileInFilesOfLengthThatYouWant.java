package cutter;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.regex.Matcher;

import com.google.common.io.Files;

public class CutAFileInFilesOfLengthThatYouWant {
	public static void cutAFile(File file , String numberOfSequencesString, String numberOfLinesInEacheSequenceString) {
		int numberOfSequences;
		int numberOfLinesInEacheSequence;
		try {
			BufferedReader br = new BufferedReader (new FileReader(file));
			String line = br.readLine()+"\n";
			
			
			try {
				numberOfSequences = Integer.parseInt(numberOfSequencesString);
			}
			catch (NumberFormatException e)
			{
				numberOfSequences = 0;
			}
			
			try {
				numberOfLinesInEacheSequence = Integer.parseInt(numberOfLinesInEacheSequenceString);
			}
			catch (NumberFormatException e)
			{
				numberOfLinesInEacheSequence = 0;
			}
			String ToCreate=file.getPath();
			ToCreate=ToCreate.substring(0, ToCreate.lastIndexOf('.'));
			File dossier=new File(ToCreate);
			if ((!dossier.exists()) || (!dossier.isDirectory())){
				dossier.mkdir();
			}
			for (int j=0; j<numberOfSequences;j++) {
				File fileToWrite=new File(ToCreate + "/CutFromLine" +j*numberOfLinesInEacheSequence + "ToLine" + (j+1)*numberOfLinesInEacheSequence+".txt");
				System.out.println("la");
				FileWriter fw = new FileWriter(fileToWrite,true);
				for (int k=0;k<numberOfLinesInEacheSequence;k++) {
					fw.write(line);
					line = br.readLine()+"\n";
				}
				fw.close();
			}
		}
		 catch (IOException ea) {
			System.err.println("IOException");
			ea.printStackTrace();
		}
	}
}