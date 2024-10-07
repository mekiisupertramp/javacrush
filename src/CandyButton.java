/*==========================================================================================
  ==========================================================================================
	Fichier : CandyButton.java

   Descritpion: Classe permettant de créer un bouton spécial pour le jeu CandyCrush.
	
	Authors : Mehmed Blazevic
	
	Date : December 2016

	Version: 1.0

=*========================================================================================*/
import javax.swing.*;

public class CandyButton extends JButton {
	
	// ---- Champs --------------------------------------------------
	private String candyName;	// contient le nom de l'animal
	
	/****************************************************************
	 * constructeur de la classe CandyButton
	 * @param ic
	 ***************************************************************/
	public CandyButton(ImageIcon ic){
		super(ic);
		candyName = new String();
	}	
	
	/****************************************************************
	 * permet de suprimer un bouton (efface l'animal)
	 ***************************************************************/
	public void removeCandyBtn(){
		this.setCandyName(null);
		this.setIcon(null);
	}
	/****************************************************************
	 * inverse deux animaux de deux boutons
	 * @param btn : le bouton avec lequel on change l'animal
	 ***************************************************************/
	public void invertCandy(CandyButton btn){
		Icon buff = btn.getIcon();
		String cName = btn.getCandyName();
		btn.setIcon(this.getIcon());
		btn.setCandyName(this.candyName);
		this.setIcon(buff);
		this.candyName = cName;
	}
	
	/****************************************************************
	 * change le nom du candyBouton (nom de l'animal)
	 * @param str : le nom de l'animal
	 ***************************************************************/
	public void setCandyName(String str){
		this.candyName = str;		
	}
	/****************************************************************
	 * récupère le nom du candyBouton (nom de l'animal)
	 * @return le nom de l'animal
	 ***************************************************************/
	public String getCandyName(){
		return this.candyName;
	}
}