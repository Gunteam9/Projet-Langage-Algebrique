package automate;

import java.util.regex.Pattern;

import org.javatuples.Triplet;

public class Automate {
    private final int nombreEtats;
    private final NoeudAutomate etatInitial;
    private NoeudAutomate[] etats;

    /**
     * Crée un automate avec un nombre d'états prédéfini
     * @param nombreEtats - Le nombre d'états
     * @param etatInitial - L'état initial
     */
    public Automate(int nombreEtats, NoeudAutomate etatInitial) {
        this.nombreEtats = nombreEtats;
        this.etatInitial = etatInitial;
        this.etats = new NoeudAutomate[nombreEtats];
    }

    /**
     * Ajoute un noeud à l'automate à d'index donné
     * @param noeudAutomate - Noeud a ajouter
     * @param index - L'index du noeud
     */
    public void ajouterEtat(NoeudAutomate noeudAutomate, int index){
        this.etats[index] = noeudAutomate;
    }

    /**
     * Verifie si le mot passé en paramètres est accepté par l'automate
     * @param mot - Le mot à vérifier
     * @return true - Si le mot est accepté par l'automate, sinon false
     */
    public boolean estValide(String mot){
        NoeudAutomate noeudAutomateActuel = this.etatInitial ;
        
        for (int i = 0; i < mot.length() ; i++) {
            Character currentLetter = mot.charAt(i);

            //Si le mot contient autre chose que des lettres entre a-z ou 0-9 ou -
        	if (!Pattern.matches("[a-z]|[0-9]|-", currentLetter.toString()))
        		return false;

        	Triplet<NoeudAutomate, Character, NoeudAutomate> transition = noeudAutomateActuel.getTransition(currentLetter);
        	if (transition == null)
        		return false;
        	else 
        		noeudAutomateActuel = transition.getValue2();
        }
        
        //Si le noeud est final à la fin du mot, alors il est accepté par l'automate
        return noeudAutomateActuel.estFinal();
    }
    
    /**
     * 
     * @return Le nombre d'états de l'automate
     */
    public final int getNombreEtats() {
    	return nombreEtats;
    }


}
