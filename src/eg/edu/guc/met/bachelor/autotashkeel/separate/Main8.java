package eg.edu.guc.met.bachelor.autotashkeel.separate;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.util.Vector;

import eg.edu.guc.met.bachelor.autotashkeel.utils.Utils;

public class Main8 
{
	public static void main(String[] args) throws Exception 
	{
		BufferedReader br = Utils.readFile("txt.txt", "utf-8");
		BufferedWriter bw = Utils.writeFile("latex.txt", "utf-16");
		Vector<String> list = new Vector<String>();
		//br.readLine();
		String s;
		int i = 0;
		while((s=br.readLine())!=null)
		{
			list.add(s);
			i++;
		}
		int j, k;
		for(j = 0, k = list.size()/2+1; j<=list.size()/2+1 && k<list.size(); j++, k++)
		{
			System.out.println(j+", "+k+" : "+list.get(j).substring(0, list.get(j).length()-2));
			bw.write(list.get(j).substring(0, list.get(j).length()-2)+"&");
			bw.write(((k>36)?"\\fullvocalize":"")+list.get(k)+((k>36)?"\\vocalize":"")+"\r\n");
		}
		bw.write(list.get(j).substring(0, list.get(j).length()-2)+"&&&\\\\");
		System.out.println(i);
		bw.close();
	}
}
