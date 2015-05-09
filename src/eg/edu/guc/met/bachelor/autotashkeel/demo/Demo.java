package eg.edu.guc.met.bachelor.autotashkeel.demo;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import javax.swing.text.*;

import eg.edu.guc.met.bachelor.autotashkeel.commandline.AT_Compare;
import eg.edu.guc.met.bachelor.autotashkeel.converters.FileConverter;
import eg.edu.guc.met.bachelor.autotashkeel.ngram.Unigram;
import eg.edu.guc.met.bachelor.autotashkeel.utils.Utils;

public class Demo 
{
	
	static JFrame frame;
	static File file;
	static File file2;
	static Unigram u;
	static JTextPane jtp;
	static JLabel loadFile;
	static JLabel buckwlater;
	static JLabel stats;
	static JLabel diac;
	static JLabel compare;
	
	static void loadFile()
	{
		try
		{
		JFileChooser jfc = new JFileChooser();
		int returnVal = jfc.showOpenDialog(frame);
		if(returnVal==0)
			jtp.read(Utils.readFile((file=jfc.getSelectedFile()).getAbsolutePath(),
					"utf-16"), null);
		}
		catch(Exception e){}
	}
	
	static void buckwalter() 
	{
		try{
			FileConverter fc = new FileConverter(file.getAbsolutePath(),"a.txt","unicode", "buckwalter");
			fc.convert();
			jtp.read(Utils.readFile("a.txt", "utf-8"), null);
		}catch(Exception e){}
	}
	
	static void stats()
	{
		try{
			u = new Unigram();
			u.createArabicStatistics(1, "a.txt");
			jtp.read(Utils.readFile("ar_out.txt", "utf-16"), null);
			u.readStats();
		}catch(Exception e)
		{
			
		}
	}
	
	static void diac()
	{
		try{
			u.diacritizeFile(file.getAbsolutePath(), "diacResult.txt");
			jtp.read(Utils.readFile("diacResult.txt", "utf-16"), null);
		}catch(Exception e){	}
	}
	
	static void compare()
	{
		try{
			JFileChooser jfc = new JFileChooser();
			int returnVal = jfc.showOpenDialog(frame);
			if(returnVal==0)
			{
				file2 = jfc.getSelectedFile();
				AT_Compare.main(new String[]{"-first",
					"diacResult.txt",
					"-second",
					file2.getAbsolutePath(),
					"-report","diacRep.txt"});
				jtp.read(Utils.readFile("diacRep.txt", "utf-16"), null);
			}
		}catch(Exception e){	}
	}
	
