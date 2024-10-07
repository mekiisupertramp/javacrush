/*==========================================================================================
  ==========================================================================================
	Fichier : VueCrush.java

   Descritpion: Classe permettant de jouer à un jeu style CandyCrush multithreadé. Tous les
   				différents threads sont implémentés ici.
	
	Authors : Mehmed Blazevic
	
	Date : December 2016

	Version: 1.0

=*========================================================================================*/

import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;
import javax.swing.Timer;
import javax.swing.event.AncestorEvent;
import javax.swing.event.AncestorListener;

public class VueCrush extends JPanel implements ActionListener{
	
	// ---- Champs -----------------------------------------------------
	protected static final String[] letter = {"bird.png", "cricket.png",
				"elephant.png", "penguin.png", "dolphin.png", "cat.png", 
			   		 "jelly_fish.png", "gnome_panel_fish.png","pig.png", 
			   		 				"dragon_fly.png", "kbugbuster.png"};
	private static Integer taille = 10;	// la taille du plateau de jeu
	private CandyButton[] btn;	// le tableau de boutons
	
	private int cptClick=0; 	// compte le nombre de clicks
	private int pos1;			// position du premier bouton clické
	private int pos2;			// position du second bouton clické
	private int cptTemps;		// durée du jeu en secondes
	
	private CandyHorChecker hChecker;	// gestion lignes
	private CandyVerChecker vChecker;	// gestion colonnes
	private CheckerGravity gChecker;	// gestion de la gravité
	private Timer timer;				// base de temps
	
	/****************************************************************
	 * constructeur de la classe VueCrush
	 * @param rnd : générateur aléatoire pour la disposition des btns
	 * @param second : la durée de la partie en secondes
	 ***************************************************************/
	public VueCrush(Random rnd, int second){
		// utilisation d'un GridLayout comme "layout"
		super(new GridLayout(taille,taille));
		cptTemps = second;
		btn = new CandyButton[taille*taille];
		String buffer;
		// initialisation du plateau de boutons
		for(int j=0;j<taille*taille;j++){ 
			buffer = letter[rnd.nextInt(letter.length)];
			btn[j] = new CandyButton(new ImageIcon(new
				ImageIcon(getClass().getClassLoader().
				getResource(buffer)).getImage().
				getScaledInstance(60, 60, Image.SCALE_DEFAULT)));
			btn[j].setName(String.valueOf(j));
			btn[j].addActionListener(this);
			btn[j].setCandyName(buffer); // ajout du nom de l'animal
			// enregistrement du listener
			this.add(btn[j]);	// ajout du bouton à ce JPanel
		}	
		
		// instanciation des "checkeurs"
		hChecker = new CandyHorChecker(btn,taille);
		vChecker = new CandyVerChecker(btn,taille);
		gChecker = new CheckerGravity(btn,taille,rnd);
		
		// création des threads
		new Thread(hChecker).start();		
		new Thread(vChecker).start();
		new Thread(gChecker).start();
		timer = new Timer(1000,this);
		timer.start();

		// ajout d'un listener sur le contenant de VueCrush (le JFrame)
		this.addAncestorListener(finish());
	}
		
	/****************************************************************
	 * gestion de tous les énévement qui surviennent
	 * ev1 : les boutons, lorsqu'on veut bouger les boutons
	 * ev2 : le timer, comptage du temps
	 ***************************************************************/
	public void actionPerformed(ActionEvent e){	
		// déplacement des boutons
		if(e.getSource().getClass() == btn[0].getClass()){
			if(this.cptClick == 0){
				this.cptClick++;
				this.pos1 = Integer.parseInt(((CandyButton)
											e.getSource()).getName());
			} else if(this.cptClick == 1){
				this.cptClick = 0;
				this.pos2 = Integer.parseInt(((CandyButton)
											e.getSource()).getName());
				if(checkPos(pos1,pos2)){
					synchronized (btn) {
						btn[this.pos1].invertCandy(btn[this.pos2]);
					}
				}	
			}
		}
		// le timer
		else if(e.getSource().getClass() == Timer.class){
			cptTemps--;			
			if(cptTemps == 0){	
				timer.stop();
				// on ferme le JFrame lorsque la partie est fini
				JFrame frame = (JFrame) 
							SwingUtilities.getWindowAncestor(this);
				frame.dispose();				
			}
		}
	}
	
	/****************************************************************
	 * vérifie si on peut inverser deux boutons côte à côte
	 * @param pos1 : la position du premier bouton cliqué
	 * @param pos2 : la position du second bouton cliqué
	 * @return si le déplacement est autorisé (boutons côte à côte)
	 ***************************************************************/
	private boolean checkPos(int pos1,int pos2){
		int p1Li = pos1/taille;
		int p1Co = pos1%taille;
		int p2Li = pos2/taille;
		int p2Co = pos2%taille;
		
		if((Math.abs(p1Li-p2Li)==1) && (Math.abs(p1Co-p2Co)==0)) {
			return true;
		}else{
			if((Math.abs(p1Co-p2Co)==1) && (Math.abs(p1Li-p2Li)==0)){
				return true;
			}
		}
		return false;
	}
	
	/****************************************************************
	 * création d'un listener sur le contenant du VueCrush, c-à-d
	 * sur le JFrame, cela permet de mettre fin à la partie 
	 * proprement (sois clique sur le croix, sois timeout)
	 * @return le listener sur le JFrame
	 ***************************************************************/
	private AncestorListener finish(){
		return new AncestorListener() {
			@Override
			public void ancestorRemoved(AncestorEvent event) {
				timer.stop();
				hChecker.setFinish(true);
				vChecker.setFinish(true);
				gChecker.setFinish(true);
				JOptionPane.showMessageDialog(null,  
						"Fini ! Points : " + (hChecker.getPoints()
											+vChecker.getPoints()));
			}
			@Override
			public void ancestorMoved(AncestorEvent event) {}
			@Override
			public void ancestorAdded(AncestorEvent event) {}
		};
	}
}