package eg.edu.guc.met.bachelor.autotashkeel.commandline;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import eg.edu.guc.met.bachelor.autotashkeel.converters.Converter;
import eg.edu.guc.met.bachelor.autotashkeel.converters.DirectoryConverter;
import eg.edu.guc.met.bachelor.autotashkeel.converters.FileConverter;
import eg.edu.guc.met.bachelor.autotashkeel.converters.UnsupportedConventionException;

public class AT_Convert 
{
	private static String helpMsg = "Usage help:\n" +
			"AT_Convert -source \"source path\" -dest \"destination path\"" +
			" -sconv \"source convention\" -dconv \"destination convention\"" +
			" -ext extention [-deep]";
	
	public static void main(String[] args) 
	{
		if(args.length==1 && args[0].equals("-help"))
		{
			displayHelp();
			return;
		}
		File source = null, destination = null;
		String sConv =null, dConv = null, fileType =null;
		boolean deep = false;
		for(int i = 0; i< args.length; i++)
		{
			if(args[i].equals("-source"))
				source = new File(args[++i]);
			else if(args[i].equals("-dest"))
				destination = new File(args[++i]);
			else if(args[i].equals("-sconv"))
				sConv = args[++i];
			else if(args[i].equals("-dconv"))
				dConv = args[++i];
			else if(args[i].equals("-ext"))
				fileType = args[++i];
			else if(args[i].equals("-deep"))
				deep = true;
		}
		boolean failed = false;
		if(source==null)
		{
			failed = true;
			System.err.println("Error. Source file/directory not specified.");
		}
		if(destination==null)
		{
			failed = true;
			System.err.println("Error. Destination file/directory not specified.");
		}
		if(sConv==null)
		{
			failed = true;
			System.err.println("Error. Source convention not specified.");
		}
		if(dConv==null)
		{
			failed = true;
			System.err.println("Error. Destination convention not specified.");
		}
		
		Converter c = null;
		boolean done = false;
		try{
			if(!failed)
			{
				if(source.isDirectory()&&(!destination.exists() || destination.isDirectory()))
					c = new DirectoryConverter(source.getAbsolutePath(),
							destination.getAbsolutePath(), sConv, dConv, fileType,
							deep);
				else if(!source.isDirectory()&&(!destination.exists() || !destination.isDirectory()))
					c = new FileConverter(source.getAbsolutePath(),
							destination.getAbsolutePath(), sConv, dConv);
				
				c.convert();
				done = true;
			}
		}catch(FileNotFoundException e){
			System.err.println("Error. File could not be found.");
		}catch(IOException e){
			System.err.println("Error. Cannot perform the convertion");
		}catch (UnsupportedConventionException e) {
			System.err.println("Error. Please make sure you have typed a supported convention");
		}finally{
			if(done && !failed) System.out.println("Convertion completed successfully.");
			else System.err.println("Type: \"AT_Convert -help\" for help");
		}
	}
	
	private static void displayHelp()
	{
		System.out.println(helpMsg);
	}
}
