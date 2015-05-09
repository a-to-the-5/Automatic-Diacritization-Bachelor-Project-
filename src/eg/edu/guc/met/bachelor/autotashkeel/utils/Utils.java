package eg.edu.guc.met.bachelor.autotashkeel.utils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;

public class Utils 
{
	
	public static BufferedReader readFile(String name, String encoding)
				throws UnsupportedEncodingException, FileNotFoundException
	{
		try{
			if(encoding!="")
				return new BufferedReader
					(new InputStreamReader(new FileInputStream(name), encoding));
			else
				return new BufferedReader
				(new InputStreamReader(new FileInputStream(name)));}
		catch (FileNotFoundException e) {
			throw new FileNotFoundException("could not read file: "+name);
		}
	}
	
	public static BufferedWriter writeFile(String name, String encoding)
				throws UnsupportedEncodingException, FileNotFoundException
	{
		try{
			if(encoding!="")
				return new BufferedWriter
					(new OutputStreamWriter(new FileOutputStream(name), encoding));
			else
				return new BufferedWriter
				(new OutputStreamWriter(new FileOutputStream(name)));
		}
		catch (FileNotFoundException e) {
			throw new FileNotFoundException("could not write to file: "+name);
		}
	}
	
	public static void main(String[] args) 
	{
		UnicodeConsole.console.println("\u0623\u0624\u0625\u0626\u0627\u0670\u0628\u0670\u0629");
	}
}
