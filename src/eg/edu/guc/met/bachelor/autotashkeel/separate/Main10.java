package eg.edu.guc.met.bachelor.autotashkeel.separate;

import java.io.BufferedReader;
import java.io.File;
import java.util.HashSet;

import eg.edu.guc.met.bachelor.autotashkeel.utils.Diacritics;
import eg.edu.guc.met.bachelor.autotashkeel.utils.Utils;

public class Main10 
{
	public static void main(String[] args) throws Exception 
	{
		File f = new File("G:\\Users\\Abdurrahman\\Desktop\\" +
			"Nemalr_cd\\NMWRC7AR\\DATA\\SETS\\test");
		HashSet<String> set = new HashSet<String>();
		int count = 0;
		for(File sub:f.listFiles())
		{
			BufferedReader br = Utils.readFile(sub.getAbsolutePath(), "utf-16");
			String s;
			while((s=br.readLine())!=null)
			{
				String[] ss = s.split("\\s");
				for(String sss: ss)
				{
					//sss = Diacritics.getDiacritics().removeDiacritics(sss);
					if(sss.matches("[\u0621-\u0671]+"))
					{
						count++;
						set.add(sss);
					}
				}
			}
		}
		System.out.println(set.size());
		System.out.println(count);
	}
}
