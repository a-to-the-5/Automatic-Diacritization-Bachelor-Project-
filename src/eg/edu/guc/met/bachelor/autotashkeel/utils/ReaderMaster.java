package eg.edu.guc.met.bachelor.autotashkeel.utils;

public interface ReaderMaster 
{
	public void newFile();
	public void lineRead(String line);
	public void endFile();
	
	public void goDeep(CorpusReader cr);
}
