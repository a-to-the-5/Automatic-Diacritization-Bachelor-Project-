package eg.edu.guc.met.bachelor.autotashkeel.utils;

import java.io.BufferedReader;
import java.util.Hashtable;

public class Diacritics 
{
	public static final int FATHATAYN = 0;
	public static final int DAMMATAYN = 1;
	public static final int KASRATAYN = 2;
	public static final int FATHA = 3;
	public static final int DAMMA = 4;
	public static final int KASRA = 5;
	public static final int SHADDAH = 6;
	public static final int SUKUUN = 7;
	
	private static Diacritics diacritics;
	private Hashtable<String, Character> map = new Hashtable<String, Character>();
	private String marks = "";
	private String nemlarDiacriticMarks = "@×^~";
	
	public static char get(int mark)
	{
		switch(mark)
		{
		case FATHATAYN: return getDiacritics().getMap().get("fatHatayn");
		case DAMMATAYN: return getDiacritics().getMap().get("Dammatayn");
		case KASRATAYN: return getDiacritics().getMap().get("kasratayn");
		case FATHA: return getDiacritics().getMap().get("fatHa");
		case DAMMA: return getDiacritics().getMap().get("Damma");
		case KASRA: return getDiacritics().getMap().get("kasra");
		case SHADDAH: return getDiacritics().getMap().get("shaddah");
		case SUKUUN: return getDiacritics().getMap().get("sukuun");
		}
		return ' ';
	}
	
	public boolean isDiacriticMark(char ch)
	{
		return (ch>'\u064B'&&ch<='\u0652');
	}
	
	public boolean isNemlarDiacriticMark(char ch) {
		return ch=='@' || ch=='×' || ch=='^' || ch=='~';
	}
	
	public String getMarks() {
		return marks;
	}
	
	public String getNemlarDiacriticMarks(){
		return nemlarDiacriticMarks;
	}

	public Hashtable<String, Character> getMap() {
		return map;
	}

	public static Diacritics getDiacritics()
	{
		if(diacritics==null)
			diacritics = new Diacritics();
		return diacritics;
	}
	
	private Diacritics()
	{
		try{
			BufferedReader br = Utils.readFile("diacritics.txt", "utf-16");
			String line;
			while((line = br.readLine())!=null)
			{
				String[] pair = line.split(":");
				map.put(pair[0], pair[1].charAt(0));
				marks+=pair[1].charAt(0);
			}
		}catch (Exception e) {}
	}
	
	public String getNextDiacritic(String s)
	{
		char[] c = s.toCharArray();
		for(char ch:c)
			if(ch>'\u064B'&&ch<='\u0652') return ch+"";
		return "";
	}
	
	public String removeDiacritics(String s)
	{
		return s.replaceAll("[\u064B-\u0652]", "");
	}
}
