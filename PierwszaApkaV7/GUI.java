	// I created this app to "automate boring stuff". Feel free do edit and make it better
	//

	
	// TODO - ADDfiltr dla file chooser tylko do plikow xlsx
	// TODO - FIX countdown timer STOP button
	// TODO - add tab
	

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.File;

import javax.swing.filechooser.FileSystemView;
import javax.swing.text.StyledEditorKit.FontFamilyAction;
import javax.swing.filechooser.FileFilter;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.event.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;


import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.ss.util.CellAddress;


import java.awt.event.*;
import java.util.TimerTask;
import java.util.Timer;
import java.awt.Color;
import java.awt.Button;


public class GUI {
	// zmienne stopera
	
	JButton start;
	JButton stop;
	JLabel display;
	JLabel msg;
	JTextField sekundy;
	JTextField minuty;
	JPanel stoper;
    
    //// zmienne wrzucacza
	
		
	private JFrame ramka;
	private JPanel panelGlowny;

	private JTextField pole5card;
	private JTextField pole4card;
	private JButton dodajHanda;
	private JButton dodajHanda5c;
	private JButton odpalPlik;
	private JTextField info;
	
	private static String sciezkaDoPliku;
	private static String sciezkaDoMuzyki = new String ("/home/rd/ECLIPSE - Stuff/eclipse/dwa/ApkaPoker/src/lew.mp3");
	
	
	private static JFileChooser wybierzPlik;
	private static Workbook wb;
	private static Sheet sh;
	private static FileInputStream fis;
	private static FileOutputStream fos;
	private static Row row;
	private static Cell cell;
	
	private static Timer timer;
	private static JTextField status;
	
	private static JTextField arkuszStatus;
	private static JTextField mp3Status;
	private static JMenuBar menuBar;
	
	
	public static void main (String[] args ) {
		GUI app = new GUI();
		app.doDziela();
		
		
		
	}
	
