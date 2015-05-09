package eg.edu.guc.met.bachelor.autotashkeel.converters;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Hashtable;
import java.util.List;
import java.util.Vector;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import eg.edu.guc.met.bachelor.autotashkeel.utils.Diacritics;

public class NemlarConvention implements Convention
{
	//Arabic alphabet less the madd characters (alif, waw, ya2, alif maqsoorah)
	static final String arabicAlphabetPattern = "\u0644";//"\u0621\u0623-\u0626\u0628-\u063A\u0641-\u0647"; 
	static final String arabicTashkeelPattern = "\u064B-\u0652\u0670^";
	Hashtable<String, String> conventionToUnicode = new Hashtable<String, String>();
	Hashtable<String, String> properConventionToUnicode = new Hashtable<String, String>();
	List<String> conventionKeys;
	List<String> properConventionKeys;
	
	public NemlarConvention(BufferedReader br) throws IOException 
	{
		String s;
		conventionKeys = new Vector<String>();
		properConventionKeys = new Vector<String>();
		boolean proper = false;
		while((s=br.readLine())!=null)
		{
			if(s.equals("#"))
			{
				proper = true;
				continue;
			}
			if(s.startsWith("%"))
				continue;
			String[] ss = s.split(":");
			if(!proper)
			{
				conventionToUnicode.put(ss[0], ss[1]);
				conventionKeys.add(ss[0]);
			}
			else
			{
				properConventionToUnicode.put(ss[0], ss[1]);
				properConventionKeys.add(ss[0]);
			}

		}
		//Collections.sort(conventionKeys);
		//Collections.sort(properConventionKeys);
//		conventionToUnicode.
//			put("["+arabicAlphabetPattern+"][^"+arabicTashkeelPattern+"]", "×");
		br.close();
	}
	
	public String getStringRepresentation(String unicodeString)
	{
		Pattern p = Pattern.compile("\\s");
		Matcher m = p.matcher(unicodeString);
		String res = "";
		int startIndex = 0;
		boolean lastLoop = true, find;
		while((find=m.find())||lastLoop)
		{
			if(!find && lastLoop) lastLoop=false;
			
			int endIndex = (find? m.start(): unicodeString.length());
			String word = unicodeString.substring(startIndex, endIndex);
			
			
			for(String key : conventionKeys)
			{
				String temp = "";
				int seqStart = 0;
				int seqEnd = 0;
				Pattern p1 = Pattern.compile(key);
				Matcher m2 = p1.matcher(word);
				while(m2.find())
				{
					seqEnd = m2.start();
					String s = m2.group();
					temp += word.substring(seqStart, seqEnd)+ 
							s.replaceAll(key, conventionToUnicode.get(key));//+
							//s.substring(s.length());
					seqStart = m2.end()-1;
				}
				word = temp+word.substring(seqStart);
			}
			
			for(String key : properConventionKeys)
			{
				Pattern p1 = Pattern.compile(key);
				Matcher m1  = p1.matcher(word);
				word = m1.replaceAll(properConventionToUnicode.get(key));
			}
			
			Pattern p1 = Pattern.compile("["+arabicAlphabetPattern+"][^"+arabicTashkeelPattern+"]");
			Matcher m1 = p1.matcher(word);
			String temp = "";
			int seqStart = 0;
			int seqEnd = 0;
			while(m1.find())
			{
				seqEnd = m1.start();
				String s = m1.group();
				temp += word.substring(seqStart, seqEnd)+
					s.charAt(0)+"×";
				seqStart = m1.end()-1;
			}
			word = temp+word.substring(seqStart);
			
			p1 = Pattern.compile("\u0627");
			m1 = p1.matcher(word);
			temp = "";
			seqStart = 0;
			seqEnd = 0;
			while(m1.find())
			{
				seqEnd = m1.start();
				String s = m1.group();
				temp += word.substring(seqStart, seqEnd)+
					s;//+((seqEnd==0||unicodeString.charAt(seqEnd-1)!=
						//Diacritics.getDiacritics().getMap().get("fatHa"))?"×":"");
				String nextDiac = Diacritics.getDiacritics().getNextDiacritic(word.substring(seqEnd));
				if(seqEnd==word.length()-1 || 
					(word.charAt(seqEnd+1)!='×' && !Diacritics.getDiacritics().isDiacriticMark(word.charAt(seqEnd+1))))
				{
					if(seqEnd==0||
							
					    (word.charAt(seqEnd-1)!=
						   Diacritics.get(Diacritics.FATHA)&&
						   
						 word.charAt(seqEnd-1)!=
							   Diacritics.get(Diacritics.FATHATAYN)) ||
							   
					    (((seqEnd == 2 && word.length()>3 &&
						   (word.charAt(0)=='\u0641' || word.charAt(0)=='\u0643' ||
						    word.charAt(0)=='\u0644' || word.charAt(0)=='\u0648') 
						   && word.charAt(1)==Diacritics.get(Diacritics.FATHA))//word.charAt(3)=='\u0644') 
							   || seqEnd == 0) && (
					   (nextDiac).equals(""+Diacritics.get(Diacritics.SUKUUN))||
					   (nextDiac).equals(""+Diacritics.get(Diacritics.SHADDAH)))))//||
					   //(nextDiac).equals(" "))
							temp+="×";
					else if(seqEnd>0 && word.charAt(seqEnd-1)==
								   Diacritics.get(Diacritics.FATHA)) 
							temp+="@";
				}
				seqStart = m1.end();
			}
			word = temp + word.substring(seqStart);
			res+=word;
			
			if(lastLoop)
			{
				res+=m.group();
				if(endIndex!=unicodeString.length())
					startIndex = m.end();
			}
		}
		
		
		return res;
	}
	
	public String getStringUnicode(String representation)
	{
		return representation.replaceAll("[@×~]", "").replaceAll("\\^", "\u0670");
	}
}
