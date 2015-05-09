package eg.edu.guc.met.bachelor.autotashkeel.utils;

import java.io.BufferedWriter;
import java.io.File;

public class CorpusWriter 
{
	BufferedWriter bw;
	String path;
	
	public CorpusWriter(String corpusPath) 
	{
		path = corpusPath;
		File corpus = new File(corpusPath);
		if(!corpus.exists())
			corpus.mkdir();
	}
	
}
