package eg.edu.guc.met.bachelor.autotashkeel.separate;

import java.io.BufferedReader;

import eg.edu.guc.met.bachelor.autotashkeel.utils.Utils;

public class Main3 {
	public static void main(String[] args) throws Exception 
	{
		BufferedReader br1 = Utils.readFile("C:\\Users\\Abdurrahman\\My Bachelor\\cd\\" +
				"NMWRC7AR\\DATA\\TextWithArabicDiacritization\\" +
				"ArabicDictionaries_WithArabicDiacritization_01_RegularRootEntries.txt", "utf-16");
		BufferedReader br2 = Utils.readFile("C:\\Users\\Abdurrahman\\My Bachelor\\cd\\" +
				"NMWRC7AR\\DATA\\RawText\\" +
				"ArabicDictionaries_Raw_01_RegularRootEntries.txt", "utf-16");
		int twad, rt;
		while((twad=br1.read())!=-1 && (rt=br2.read())!=-1 )
		{
			System.out.println("twad: "+twad+ " : "+(char)twad);
			System.out.println("rt: "+rt+ " : "+(char)rt);
		}
	}
}
