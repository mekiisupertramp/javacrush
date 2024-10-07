/*==========================================================================================
  ==========================================================================================
	Fichier : CandyHorChecker.java

   Descritpion: Permet de balayer le jeu et de vérifier s'il y a des cases horizontales à
   				supprimer. Incrémente le nombre de points
	
	Authors : Mehmed Blazevic
	
	Date : December 2016

	Version: 1.0

=*========================================================================================*/


public class CandyHorChecker extends Checker{	
	
	/****************************************************************
	 * constructeur de la classe CandyHorChecker
	 * @param vue : le tableau de boutons représentant le jeu
	 * @param nbr : la taille du terrain de jeu
	 ***************************************************************/
	public CandyHorChecker(CandyButton[] vue, int nbr){
		game = vue;
		taille = nbr;
	}
	
	/****************************************************************
	 * méthode permettant de faire la suppression horizontale, en se 
	 * synchronisant sur la plateau de jeu, et compte le nombre de 
	 * points
	 ***************************************************************/
	@Override
	public void run() {			
		while(!finish){
			cpt = 1;
			// balayage du plateau de jeu
			for(int li=0 ; li<taille*taille ; li+=taille){
				for(int col=0 ; col <taille; col++){
					synchronized (game) {
						if(game[li+col].getCandyName() != null){
							// si on dépasse pas horizontalement
							if((li+col+1)<li+taille){
								comptage(li+col);
							}
						}
						cpt = 1;
					}
				}
			}
			// sauve l'ordinateur de la destruction
			try{
				Thread.sleep(20);
			}catch(InterruptedException e){}
		}
	}
	
	// --- méthodes abstraites implémentées ----------------------------------------
	protected void supprimer(int pos, int nbr){
		for(int i=pos ; i<pos+nbr ; i++){
			game[i].removeCandyBtn();
		}
	}	
	protected void comptage(int pos){
		// si deux cases sont identiques
		while(game[pos].getCandyName() == game[pos+cpt].getCandyName()){
			cpt++;
			if((pos+cpt+1) > (pos/taille)*taille+taille) break;
		}
		if(cpt>2){
			cptPoints();
			supprimer(pos,cpt);								
		}
	}
}