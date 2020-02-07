import java.io.FileInputStream;
import java.io.FileNotFoundException;

import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.Player;


public class ZagrajMuzyczke {
	
	public static void main (String[] args) {
		
	}
		
		public static void playMusic (String filepath) {
		
		try {
			FileInputStream music = new FileInputStream (filepath);
			Player player = new Player (music);
			player.play();
			System.out.println ("Gra");
			
		}catch(JavaLayerException e) {
			System.out.println("Zly plik");
			e.printStackTrace();
		}catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
	}
}
