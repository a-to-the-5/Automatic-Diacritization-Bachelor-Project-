package eg.edu.guc.met.bachelor.autotashkeel.commandline;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

import eg.edu.guc.met.bachelor.autotashkeel.statistics.Compare;
import eg.edu.guc.met.bachelor.autotashkeel.statistics.CompareDir;
import eg.edu.guc.met.bachelor.autotashkeel.statistics.CompareFile;
import eg.edu.guc.met.bachelor.autotashkeel.statistics.DirectoriesNotIdenticalException;

public class AT_Compare 
{
	private static String helpMsg = "help:\ncommand fromat: \n" +
			"AT_Compare [-f] path1 path2 [extention]\nUse -f if the arguments are files (not directories)." +
			"\n\"extention\" will only compare files with the specified extention. It is ignored if " +
			"-f is used.";
	
	public static void main(String[] args) throws Exception
	{
		if(args.length==1 && args[0].equals("-help"))
		{
			displayHelp();
			return;
		}
		
		File first = null, second = null;
		String report = null, fileType = null;
		boolean deep = false, xml = false;
		
		for(int i = 0; i< args.length; i++)
		{
			if(args[i].equals("-first"))
				first = new File(args[++i]);
			else if(args[i].equals("-second"))
				second = new File(args[++i]);
			else if(args[i].equals("-report"))
				report = args[++i];
			else if(args[i].equals("-ext"))
				fileType = args[++i];
			else if(args[i].equals("-xml"))
				xml = true;
			else if(args[i].equals("-deep"))
				deep = true;
		}
		boolean failed = false;
		if(first==null || second==null)
		{
			failed = true;
			System.err.println("Error. One or both files not found.");
		}
		if(report==null)
		{
			failed = true;
			System.err.println("Error. Report path missing.");
		}
		
		Compare c = null;
		boolean done = false;
		try{
			if(!failed){
				if(!first.exists())
				{
					failed=true;
					System.err.println("Error. First file/folder not found.");
				}
				else if(!second.exists())
				{
					failed=true;
					System.err.println("Error. Second file/folder not found.");
				}
				else if(first.isDirectory() && second.isDirectory())
					c= new CompareDir(first.getAbsolutePath(), second.getAbsolutePath()
							, report, fileType, deep, xml);
				else if(!first.isDirectory() && !second.isDirectory())
					c= new CompareFile(first.getAbsolutePath(), second.getAbsolutePath()
							, report, xml);
				else failed = true;
				if(!failed)
				{
					c.doCompare();
					c.initReport();
				}
				done = true;
			}
		}catch (FileNotFoundException e) {
			if(e.getMessage()!=null)
				System.err.println(e.getMessage());
			else
				System.err.println("The file(s)/directorie(s) you have entered were not found." +
						"Please make sure you type the paths correctly");
		}catch (UnsupportedEncodingException e) {
			System.err.println("Error. Please make sure the files are encoded in UTF-16");
		}catch (IOException e) {
			System.err.println("Error reading/writing. Make sure you have the right permissions.");
		}catch (DirectoriesNotIdenticalException e) {
			System.err.println("Error. Cannot compare since the two directories are not identical");
		}finally {
			if(!done || failed)
				System.err.println("Type: \"AT_Compare -help\" for help");
			else
				System.out.println("Comparison finished successfully.");
		}
	}
	
	private static void displayHelp()
	{
		System.out.println(helpMsg);
	}
}
