package automate;

import java.util.regex.Pattern;

import org.javatuples.Triplet;

/**
 * Classe d'automate
 */
public class Automate {
    private int nombreEtats;
    private NoeudAutomate etatInitial;
    private NoeudAutomate[] etats;

    /**
     * Crée un automate avec un nombre d'états définis
     * @param nombreEtats est le nombre d'états
     * @param etatInitial est l'état initial de l'automate
     */
    public Automate(int nombreEtats, NoeudAutomate etatInitial) {
        this.nombreEtats = nombreEtats;
        this.etatInitial = etatInitial;
        this.etats = new NoeudAutomate[nombreEtats];
    }

    /**
     * Méthode qui ajoute un noued à l'automate à un certain index, l'index n'a pas d'importance mais doit-être dans la longueur
     * @param noeudAutomate est le noeud a rajouter
     * @param index est l'index à r'ajouter le noeud
     */
    public void ajouterEtat(NoeudAutomate noeudAutomate, int index){
        this.etats[index] = noeudAutomate;
    }

    /**
     * Méthode qui verifie si le mot passé en paramètres est accepté par l'automate
     * @param mot est le mot a verifier
     * @return true ou false
     */
    public boolean estValide(String mot){
        NoeudAutomate noeudAutomateActuel = this.etatInitial ;
        
        for (int i = 0; i < mot.length() ; i++) {
            Character currentLetter = mot.charAt(i);

        	if (!Pattern.matches("[a-z]|[0-9]|-", currentLetter.toString()))
        		return false;

        	Triplet<NoeudAutomate, Character, NoeudAutomate> transition = noeudAutomateActuel.getTransition(currentLetter);
        	if (transition == null)
        		return false;
        	else 
        		noeudAutomateActuel = transition.getValue2();
        }
        
        return noeudAutomateActuel.estFinal();
    }


}
