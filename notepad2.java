/*
-------------------------------------------
	|		PROJECT: Notepad 2.0		|
	|		VERSION: 1.0				|
	|		AUTHOR: Suvankar Das		|
-------------------------------------------
*/

import java.awt.Font;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
import javax.swing.undo.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.Color;
import java.awt.print.*;
import java.io.*;
import java.awt.GraphicsEnvironment;
import java.awt.GridBagLayout;
import java.awt.FlowLayout;
import java.awt.Container;

public class notepad2 extends JFrame implements ActionListener, ItemListener
{
	ImageIcon appicon = new ImageIcon(getClass().getResource("appicon_small.png"));
	
	//Declaration of components & variables for notepad class
	JMenuBar menuBar;
	Color bg, fg, crt;
	JMenu file, edit, help, format, theme, custom;
	JMenuItem f_new, f_open, f_save, f_print, f_exit, e_cut, e_copy, e_paste, e_selectall, e_undo, e_redo, fr_wordwrap, fr_font,theme_light,theme_dark,theme_pink,theme_blue,custom_bg, custom_fg, custom_caret, h_about, pop_cut, pop_copy, pop_paste, pop_selectall, pop_undo, pop_redo;
	JTextArea textArea;
	JScrollPane scrollPane;
	boolean wrap = true;
	JPopupMenu pmenu;
	UndoManager undo_redo;
	
	//Declaration of components & variables for fontChoose JFrame
	JFrame fontChoose = new JFrame();
	JPanel fontPanel = new JPanel();
	JPanel sample = new JPanel();
	JPanel buttons = new JPanel();
	JLabel fontLabel, sizeLabel, styleLabel, sampleLabel, sampleText;
	JComboBox fonts, styles, sizes;
	JTextField sizeField;
	String fontList[] = GraphicsEnvironment.getLocalGraphicsEnvironment().getAvailableFontFamilyNames();
	String styleList[] = {"Plain", "Bold", "Italic", "Bold Italic"};
	int sizeList[] = {8,9,10,11,12,14,16,18,20,22,24,26,28,36,48,72};
	JButton ok, cancel, apply;
	
	//Declaration of components for about JFrame
	JFrame about = new JFrame();
	JPanel aboutPanel = new JPanel();
	JButton aboutOk;
	JLabel icon;
	JLabel info = new JLabel("<html>version: 1.0<br>Welcome to Notepad 2.0 created by Suvankar.<br>This is a simple yet enhanced version of our<br>OG Notepad. Fell free to use it as default if<br>you like using it. :D<br><br>All rights reserved @Suvankar");
	JLabel title = new JLabel("NOTEPAD 2.0");
	
