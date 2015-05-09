package eg.edu.guc.met.bachelor.autotashkeel.statistics;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import java.util.List;
import java.util.Vector;

import eg.edu.guc.met.bachelor.autotashkeel.utils.Utils;

public class CompareDir implements Compare
{
	private List<Compare> files = new Vector<Compare>();
	private File firstDir;
	private File secondDir;
	private BufferedWriter bw;
	private boolean deep;
	private boolean xml;
	//private String extention;
	
	public CompareDir(String firstDirPath, String secondDirPath, String reportPath, String ext,
			boolean deep, boolean xml)
	throws UnsupportedEncodingException, FileNotFoundException, FileIsNotDirectoryException, DirectoriesNotIdenticalException 
	{
		this.deep = deep;
		this.xml = xml;
		firstDir = new File(firstDirPath);
		secondDir = new File(secondDirPath);
		if(!firstDir.exists())
			throw new FileNotFoundException
			("The directory "+firstDirPath+" does not exist.");
		if(!secondDir.exists())
			throw new FileNotFoundException
			("The directory "+secondDirPath+" does not exist.");
		if(!firstDir.isDirectory())
			throw new FileIsNotDirectoryException
			("The file "+firstDirPath+" is not a directory");
		if(!secondDir.isDirectory())
			throw new FileIsNotDirectoryException
			("The file "+secondDirPath+" is not a directory");
		if(reportPath!=null)
			bw = Utils.writeFile(reportPath, "utf-16");
		
		String[] subFiles1 = firstDir.list();
		String[] subFiles2 = secondDir.list();
		if(subFiles1.length!=subFiles2.length)
			throw new DirectoriesNotIdenticalException();
		Arrays.sort(subFiles1);
		Arrays.sort(subFiles2);
		
		for(int i = 0; i<subFiles1.length; i++)
		{
			String subFile1 = firstDir.getAbsolutePath()+"\\"+subFiles1[i];
			String subFile2 = secondDir.getAbsolutePath()+"\\"+subFiles2[i];
			File f1 = new File(subFile1);
			File f2 = new File(subFile2);
			if(f1.isDirectory()&&f2.isDirectory())
			{
				if(deep)
					files.add(new CompareDir(subFile1, subFile2, null, ext, deep, xml));
			}
			else if(!f1.isDirectory() && !f2.isDirectory())
			{
				if(ext==null||f1.getName().matches(".*\\."+ext))
					files.add(new CompareFile(subFile1, subFile2, null, xml));
			}
			else if(deep)
				throw new DirectoriesNotIdenticalException();
		}
	}
	
	public void doCompare() throws IOException
	{
		for(Compare c:files)
			c.doCompare();
	}
	
	public void initReport() throws IOException
	{
		bw.write("Comparison Report.\r\n");
		writeSummary(bw);
		bw.close();
	}
	
	public void writeSummary(BufferedWriter bw) throws IOException
	{
		bw.write("$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$\r\n");
		bw.write("$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$\r\n");
		bw.write("$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$\r\n");
		bw.write("Directories: "+firstDir.getName()+" : "+secondDir.getName()+"\r\n");
		bw.write("Word Count: "+getWordCount()+"\r\n");
		bw.write("Word Error Rate: "+getWordErrorRate()+"\r\n");
		bw.write("Mismatching Word count: "+getMismachingWords()+"\r\n");
		bw.write("Letter Count: "+getLetterCount()+"\r\n");
		bw.write("Diacritic Error Rate: "+getLetterErrorRate()+"\r\n\r\n");
		bw.write("Error Summary:\r\n");
		for(Compare c:files)
			c.writeSummary(bw);
		bw.write("$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$\r\n");
		bw.write("$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$\r\n");
		bw.write("$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$\r\n");
	}

	@Override
	public int getIncorrectLetters() {
		int total = 0;
		for(Compare c: files)
			total+=c.getIncorrectLetters();
		return total;
	}

	@Override
	public int getIncorrectWords() {
		int total = 0;
		for(Compare c: files)
			total+=c.getIncorrectWords();
		return total;
	}

	@Override
	public int getLetterCount() {
		int total = 0;
		for(Compare c: files)
			total+=c.getLetterCount();
		return total;
	}

	@Override
	public double getLetterErrorRate() {
		return getIncorrectLetters()/(getLetterCount()*1.0);
	}

	@Override
	public int getWordCount() {
		int total = 0;
		for(Compare c: files)
			total+=c.getWordCount();
		return total;
	}

	@Override
	public double getWordErrorRate() {
		return getIncorrectWords()/(getWordCount()*1.0);
	}

	@Override
	public int getMismachingWords() {
		int total = 0;
		for(Compare c: files)
			total+=c.getMismachingWords();
		return total;
	}
}
