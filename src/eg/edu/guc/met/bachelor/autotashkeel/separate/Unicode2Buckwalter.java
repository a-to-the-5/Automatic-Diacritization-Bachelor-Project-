package eg.edu.guc.met.bachelor.autotashkeel.separate;

import java.io.*;
import java.util.Scanner;



public class Unicode2Buckwalter {
	
	File file;
    public Unicode2Buckwalter(String filename){
    	file = new File (filename);
    }
    
    
    
    
    
//********************************************* READ FILE METHOD ***********************************************//
    public String readFile()throws FileNotFoundException{
    	
    	String word = "", fileCon = "";
    //	try{
    		Scanner scanner = new Scanner(file, "utf-16");

		    
		    while (scanner.hasNext()){
		       word = scanner.next();
		       
		       fileCon = fileCon + " " + word;
		    }
		    
		    
//		    System.out.println("End Of File");
		    scanner.close();
		    
    //	}
	    
	    /*catch(NullPointerException e){
	    	System.out.println("Null Pointer Exception :(");
	    }*/
	    System.out.println("File Content = " + fileCon);
	    return fileCon;
	}
    
    
    
    
    
    
//********************************************* CONVERT METHOD ***********************************************//    
    private String convert (String uni){
    	
    	System.out.println("length of the string = " + uni.length());
    	char u ;
    	String b = "";
    	
    	for (int i = 0; i < uni.length(); i++){
    		u = uni.charAt(i);
    		Character uc = new Character (u);
    		System.out.println(i + ". char value = " + (int)(u) +"");
    		switch (u){
    			case '\u0621':	b = b + "?";	break; // ?
    			case '\u0622':	b = b + "?a:";	break; // ?
    			case '\u0623':	b = b + "?";	break; // ?
    			case '\u0624':	b = b + "?";	break; // ?
    			case '\u0625':	b = b + "?";	break; // ?
    			case '\u0626':	b = b + "?";	break; // ?
    			case '\u0627':	b = b + "?";	break; // ? 
    			case '\uFE91':
    			case '\u0628':	b = b + "b";	break; // ?
    			case '\u0629':	b = b + "t";	break; // ?
    			case '\u062A':	b = b + "t";	break; // ?
    			case '\u062B':	b = b + "T";	break; // ?
    			case '\u062C':	b = b + "Z";	break; // ?
    			case '\u062D':	b = b + "X\\";	break; // ?
    			case '\u062E':	b = b + "x";	break; // ?
    			case '\u062F':	b = b + "d";	break; // ?
    			case '\u0630':	b = b + "D";	break; // ?
    			case '\u0631':	b = b + "r";	break; // ? 
    			case '\u0632':	b = b + "z";	break; // ?
    			case '\u0633':	b = b + "s";	break; // ?
    			case '\u0634':	b = b + "S";	break; // ?
    			case '\u0635':	b = b + "s'";	break; // ?
    			case '\u0636':	b = b + "d'";	break; // ?
    			case '\u0637':	b = b + "t'";	break; // ?
    			case '\u0638':	b = b + "D'";	break; // ?
    			case '\u0639':	b = b + "?/";	break; // ?
    			case '\u063A':	b = b + "G";	break; // ?
    		//	case '\u0640':	b = b + "?/";	break; //_
    			case '\u0641':	b = b + "f";	break; // ?
    			case '\u0642':	b = b + "q";	break; // ?
    			case '\u0643':	b = b + "k";	break; // ?
    			case '\u0644':	b = b + "l";	break; // ?
    			case '\u0645':	b = b + "m";	break; // ?
    			case '\u0646':	b = b + "n";	break; // ?
    			case '\u0647':	b = b + "h";	break; // ?
    			case '\u0648':	b = b + "w";	break; // ?
    			case '\u0649':	b = b + "a:";	break; // ?
    			case '\u064A':	b = b + "j";	break; // ?
    			case '\u064B':	b = b + "a n";	break; // ??
    			case '\u064C':	b = b + "u n";	break; // ?
    			case '\u064D':	b = b + "i n";	break; // *?
    			case '\u064E':	b = b + "a";	break; // ??
    			case '\u064F':	b = b + "u";	break; // ??
    			case '\u0650':	b = b + "i";	break; // ??
    			default: b = b + " ";
    			   			
    		}
    		
    		
    	}
    	System.out.println("Buck String = " + b);
    	return b;
    }
    
//********************************************* CONVERT 2 METHOD ***********************************************//
	String convert()throws FileNotFoundException{
		return convert(readFile());
	}


//********************************************* MAIN METHOD ***********************************************//
    public static void main (String [] args)throws FileNotFoundException{
    	
    	Unicode2Buckwalter u2b = new Unicode2Buckwalter("me.txt");
        	
    	System.out.println(u2b.convert());
    	
    	
    	char c = '\u0647';
    	System.out.println(c);
    }
    
}



/*
import java.io.*;
class FileRead 
{
   public static void main(String args[])
  {
      try{
    // Open the file that is the first 
    // command line parameter
    FileInputStream fstream = new FileInputStream("textfile.txt");
    // Get the object of DataInputStream
    DataInputStream in = new DataInputStream(fstream);
        BufferedReader br = new BufferedReader(new InputStreamReader(in));
    String strLine;
    //Read File Line By Line
    while ((strLine = br.readLine()) != null)   {
      // Print the content on the console
      System.out.println (strLine);
    }
    //Close the input stream
    in.close();
    }catch (Exception e){//Catch exception if any
      System.err.println("Error: " + e.getMessage());
    }
  }
}*/