	public notepad2()
	{
		setTitle("Notepad 2.0");
		setSize(800,500);
		setLocationRelativeTo(null);
		//ImageIcon appicon = new ImageIcon("appicon.png");
		
		textArea = new JTextArea();
		textArea.setFont(new Font("Consolas", Font.PLAIN, 16));
		textArea.setLineWrap(true);
		textArea.setWrapStyleWord(true);
		
		undo_redo = new UndoManager();
		textArea.getDocument().addUndoableEditListener(new UndoableEditListener()
		{
			public void undoableEditHappened(UndoableEditEvent e)
			{
				undo_redo.addEdit(e.getEdit());
			}
		});
		
		scrollPane = new JScrollPane(textArea, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		add(scrollPane);
		scrollPane.setBorder(BorderFactory.createEmptyBorder());
				
		createMenuBar();
		setKeyBoardShortcuts();
		setPopupMenu();
		
		createFontChooser();
		createAbout();
		
		sizes.setSelectedItem("16");
		fonts.setSelectedItem("Consolas");
		
		setIconImage(appicon.getImage());
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setVisible(true);
	}
	
	public void actionPerformed(ActionEvent ae)
	{
		if(ae.getSource() == f_new)
		{
			if(!textArea.getText().equals(""))
			{
				int action = JOptionPane.showConfirmDialog(this,"<html>Creating new file will delete any unsaved changes.<br>Do you want to continue?</html>","Creating New File",JOptionPane.YES_NO_OPTION,JOptionPane.PLAIN_MESSAGE);

				if(action == JOptionPane.YES_OPTION)
				{
					textArea.setText(null);
					setTitle("Notepad 2.0");
				}
			}
		}
		else if(ae.getSource()==f_open)
		{
			JFileChooser fileChooser = new JFileChooser();
			FileNameExtensionFilter txtFilter = new FileNameExtensionFilter("Text Documents (.txt)","txt");
			fileChooser.addChoosableFileFilter(txtFilter);
			
			int action = fileChooser.showOpenDialog(this);
			if(action!=JFileChooser.APPROVE_OPTION)
			{
				return;
			}
			else
			{
				try
				{
					BufferedReader reader = new BufferedReader(new FileReader(fileChooser.getSelectedFile()));
					textArea.read(reader,null);
					String fname = fileChooser.getSelectedFile().getName();
					setTitle("Notepad 2.0" + " ---> " +fname);
				}
				catch(IOException e)
				{
					System.out.println(e);
				}
			}
		}
		else if(ae.getSource()==f_save)
		{
			
			JFileChooser fileChooser = new JFileChooser();
			
			FileNameExtensionFilter txtFilter = new FileNameExtensionFilter("Text Document (.txt)","txt");
			fileChooser.addChoosableFileFilter(txtFilter);
			
			int action = fileChooser.showSaveDialog(this);
			
			if(action != JFileChooser.APPROVE_OPTION)
			{
				return;
			}
			else
			{
				String fileName = fileChooser.getSelectedFile().getAbsolutePath().toString();
				if(!fileName.contains(".txt"))
					fileName+=".txt";
				try
				{
					BufferedWriter writer = new BufferedWriter(new FileWriter(fileName));
					textArea.write(writer);
					String fname = fileChooser.getSelectedFile().getName();
					setTitle("Notepad 2.0" + " ---> " +fname);
				}
				catch(IOException e)
				{
					System.out.println(e);
				}
			}
		}
		else if(ae.getSource()==f_print)
		{
			try
			{
				textArea.print();
			}
			catch(PrinterException e)
			{
				System.out.println(e);
			}
		}	
		else if(ae.getSource()==f_exit)
		{
			int action = JOptionPane.showConfirmDialog(this,"Do you want to close the application?","Closing Notepad 2.0",JOptionPane.YES_NO_OPTION,JOptionPane.PLAIN_MESSAGE);
			if(action==JOptionPane.YES_OPTION)
			{
				System.exit(0);
			}
		}
		
		else if((ae.getSource()==e_copy) || (ae.getSource() == pop_copy))
		{
			textArea.copy();
		}
		else if((ae.getSource()==e_cut) || (ae.getSource()==pop_cut))
		{
			textArea.cut();
		}
		else if((ae.getSource()==e_paste) || (ae.getSource()==pop_paste))
		{
			textArea.paste();
		}
		else if((ae.getSource()==e_redo) || (ae.getSource()== pop_redo))
		{
			undo_redo.redo();	
		}
		else if((ae.getSource()==e_undo) || (ae.getSource()==pop_undo))
		{
			undo_redo.undo();
		}
		else if((ae.getSource()==e_selectall) || (ae.getSource()==pop_selectall))
		{
			textArea.selectAll();
		}
		
		else if(ae.getSource()==fr_font)
		{
			this.setEnabled(false);
			fontChoose.setVisible(true);
		}
		else if(ae.getSource()==fr_wordwrap)
		{
			if(wrap == false)
			{
				textArea.setLineWrap(true);
				textArea.setWrapStyleWord(true);
				fr_wordwrap.setText("Word Wrap: On      ");
				wrap = true;
			}
			else if(wrap == true)
			{
				textArea.setLineWrap(false);
				textArea.setWrapStyleWord(false);
				fr_wordwrap.setText("Word Wrap: Off      ");
				wrap = false;
			}
		}
		else if(ae.getSource()==theme_dark)
		{
			bg = new Color(30,30,30);
			fg = new Color(255,255,255);
			crt = new Color(255,255,255);
			textArea.setBackground(bg);
			textArea.setForeground(fg);
			textArea.setCaretColor(fg);
			aboutPanel.setBackground(bg);
			info.setForeground(fg);
			title.setForeground(fg);
			//fontLabel, sizeLabel, styleLabel, sampleLabel, sampleText;
			fontLabel.setForeground(fg);
			sizeLabel.setForeground(fg);
			styleLabel.setForeground(fg);
			sampleLabel.setForeground(fg);
			sampleText.setForeground(fg);
			
			fontPanel.setBackground(bg);
			sample.setBackground(bg);
			buttons.setBackground(bg);
		}
		else if(ae.getSource() ==theme_light)
		{
			bg = new Color(255,255,255);
			fg = new Color(0,0,0);
			crt = new Color(0,0,0);
			textArea.setBackground(bg);
			textArea.setForeground(fg);
			textArea.setCaretColor(fg);
			aboutPanel.setBackground(bg);
			info.setForeground(fg);
			title.setForeground(fg);
			
			fontLabel.setForeground(fg);
			sizeLabel.setForeground(fg);
			styleLabel.setForeground(fg);
			sampleLabel.setForeground(fg);
			sampleText.setForeground(fg);
			
			fontPanel.setBackground(bg);
			sample.setBackground(bg);
			buttons.setBackground(bg);
		}
		else if(ae.getSource() ==theme_pink)
		{
			bg = new Color(255,220,255);
			fg = new Color(0,0,0);
			crt = new Color(0,0,0);
			textArea.setBackground(bg);
			textArea.setForeground(fg);
			textArea.setCaretColor(fg);
			aboutPanel.setBackground(bg);
			info.setForeground(fg);
			title.setForeground(fg);
			
			fontLabel.setForeground(fg);
			sizeLabel.setForeground(fg);
			styleLabel.setForeground(fg);
			sampleLabel.setForeground(fg);
			sampleText.setForeground(fg);
			
			fontPanel.setBackground(bg);
			sample.setBackground(bg);
			buttons.setBackground(bg);
		}
		else if(ae.getSource() == theme_blue)
		{
			bg = new Color(181,213,255);
			fg = new Color(0,0,0);
			crt = new Color(0,0,0);
			textArea.setBackground(bg);
			textArea.setForeground(fg);
			textArea.setCaretColor(fg);
			aboutPanel.setBackground(bg);
			info.setForeground(fg);
			title.setForeground(fg);
			
			fontLabel.setForeground(fg);
			sizeLabel.setForeground(fg);
			styleLabel.setForeground(fg);
			sampleLabel.setForeground(fg);
			sampleText.setForeground(fg);
			
			fontPanel.setBackground(bg);
			sample.setBackground(bg);
			buttons.setBackground(bg);
		}
		else if(ae.getSource()==custom_bg)
		{
			bg = JColorChooser.showDialog(this,"Select background color",Color.white);
			textArea.setBackground(bg);
			aboutPanel.setBackground(bg);
			
			fontPanel.setBackground(bg);
			sample.setBackground(bg);
			buttons.setBackground(bg);
		}
		else if(ae.getSource()==custom_fg)
		{
			fg = JColorChooser.showDialog(this,"Select text color",Color.black);
			textArea.setForeground(fg);
			info.setForeground(fg);
			title.setForeground(fg);
			
			fontLabel.setForeground(fg);
			sizeLabel.setForeground(fg);
			styleLabel.setForeground(fg);
			sampleLabel.setForeground(fg);
			sampleText.setForeground(fg);
		}
		else if(ae.getSource()==custom_caret)
		{
			crt = JColorChooser.showDialog(this,"Select caret/cursor color",Color.black);
			textArea.setCaretColor(crt);
		}
		else if(ae.getSource()==h_about)
		{
			this.setEnabled(false);
			about.setVisible(true);
		}
		
		
		if(ae.getSource() == sizeField)
		{
			try
			{
				sizes.setSelectedItem(sizeField.getText());
				sampleText.setFont(new Font(fonts.getSelectedItem().toString(),styles.getSelectedIndex(),Integer.parseInt(sizeField.getText())));
			}
			catch(Exception e)
			{
				JOptionPane.showMessageDialog(null,"Please enter a number!","Invalid Input",2);
			}
		}
		
		if(ae.getSource() == ok)
		{
			textArea.setFont(sampleText.getFont());
			fontChoose.dispose();
			this.setEnabled(true);
			this.requestFocus();
		}
		else if(ae.getSource() == cancel)
		{
			fontChoose.dispose();
			this.setEnabled(true);
			this.requestFocus();
		}
		else if(ae.getSource() == apply)
		{
			textArea.setFont(sampleText.getFont());
		}
		
		if(ae.getSource() == aboutOk)
		{	
			this.setEnabled(true);
			about.dispose();
			this.requestFocus();
		}
		
	}
	
	public void itemStateChanged(ItemEvent ie)
	{
		sampleText.setFont(new Font(fonts.getSelectedItem().toString(),styles.getSelectedIndex(),Integer.parseInt(sizes.getSelectedItem().toString())));
		sizeField.setText(sizes.getSelectedItem().toString());
	}
	
	public void createMenuBar()
	{
		menuBar = new JMenuBar();
		menuBar.setBackground(new Color(230,230,230));
		
		file = new JMenu("File");
		edit = new JMenu("Edit");
		help = new JMenu("Help");
		format = new JMenu("Format");
		
		f_new = new JMenuItem("New      ");
		f_open = new JMenuItem("Open      ");
		f_save = new JMenuItem("Save      ");
		f_print= new JMenuItem("Print      ");
		f_exit = new JMenuItem("Exit      ");
		f_new.addActionListener(this);
		f_open.addActionListener(this);
		f_save.addActionListener(this);
		f_print.addActionListener(this);
		f_exit.addActionListener(this);
		file.add(f_new);
		file.add(f_open);
		file.add(f_save);
		file.addSeparator();
		file.add(f_print);
		file.addSeparator();
		file.add(f_exit);
		
		e_copy = new JMenuItem("Copy      ");
		e_cut = new JMenuItem("Cut      ");
		e_paste = new JMenuItem("Paste      ");
		e_undo = new JMenuItem("Undo      ");
		e_redo = new JMenuItem("Redo      ");
		e_selectall = new JMenuItem("Select All      ");
		e_copy.addActionListener(this);
		e_cut.addActionListener(this);
		e_paste.addActionListener(this);
		e_redo.addActionListener(this);
		e_selectall.addActionListener(this);
		e_undo.addActionListener(this);
		edit.add(e_cut);
		edit.add(e_copy);
		edit.add(e_paste);
		edit.addSeparator();
		edit.add(e_selectall);
		edit.addSeparator();
		edit.add(e_undo);
		edit.add(e_redo);
		
		fr_wordwrap = new JMenuItem("Word Wrap: On      ");
		fr_font = new JMenuItem("Font..      ");
		fr_font.addActionListener(this);
		fr_wordwrap.addActionListener(this);
		format.add(fr_wordwrap);
		format.add(fr_font);
		format.addSeparator();
		
		theme = new JMenu("Theme      ");
		format.add(theme);
		
		theme_light = new JMenuItem("Light Mode      ");
		theme_dark = new JMenuItem("Dark Mode      ");
		theme_pink = new JMenuItem("Cute Pink      ");
		theme_blue = new JMenuItem("Ocean Blue      ");
		
		theme_dark.addActionListener(this);
		theme_light.addActionListener(this);
		theme_pink.addActionListener(this);
		theme_blue.addActionListener(this);
		theme.add(theme_light);
		theme.add(theme_dark);
		theme.add(theme_pink);
		theme.add(theme_blue);
		
		custom = new JMenu("Set Custom Theme");
		theme.add(custom);
		
		custom_bg = new JMenuItem("Background Color");
		custom_fg = new JMenuItem("Text Color");
		custom_caret = new JMenuItem("Caret/Cursor Color");
		custom.add(custom_bg);
		custom.add(custom_fg);
		custom.add(custom_caret);
		custom_bg.addActionListener(this);
		custom_caret.addActionListener(this);
		custom_fg.addActionListener(this);
		
		h_about = new JMenuItem("About Notepad 2.0      ");
		h_about.addActionListener(this);
		help.add(h_about);
		
		menuBar.add(file);
		menuBar.add(edit);
		menuBar.add(format);
		menuBar.add(help);
		
		setJMenuBar(menuBar);
	}
	
	public void setPopupMenu()
	{
		pop_cut = new JMenuItem("Cut      ");
		pop_copy = new JMenuItem("Copy      ");
		pop_paste = new JMenuItem("Paste      ");
		pop_selectall = new JMenuItem("Select All      ");
		pop_undo = new JMenuItem("Undo      ");
		pop_redo = new JMenuItem("Redo      ");
		
		pop_cut.addActionListener(this);
		pop_copy.addActionListener(this);
		pop_paste.addActionListener(this);
		pop_selectall.addActionListener(this);
		pop_undo.addActionListener(this);
		pop_redo.addActionListener(this);
		
		pmenu = new JPopupMenu();
		pmenu.add(pop_cut);
		pmenu.add(pop_copy);
		pmenu.add(pop_paste);
		pmenu.addSeparator();
		pmenu.add(pop_selectall);
		pmenu.addSeparator();
		pmenu.add(pop_undo);
		pmenu.add(pop_redo);
		
		textArea.add(pmenu);
		textArea.addMouseListener(new MouseAdapter()
		{
			public void mouseReleased(MouseEvent e)
			{
				
				if(textArea.getSelectedText() != null)
				{
					pop_cut.setEnabled(true);
					pop_copy.setEnabled(true);
				}
				else
				{
					pop_cut.setEnabled(false);
					pop_copy.setEnabled(false);
				}
				
				if(undo_redo.canRedo())
				{
					pop_redo.setEnabled(true);
				}
				else{
					pop_redo.setEnabled(false);
				}
				
				if(undo_redo.canUndo())
				{
					pop_undo.setEnabled(true);
				}
				else
				{
					pop_undo.setEnabled(false);
				}
				
				if(e.getButton() == MouseEvent.BUTTON3)
					pmenu.show(notepad2.this,e.getX(),e.getY());
			}
		});
	}
	
	public void setKeyBoardShortcuts()
	{
		f_new.setAccelerator(KeyStroke.getKeyStroke("control N"));
		f_open.setAccelerator(KeyStroke.getKeyStroke("control O"));
		f_save.setAccelerator(KeyStroke.getKeyStroke("control S"));
		f_print.setAccelerator(KeyStroke.getKeyStroke("control P"));
		
		e_cut.setAccelerator(KeyStroke.getKeyStroke("control X"));
		e_copy.setAccelerator(KeyStroke.getKeyStroke("control C"));
		e_paste.setAccelerator(KeyStroke.getKeyStroke("control V"));
		e_selectall.setAccelerator(KeyStroke.getKeyStroke("control A"));
		e_undo.setAccelerator(KeyStroke.getKeyStroke("control Z"));
		e_redo.setAccelerator(KeyStroke.getKeyStroke("control Y"));
		
		fr_wordwrap.setAccelerator(KeyStroke.getKeyStroke("control W"));
		fr_font.setAccelerator(KeyStroke.getKeyStroke("control F"));
		
		theme_light.setAccelerator(KeyStroke.getKeyStroke("control shift 1"));
		theme_dark.setAccelerator(KeyStroke.getKeyStroke("control shift 2"));
		theme_pink.setAccelerator(KeyStroke.getKeyStroke("control shift 3"));
		theme_blue.setAccelerator(KeyStroke.getKeyStroke("control shift 4"));
		
		h_about.setAccelerator(KeyStroke.getKeyStroke("control I"));
	}
	
	public void createFontChooser()
	{
		
		fontChoose.setTitle("Font Chooser");
		fontChoose.setSize(410,360);
		fontChoose.setResizable(false);
		fontChoose.setLocationRelativeTo(null);
		fontPanel.setLayout(null);
		
		fontChoose.setAlwaysOnTop(true);
		
		fontChoose.setIconImage(appicon.getImage());
			
		fontLabel = new JLabel("Font:");
		fontLabel.setBounds(50,220,60,30);
		fontPanel.add(fontLabel);
		
		fonts = new JComboBox();
		for(int i=0; i<fontList.length; i++)
		{
			fonts.addItem(fontList[i]);
		}
		fonts.setBounds(90,225,260,20);
		fontPanel.add(fonts);
		fonts.addItemListener(this);
		
		styleLabel = new JLabel("Style:");
		styleLabel.setBounds(50,180,60,30);
		fontPanel.add(styleLabel);
			
		styles = new JComboBox();
		for(int i=0;i<styleList.length; i++)
		{
			styles.addItem(styleList[i]);
		}
		styles.setBounds(90,185,100,20);
		fontPanel.add(styles);
		styles.addItemListener(this);
			
		sizeLabel = new JLabel("Size:");
		sizeLabel.setBounds(210,180,60,30);
		fontPanel.add(sizeLabel);		
			
		sizes = new JComboBox();	
		for(int i=0; i<sizeList.length; i++)
		{
			sizes.addItem(Integer.toString(sizeList[i]));
		}
		sizes.setBounds(250,185,50,20);
		fontPanel.add(sizes);
		sizes.addItemListener(this);
		
			
		sizeField = new JTextField(5);
		sizeField.setBounds(310,185,40,20);
		fontPanel.add(sizeField);
		sizeField.addActionListener(this);
			
		sampleLabel = new JLabel("Sample:");
		sampleLabel.setBounds(70,20,70,30);
		fontPanel.add(sampleLabel);
			
		sample.setBorder(BorderFactory.createLineBorder(new Color(190,190,190)));
		sample.setBounds(50,45,300,120);
		sample.setLayout(new GridBagLayout());
		fontPanel.add(sample);
				
		sampleText = new JLabel("FONT STYLE");
		sample.add(sampleText);
			
		ok = new JButton("OK");
		cancel = new JButton("CANCEL");
		apply = new JButton("APPLY");
		ok.addActionListener(this);
		cancel.addActionListener(this);
		apply.addActionListener(this);
			
		buttons.setBounds(130,280,300,40);
		buttons.setLayout(new FlowLayout());
		fontPanel.add(buttons);
			
		buttons.add(ok);
		buttons.add(cancel);
		buttons.add(apply);
		
		fontChoose.add(fontPanel);
			
		fontChoose.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
	}
	
	public void createAbout()
	{
		about.setTitle("About Notepad 2.0");
		about.setSize(500,360);
		about.setLocationRelativeTo(this);
		about.setResizable(false);
		aboutPanel.setLayout(null);
		
		about.setAlwaysOnTop(true);
	
		JLabel icon = new JLabel(appicon);
		
		title.setFont(new Font("Times New Roman",1,30));
		title.setBounds(150,20,250,50);
		aboutPanel.add(title);
		
		about.setIconImage(appicon.getImage());
		icon.setBounds(45,100,100,100);
		aboutPanel.add(icon);
		
		info.setFont(new Font("Ariel",Font.PLAIN,12));
		info.setBounds(170,75,300,150);
		aboutPanel.add(info);
		
		aboutOk = new JButton("OK");
		aboutOk.setBounds(400,280,70,30);
		aboutOk.addActionListener(this);
		aboutPanel.add(aboutOk);
		
		about.add(aboutPanel);
		
		about.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		
		
	}
	
	public static void main(String s[]) throws Exception 
	{
		UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		notepad2 ob = new notepad2();
	}
	
}