package eg.edu.guc.met.bachelor.autotashkeel.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;

public class CorpusReader 
{
	private ReaderMaster master;
	private String extension = "txt";
	private String path;
	private boolean deep;
	private String encoding = "utf-16";
	
	public CorpusReader(ReaderMaster master, String path, boolean deep)
	{
		this.master = master;
		this.deep = deep;
		this.path = path;
	}
	
	public CorpusReader(ReaderMaster master, String path, boolean deep,
			String extension, String encoding)
	{
		this(master, path, deep);
		this.extension = extension;
		this.encoding = encoding;
	}
	
	public void read() throws IOException
	{
		File toRead = new File(path);
		if(toRead.isDirectory())
		{
			File[] contents = toRead.listFiles();
			for(File f: contents)
			{
				if((!f.isDirectory() && (extension==null ||f.getName().endsWith(extension)))
						||deep)
				{
					CorpusReader cr = new CorpusReader(null, f.getAbsolutePath(), deep);
					master.goDeep(cr);
				}
			}
		}else
			readFile();
	}
	
	public void readFile() throws IOException
	{
		master.newFile();
		BufferedReader br = Utils.readFile(path, encoding);
		String s;
		while((s=br.readLine())!=null)
		{
			master.lineRead(s);
		}
		br.close();
		master.endFile();
	}
}
