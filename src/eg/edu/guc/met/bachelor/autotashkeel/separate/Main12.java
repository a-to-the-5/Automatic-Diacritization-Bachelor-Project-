package eg.edu.guc.met.bachelor.autotashkeel.separate;

import java.io.File;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Vector;

import eg.edu.guc.met.bachelor.autotashkeel.utils.FileUtils;

public class Main12 
{
	public static void main(String[] args) throws Exception
	{
		File f1 = new File("C:\\Users\\Abdurrahman\\My Bachelor\\cd\\" +
				"NMWRC7AR\\DATA\\SETS\\ComparisonSet");
		File f2 = new File("C:\\Users\\Abdurrahman\\My Bachelor\\cd\\" +
				"NMWRC7AR\\DATA\\SETS\\TrainingSet");
		Vector<File> all = new Vector<File>();
		
		for(File f:f1.listFiles())
			all.add(f);
		for(File f:f2.listFiles())
			all.add(f);
		
		Collections.sort(all, new Comparator<File>(){
			public int compare(File f1, File f2)
			{
				return f1.getName().compareTo(f2.getName());
			}
		});
		
		boolean[] intraining = new boolean[all.size()];
		for (int i = 0; i < all.size(); i++) 
		{
			if(all.get(i).getAbsolutePath().contains("TrainingSet"))
				intraining[i] = true;
			else
				intraining[i] = false;
		}
		
		File source = new File("G:\\Users\\Abdurrahman\\Desktop\\" +
				"Nemalr_cd\\NMWRC7AR\\DATA\\SETS\\newDiac");
		File[] sub = source.listFiles();
		Arrays.sort(sub, new Comparator<File>(){
			public int compare(File f1, File f2)
			{
				return f1.getName().compareTo(f2.getName());
			}
		});
		
		File training = new File("G:\\Users\\Abdurrahman\\Desktop\\" +
			"Nemalr_cd\\NMWRC7AR\\DATA\\SETS\\training");
		training.mkdir();
		
		File test = new File("G:\\Users\\Abdurrahman\\Desktop\\" +
			"Nemalr_cd\\NMWRC7AR\\DATA\\SETS\\test");
		test.mkdir();
		
		for (int i = 0; i < sub.length; i++) {
			if(intraining[i])
			{
				FileUtils.copyFile(sub[i], new File(training.getAbsolutePath()+"\\"+sub[i].getName()));
			}else
			{
				FileUtils.copyFile(sub[i], new File(test.getAbsolutePath()+"\\"+sub[i].getName()));
			}
		}
	}
}
