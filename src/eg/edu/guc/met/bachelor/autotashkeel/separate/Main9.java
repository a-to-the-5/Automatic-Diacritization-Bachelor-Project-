package eg.edu.guc.met.bachelor.autotashkeel.separate;

import java.io.IOException;

import eg.edu.guc.met.bachelor.autotashkeel.statistics.CompareFile;

public class Main9 {
	public static void main(String[] args) throws IOException 
	{
		CompareFile c= new CompareFile("text1.txt", "text2.txt", "textcomprep.txt", false);
		c.doCompare();
		c.initReport();
	}
}
