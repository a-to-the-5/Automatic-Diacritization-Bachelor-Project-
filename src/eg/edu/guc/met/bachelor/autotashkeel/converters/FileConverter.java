package eg.edu.guc.met.bachelor.autotashkeel.converters;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;


import eg.edu.guc.met.bachelor.autotashkeel.utils.Utils;

public class FileConverter implements Converter
{
	private String sourceFile;
	private String destinationFile;
	private String sourceConvention;
	private String destinationConvention;
	
	public FileConverter(String sourceFile, String destinationFile,
			String sourceConvention, String destinationConvention) 
	{
		this.sourceFile = sourceFile;
		this.destinationFile = destinationFile;
		this.sourceConvention = sourceConvention;
		this.destinationConvention = destinationConvention;
	}
	
	public void convert() throws IOException, UnsupportedConventionException
	{
		File dest = new File(destinationFile);
		if(!dest.exists())
			dest.createNewFile();
		BufferedReader br = Utils.readFile(sourceFile, "utf-16");
		BufferedWriter bw = Utils.writeFile(destinationFile, "utf-8");
		
		String s;
		while((s=br.readLine())!=null)
			bw.write(StringConverter.getConverter().convertString(
					s, sourceConvention, destinationConvention)+"\r\n");
		br.close();
		bw.close();
	}
}
