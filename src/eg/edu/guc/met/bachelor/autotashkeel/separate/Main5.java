package eg.edu.guc.met.bachelor.autotashkeel.separate;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;

import eg.edu.guc.met.bachelor.autotashkeel.utils.Utils;

public class Main5 {
	public static void main(String[] args) throws Exception{
		File f = new File("C:\\Users\\Abdurrahman\\My Bachelor\\cd\\NMWRC7AR\\DATA\\SETS\\ComparisonSet");
		
//		BufferedWriter bw = Utils.writeFile("lines.txt", "utf-16");
//		for(int i =0; i<24; i++)
//		{
//			bw.write((char)(i+'a')+"\r\r\n");
//		}
//		bw.close();
		for(File sub:f.listFiles())
		{
			BufferedReader br = Utils.readFile(sub.getAbsolutePath(), "utf-16");
			StringBuilder sb = new StringBuilder();
			String s;
			while((s=br.readLine())!=null)
			{
				sb.append(s).append("\r\n");
				br.readLine();
			}
			br.close();
			BufferedWriter bw = Utils.writeFile(sub.getAbsolutePath(), "utf-16");
			bw.write(sb.toString());
			bw.close();
		}
	}	
}