	public void doDziela() {
		
		
		
		
		/////////////////////// PANEL GLOWNY + RAMKA
		
		ramka = new JFrame();
		ramka.getContentPane().setBackground(Color.DARK_GRAY);
		panelGlowny = new JPanel();
		panelGlowny.setBounds(0, 0, 625, 35);
		panelGlowny.setBackground(Color.DARK_GRAY);
		pole4card = new JTextField(10);
		pole4card.setBounds(68, 8, 124, 19);
		
		pole5card = new JTextField(10);
		pole5card.setBounds(66, 47, 124, 19);
		
		info = new JTextField(20);
		info.setBounds(190, 35, 244, 19);
		JLabel label4c = new JLabel ("4card");
		label4c.setForeground(Color.WHITE);
		label4c.setBounds(12, 10, 54, 15);
		
		dodajHanda = new JButton ("Dodaj 4c");
		dodajHanda.setBackground(Color.WHITE);
		dodajHanda.setForeground(Color.BLACK);
		dodajHanda.setBounds(204, 5, 92, 25);
		dodajHanda.addActionListener(new DodajHandaListener() );
				
		
		
		//odpalPlik.addActionListener(new OdpalPlikListener() );
		
				
		
		
		
		////////////////// PANEL STOPER//////////////////
		stoper = new JPanel();
		stoper.setBounds(10, 73, 284, 35);
		stoper.setBackground(Color.DARK_GRAY);
		start = new JButton ("START");
		start.setBackground(Color.WHITE);
		msg = new JLabel("Ustaw czas");
		sekundy = new JTextField();
		sekundy.setText("00");
		stoper.setLayout(null);
		minuty = new JTextField();
		minuty.setText("00");
				
		stoper.add(minuty);
		stoper.add(sekundy);
		stoper.add(start);
		
		sekundy.setBounds(51, 5, 44, 34);
		minuty.setBounds(0, 5, 39, 34);
		start.setBounds(107,5,77,25);
		
		start.addActionListener(new StartListener() );
		
		Font czcionka = new Font ("Arial",Font.BOLD,25);
		
		sekundy.setFont(czcionka);
		minuty.setFont(czcionka);
		ramka.getContentPane().setLayout(null);
		panelGlowny.setLayout(null);
        
										
		panelGlowny.add(label4c);
		panelGlowny.add(pole4card);
		panelGlowny.add(dodajHanda);
		panelGlowny.add(info);
		
		ramka.getContentPane().add(panelGlowny);
		ramka.getContentPane().add(stoper);
		stop = new JButton ("STOP");
		stop.setBounds(191, 5, 93, 25);
		stoper.add(stop);
		stop.setBackground(Color.WHITE);
		stop.addActionListener(new StopListener());
		display = new JLabel();
		display.setBounds(10, 109, 106, 54);
		display.setForeground(Color.GREEN);
		ramka.getContentPane().add(display);
		display.setText("00:00");
		display.setFont(czcionka);
		
		JLabel label5c = new JLabel ("5card");
		label5c.setBounds(10, 47, 55, 14);
		label5c.setForeground(Color.WHITE);
		ramka.getContentPane().add(label5c);
		ramka.setVisible(true);
				
					
	
		ramka.getContentPane().add(pole5card);
		
		dodajHanda5c = new JButton ("Dodaj 5c");
		dodajHanda5c.setBounds(202, 36, 92, 30);
		dodajHanda5c.addActionListener(new DodajHanda5cListener() );
		dodajHanda5c.setBackground(Color.WHITE);
		dodajHanda5c.setForeground(Color.BLACK);
		ramka.getContentPane().add(dodajHanda5c);
		
		status = new JTextField();
		status.setBounds(113, 120, 55, 19);
		ramka.getContentPane().add(status);
		status.setColumns(10);
		
		JButton btnReset = new JButton("Reset");
		btnReset.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent zd) {
				minuty.setText("00");
				sekundy.setText("00");
				display.setText("00:00");
				timer.cancel();
			}
		});
		btnReset.setBounds(180, 120, 117, 25);
		ramka.getContentPane().add(btnReset);
		
		
	
		ramka.setSize(300,200);
		ramka.setVisible(true);
		
		
		menuBar = new JMenuBar();
		ramka.setJMenuBar(menuBar);
		
		JMenu mnPlik = new JMenu("Plik");
		menuBar.add(mnPlik);
		
		JMenuItem mntmOtwrzPlik = new JMenuItem("Otwórz plik");
		mntmOtwrzPlik.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent click) {
				wybierzPlik = new JFileChooser();
				sciezkaDoPliku = new String();
				
				if (wybierzPlik.showOpenDialog(wybierzPlik) == JFileChooser.APPROVE_OPTION) {
					sciezkaDoPliku = wybierzPlik.getSelectedFile().getAbsolutePath();
					System.out.println(sciezkaDoPliku);
					arkuszStatus.setText(sciezkaDoPliku);
				}
				
			}
		});
		mnPlik.add(mntmOtwrzPlik);
		
		JMenu mnNewMenu = new JMenu("Status");
		menuBar.add(mnNewMenu);
		
		arkuszStatus = new JTextField();
		mnNewMenu.add(arkuszStatus);
		arkuszStatus.setColumns(10);
		
		mp3Status = new JTextField();
		mnNewMenu.add(mp3Status);
		mp3Status.setColumns(10);
		odpalPlik = new JButton ("Sciezka do pliku");
		odpalPlik.setBounds(267, 83, 144, 25);
		
		
		//odpalPlik.setBackground(Color.WHITE0);
			
	}//koniec doDziela
	

	class DodajHanda5cListener implements ActionListener {
		public void actionPerformed (ActionEvent zd) {
			try {
				fis = new FileInputStream(sciezkaDoPliku);
				wb = WorkbookFactory.create(fis);
				sh = wb.getSheet("plo5c");
				int rows = sh.getLastRowNum();
				int dobreRows = rows + 1;
				
				System.out.println("dupa");
										
				int colID = 1;
					
				row = sh.createRow(dobreRows);
				cell = row.createCell(colID);
				cell.setCellValue(pole5card.getText()); 
				
				fos = new FileOutputStream(sciezkaDoPliku);
				wb.write(fos);
				fos.flush();
				fos.close();
				
				info.setText("Done");
		
			}catch(Exception ex) {
				info.setText("Error");
			}
		}
	}
	
	
	
	
	class DodajHandaListener implements ActionListener {
		public void actionPerformed (ActionEvent zd) {
			try {
				fis = new FileInputStream(sciezkaDoPliku);
				wb = WorkbookFactory.create(fis);
				sh = wb.getSheet("plo4c");
				int rows = sh.getLastRowNum();
				int dobreRows = rows + 1;
										
				int colID = 1;
					
				row = sh.createRow(dobreRows);
				cell = row.createCell(colID);
				cell.setCellValue(pole4card.getText()); 
				
				fos = new FileOutputStream(sciezkaDoPliku);
				wb.write(fos);
				fos.flush();
				fos.close();
				
				info.setText("Done");
		
			}catch(Exception ex) {
				info.setText("Nie wyszlo");
			
			}
		
	}
}

	public class StartListener implements ActionListener{
		
		public void actionPerformed(ActionEvent e) {
			timer = new Timer();	
			timer.scheduleAtFixedRate(new TimerTask() {
			int	secs = Integer.parseInt(sekundy.getText());
			int	mins = Integer.parseInt(minuty.getText());
				
				@Override
				public void run() {
										
					display.setText(mins + ":" + (secs--));
					status.setText("Hula");
					
					if (mins >= 1 && secs <= 0) {
						mins --;
						secs = 59;
					}
					
					if (mins == 0 && secs < 0) {
						timer.cancel();
						display.setText("Koniec");
						status.setText("Koniec");
						ZagrajMuzyczke.playMusic(sciezkaDoMuzyki);
					}
					
					
				}
			}, 0, 1000);
			
					
		}
		
	}
	
	public class StopListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			status.setText("Zatrzymano");
			
			timer.cancel();
															
		}
	}
}
	
	

	
 

