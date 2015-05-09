package eg.edu.guc.met.bachelor.autotashkeel.separate;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.StreamTokenizer;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.sun.org.apache.xerces.internal.impl.xpath.regex.Match;

import eg.edu.guc.met.bachelor.autotashkeel.commandline.AT_Compare;
import eg.edu.guc.met.bachelor.autotashkeel.commandline.AT_Convert;
import eg.edu.guc.met.bachelor.autotashkeel.converters.ConventionImpl;
import eg.edu.guc.met.bachelor.autotashkeel.converters.StringConverter;
import eg.edu.guc.met.bachelor.autotashkeel.ngram.StatisticsCreator;
import eg.edu.guc.met.bachelor.autotashkeel.statistics.CompareFile;
import eg.edu.guc.met.bachelor.autotashkeel.utils.Diacritics;
import eg.edu.guc.met.bachelor.autotashkeel.utils.Utils;

public class Main 
{
	public static void main(String[] args) throws Exception
	{
//		BufferedReader br = Utils.readFile("temp2.txt", "utf-16"); 
//		String s;
//		BufferedWriter bw = Utils.writeFile("temp3.txt", "utf-16");
//		while((s=br.readLine())!=null)
//			bw.write(s.replaceAll("["+Diacritics.getDiacritics().getMarks()+
//						Diacritics.getDiacritics().getNemlarDiacriticMarks().replaceAll("\\^", "\\\\^")
//						+"]", "")+"\r\n");
//		bw.close();
//		System.out.println("["+ ( (ConventionImpl)(StringConverter.getConverter().getConvention("buckwalter")) ).getConventionRegex()+"+]");
//		Pattern p = Pattern.compile("["+ ( (ConventionImpl)(StringConverter.getConverter().getConvention("buckwalter")) ).getConventionRegex()+"]+");
//		System.out.println(((ConventionImpl)(StringConverter.getConverter().getConvention("buckwalter"))).getConventionRegex());
//		System.out.println(p.matcher("Abp<>w").matches());
		//System.out.println("Abp><".matches("[("+((ConventionImpl)(StringConverter.getConverter().getConvention("buckwalter"))).getConventionRegex())+")+]");
		
//		BufferedReader br = Utils.readFile(
//				"C:\\Users\\Abdurrahman\\Desktop\\Text-NSP-1.09\\Text-NSP-1.09\\" +
//				"bin\\test.txt", "utf-8");
//		BufferedWriter bw = Utils.writeFile("temp3.txt", "utf-16");
//		String s;
//		while((s=br.readLine())!=null)
//		{
//			//bw.write(s+"\r\n");
//			Pattern p = Pattern.compile("[\u0621-\u063A\u0641-\u0652\u0671]+");
//			Matcher m = p.matcher(s);
//			while(m.find())
//				bw.write(m.group()+"\r\n");
//		}
//		StatisticsCreator sc = new StatisticsCreator(2,
//				"[\u0621-\u063A\u0641-\u0652\u0671]+", bw, 
//				"C:\\Users\\Abdurrahman\\My Bachelor\\cd\\NMWRC7AR\\" +
//				"DATA\\TextWithStandardArabicDiacritization\\ArabicDictionaries_Wi" +
//				"thStandardArabicDiacritization_04_RegularRootEntries.txt");
//		sc.doStatistics();
//		sc.outputStatistics();
//		bw.close();
//		br.close();
		
//		ConventionImpl ci = new ConventionImpl(Utils.readFile("ConversionTables\\" +
//				"Buckwalter.txt", "utf-16"));
//		
//		String s = "{}";
//		System.out.println(Pattern.compile(ci.getConventionRegex()).matcher(s).find());
//		System.out.println(s.matches(ci.getConventionRegex()));
//		String s = "\\\\";
//		System.out.println(s.replaceAll("\\\\", "\\\\"));
//		System.out.println(Pattern.compile("\\\\").matcher(s).replaceAll("\\\\"));
		ConventionImpl c = new ConventionImpl(Utils.readFile("ConversionTables\\Buckwalter.txt", "utf-16"));
		System.out.println(c.getConventionRegex());
		long time = System.currentTimeMillis();
		AT_Convert.main(new String[]{
				"-source",
				"C:\\Users\\Abdurrahman\\My Bachelor\\cd\\NMWRC7AR\\DATA" +
				"\\TextWithStandardArabicDiacritization", "-sconv", "unicode", "-dest",
				"C:\\Users\\Abdurrahman\\My Bachelor\\cd\\NMWRC7AR\\DATA" +
				"\\BuckwalterText", "-dconv", "buckwalter"});
		System.out.println((System.currentTimeMillis()-time)/1000.0);
		time = System.currentTimeMillis();
		AT_Convert.main(new String[]{
				"-dest",
				"C:\\Users\\Abdurrahman\\My Bachelor\\cd\\NMWRC7AR\\DATA" +
				"\\TextWithArabicDiacritization_back", "-dconv", "unicode", "-source",
				"C:\\Users\\Abdurrahman\\My Bachelor\\cd\\NMWRC7AR\\DATA" +
				"\\BuckwalterText", "-sconv", "buckwalter"});
		System.out.println((System.currentTimeMillis()-time)/1000.0);
		time = System.currentTimeMillis();
		AT_Compare.main(new String[]{
				"-first",
				"C:\\Users\\Abdurrahman\\My Bachelor\\cd\\NMWRC7AR\\DATA" +
				"\\TextWithStandardArabicDiacritization", "-second",
				"C:\\Users\\Abdurrahman\\My Bachelor\\cd\\NMWRC7AR\\DATA" +
				"\\TextWithArabicDiacritization_back", "-report","temp3.txt"});
		System.out.println((System.currentTimeMillis()-time)/1000.0);
		time = System.currentTimeMillis();
//		Pattern p = Pattern.compile("ab$");
//		Matcher m = p.matcher("abab");
//		System.out.println(m.replaceAll("m"));
		//while(m.find())
			//System.out.println(m.group());
//		File f = new File("diacritics.txt");
//		f.createNewFile();
//		
////		BufferedReader br = new BufferedReader(new InputStreamReader
////				(new FileInputStream("diacritics.txt")));
//		
//		BufferedWriter bw1 = new BufferedWriter(new OutputStreamWriter
//				(new FileOutputStream("diacritics.txt"), "utf-16"));
//		
//		bw1.write("tanween_fat7a:"+Converter.getConverter().
//				convertString("F", "Buckwalter", "unicode")+"\r\n");
//		bw1.write("tanween_damma:"+Converter.getConverter().
//				convertString("N", "Buckwalter", "unicode")+"\r\n");
//		bw1.write("tanween_kasra:"+Converter.getConverter().
//				convertString("K", "Buckwalter", "unicode")+"\r\n");
//		bw1.write("fat7a:"+Converter.getConverter().
//				convertString("a", "Buckwalter", "unicode")+"\r\n");
//		bw1.write("damma:"+Converter.getConverter().
//				convertString("u", "Buckwalter", "unicode")+"\r\n");
//		bw1.write("kasra:"+Converter.getConverter().
//				convertString("i", "Buckwalter", "unicode")+"\r\n");
//		bw1.write("shadda:"+Converter.getConverter().
//				convertString("~", "Buckwalter", "unicode")+"\r\n");
//		bw1.write("sokoon:"+Converter.getConverter().
//				convertString("o", "Buckwalter", "unicode")+"\r\n");
//		bw1.write("alif_5injareyya:"+Converter.getConverter().
//				convertString("`", "Buckwalter", "unicode")+"\r\n");
//		bw1.close();
		
//		BufferedReader br = Utils.readFile("ConversionTables\\Nemlar.txt", "utf-16");
//		BufferedWriter bw = Utils.writeFile("temp.txt", "utf-16");
//		
//		String s;
//		while((s=br.readLine())!=null)
//		{
//			for(char c: s.toCharArray())
//				bw.write(c+" : "+(int)c+"\r\n");
//		}
//		br.close();
//		bw.close();
//		Diacritics d = Diacritics.getDiacritics();
//		BufferedWriter bw = Utils.writeFile("temp.txt", "utf-16");
//		bw.write(d.getMarks());
//		bw.close();
		
//		Compare c = new Compare();
//		c.CompareFiles("ArabicDictionaries_Raw_01_RegularRootEntries.txt"
//				, "ArabicDictionaries_Raw_01_RegularRootEntries1.txt");
//		System.out.println("words: "+c.getWordCount()+"\n"+
//				"letters: "+c.getLetterCount()+"\n"+
//				"incorrect: " +c.getIncorrectWords());
//		
//		BufferedWriter bw2 = new BufferedWriter(new OutputStreamWriter
//				(new FileOutputStream("ConversionTables\\ISO-8859.txt"), "utf-16"));
//		
//		String s;
//		while((s=br.readLine())!=null)
//		{
//			String[] ss = s.split(":");
//			bw1.write(ss[0]+":"+ss[1]+"\r\n");
//			bw2.write(ss[0]+":"+ss[2]+"\r\n");
//		}
//		
//		bw1.close();
//		bw2.close();
//		br.close();
//		Pattern p = Pattern.compile("(^|b)a*$");
//		Matcher m = p.matcher("aaa");
//		System.out.println(m.find());
//		BufferedReader br = Utils.readFile("temp2.txt", "utf-16");
//		String s = br.readLine();
//		String[] ss = s.split(":");
//		for(String s1:ss)
//		System.out.println(s1.trim().indexOf(Diacritics.get(Diacritics.FATHATAYN))+" "+s1.trim().length());
		/**
		BufferedReader br = Utils.readFile("ArabicLiterature_WithArabicDiacritization_03_Essays.txt", "utf-16");
		BufferedReader con = Utils.readFile("ConversionTables\\Buckwalter.txt", "utf-16");
		BufferedWriter bw = Utils.writeFile("temp.txt", "utf-16");
		BufferedWriter bw2 = Utils.writeFile("temp2.txt", "utf-16");
		//ConventionImpl c = new ConventionImpl(con);
		Converter c = new Converter();
		String s;
		while((s=br.readLine())!=null)
		{
			bw.write(c.convertString(c.convertString(s, "Nemlar", "Buckwalter")
					, "Buckwalter", "Nemlar")+"\r\n");
		}
		br.close();
		con.close();
		bw.close();
		CompareImpl cc = new CompareImpl();
		cc.CompareFiles("ArabicLiterature_WithArabicDiacritization_03_Essays.txt", "temp.txt");
		System.out.println("words: "+cc.getWordCount()+"\n"+
				"letters: "+cc.getLetterCount()+"\n"+
				"incorrect: " +cc.getIncorrectWords());
		for(String ss: cc.getIncorrectToCorrect().keySet())
			bw2.write(ss+" : "+cc.getIncorrectToCorrect().get(ss)+"\r\n");
		bw2.close();
		System.out.println((int)Diacritics.get(Diacritics.FATHATAYN));**/
//		String[] arr = ("a bunconv{{a}}c aa".split("\\s|(unconv\\{\\{.+\\}\\})"));
//		for(String s:arr)
//			System.out.println(s);
//		BufferedReader con = Utils.readFile("ConversionTables\\Buckwalter.txt", "utf-16");
//		ConventionImpl c = new ConventionImpl(con);
//		String regex = "";String raw = "";
//		for(char ch = '\u0621'; ch < '\u0671'; ch++)
//		{
//			if(ch=='\u063B')
//				ch = '\u0640';
//			if(ch=='\u0653')
//				ch = '\u0670';
//			String s = c.getCharRepresentation((int)ch);
//			raw+=s;
//		}
//		regex = c.getConventionRegex();
//		System.out.println(regex+"\n"+raw);
//		Pattern p = Pattern.compile(regex);
//		Matcher m = p.matcher(raw);
//		while(m.find())
//			System.out.println(m.start()+":"+raw.length());
//		System.out.println("a$".replaceAll("\\$", Matcher.quoteReplacement("\\")+Matcher.quoteReplacement("$")));
//		System.out.println("^".replaceAll("\\^", "k"));
//		BufferedReader br = Utils.readFile("temp.txt", "utf-16");
//		Pattern p = Pattern.compile("\u0650\u064A");
//		String s = br.readLine();
//		Matcher m = p.matcher(s);
//		System.out.println(m.find());
//		System.out.println(m.start());
//		s=(m.replaceAll("koko"));
//		//s.replaceAll("\u0650\u064A", "koko");
//		System.out.println("hello :)");
	}
}
