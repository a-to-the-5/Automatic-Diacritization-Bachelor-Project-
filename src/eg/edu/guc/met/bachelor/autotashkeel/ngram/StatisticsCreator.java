package eg.edu.guc.met.bachelor.autotashkeel.ngram;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Hashtable;
import java.util.List;
import java.util.Vector;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import eg.edu.guc.met.bachelor.autotashkeel.utils.Utils;

public class StatisticsCreator 
{
	private String regex;
	private Hashtable<String, Integer> stats;
	private BufferedWriter bw;
	private BufferedReader br;
	private List<String> ngram;
	private int n;
	
	
	public StatisticsCreator(int n, String regex,
			BufferedWriter report, String file) throws UnsupportedEncodingException, FileNotFoundException 
	{
		this.n = n;
		stats = new Hashtable<String, Integer>();
		ngram = new Vector<String>(n);
		this.regex = regex;
		br = Utils.readFile(file, "utf-16");
		bw = report;
	}
	
	public void doStatistics() throws IOException
	{
		String s;
		while((s=br.readLine())!=null)
		{
			Pattern p = Pattern.compile(regex);
			Matcher m = p.matcher(s);
			while(m.find())
			{
				String temp = "";
				if(ngram.size()==n)
					ngram.remove(0);
				ngram.add(m.group());
				temp+=(ngram.get(0));
				for(int i = 1; i<ngram.size(); i++)
				{
					temp+=("<>"+ngram.get(i));
					Integer num = stats.get(temp);
					if(num!=null)
					{
						stats.remove(temp);
						num = new Integer(num.intValue()+1);
					}else num = new Integer(1);
					stats.put(temp, num);
				}
				 
			}
		}
	}
	
	public void outputStatistics() throws IOException
	{
		for(String s:stats.keySet())
		{
			String[] words = s.split("<>");
			if(words.length==n)
				bw.write(s+"\t"+stats.get(s)+"\r\n");
		}
	}
	
}
