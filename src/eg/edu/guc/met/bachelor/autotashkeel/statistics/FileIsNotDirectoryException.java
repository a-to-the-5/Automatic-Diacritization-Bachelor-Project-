package eg.edu.guc.met.bachelor.autotashkeel.statistics;

public class FileIsNotDirectoryException extends Exception 
{
	String message;
	
	public String getMessage() {
		return message;
	}

	public FileIsNotDirectoryException(String message) {
		this.message = message;
	}
	
	
}
