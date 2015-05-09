package eg.edu.guc.met.bachelor.autotashkeel.separate;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;

import eg.edu.guc.met.bachelor.autotashkeel.utils.Diacritics;
import eg.edu.guc.met.bachelor.autotashkeel.utils.Utils;

public class Main11 
{
	public static void main(String[] args) throws Exception 
	{
		File topdir = new File("G:\\Users\\Abdurrahman\\Desktop" +
				"\\Nemalr_cd\\NMWRC7AR\\DATA");
		File diac = new File(topdir.getAbsolutePath()+
				"\\TextWithArabicDiacritization");
		File dest = new File("G:\\Users\\Abdurrahman\\Desktop" +
				"\\Nemalr_cd\\NMWRC7AR\\DATA\\SETS\\newDiac");
		
		for(File f:diac.listFiles())
		{
			//StringBuilder b = new StringBuilder();
			System.out.println(f.getName());
			BufferedReader br = Utils.readFile(f.getAbsolutePath(), "utf-16");
			BufferedWriter bw = Utils.writeFile(dest.getAbsolutePath()+"\\"+f.getName(), "utf-16");
			int s;
			String temp = "";
			boolean lastArabic = false;
			int i = 0;
			while((s = (char)br.read())!= -1 && s != 65535)
			{
				//System.out.println(i++ +" : "+s);
				if(s=='\r'||s=='\n')
				{
					temp+=s;
				}
				else
				{
					if(!temp.equals(""))
					{
						bw.write('\r');
						bw.write('\n');
						temp = "";
					}
					if(!(lastArabic && Diacritics.getDiacritics().isNemlarDiacriticMark((char)s)))
						bw.write((char)s);
					if(s>='\u0621' && s<='\u0671')
						lastArabic = true;
					else lastArabic = false;
				}
			}
			//String res = b.toString();
			//res = res.replace("\r\r\n", "\r\n");
			//bw.write(res);
			bw.close();
			br.close();
			System.out.println(f.getName()+" done...");
		}
	}
}
