package eg.edu.guc.met.bachelor.autotashkeel.utils;
import java.io.*;
import java.nio.channels.*;
import java.util.Hashtable;
import java.util.List;
import java.util.Vector;

public class FileUtils{
    public static void copyFile(File in, File out) 
        throws IOException 
    {
        FileChannel inChannel = new
            FileInputStream(in).getChannel();
        FileChannel outChannel = new
            FileOutputStream(out).getChannel();
        try {
            inChannel.transferTo(0, inChannel.size(),
                    outChannel);
        } 
        catch (IOException e) {
            throw e;
        }
        finally {
            if (inChannel != null) inChannel.close();
            if (outChannel != null) outChannel.close();
        }
    }

    public static void main(String args[]) throws IOException
    {
    	File Buckwalter = new File("C:\\Users\\" +
    			"Abdurrahman\\My Bachelor\\cd\\NMWRC7AR\\DATA\\BuckwalterText");
    	Hashtable<String, List<String>> domains = new Hashtable<String, List<String>>();
    	
    	for(String filename: Buckwalter.list())
    	{
    		String [] ss = filename.split("_");
    		List<String> all = domains.get(ss[0]);
    		if(all == null)
    			domains.put(ss[0], all=new Vector<String>());
    		all.add(filename);
    	}
    	
    	for(String key: domains.keySet())
    	{
    		List<String> all = domains.get(key);
    		int test = (int)Math.round(all.size()*0.7);
    		for(int i = 0; i<test; i++)
    		{
    			String temp = all.remove((int)(Math.random()*all.size()));
    			File out = new File("C:\\Users\\Abdurrahman\\" +
						"My Bachelor\\cd\\NMWRC7AR\\DATA\\SETS\\TrainingSet\\"+temp);
    			out.createNewFile();
    			copyFile(new File(Buckwalter.getAbsolutePath()+"\\"+temp),
    					out);
    		}
    		
    		while(!all.isEmpty())
    		{
    			String source1 = ("C:\\Users\\Abdurrahman\\" +
						"My Bachelor\\cd\\NMWRC7AR\\DATA\\RawText");
    			
    			String dest1 = ("C:\\Users\\Abdurrahman\\" +
						"My Bachelor\\cd\\NMWRC7AR\\DATA\\SETS\\TestSet");
    			
    			String source2 = ("C:\\Users\\Abdurrahman\\" +
						"My Bachelor\\cd\\NMWRC7AR\\DATA\\TextWithStandardArabicDiacritization");
    			
    			String dest2 = ("C:\\Users\\Abdurrahman\\" +
						"My Bachelor\\cd\\NMWRC7AR\\DATA\\SETS\\ComparisonSet");
    			
    			String filename = all.remove(0);
    			
    			File d1 = new File(dest1+"\\"+filename);
    			d1.createNewFile();
    			
    			copyFile(new File(source1+"\\"+filename.replace("WithStandardArabicDiacritization",
    					"Raw")), 
    					d1);
    			
    			File d2 = new File(dest2+"\\"+filename);
    			d2.createNewFile();
    			
    			copyFile(new File(source2+"\\"+filename), d2);
    			
    		}
    	}
    	
    }
    
}
