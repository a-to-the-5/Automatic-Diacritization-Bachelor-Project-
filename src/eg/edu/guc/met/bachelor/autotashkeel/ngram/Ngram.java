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
import eg.edu.guc.met.bachelor.autotashkeel.converters.StringConverter;
import eg.edu.guc.met.bachelor.autotashkeel.utils.Diacritics;
import eg.edu.guc.met.bachelor.autotashkeel.utils.Utils;

public class Ngram 
{
	Hashtable<String, Hashtable<String, Integer>> stats = 
		new Hashtable<String, Hashtable<String,Integer>>();
	
	Hashtable<String, Hashtable<String, Integer[]>> uniStats = 
		new Hashtable<String, Hashtable<String,Integer[]>>();
	
	Vector<String> lastNTokens = new Vector<String>();
	
	Hashtable<String, Integer> words = new Hashtable<String, Integer>();
	
	Hashtable<Integer, String> wordsList = new Hashtable<Integer, String>();
	
	Hashtable<String[], Integer> tashkeel = new Hashtable<String[], Integer>();
	
	Hashtable<Integer, String[]> tashkeelList = new Hashtable<Integer, String[]>();
		
	int n;
	int outOfVocabulary = 0;
	int total = 0;
	int corpusTotal = 0;
	List<String> outOfVocabWords = new Vector<String>();
	
//	public static void main(String[] args) throws IOException, InterruptedException, UnsupportedConventionException
//	{
//		Ngram n = new Ngram(6);
//		FileConverter fc = new FileConverter("ar.txt","ba.txt", "unicode", "buckwalter");
//		fc.convert();
//		n.createArabicStatistics("ba.txt");
//		n.readStats();
//		BufferedWriter bw = Utils.writeFile("seesmthng.txt", "utf-16");
//		for(String s: n.stats.keySet())
//		{
//			bw.write(s+"\r\n");
//			for(String ss:n.stats.get(s).keySet())
//			{
//				bw.write("\t"+ss+" : "+n.stats.get(s).get(ss)+"\r\n");
//			}
//		}
//		
//		for(String s: n.uniStats.keySet())
//		{
//			bw.write(s+"\r\n");
//			for(String ss:n.uniStats.get(s).keySet())
//			{
//				bw.write("\t"+ss+" : "+sum(n.uniStats.get(s).get(ss))+"\r\n");
//			}
//		}
//		bw.close();
//	}
	
	public static void main(String[] args) throws Exception
	{
//		BufferedWriter bw = Utils.writeFile("test2.txt", "utf-16");
//		String[] s = new Ngram(3).tashkeel(StringConverter.getConverter().convertString("taDoTarimu", "buckwalter", "unicode").trim());
//		for(String ss:s)
//			bw.write(ss+"\r\n");
//		bw.write(s.length+"\r\n");
//		bw.close();
		Ngram u = new Ngram(2);
		u.createArabicStatistics("C:\\Users\\Abdurrahman\\" +
				"My Bachelor\\cd\\NMWRC7AR\\DATA\\SETS\\TrainingSet");
		u.readStats();
		u.diacritizeCorpus("C:\\Users\\Abdurrahman\\My Bachelor\\cd\\NMWRC7AR\\DATA\\SETS\\NewTestSet",
				"C:\\Users\\Abdurrahman\\My Bachelor\\cd\\NMWRC7AR\\DATA\\AutoDiac");
		//u.printReport("rep.txt");
		AT_Compare.main(new String[]{
				"-first",
				"C:\\Users\\Abdurrahman\\My Bachelor\\cd\\NMWRC7AR\\DATA\\SETS\\ComparisonSet",
				"-second",
				"C:\\Users\\Abdurrahman\\My Bachelor\\cd\\NMWRC7AR\\DATA" +
				"\\AutoDiac", "-report","temp3.txt"});
		System.out.println("OOV: "+u.outOfVocabulary+"/"+u.total);
	}
	
	static int sum(Integer[] a)
	{
		int sum = 0;
		for(Integer i:a)
			if(i!=null)
				sum+=i;
		return sum;
	}
	
	public Ngram(int n) {
		this.n = n;
	}
	
	private void createFreq() throws IOException
	{
		BufferedWriter bw = Utils.writeFile("freq.txt", "utf-8");
		for(int i = 0; i< n; i++)
			bw.write(i+" ");
		bw.write("\r\n");
		for(int i = 0; i< n; i++)
			bw.write(i+" \r\n");
		bw.close();
	}
	
