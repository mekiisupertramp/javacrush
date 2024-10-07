/*==========================================================================================
  ==========================================================================================
	Fichier : CandyVerChecker.java

   Descritpion: Permet de balayer le jeu et de vérifier s'il y a des cases verticales à
   				supprimer. Incrémente le nombre de points
	
	Authors : Mehmed Blazevic
	
	Date : December 2016

	Version: 1.0

=*========================================================================================*/


public class CandyVerChecker extends Checker{
	
	// ---- Champs --------------------------------------------------
	private int deplace;	// permet de faire le déplacement verticale
	
	/****************************************************************
	 * constructeur de la classe CandyHVerChecker
	 * @param vue : le tableau de boutons représentant le jeu
	 * @param nbr : la taille du terrain de jeu
	 ***************************************************************/
	public CandyVerChecker(CandyButton[] vue, int nbr){
		game = vue;
		taille = nbr;
	}
	
	/****************************************************************
	 * méthode permettant de faire la suppression verticale, en se 
	 * synchronisant sur la plateau de jeu, et compte le nombre de 
	 * points
	 ***************************************************************/
	@Override
	public void run() {	
		while(!finish){
			cpt = 1;
			deplace = taille;
			// balayage du plateau de jeu
			for(int li=0 ; li<taille*taille ; li+=taille){
				for(int col=0 ; col <taille ; col++){
					synchronized (game) {
						if(game[li+col].getCandyName() != null){
							// si on dépasse pas verticalement
							if((li+col+deplace)<taille*taille){
								comptage(li+col);
							}
						}
						deplace = taille;
						cpt = 1;
					}
				}
			}
			// sauve l'ordinateur de la destruction
			try{
				Thread.sleep(200);
			}catch(InterruptedException e){}
		}
	}
	
	// --- méthodes abstraites implémentées ----------------------------------------
	protected void supprimer(int pos, int nbr){
		for(int i=0 ; i<nbr ; i++){
			game[pos+(i*taille)].removeCandyBtn();
		}
	}
	protected void comptage(int pos){
		// si deux cases sont identiques
		while(game[pos].getCandyName() == game[pos+deplace].getCandyName()){								
			cpt++;								
			if((pos+deplace+taille) >= taille*taille) break;
			deplace+=taille;
		}
		if(cpt>2){	
			cptPoints();
			supprimer(pos,cpt);							
		}
	}
}
