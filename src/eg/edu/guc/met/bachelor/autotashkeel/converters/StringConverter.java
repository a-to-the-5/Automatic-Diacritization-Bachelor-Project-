package eg.edu.guc.met.bachelor.autotashkeel.converters;

import java.io.File;
import java.util.Hashtable;

import eg.edu.guc.met.bachelor.autotashkeel.utils.Utils;

public class StringConverter 
{
	private static StringConverter converter;
	
	private Hashtable<String, Convention> conventions =
		new Hashtable<String, Convention>();
	
	public static synchronized StringConverter getConverter()
	{
		if(converter==null) converter = new StringConverter();
		return converter;
	}
	
	public Convention getConvention(String name)
	{
		return conventions.get(name);
	}
	
	public StringConverter() 
	{
		try
		{
			File conversionsDir = new File("ConversionTables");
			for(File f : conversionsDir.listFiles())
				conventions.put(f.getName().replaceAll("\\..+", "").toLowerCase(), 
						new ConventionImpl(Utils.readFile
								(f.getAbsolutePath(), "utf-16")));
			conventions.put("nemlar", new NemlarConvention(Utils.readFile
					("NemlarTables\\Nemlar.txt", "utf-16")));
		}catch(Exception e){
			System.err.print("Cannot open/parse conversions file: ");
			e.printStackTrace();
		} 
		
	}
	
	public String convertString(String toBeConverted, String sourceConvention,
			String destinationConvention) throws UnsupportedConventionException
	{
		try{
			if(sourceConvention.equalsIgnoreCase("unicode"))
				return conventions.get(destinationConvention.toLowerCase()).
					getStringRepresentation(toBeConverted);
			else if(destinationConvention.equalsIgnoreCase("unicode"))
				return conventions.get(sourceConvention.toLowerCase()).getStringUnicode(toBeConverted);
			else
				return conventions.get(destinationConvention.toLowerCase()).
				getStringRepresentation(conventions.get(sourceConvention.toLowerCase()).
						getStringUnicode(toBeConverted));
		}catch(NullPointerException e)
		{
			throw new UnsupportedConventionException();
		}
	}
}
