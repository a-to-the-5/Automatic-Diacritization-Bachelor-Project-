package eg.edu.guc.met.bachelor.autotashkeel.separate;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import eg.edu.guc.met.bachelor.autotashkeel.utils.Utils;

public class Main6 {
	public static void main(String[] args) throws Exception 
	{
		File f = new File("C:\\Users\\Abdurrahman\\My Bachelor\\cd\\NMWRC7AR\\DATA\\" +
				"TextWithArabicDiacritization");
		File f2 = new File("C:\\Users\\Abdurrahman\\My Bachelor\\cd\\NMWRC7AR\\DATA\\" +
				"AutoRawText");
		f2.mkdir();
		
		Pattern p = Pattern.compile(
				"[\u0621-\u063A\u0640-\u0652\u0670\u0671@×\\^~]*" +
				"[\u0621-\u063A\u0640-\u0652\u0670\u0671]+" +
				"[\u0621-\u063A\u0640-\u0652\u0670\u0671@×\\^~]*");
		for(File sub:f.listFiles())
		{
			BufferedReader br = Utils.readFile(sub.getAbsolutePath(), "utf-16");
			BufferedWriter bw = Utils.writeFile(f2.getAbsolutePath()+
					File.separator+sub.getName().replaceAll("WithArabicDiacritization", "AutoRaw"), "utf-16");
			String s;
			while((s=br.readLine())!=null)
			{
				StringBuilder sb = new StringBuilder();
				Matcher m = p.matcher(s);
				int start = 0;
				while(m.find())
				{
					sb.append(s.substring(start, m.start()));
					sb.append(m.group().replaceAll("[\u064B-\u0652@×\\^~]", ""));
					start = m.end();
				}
				sb.append(s.substring(start)).append("\r\n");
				bw.write(sb.toString());
			}
			bw.close();
			br.close();
		}
	}
}
