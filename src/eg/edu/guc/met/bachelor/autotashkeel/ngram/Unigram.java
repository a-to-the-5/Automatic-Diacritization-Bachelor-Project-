package eg.edu.guc.met.bachelor.autotashkeel.ngram;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.util.Hashtable;
import java.util.List;
import java.util.Vector;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import eg.edu.guc.met.bachelor.autotashkeel.commandline.AT_Compare;
import eg.edu.guc.met.bachelor.autotashkeel.converters.ConventionImpl;
import eg.edu.guc.met.bachelor.autotashkeel.utils.Diacritics;
import eg.edu.guc.met.bachelor.autotashkeel.utils.Utils;

public class Unigram 
{
	Hashtable<String, Hashtable<String, Integer>> stats = 
		new Hashtable<String, Hashtable<String,Integer>>();
	int outOfVocabulary = 0;
	int total = 0;
	int corpusTotal = 0;
	List<String> outOfVocabWords = new Vector<String>();
	
	
	public void readStats() throws NumberFormatException, IOException
	{
		BufferedReader  br = Utils.readFile("ar_out.txt", "utf-16");
		String s;
		corpusTotal = Integer.parseInt(br.readLine().trim());
		while((s=br.readLine())!=null)
		{
			String[] ss = s.split("<>"); 
			insert(ss[0].trim(), Integer.parseInt(ss[1].trim()));
		}
	}
	
	public void diacritizeFile(String source, String dest) throws IOException
	{
		BufferedReader br = Utils.readFile(source, "utf-16");
		new File(dest).createNewFile();
		BufferedWriter bw = Utils.writeFile(dest, "utf-16");
		diacritize(br, bw);
	}
	
	public void diacritizeCorpus(String source, String dest) throws IOException
	{
		File s = new File(source);
		for(String fileName: s.list())
		{
			BufferedReader br = Utils.readFile(source+File.separator+fileName, "utf-16");
			new File(dest+File.separator+fileName).createNewFile();
			BufferedWriter bw = Utils.writeFile(dest+File.separator+fileName, "utf-16");
			diacritize(br, bw);
		}
	}
	
	public void diacritize(BufferedReader source, BufferedWriter dest) throws IOException
	{
		String s;
		while((s=source.readLine())!=null)
		{
			Pattern p = Pattern.compile("[\u0621-\u063A\u0640-\u0652\u0670\u0671]+");
			Matcher mat = p.matcher(s);
			String temp1 = "";
			int startIndx = 0;//, endIndx = 0;
			while(mat.find())
			{
				String group = mat.group();
				temp1+= (s.substring(startIndx, mat.start()) + get(Diacritics.getDiacritics().removeDiacritics(group)));
				startIndx = mat.end();
			}
			temp1+=s.substring(startIndx);
			dest.write(temp1+"\r\n");
		}
		dest.close();
	}
	
	public void createArabicStatistics(int n, String path) throws IOException, InterruptedException
	{
		createStatistics(n, path);
		ConventionImpl c = new ConventionImpl(Utils.readFile("ConversionTables\\" +
				"Buckwalter.txt", "utf-16"));
		BufferedReader br = Utils.readFile("out.txt", "utf-8");
		BufferedWriter bw = Utils.writeFile("ar_out.txt", "utf-16");
		Pattern p = Pattern.compile("<>");
		String s;
		while((s=br.readLine())!=null)
		{
			StringBuilder sb = new StringBuilder();
			int start = 0;
			Matcher m = p.matcher(s);
			while(m.find())
			{
				sb.append(c.getStringUnicode(s.substring(start, m.start()))).append(m.group());
				start = m.end();
			}
			sb.append(s.substring(start));
			bw.write(sb.append("\r\n").toString());
		}
		bw.close();
		br.close();
	}
	
	private void createStatistics(int n, String path) throws IOException, InterruptedException
	{
		File f = new File("out.txt");
		if(f.exists())
			f.delete();
		Process p = Runtime.getRuntime().exec("perl count.pl" +
				" --ngram " + n +
				" --token regex.txt" +
				" out.txt \"" + path + "\""
				, null, null);
		p.waitFor();
	}
	
	public String get(String word)
	{
		total++;
		Hashtable<String, Integer> map = 
			stats.get(Diacritics.getDiacritics().removeDiacritics(word));
		if(map==null||map.isEmpty())
		{
			outOfVocabulary++;
			outOfVocabWords.add(word);
			return oovDiacritize(word);
		}
		int max = 0;
		String maxString = word;
		for(String key:map.keySet())
		{
			int temp = map.get(key);
			if(temp>max)
			{
				max = temp;
				maxString = key;
			}
		}
		return maxString;
	}
	
	public String oovDiacritize(String oovWord)
	{
		oovWord = oovWord.replaceAll("[\u064B-\u0652]", "");
		StringBuilder sb = new StringBuilder();
		int i;
		for (i = 0; i < oovWord.length()-1; i++)//(char c: oovWord.toCharArray())
		{
			sb.append(oovWord.charAt(i)).append('\u064E');
		}
		if(i<oovWord.length())
			sb.append(oovWord.charAt(i)).append('\u0652');
		return sb.toString();
	}
	
	public void insert(String word, int freq)
	{
		Hashtable<String, Integer> map = 
			stats.get(Diacritics.getDiacritics().removeDiacritics(word));
		if(map == null)
		{
			stats.put(Diacritics.getDiacritics().removeDiacritics(word), map = new Hashtable<String, Integer>());
		}
		map.put(word, freq);			
	}
	
	public void printReport(String path) throws IOException
	{
		BufferedWriter bw = Utils.writeFile(path, "utf-16");
		bw.write("Out Of Vovabulary: "+outOfVocabulary+"\r\n");
		for(String s: outOfVocabWords)
			bw.write(s+"\r\n");
		bw.close();
	}
	
	public static void main(String[] args) throws Exception
	{
		Unigram u = new Unigram();
		u.createArabicStatistics(1, "C:\\Users\\Abdurrahman\\" +
				"My Bachelor\\cd\\NMWRC7AR\\DATA\\SETS\\TrainingSet");
		u.readStats();
		u.diacritizeCorpus("C:\\Users\\Abdurrahman\\My Bachelor\\cd\\NMWRC7AR\\DATA\\SETS\\NewTestSet",
				"C:\\Users\\Abdurrahman\\My Bachelor\\cd\\NMWRC7AR\\DATA\\AutoDiac");
		u.printReport("rep.txt");
		AT_Compare.main(new String[]{
				"-first",
				"C:\\Users\\Abdurrahman\\My Bachelor\\cd\\NMWRC7AR\\DATA\\SETS\\ComparisonSet",
				"-second",
				"C:\\Users\\Abdurrahman\\My Bachelor\\cd\\NMWRC7AR\\DATA" +
				"\\AutoDiac", "-report","temp3.txt"});
		System.out.println("OOV: "+u.outOfVocabulary+"/"+u.total);
	}
}
