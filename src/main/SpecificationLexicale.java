package main;
import automate.Automate;
import automate.NoeudAutomate;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Pattern;

/**
 * Classe pour gérer la specification lexicale
 */
public class SpecificationLexicale {
    public Automate automateEntiers ;
    public Automate automateIdentificateurs ;

    private List<String> listMotCles = new ArrayList<>(Arrays.asList(
            "program",
            "begin",
            "end",
            "break",
            "while",
            "for",
            "to",
            "if",
            "do",
            "true",
            "false",
            "from",
            "not",
            "then",
            "else",
            "and",
            "or"
    ));

    private List<String> operateursArithmetiques = new ArrayList<>(Arrays.asList(
            "*",
            "-",
            "+",
            "/",
            "%"
    ));

    private List<String> operateursBinaires = new ArrayList<>(Arrays.asList(
            "<",
            "<=",
            ">",
            ">=",
            "==",
            "<-"
    ));

    private List<String> caracteresSpeciaux = new ArrayList<>(Arrays.asList(
            ".",
            ";",
            "[",
            "]"
    ));


    /**
     * Constructeur qui crée les deux automates
     */
    public SpecificationLexicale() {
        NoeudAutomate a1n1 = new NoeudAutomate(false,1);
        NoeudAutomate a1n2 = new NoeudAutomate(true,2);
        NoeudAutomate a1n3 = new NoeudAutomate(true,3);
        NoeudAutomate a1n4 = new NoeudAutomate(true,4);

        a1n1.ajouterTransition('0', a1n2);

        a1n1.ajouterTransition('-',a1n3);


        a1n3.ajouterTransition("123456789", a1n4);
        a1n1.ajouterTransition("123456789", a1n4);

        a1n4.ajouterNombres(a1n4);

        this.automateEntiers =  new Automate(4,a1n1);

        this.automateEntiers.ajouterEtat(a1n1,0);
        this.automateEntiers.ajouterEtat(a1n2,1);
        this.automateEntiers.ajouterEtat(a1n3,2);
        this.automateEntiers.ajouterEtat(a1n4,3);


        NoeudAutomate a2n1 = new NoeudAutomate(false,1);
        NoeudAutomate a2n2 = new NoeudAutomate(true,2);


        a2n1.ajouterAlphabet(a2n2);

        a2n2.ajouterAlphabet(a2n2);
        a2n2.ajouterNombres(a2n2);

        this.automateIdentificateurs = new Automate(2, a2n1);

        this.automateIdentificateurs.ajouterEtat(a2n1,0);
        this.automateIdentificateurs.ajouterEtat(a2n2,1);

    }

    public Automate getAutomateEntiers() {
        return automateEntiers;
    }


    public Automate getAutomateIdentificateurs() {
        return automateIdentificateurs;
    }

    /**
     * Méthode qui remplace tous les identificateurs par le token ident et tous les entiers par le token entier (sauf les mots clés)
     * @param algo1 est l'algo en entrée
     * @return un string contenant algo1 avec les mots remplacés
     * @throws FileNotFoundException si le fichier n'existe pas
     */
    public String remplacer(File algo1) throws FileNotFoundException {
        Scanner scAlgo = new Scanner(algo1);
        StringBuilder remplacement = new StringBuilder();
        String ligne;
        while (scAlgo.hasNextLine()) {
            ligne = scAlgo.nextLine();
            ligne = this.separerCharSpeciaux(ligne);
            String[] mots = ligne.strip().split(" ");
            for (String mot : mots) {
                if (this.automateIdentificateurs.estValide(mot)) {
                    if (this.listMotCles.contains(mot)) { // si c'est un mot clé on le laisse tel quel
                        remplacement.append(" ").append(mot);
                    } else { // si c'est un identificateur on le remplace par ident
                        remplacement.append(" ident");
                    }
                } else if (this.automateEntiers.estValide(mot)) {// si c'est un entier on le remplacer par entier
                    remplacement.append(" entier");
                } else if (this.operateursArithmetiques.contains(mot) || this.operateursBinaires.contains(mot)) { // si c'est un operateur arithmetique ou binaire on le laisse tel quel
                    remplacement.append(" ").append(mot);

                } else if (this.caracteresSpeciaux.contains(mot)) { // si c'est un caractère spécial on le laisse tel quel sans espace devant
                    remplacement.append(mot);
                }
            }
        }
        return this.enleverEspaces(remplacement.toString());
    }

    /**
     * Méthode qui sépare tous les caractères specieux afin de pouvoir traiter le string plus facilement
     * @param s est le string
     * @return le string dont les char spéciaux sont séparés
     */
    public String separerCharSpeciaux(String s){ // rajouter les bool
        return s.replaceAll(";"," ; ")
                .replaceAll("\\."," . ")
                .replaceAll("\\+", " + ")
                .replaceAll("\\*", " * ")
                .replaceAll("-"," - ")
                .replaceAll("/"," / ")
                .replaceAll("%", " % ")
                .replaceAll("\\[", " [ ")
                .replaceAll("]", " ] ")
                .replaceAll("<", " < ")
                .replaceAll(">", " > ")
                .replaceAll("==", " == ")

                .replaceAll("< -","<-")
                ;
    }

    /**
     * Méthode qui enlève les espaces inutiles en trop
     * @param s le string
     * @return s sans les espaces inutiles
     */
    public String enleverEspaces(String s){
        return s.replaceAll("\\[ *", "[")
                .replaceAll(" *; *",";")
                .replaceAll(" *\\+","+")
                .replaceAll(" +- *","-")
                .replaceAll(" *\\* *","*")
                .replaceAll(" */ *","/")
                .replaceAll(" *% *","%")
                .replaceAll(" *\\+ *","+")
                .replaceAll(" *\\. *",".")
                .replaceAll(" +<-"," <- ");

                /* decommenter ça pour enlever les espaces apre_s les bool
                .replaceAll(" *== *","==")
                .replaceAll(" +< +","<")
                .replaceAll(" +> +",">");
                */


    }
}
