package automate;

import java.util.ArrayList;
import java.util.regex.Pattern;

import org.javatuples.Triplet;

import main.Utils;

/**
 * Class qui représente un état de l'automate
 */
public class NoeudAutomate {
    private boolean estFinal;
    private int numeroEtat;
    private ArrayList<Triplet<NoeudAutomate, Character, NoeudAutomate>> transitions = new ArrayList<Triplet<NoeudAutomate, Character, NoeudAutomate>>();

    /**
     * Création d'un noeud avec un nombre de transition égal à la longueu de l'alphabet : ici 36
     * @param estFinal true si l'état est final
     * @param numeroEtat est le numéro de l'état
     */
    public NoeudAutomate(boolean estFinal, int numeroEtat) {
        this.estFinal = estFinal;
        this.numeroEtat = numeroEtat;
    }

    /**
     * Méthode qui ajoute une transition à un noeud
     * @param n est l'état d'arrivé
     * @param s est le ou les caractères de transition
     */
    public void ajouterTransition(String a, NoeudAutomate b) {
        for (Character transition: a.toCharArray()) {
        	this.ajouterTransition(transition, b);
        }
    }
    
    public void ajouterTransition(Character a, NoeudAutomate b) {
    	if (Pattern.matches("[a-z]|[0-9]|-", a.toString())) {
    		transitions.add(new Triplet<NoeudAutomate, Character, NoeudAutomate>(this, a, b));
    	}
    }

    /**
     * Ajoute tout l'alphabet
     * @param a est l'état de départ
     * @param b est l'état d'arrivée
     */
    public void ajouterAlphabet(NoeudAutomate b){
        this.ajouterTransition(Utils.ALPHABET, b);
    }

    /**
     * Ajoute les nombres de 0 à 9
     * @param a est l'état de départ
     * @param b est l'état d'arrivée
     */
    public void ajouterNombres(NoeudAutomate b){
        this.ajouterTransition(Utils.NOMBRES, b);
    }

    /**
     * Méthode qui retourne la transition avec le mot passé en paramètres
     * @param s est le mot passé en paramètres
     * @return le nouvel état ou null si la transition n'existe pas
     */
    public Triplet<NoeudAutomate, Character, NoeudAutomate> getTransition(Character a) {
        for (Triplet<NoeudAutomate, Character, NoeudAutomate> triplet : transitions) {
			if (triplet.getValue1() == a) {
				return triplet;
			}
		}
        return null;
    }
    
    public ArrayList<Triplet<NoeudAutomate, Character, NoeudAutomate>> getTransitions(String string) {
    	ArrayList<Triplet<NoeudAutomate, Character, NoeudAutomate>> liste = new ArrayList<Triplet<NoeudAutomate, Character, NoeudAutomate>>();
    	for (Character c : string.toCharArray()) {
			liste.add(this.getTransition(c));
		}
    	return liste;
    }




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
