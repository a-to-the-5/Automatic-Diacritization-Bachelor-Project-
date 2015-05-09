package eg.edu.guc.met.bachelor.autotashkeel.commandline;

import java.io.IOException;

import eg.edu.guc.met.bachelor.autotashkeel.ngram.Unigram;

public class AT_Diacritize 
{
	private static String helpMsg;
	public static void main(String[] args) 
	{
		if(args.length==1 && args[0].equals("-help"))
		{
			displayHelp();
			return;
		}
		
		String training = null;;
		String toBeDiacritized = "";
		String destination = "";
		String report = null;
		
		for(int i = 0; i< args.length; i++)
		{
			if(args[i].equals("-training"))
				training = args[++i];
			else if(args[i].equals("-diac"))
				toBeDiacritized = args[++i];
			else if(args[i].equals("-dest"))
				destination = args[++i];
			else if(args[i].equals("-report"))
				report = args[++i];
		}
		
		Unigram u = new Unigram();
		boolean done = true;
		try
		{
			if(training!=null)
				u.createArabicStatistics(1, training);
			u.readStats();
		}catch (InterruptedException e) {
			done = false;
			System.err.println("Could not find ngram module.");
		}catch (IOException e) {
			done = false;
			System.err.println("No statistics found. Please use -training to specify a training set.");
		}
		
		try{
			u.diacritizeCorpus(toBeDiacritized, destination);
		}catch(IOException e)
		{
			done = false;
			System.err.println("Source folder not found.");
		}
		
		if(!done)
		{
			System.err.println("Could not complete diacritization");
		}else
		{
			System.out.println("Diacritization successfully completed.");
		}
		
		if(report!=null)
			try{
				u.printReport(report);
			}catch (Exception e) {
				System.err.println("Error printing report.");
			}
		
	}
	
	private static void displayHelp()
	{
		System.out.println(helpMsg);
	}
}
