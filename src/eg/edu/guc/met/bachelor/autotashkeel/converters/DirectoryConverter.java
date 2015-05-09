package eg.edu.guc.met.bachelor.autotashkeel.converters;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

public class DirectoryConverter implements Converter 
{
	private String sourceDirectory;
	private String destinationDirectory;
	private String sourceConvention;
	private String destinationConvention;
	private String extention;
	private boolean deep;
	
	
	public DirectoryConverter(String sourceDirectory, String destinationDirectory,
			String sourceConvention, String destinationConvention, String extention, 
			boolean deep)
	{
		this.sourceDirectory = sourceDirectory;
		this.sourceConvention = sourceConvention;
		this.destinationDirectory = destinationDirectory;
		this.destinationConvention = destinationConvention;
		this.extention = extention;
		this.deep = deep;
	}
	
	public void convert() throws IOException, UnsupportedConventionException
	{
		File source = new File(sourceDirectory);
		File destination = new File(destinationDirectory);
		
		if(!source.exists())
			throw new FileNotFoundException();
		if(!destination.exists())
			destination.mkdir();
		String[] subFiles = source.list();
		for(String file: subFiles)
		{
			File f = new File(sourceDirectory+File.separator+file);
			if(f.isDirectory() && deep)
				new DirectoryConverter(sourceDirectory+File.separator+file,
						destinationDirectory+File.separator+file, 
						sourceConvention, destinationConvention, extention,
						deep).convert();
			else if(!f.isDirectory() && (extention==null || f.getName().matches(".*\\."+extention)))
			{
				new FileConverter(sourceDirectory+File.separator+file,
						destinationDirectory+File.separator+file, 
						sourceConvention, destinationConvention).convert();
			}
		}
	}
}
