package eg.edu.guc.met.bachelor.autotashkeel.statistics;

import java.io.BufferedWriter;
import java.io.IOException;

public interface Compare 
{
	public void writeSummary(BufferedWriter bw) throws IOException;
	public int getWordCount();
	public int getIncorrectWords();
	public int getLetterCount();
	public int getIncorrectLetters();
	public double getWordErrorRate();
	public double getLetterErrorRate();
	public int getMismachingWords();
	public void doCompare() throws IOException;
	public void initReport() throws IOException;
}
