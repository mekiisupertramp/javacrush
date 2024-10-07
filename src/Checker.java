/*==========================================================================================
  ==========================================================================================
	Fichier : Checker.java

   Descritpion: Définit le cadre général des sous-classes permettant de vérifier les cases
   				contenues dans le jeu CandyCrush.
	
	Authors : Mehmed Blazevic
	
	Date : December 2016

	Version: 1.0

=*========================================================================================*/

public abstract class Checker implements Runnable {
	
	// ---- Champs -----------------------------------------------------
	protected static final String[] letter = {"bird.png", "cricket.png",
				"elephant.png", "penguin.png", "dolphin.png", "cat.png", 
			   		 "jelly_fish.png", "gnome_panel_fish.png","pig.png", 
				 					 "dragon_fly.png","kbugbuster.png"};
	
	protected CandyButton game[];	// le plateau de jeu
	protected int cpt;				// nombre de cases vide
	protected int taille;			// la taille du jeu
	protected int points;			// le nombre de points
	protected boolean finish;		// variable permettant de finir les threads
	
	/****************************************************************
	 * compte le nombre de points
	 ***************************************************************/
	protected void cptPoints(){
		if(cpt == 3) points+=100;
		else if(cpt == 4) points +=300;
		else if(cpt == 5) points +=500;
		else if(cpt == 6) points +=1000;
	}
	
	/****************************************************************
	 * supprimer les cases à une position donnée
	 * @param pos : la position à laquelle la suppression commence
	 * @param nbr : le nombre de cases à supprimer
	 ***************************************************************/
	protected abstract void supprimer(int pos, int nbr);
	
	/****************************************************************
	 * compte le nombre de cases à supprimer
	 * @param pos : la position à laquelle on commence à compter
	 ***************************************************************/
	protected abstract void comptage(int pos);
	
	/****************************************************************
	 * récupère le nombre de points
	 * @return le nombre de points
	 ***************************************************************/
	public int getPoints(){return this.points;}
	
	/****************************************************************
	 * prévient les threads lorsqu'il faut finir
	 * @param fin : met fin aux threads
	 ***************************************************************/
	public void setFinish(boolean fin){this.finish = fin;}
}