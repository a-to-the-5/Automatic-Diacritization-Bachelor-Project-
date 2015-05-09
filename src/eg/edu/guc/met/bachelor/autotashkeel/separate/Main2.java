package eg.edu.guc.met.bachelor.autotashkeel.separate;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import eg.edu.guc.met.bachelor.autotashkeel.commandline.AT_Compare;
import eg.edu.guc.met.bachelor.autotashkeel.converters.ConventionImpl;
import eg.edu.guc.met.bachelor.autotashkeel.utils.Utils;

public class Main2 
{
	public static void main(String[] args) throws Exception
	{
		ConventionImpl c = new ConventionImpl(Utils.readFile("ConversionTables\\Buckwalter.txt", "utf-16"));
		System.out.println((int)'×');
		System.out.println(c.getConventionRegex());
		BufferedReader br = Utils.readFile("" +
				"C:\\Users\\Abdurrahman\\Desktop\\Text-NSP-1.09\\Text-NSP-1.09\\bin" +
				"\\output3.txt", "utf-8");
		BufferedWriter bw = Utils.writeFile("ar_out3.txt", "utf-16");
		String s;
		while((s=br.readLine())!=null)
		{
			String[] ss = s.split("<>");
			if(ss.length>1)
				bw.write(c.getStringUnicode(ss[0])+"<>"+c.getStringUnicode(ss[1])+"\r\n");
			else
				bw.write(c.getStringUnicode(ss[0]));
		}
		bw.close();
//		BufferedReader br = Utils.readFile("" +
//				"C:\\Users\\Abdurrahman\\My Bachelor\\cd\\NMWRC7AR\\DATA\\" +
//				"TextWithArabicDiacritization\\ArabicDictionaries_" +
//				"WithArabicDiacritization_01_RegularRootEntries.txt", "utf-16");
//		//String s =(br.readLine());
//		
//		int i;
//		while((i=br.read())!=-1)
//		{
//			System.out.println(i);
//			System.out.println((char)i);
//			System.out.println(i-'\r');
//			System.out.println(i-'\n');
//		}
		
		/**File file = new File("C:\\Users\\Abdurrahman\\My Bachelor\\cd\\NMWRC7AR\\DATA\\" +
				"TextWithArabicDiacritization");
		File f = new File("C:\\Users\\Abdurrahman\\My Bachelor\\cd\\NMWRC7AR\\DATA\\" +
				"TextWithStandardArabicDiacritization2");
		for(File temp:file.listFiles())
		{
			BufferedWriter bw = Utils.writeFile(f.getAbsolutePath()+File.separator+
					temp.getName(), "utf-16");
			BufferedReader br = Utils.readFile(temp.getAbsolutePath(), "utf-16");
			String s;
			int i = 0;
			while((s=br.readLine())!=null)
			{
				bw.write(s);
				if(i%2==0)
					bw.write("\r");
				else
					bw.write("\r\n");
				i++;
			}
			bw.close();
		}
		AT_Compare.main(new String[]{
				"-first",
				"C:\\Users\\Abdurrahman\\My Bachelor\\cd\\NMWRC7AR\\DATA" +
				"\\TextWithStandardArabicDiacritization2", "-second",
				"C:\\Users\\Abdurrahman\\My Bachelor\\cd\\NMWRC7AR\\DATA" +
				"\\TextWithArabicDiacritization", "-report","temp3.txt"});
*/	}
}
