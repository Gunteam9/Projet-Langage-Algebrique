package main;
import automate.Automate;

import java.io.File;
import java.io.FileNotFoundException;

public class Main {

    public static void main(String[] args) {
       SpecificationLexicale specificationLexicale = new SpecificationLexicale();
        try {
            String s = specificationLexicale.remplacer(new File("src/programmesAnnexes/Exemple5"));
            System.out.println(s);
        } catch (FileNotFoundException e) {
            System.out.println("fichier non trouvé");
        }
    }
}
