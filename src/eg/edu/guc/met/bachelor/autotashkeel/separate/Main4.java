package eg.edu.guc.met.bachelor.autotashkeel.separate;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class Main4 
{
	public static void main(String[] args) throws Exception 
	{
		System.out.println((int)'Y');
		Process p = Runtime.getRuntime().exec("perl count.pl --ngram 1 --token regex.txt" +
				" --histogram hist.txt out.txt txt.txt", null, null);
		BufferedReader stdInput = new BufferedReader(new 
                InputStreamReader(p.getInputStream()));
		p.getOutputStream().write('y');

        BufferedReader stdError = new BufferedReader(new 
                InputStreamReader(p.getErrorStream()));

        String s;
        
        System.out.println("Here is the standard error of the command (if any):\n");
        while ((s = stdError.readLine()) != null) {
        	System.out.println(s);
        }
        
        System.out.println("Here is the standard output of the command:\n");
        while ((s = stdInput.readLine()) != null) {
            System.out.println(s);
        }
        
        // read any errors from the attempted command
	}
}
