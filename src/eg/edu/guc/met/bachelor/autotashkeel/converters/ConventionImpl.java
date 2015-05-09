package eg.edu.guc.met.bachelor.autotashkeel.converters;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Hashtable;
import java.util.List;
import java.util.Vector;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ConventionImpl implements Convention 
{
	
	int charsPerLetter;
	
	private Hashtable<Integer, String> unicodeToConvention = 
		new Hashtable<Integer, String>();
	
	private Hashtable<String, Integer> conventionToUnicode = 
		new Hashtable<String, Integer>();
	
	private List<String> conventionList = new Vector<String>();
	
	private String conventionRegex = "";
	
	public String getConventionRegex() {
		return conventionRegex;
	}

	public ConventionImpl(int c) {
		charsPerLetter = c;
	}
	
	public ConventionImpl(BufferedReader r) throws IOException
	{
		String[] firstLine =  r.readLine().split("/");
		charsPerLetter = Integer.parseInt(firstLine[1]);
		String line;
		while((line = r.readLine())!=null)
		{
			String[] map = line.split(":");
			
			unicodeToConvention.put((int)map[0].charAt(0), map[1]);
			conventionToUnicode.put(map[1], (int)map[0].charAt(0));
			conventionRegex+=/**"|("+**/map[1].replaceAll("\\|", "\\\\|")
			.replaceAll("\\*", "\\\\*").replaceAll("\\{", "\\\\{")
			.replaceAll("\\&", "\\\\&").replaceAll("\\}", "\\\\}")
			.replaceAll("\\<", "\\\\<").replaceAll("\\>", "\\\\>")
			.replaceAll("\\$", Matcher.quoteReplacement("\\")+Matcher.quoteReplacement("$"))
			/**+")"**/;
			conventionList.add(map[1]);
			
		}
		//conventionRegex = conventionRegex.substring(1);
	}
	
	public void add(int unicode, String representation)
	{
		unicodeToConvention.put(unicode, representation);
		conventionToUnicode.put(representation, unicode);
	}
	
	public Integer  getCharUnicode(String representation) 
	{
		return conventionToUnicode.get(representation);
	}
	
	public String getCharRepresentation(int unicode) 
	{
		return unicodeToConvention.get(unicode);
	}
	
	public String getStringRepresentation(String unicodeString)
	{
		String[] words = unicodeString.split("\\s");
		Pattern p = Pattern.compile("\\s");
		Matcher m = p.matcher(unicodeString);
		StringBuilder result = new StringBuilder();
		StringBuilder tempWord = new StringBuilder();
		String tempChar = "";
		for(String word:words)
		{
			tempWord = new StringBuilder();
			boolean skip = false;
			Pattern p1 = Pattern.compile("[\u0621-\u0671]");
			Matcher m1 = p1.matcher(word);
			if(!word.equals("")&&!m1.find())
			{
				tempWord=new StringBuilder("Q[[").append(word).append("]]");
				skip = true;
			}
			
			if(!skip)
			for(int i = 0; i<word.length();)
			{
				String temp = "";
				while(i<word.length() && conventionList.contains(""+word.charAt(i)))
				{
					temp += word.charAt(i);
					i++;
				}
				if(!temp.equals(""))
					tempWord.append("Q[[").append(temp).append("]]");
				while(i<word.length() && !conventionList.contains(""+word.charAt(i)))
				{
					tempChar = getCharRepresentation((int)word.charAt(i));
					if(tempChar!=null)
						tempWord.append(tempChar);
					else
						tempWord.append(word.charAt(i));
					i++;
				}
			}
			
			result.append(tempWord);
			if(m.find()) result.append(m.group());
		}
		return result.toString();
	}
	
	public String getStringUnicode(String rep)
	{
		StringBuilder representation = new StringBuilder(rep);
		Pattern p = Pattern.compile("Q\\[\\[" + "(.*?)" + "\\]\\]");
		Matcher confuseConvention = p.matcher(representation);
		int startIndex = 0;
		StringBuilder result = new StringBuilder();
		StringBuilder tempWord = new StringBuilder();
		Object temp;
		while(confuseConvention.find())
		{
			String word = representation.substring(startIndex, confuseConvention.start());
			boolean skip = false;
			tempWord = new StringBuilder();
			if(word.matches("Q\\[\\[.+\\]\\]"))
			{
				tempWord=new StringBuilder(word.replaceAll("Q\\[\\[", "").
				replaceAll("\\]\\]", ""));
				skip = true;
			}
			if(!skip)
			for(int i = 0; i<word.length(); i+=charsPerLetter)
			{
				if(i+charsPerLetter>word.length())
				{
					tempWord.append(word.substring(i, word.length()));
					break;
				}
				temp = getCharUnicode
					(word.substring(i, i+charsPerLetter));
				if(temp!=null)
					tempWord.append((char)((Integer)temp).intValue());
				else
				{
					tempWord.append(word.charAt(i));
					i-=(charsPerLetter-1);
				}
			}
			
			result.append(tempWord);
			 
			result.append(confuseConvention.group().replaceAll("Q\\[\\[", "").
				replaceAll("\\]\\]", ""));
			startIndex = confuseConvention.end();
		}
		String word=representation.substring(startIndex, representation.length());
		tempWord=new StringBuilder();
		for(int i = 0; i<word.length(); i+=charsPerLetter)
		{
			if(i+charsPerLetter>word.length())
			{
				tempWord.append(word.substring(i, word.length()));
				break;
			}
			temp = getCharUnicode
				(word.substring(i, i+charsPerLetter));
			if(temp!=null)
				tempWord.append((char)((Integer)temp).intValue());
			else
			{
				tempWord.append(word.charAt(i));
				i-=(charsPerLetter-1);
			}
		}
		return result.append(tempWord).toString();
	}
}
