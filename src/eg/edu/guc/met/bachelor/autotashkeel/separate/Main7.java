package eg.edu.guc.met.bachelor.autotashkeel.separate;

import java.io.File;

import eg.edu.guc.met.bachelor.autotashkeel.utils.FileUtils;

public class Main7 {
	public static void main(String[] args) throws Exception {
		File TestSet = new File("C:\\Users\\Abdurrahman\\My Bachelor\\cd\\NMWRC7AR\\" +
				"DATA\\SETS\\TestSet");
		
		File autoRaw = new File("C:\\Users\\Abdurrahman\\My Bachelor\\cd\\NMWRC7AR\\" +
				"DATA\\AutoRawText");
		
		File newTestSet = new File("C:\\Users\\Abdurrahman\\My Bachelor\\cd\\NMWRC7AR\\" +
				"DATA\\SETS\\NewTestSet"); 
		
		for(String f: TestSet.list())
		{
			File source = new File(autoRaw.getAbsolutePath()+File.separator+
					f.replaceAll("WithStandardArabicDiacritization", "AutoRaw"));
			File dest = new File(newTestSet.getAbsolutePath()+File.separator+source.getName());
			dest.createNewFile();
			FileUtils.copyFile(source, dest);
		}
		
	}
}
