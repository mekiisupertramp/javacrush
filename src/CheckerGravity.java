/*==========================================================================================
  ==========================================================================================
	Fichier : CheckerGravity.java

   Descritpion: Balaye le jeu, descend les animaux lorsqu'il y a des cases supprimées et 
   				génère de nouvelles cases.
	
	Authors : Mehmed Blazevic
	
	Date : December 2016

	Version: 1.0

=*========================================================================================*/

import java.awt.Image;
import java.util.Random;
import javax.swing.ImageIcon;

public class CheckerGravity extends Checker{
	
	// ---- Champs --------------------------------------------------
	private int deplace;	// permet de faire le déplacement verticale
	private Random rnd;		// permet de génerer les nouvelles cases

	/****************************************************************
	 * constructeur de la classe CheckerGravity
	 * @param vue : le tableau de boutons représentant le jeu
	 * @param nbr : la taille du terrain de jeu
	 * @param rand: générateur aléatoire d'animaux
	 ***************************************************************/
	public CheckerGravity(CandyButton[] btn, int nbr,Random rand){
		game = btn;
		taille = nbr;
		this.rnd = rand;
	}

	/****************************************************************
	 * méthode permettant de faire de descendre les animaux après
	 * une suppression et ajout de nouveaux animaux, en se 
	 * synchronisant sur la plateau de jeu
	 ***************************************************************/
	@Override
	public void run() {
		while(!finish){
			deplace = taille;
			// balayage de chaque colonne de bas en haut
			for(int co=0 ; co<taille ; co++){
				for(int li=taille*taille-taille ; li>=0 ; li-=taille){
					synchronized (game) {
						// si on dépasse pas verticalement (première ligne)
						if((li+co-deplace)>=0){
							if(game[(li+co)].getCandyName() == null){							
								descente(li+co);
							}
						}
						if((li==0) && (game[li+co].getCandyName() == null)){							
							remplir(co); // remplit la première ligne, si libre
						}
						deplace = taille;
					}					
				}				
			}
			// sauve l'ordinateur de la destruction
			try{
				Thread.sleep(20);
			}catch(InterruptedException e){}
		}
	}
	/****************************************************************
	 * permet de remplir le tableau à partir de la première ligne
	 * @param col : la colonne que l'on souhaite remplir
	 ***************************************************************/
	private void remplir(int col){
		String buffer = letter[rnd.nextInt(letter.length)];		
		game[col].setIcon(new ImageIcon(new
				ImageIcon(getClass().getClassLoader().
				getResource(buffer)).getImage().
				getScaledInstance(60, 60, Image.SCALE_DEFAULT)));
		game[col].setCandyName(buffer);
	}
	
	/****************************************************************
	 * descent les animaux lorsqu'il y a des cases vides
	 * @param pos : la position à laquelle on descend les cases
	 ***************************************************************/
	private void descente(int pos){
		int li = pos;
		while((game[li].getCandyName() == null) && (li > taille-1)){
			game[li].invertCandy(game[li-taille]);
			li-=taille;
		}
	}

	// --- Méthodes non utilisées -------------------------------------------------
	@Override
	protected void supprimer(int pos, int nbr) {}
	@Override
	protected void comptage(int pos) {}
	
}