	public static void main(String[] args) throws UnsupportedEncodingException, FileNotFoundException, IOException, BadLocationException 
	{
		frame = new JFrame("AutoTashkeel Demo");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setBounds(400, 100, 520, 500);
		frame.setLayout(null);

		loadFile = new JLabel();
		//ImageIcon i = new ImageIcon("loadFile.bmp");
		//System.out.println(i);
		loadFile.setIcon(new ImageIcon("folder.jpg"));
		loadFile.setBounds(10, 20, 80, 66);
		loadFile.setBackground(Color.BLACK);
		Border border = new LineBorder(Color.BLACK, 1);
		loadFile.setBorder(border);
		loadFile.addMouseListener(new MouseListener(){
			@Override
			public void mouseClicked(MouseEvent e) {	
				Demo.loadFile();
			}
			@Override
			public void mouseEntered(MouseEvent e) {	
				Border border = new LineBorder(Color.BLACK, 2);
				Demo.loadFile.setBorder(border);
			}
			@Override
			public void mouseExited(MouseEvent e) {		
				Border border = new LineBorder(Color.BLACK, 1);
				Demo.loadFile.setBorder(border);
			}
			@Override
			public void mousePressed(MouseEvent e) {			}
			@Override
			public void mouseReleased(MouseEvent e) {			}});
		frame.getContentPane().add(loadFile);


		buckwlater = new JLabel();
		//ImageIcon i = new ImageIcon("loadFile.bmp");
		//System.out.println(i);
		buckwlater.setIcon(new ImageIcon("Buckwalter.jpg"));
		buckwlater.setBounds(110, 20, 80, 66);
		buckwlater.setBackground(Color.BLACK);
		border = new LineBorder(Color.BLACK, 1);
		buckwlater.setBorder(border);
		buckwlater.addMouseListener(new MouseListener(){
			@Override
			public void mouseClicked(MouseEvent e) {	
				Demo.buckwalter();
			}
			@Override
			public void mouseEntered(MouseEvent e) {	
				Border border = new LineBorder(Color.BLACK, 2);
				buckwlater.setBorder(border);
			}
			@Override
			public void mouseExited(MouseEvent e) {		
				Border border = new LineBorder(Color.BLACK, 1);
				buckwlater.setBorder(border);
			}
			@Override
			public void mousePressed(MouseEvent e) {			}
			@Override
			public void mouseReleased(MouseEvent e) {			}});
		frame.getContentPane().add(buckwlater);


		stats = new JLabel();
		//ImageIcon i = new ImageIcon("loadFile.bmp");
		//System.out.println(i);
		stats.setIcon(new ImageIcon("stats.jpg"));
		stats.setBounds(210, 20, 80, 66);
		stats.setBackground(Color.BLACK);
		border = new LineBorder(Color.BLACK, 1);
		stats.setBorder(border);
		stats.addMouseListener(new MouseListener(){
			@Override
			public void mouseClicked(MouseEvent e) {	
				Demo.stats();
			}
			@Override
			public void mouseEntered(MouseEvent e) {	
				Border border = new LineBorder(Color.BLACK, 2);
				stats.setBorder(border);
			}
			@Override
			public void mouseExited(MouseEvent e) {		
				Border border = new LineBorder(Color.BLACK, 1);
				stats.setBorder(border);
			}
			@Override
			public void mousePressed(MouseEvent e) {			}
			@Override
			public void mouseReleased(MouseEvent e) {			}});
		frame.getContentPane().add(stats);

		diac = new JLabel();
		//ImageIcon i = new ImageIcon("loadFile.bmp");
		//System.out.println(i);
		diac.setIcon(new ImageIcon("diac.jpg"));
		diac.setBounds(310, 20, 80, 66);
		diac.setBackground(Color.BLACK);
		border = new LineBorder(Color.BLACK, 1);
		diac.setBorder(border);
		diac.addMouseListener(new MouseListener(){
			@Override
			public void mouseClicked(MouseEvent e) {	
				Demo.diac();
			}
			@Override
			public void mouseEntered(MouseEvent e) {	
				Border border = new LineBorder(Color.BLACK, 2);
				diac.setBorder(border);
			}
			@Override
			public void mouseExited(MouseEvent e) {		
				Border border = new LineBorder(Color.BLACK, 1);
				diac.setBorder(border);
			}
			@Override
			public void mousePressed(MouseEvent e) {			}
			@Override
			public void mouseReleased(MouseEvent e) {			}});
		frame.getContentPane().add(diac);

		compare = new JLabel();
		//ImageIcon i = new ImageIcon("loadFile.bmp");
		//System.out.println(i);
		compare.setIcon(new ImageIcon("compare.jpg"));
		compare.setBounds(410, 20, 80, 66);
		compare.setBackground(Color.BLACK);
		border = new LineBorder(Color.BLACK, 1);
		compare.setBorder(border);
		compare.addMouseListener(new MouseListener(){
			@Override
			public void mouseClicked(MouseEvent e) {	
				Demo.compare();
			}
			@Override
			public void mouseEntered(MouseEvent e) {	
				Border border = new LineBorder(Color.BLACK, 2);
				compare.setBorder(border);
			}
			@Override
			public void mouseExited(MouseEvent e) {		
				Border border = new LineBorder(Color.BLACK, 1);
				compare.setBorder(border);
			}
			@Override
			public void mousePressed(MouseEvent e) {			}
			@Override
			public void mouseReleased(MouseEvent e) {			}});
		frame.getContentPane().add(compare);

		
		jtp = new JTextPane();
		
		jtp.setBounds(10, 100, 480, 450);
		//jtp.setText("hello");
		
		//jtp.setAutoscrolls(true);
		jtp.setFont(new Font("Arabic Typesetting", Font.PLAIN, 30));
		jtp.setEditable(false);
		
//		StyledDocument doc = jtp.getStyledDocument();
//		MutableAttributeSet standard = new SimpleAttributeSet();
//		StyleConstants.setAlignment(standard, StyleConstants.ALIGN_RIGHT);
//		doc.setParagraphAttributes(0, 0, standard, true); 
		//frame.add(jtp);
		
//		jtp.setEditorKit(new MyEditorKit());
//		SimpleAttributeSet attrs=new SimpleAttributeSet();
//		StyleConstants.setAlignment(attrs,StyleConstants.ALIGN_CENTER);
//		StyledDocument doc=(StyledDocument)jtp.getDocument();
//		doc.insertString(0,"111\n2222222\n33333333333333",attrs);
//		doc.setParagraphAttributes(0,doc.getLength()-1,attrs,false);
		
		//jtp.read(Utils.readFile("Business_10_Raw.txt", "utf-16"), null);
		JScrollPane scrPane = new JScrollPane();
		scrPane.setBounds(10, 100, 480, 350);
		scrPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
		//scrPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED); 
		scrPane.getViewport().add(jtp);
		frame.getContentPane().add(scrPane); 
		//frame.add(scrPane);
		
		frame.setVisible(true);
	}
}


class MyEditorKit extends StyledEditorKit {
	/**
	 * 
	 */
	private static final long serialVersionUID = -3591004631619683137L;

	public ViewFactory getViewFactory() {
	return new StyledViewFactory();
	}

	static class StyledViewFactory implements ViewFactory {
	public View create(Element elem) {
	String kind = elem.getName();
	if (kind != null) {
	if (kind.equals(AbstractDocument.ContentElementName)) {
	return new LabelView(elem);
	} else if (kind.equals(AbstractDocument.ParagraphElementName)) {
	return new ParagraphView(elem);
	} else if (kind.equals(AbstractDocument.SectionElementName)) {
	return new CenteredBoxView(elem, View.Y_AXIS);
	} else if (kind.equals(StyleConstants.ComponentElementName)) {
	return new ComponentView(elem);
	} else if (kind.equals(StyleConstants.IconElementName)) {
	return new IconView(elem);
	}
	}

	// default to text display
	return new LabelView(elem);
	}
	}
	}

	class CenteredBoxView extends BoxView {
	public CenteredBoxView(Element elem, int axis) {
	super(elem,axis);
	}
	protected void layoutMajorAxis(int targetSpan, int axis, int[] offsets, int[] spans) {
	super.layoutMajorAxis(targetSpan,axis,offsets,spans);
	int textBlockHeight = 0;
	int offset = 0;

	for (int i = 0; i < spans.length; i++) {
	textBlockHeight = spans[ i ];
	}
	offset = (targetSpan - textBlockHeight) / 2;
	for (int i = 0; i < offsets.length; i++) {
	offsets[ i ] += offset;
	}
	}
	} 