package eg.edu.guc.met.bachelor.autotashkeel.statistics;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Hashtable;
import java.util.List;
import java.util.Vector;

import eg.edu.guc.met.bachelor.autotashkeel.utils.Diacritics;
import eg.edu.guc.met.bachelor.autotashkeel.utils.Utils;

public class CompareFile implements Compare 
{
	private String file1Path;
	private String file2Path;
	private BufferedWriter bw;
	private int wordCount = 0;
	private int mismatchingWords = 0;
	private int incorrectWords = 0;
	private int letterCount = 0;
	private int incorrectLetters = 0;
	private boolean xml;
	//private Hashtable<String, String> incorrectToCorrect = new Hashtable<String, String>();
	private List<String> incorrect = new Vector<String>();
	private List<String> correct = new Vector<String>();
	
	public CompareFile(String file1Path, String file2Path, String reportPath,
			boolean xml) throws UnsupportedEncodingException, FileNotFoundException
	{
		this.file1Path = file1Path;
		this.file2Path = file2Path;
		this.xml = xml;
		if(reportPath!=null)
			bw = Utils.writeFile(reportPath, "utf-16");
	}
	
//	public Hashtable<String, String> getIncorrectToCorrect() {
//		return incorrectToCorrect;
//	}

	public int getWordCount() {
		return wordCount;
	}

	public int getIncorrectWords() {
		return incorrectWords;
	}

	public int getMismachingWords(){
		return mismatchingWords;
	}
	
	public int getLetterCount() {
		return letterCount;
	}

	public int getIncorrectLetters() {
		return incorrectLetters;
	}
	
	public double getWordErrorRate()
	{
		return incorrectWords/(wordCount*1.0);
	}
	
	public double getLetterErrorRate()
	{
		return incorrectLetters/(letterCount*1.0);
	}
	
	public void doCompare() throws IOException
	{
		CompareFiles(file1Path, file2Path);
	}
	
	public void initReport() throws IOException
	{
		bw.write("Comparison Report.\r\n");
		writeSummary(bw);
		bw.close();
	}

	public void CompareFiles(String f1, String f2) 
				throws IOException
	{
		BufferedReader br1 = Utils.readFile(f1, "UTF-16");
		BufferedReader br2 = Utils.readFile(f2, "UTF-16");
		
		String s1, s2;
		while((s1=br1.readLine())!=null&&(s2=br2.readLine())!=null)
		{
			compareString(s1, s2);
		}
	}
	
	private void compareString(String s1, String s2)
	{
		String[] ar1 = s1.split("\\s");
		String[] ar2 = s2.split("\\s");
		
		mismatchingWords+=Math.abs(ar1.length-ar2.length);
		if(ar1.length!=ar2.length)
		{
			incorrect.add(s1);
			correct.add(s2);
//			incorrectToCorrect.put(s1, s2);
		}
		
		for(int i = 0; i<ar1.length&&i<ar2.length; i++)
		{
			wordCount++;
			//letterCount+=ar1[i].length();
			
			if(!ar1[i].replaceAll("["+Diacritics.getDiacritics().getMarks()+
						Diacritics.getDiacritics().getNemlarDiacriticMarks().replaceAll("\\^", "\\\\^")
						+"]", "").equals(ar2[i].replaceAll("["+Diacritics.getDiacritics().getMarks()+
						Diacritics.getDiacritics().getNemlarDiacriticMarks().replaceAll("\\^", "\\\\^")
						+"]", "")) )
			{
				mismatchingWords++;
				continue;
			}
			
			if(!ar1[i].equals(ar2[i]))
			{
				incorrectWords++;
				compareWords(ar1[i], ar2[i]);
				incorrect.add(ar1[i]);
				correct.add(ar2[i]);
//				incorrectToCorrect.put(ar1[i], ar2[i]);
			}
			else
				letterCount+=ar1[i].replaceAll("["+Diacritics.getDiacritics().getMarks()+
						Diacritics.getDiacritics().getNemlarDiacriticMarks().replaceAll("\\^", "\\\\^")
						+"]", "").length();
		}
	}
	
	private void compareWords(String word1, String word2)
	{
		boolean mismaching = false;
		int startI=0, startJ=0;
		for(int i =0, j=0; i<word1.length() && j<word2.length(); i++, j++)
		{
			while(i<word1.length()&& (Diacritics.getDiacritics().isDiacriticMark(word1.charAt(i))||
					Diacritics.getDiacritics().isNemlarDiacriticMark(word1.charAt(i))))
				i++;
			while(j<word2.length() && (Diacritics.getDiacritics().isDiacriticMark(word2.charAt(j))||
					Diacritics.getDiacritics().isNemlarDiacriticMark(word2.charAt(j))))
				j++;
			if(i==word1.length() || j==word2.length()) break;
			if(word1.charAt(i)!=word2.charAt(j) && !mismaching)
				mismaching = true;
			if(!word1.substring(startI, i).equals(word2.substring(startJ, j)))
				incorrectLetters++;
			letterCount++;
			startI=i; startJ=j;
		}
		if(!word1.substring(startI, word1.length()).equals(word2.substring(startJ, word2.length())))
			incorrectLetters++;
	}
	
	public void writeSummary(BufferedWriter bw) throws IOException
	{
		bw.write("##########################################\r\n");
		bw.write("##########################################\r\n");
		bw.write("##########################################\r\n");
		bw.write("Files: "+file1Path+" : "+file2Path+"\r\n");
		bw.write("Word Count: "+getWordCount()+"\r\n");
		bw.write("Word Error Rate: "+getWordErrorRate()+"\r\n");
		bw.write("Mismatching Word count: "+getMismachingWords()+"\r\n");
		bw.write("Letter Count: "+getLetterCount()+"\r\n");
		bw.write("Diacritic Error Rate: "+getLetterErrorRate()+"\r\n\r\n");
		bw.write("Error Summary:\r\n");
		for(int i = 0; i< incorrect.size(); i++)//String key: incorrectToCorrect.keySet())
			bw.write(incorrect.get(i)+" : "+correct.get(i)+"\r\n");
		if(incorrect.isEmpty())
			bw.write("No Errors. Files are identical.\r\n");
	}
}