	private void createStatistics(String path) throws IOException, InterruptedException
	{
		createFreq();
		File f = new File("out.txt");
		if(f.exists())
			f.delete();
		Process p = Runtime.getRuntime().exec("perl count.pl" +
				" --ngram " + n +
				" --token regex.txt " +
				//((n==1)?"":"--set_freq_combo " + "freq.txt ")+
				" out.txt \"" + path + "\""
				, null, null);
		p.waitFor();
	}
	
	public void createArabicStatistics(String path) throws IOException, InterruptedException
	{
		createStatistics(path);
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
	
	public void readStats() throws NumberFormatException, IOException
	{
		BufferedReader  br = Utils.readFile("ar_out.txt", "utf-16");
		String s;
		corpusTotal = Integer.parseInt(br.readLine().trim());
		int lines = 0;
		while((s=br.readLine())!=null)
		{
			lines++;
			if(lines%100==0)
				System.out.println(lines);
			if(lines>165000)
				break;
			String[] ss = s.split("(<>)|\\s");
			StringBuilder sb = new StringBuilder();
			int i;
			for(i = 0; i<n; i++)
			{
				sb.append(ss[i]+" ");
			}
			insert(sb.toString().trim(), Integer.parseInt(ss[i].trim()));
			i++;
			for(int j = 0; i<ss.length; j++, i++)
			{
				Hashtable<String,Integer[]> temp = uniStats.get(Diacritics.getDiacritics().removeDiacritics(ss[j]));
				if(temp==null)
					uniStats.put(Diacritics.getDiacritics().removeDiacritics(ss[j]), temp = new Hashtable<String, Integer[]>());
				
				Integer[] nums = temp.get(ss[j]);
				if(nums == null)
					temp.put(ss[j], nums=new Integer[n]);
				nums[j] = Integer.parseInt(ss[i].trim());
				
				//insert(ss[j].trim(), Integer.parseInt(ss[i].trim()));
			}
		}
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
		Pattern p = Pattern.compile("[\u0621-\u063A\u0640-\u0652\u0670\u0671]+");
		while((s=source.readLine())!=null)
		{
			Matcher mat = p.matcher(s);
			String temp1 = "";
			int startIndx = 0;//, endIndx = 0;
			while(mat.find())
			{
				String group = mat.group();
				lastNTokens.add(Diacritics.getDiacritics().removeDiacritics(group));
				if(lastNTokens.size()>n)
					lastNTokens.removeElementAt(0);
				temp1+= (s.substring(startIndx, mat.start()) + get());
				startIndx = mat.end();
			}
			temp1+=s.substring(startIndx);
			dest.write(temp1+"\r\n");
		}
		dest.close();
	}
	
	String get()
	{
		total++;
		StringBuilder query = new StringBuilder();
		for(String s:lastNTokens)
			query.append(s).append(' ');
		Hashtable<String, Integer> map = 
			stats.get(Diacritics.getDiacritics().removeDiacritics(query.toString().trim()));
		if(map==null||map.isEmpty())
		{
			Hashtable<String, Integer[]> map2 = 
				uniStats.get(lastNTokens.get(lastNTokens.size()-1));
			if(map2==null||map2.isEmpty())
			{
				outOfVocabulary++;
				//outOfVocabWords.add(lastNTokens.get(lastNTokens.size()-1));
				return oovDiacritize(lastNTokens.get(lastNTokens.size()-1));
			}
			int max = 0;
			String maxString = lastNTokens.get(lastNTokens.size()-1);
			for(String key:map2.keySet())
			{
				int temp = sum(map2.get(key));
				if(temp>max)
				{
					max = temp;
					maxString = key;
				}
			}
			return maxString;
		}
		int max = 0;
		String maxString = query.toString();
		for(String key:map.keySet())
		{
			int temp = map.get(key);
			if(temp>max)
			{
				max = temp;
				maxString = key;
			}
		}
		return maxString.split(" ")[n-1];
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
			stats.put(Diacritics.getDiacritics().removeDiacritics(word), 
					map = new Hashtable<String, Integer>());
		}
		map.put(word, freq);			
	}
	
	String[] tashkeel(String word)
	{
		return word.split("[^\u064B-\u0652]");
	}
}
