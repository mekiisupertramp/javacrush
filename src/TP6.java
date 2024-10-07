/*==========================================================================================
  ==========================================================================================
	Fichier : TP6.java

   Descritpion: Programme principal permettant de créer un jeu CandyCrush. L'utilisateur
   				peut régler le temps de jeu, grâce au constructeur de la classe VueCrush.
   				Le jeu affiche un tableau de 10x10 animaux.
	
	Authors : Mehmed Blazevic
	
	Date : December 2016

	Version: 1.0

=*========================================================================================*/

import java.util.*;
import javax.swing.JFrame;

public class TP6 {

	public static void main(String[] args) {
		Random rnd = new Random(System.currentTimeMillis());
		int sec = 600;
		VueCrush vue = new VueCrush(rnd,sec);
		
		JFrame f = new JFrame();
		f.setTitle("JavaCrush");
		f.getContentPane().add(vue);				
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		f.setSize(650,650);
		f.setVisible(true);
	}
}
