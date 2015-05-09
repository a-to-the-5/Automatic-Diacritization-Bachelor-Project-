package eg.edu.guc.met.bachelor.autotashkeel.utils;

import java.awt.ComponentOrientation;
import java.awt.Font;

import javax.swing.JFrame;
import javax.swing.JTextArea;

public class UnicodeConsole extends JFrame
{

	private static final long serialVersionUID = 327617861301889436L;

	public static UnicodeConsole console = new UnicodeConsole("console");
	
	private JTextArea text;
	
	public UnicodeConsole(String name)
	{
		super(name);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 500, 300);
		setResizable(false);
		setLayout(null);
		text = new JTextArea();
		text.setBounds(0, 0, 480, 280);
		text.setFont(new Font("Times New Roman", Font.PLAIN, 30));
		text.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
		add(text);
		setVisible(true);
	}
	
	public void print(String s)
	{
		text.setText(text.getText()+s);
	}
	
	public void println(String s)
	{
		print(s+"\n");
	}
	
}
