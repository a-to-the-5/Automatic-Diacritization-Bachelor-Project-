package eg.edu.guc.met.bachelor.autotashkeel.converters;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Hashtable;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import eg.edu.guc.met.bachelor.autotashkeel.utils.Diacritics;

public class NemlarConventionBckp implements Convention
{
	//Arabic alphabet less the madd characters (alif, waw, ya2, alif maqsoorah)
	static final String arabicAlphabetPattern = "\u0621\u0623-\u0626\u0628-\u063A\u0641-\u0647"; 
	static final String arabicTashkeelPattern = "\u064B-\u0652";
	Hashtable<String, String> conventionToUnicode = new Hashtable<String, String>();
	Hashtable<String, String> properConventionToUnicode = new Hashtable<String, String>();
	
	public NemlarConventionBckp(BufferedReader br) throws IOException 
	{
		String s;
		boolean proper = false;
		while((s=br.readLine())!=null)
		{
			if(s.equals("#"))
			{
				proper = true;
				continue;
			}
			String[] ss = s.split(":");
			if(!proper)
				conventionToUnicode.put(ss[0], ss[1]);
			else
				properConventionToUnicode.put(ss[0], ss[1]);

		}
//		conventionToUnicode.
//			put("["+arabicAlphabetPattern+"][^"+arabicTashkeelPattern+"]", "×");
		br.close();
	}
	
	public String getStringRepresentation(String unicodeString)
	{
		Pattern p;
		Matcher m;
		for(String key : conventionToUnicode.keySet() )
		{
			String temp = "";
			int seqStart = 0;
			int seqEnd = 0;
			p = Pattern.compile(key);
			m  = p.matcher(unicodeString);
			while(m.find())
			{
				seqEnd = m.start();
				String s = m.group();
				temp += unicodeString.substring(seqStart, seqEnd)+ 
						s.replaceAll(key, conventionToUnicode.get(key));//+
						//s.substring(s.length());
				seqStart = m.end()-1;
			}
			unicodeString = temp+unicodeString.substring(seqStart);
		}
		for(String key : properConventionToUnicode.keySet() ){
			p = Pattern.compile(key);
			m  = p.matcher(unicodeString);
			unicodeString = m.replaceAll(properConventionToUnicode.get(key));
		}
		
		p = Pattern.compile("["+arabicAlphabetPattern+"][^"+arabicTashkeelPattern+"]");
		m = p.matcher(unicodeString);
		String temp = "";
		int seqStart = 0;
		int seqEnd = 0;
		while(m.find())
		{
			seqEnd = m.start();
			String s = m.group();
			temp += unicodeString.substring(seqStart, seqEnd)+
				s.charAt(0)+"×";
			seqStart = m.end()-1;
		}
		unicodeString = temp+unicodeString.substring(seqStart);
		
		p = Pattern.compile("\u0627");
		m = p.matcher(unicodeString);
		temp = "";
		seqStart = 0;
		seqEnd = 0;
		while(m.find())
		{
			seqEnd = m.start();
			String s = m.group();
			temp += unicodeString.substring(seqStart, seqEnd)+
				s;//+((seqEnd==0||unicodeString.charAt(seqEnd-1)!=
					//Diacritics.getDiacritics().getMap().get("fatHa"))?"×":"");
			String nextDiac = Diacritics.getDiacritics().getNextDiacritic(unicodeString.substring(seqEnd));
			if(seqEnd==unicodeString.length()-1 || !Diacritics.getDiacritics().isDiacriticMark(unicodeString.charAt(seqEnd+1)))
			{
				if(/**seqEnd==0||**/(seqEnd>0 && 
				   unicodeString.charAt(seqEnd-1)!=
					   Diacritics.get(Diacritics.FATHA)) ||
				   (nextDiac).equals(""+Diacritics.get(Diacritics.SUKUUN))||
				   (nextDiac).equals(""+Diacritics.get(Diacritics.SHADDAH))||
				   (nextDiac).equals(" "))
						temp+="×";
				else temp+="@";
			}
			seqStart = m.end();
		}
		unicodeString = temp+unicodeString.substring(seqStart);
		
		return unicodeString;
	}
	
	public String getStringUnicode(String representation)
	{
		return representation.replaceAll("[@×~]", "").replaceAll("\\^", "\u0670");
	}
}
