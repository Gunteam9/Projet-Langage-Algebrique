package automate;

import java.util.ArrayList;
import java.util.regex.Pattern;

import org.javatuples.Triplet;

import main.Utils;

public class NoeudAutomate {
    private boolean estFinal;
    private int numeroEtat;
    
    //On utilise des triplets pour chaque transition. 
    //Une transition c'est:
    // - Le noeud vers lequel la transition part (donc toujours celui ci (this))
    // - La lettre de transition (Un Character (a-z ou 0-9 ou -)
    // - Le noeud de destination
    private ArrayList<Triplet<NoeudAutomate, Character, NoeudAutomate>> transitions = new ArrayList<Triplet<NoeudAutomate, Character, NoeudAutomate>>();

    /**
     * Crée le noeud de l'automate
     * @param estFinal true si l'état est final
     * @param numeroEtat est le numéro de l'état
     */
    public NoeudAutomate(boolean estFinal, int numeroEtat) {
        this.estFinal = estFinal;
        this.numeroEtat = numeroEtat;
    }

    /**
     * Ajoute une transition pour chaque lettre dans le string donné
     * @param a - Le string contenant chaque transition
     * @param b - Le noeud de destination
     */
    public void ajouterTransition(String a, NoeudAutomate b) {
        for (Character transition: a.toCharArray()) {
        	this.ajouterTransition(transition, b);
        }
    }
    
    /**
     * Ajoute une transition pour la letre donné
     * @param a - La lettre de transition
     * @param b - L'état de destination
     */
    public void ajouterTransition(Character a, NoeudAutomate b) {
    	//On verifie que la lettre soit bien compris entre a-z ou 0-9 ou -
    	if (Pattern.matches("[a-z]|[0-9]|-", a.toString()))
    		transitions.add(new Triplet<NoeudAutomate, Character, NoeudAutomate>(this, a, b));
    	else 
    		throw new RuntimeException("Le caractère de transition n'est pas entre a-z ou 0-9 ou -");
    }

    /**
     * Ajoute tout l'alphabet
     * @param b est l'état d'arrivée
     */
    public void ajouterAlphabet(NoeudAutomate b){
        this.ajouterTransition(Utils.ALPHABET, b);
    }

    /**
     * Ajoute tout les nombres de 0 à 9
     * @param b est l'état d'arrivée
     */
    public void ajouterNombres(NoeudAutomate b){
        this.ajouterTransition(Utils.NOMBRES, b);
    }

    /**
     * Renvoi la transition avec le caracètre passé en paramètre
     * @param a - Le caractère de la transition
     * @return Un triplet pour la transition au complet
     */
    public Triplet<NoeudAutomate, Character, NoeudAutomate> getTransition(Character a) {
        for (Triplet<NoeudAutomate, Character, NoeudAutomate> triplet : transitions) {
			if (triplet.getValue1() == a) {
				return triplet;
			}
		}
        return null;
    }
    
    /**
     * Renvoi une liste de transition avec le mot passé en paramètre
     * @param string - La liste des caractères
     * @return Une liste de triplet pour les transitions au complet
     */
    public ArrayList<Triplet<NoeudAutomate, Character, NoeudAutomate>> getTransitions(String string) {
    	ArrayList<Triplet<NoeudAutomate, Character, NoeudAutomate>> liste = new ArrayList<Triplet<NoeudAutomate, Character, NoeudAutomate>>();
    	for (Character c : string.toCharArray()) {
			liste.add(this.getTransition(c));
		}
    	return liste;
    }


    /**
     * 
     * @return true si l'état est final
     */
    public boolean estFinal() {
        return this.estFinal;
    }


    @Override
    public String toString() {
        return "automate.NoeudAutomate{" +
                "estFinal=" + estFinal +
                ", numeroEtat=" + numeroEtat +
                '}';
    }
}
