package main;

import attaque.Capacite;
import combat.Combat;
import dresseur.DresseurIA;
import dresseur.DresseurIAAleatoire;
import interfaces.*;
import other.Chrono;
import pokedex.Pokedex;

import java.util.Random;

/**
 * Classe permettant de tester le programme
 *
 * @author Lacroix Baptiste and Vidal Théo
 */
public class Main {
    /**
     * Ceci est une méthode qui illustre le fonctionnement des différentes classes
     * Il crée un Pokedex, crée un Pokémon, crée un Capacite, puis imprime le nom du Pokémon, son espèce, ses statistiques
     * et les dommages qu'il causerait à un autre Pokémon.
     *
     * @param args main param
     */
    public static void main(String[] args) {
        IPokedex pokedex = new Pokedex();
        // DresseurHuman baptiste = new DresseurHuman("Baptiste", pokedex);
        DresseurIAAleatoire IA1 = new DresseurIAAleatoire("IA Aléatoire", pokedex);
        DresseurIA IA2 = new DresseurIA("IA MinMax", pokedex);

        // for (IPokemon pokemon : baptiste.getRanch()) {
        //     ICapacite[] capacites = pokemon.getEspece().getCapSet();
        //     ICapacite[] capacitesApp = new ICapacite[4];
        //     int counter = 0;
        //     System.out.println("Capcités disponilbes : ");
        //     for (ICapacite cap : capacites) {
        //         System.out.printf("%-32s", cap.toString() + "\n");
        //     }
        //     System.out.println();
        //     for (int i = 0; i < 4; i++) {
        //         Scanner input = new Scanner(System.in);  // Create a Scanner object
        //         System.out.print(baptiste.getNom() + " choose a new capacity to learn for your pokemon " + pokemon.getNom() + " (give the name) : ");
        //         String choixCapacite = input.nextLine();  // Read user input
        //         for (ICapacite cap : capacites) {
        //             if (cap.getNom().equals(choixCapacite)) {
        //                 capacitesApp[counter] = cap;
        //                 counter++;
        //             }
        //         }
        //     }
        //     pokemon.apprendCapacites(capacitesApp);
        //     affichage(pokemon);
        // }

        Chrono chrono2 = new Chrono();
        chrono2.start();
        choixIAAleatoire(IA1);
        choixIA(IA2);

        //////////////////////////////////////////////////////////

        ICombat combat = new Combat(IA1, IA2);
        int i;
        for (i = 0; i < 1; i++)
            combat.commence();
        chrono2.stop();
        System.out.println("durée total pour " + i + " combats : " + chrono2.getDureeTxt());

        // combat = new Combat(IA1, baptiste);
        // combat.commence();
    }

    /**
     * Il choisit une capacité aléatoire pour chaque pokémon du dresseur
     *
     * @param dresseur l'entraîneur
     */
    public static void choixIA(DresseurIA dresseur) {
        Random rand = new Random();
        for (IPokemon pokemon : dresseur.getRanch()) {
            ICapacite[] capacites = pokemon.getEspece().getCapSet();
            ICapacite[] capacitesApp = new ICapacite[4];
            for (int i = 0; i < 4; i++) {
                Capacite random = (Capacite) capacites[rand.nextInt(capacites.length)];
                while (pokemon.getNiveau() < random.getNiveau())
                    random = (Capacite) capacites[rand.nextInt(capacites.length)];
                System.out.println(dresseur.getNom() + " choose a new capacity to learn for " + pokemon.getNom() + " : " + random.getNom());
                capacitesApp[i] = random;
            }
            System.out.println();
            pokemon.apprendCapacites(capacitesApp);
            // affichage(pokemon);
        }
    }

    /**
     * Il choisit une capacité aléatoire pour chaque pokémon du dresseur
     *
     * @param dresseur l'entraîneur
     */
    public static void choixIAAleatoire(DresseurIAAleatoire dresseur) {
        Random rand = new Random();
        for (IPokemon pokemon : dresseur.getRanch()) {
            ICapacite[] capacites = pokemon.getEspece().getCapSet();
            ICapacite[] capacitesApp = new ICapacite[4];
            for (int i = 0; i < 4; i++) {
                Capacite random = (Capacite) capacites[rand.nextInt(capacites.length)];
                while (pokemon.getNiveau() < random.getNiveau())
                    random = (Capacite) capacites[rand.nextInt(capacites.length)];
                System.out.println(dresseur.getNom() + " choose a new capacity to learn for " + pokemon.getNom() + " : " + random.getNom());
                capacitesApp[i] = random;
            }
            System.out.println();
            pokemon.apprendCapacites(capacitesApp);
            // affichage(pokemon);
        }
    }

    /**
     * Il affiche le nom du pokémon et ses capacités apprises
     *
     * @param pokemon le pokémon que vous voulez afficher
     */
    private static void affichage(IPokemon pokemon) {
        System.out.println("\n" + pokemon.getNom() + " capacités apprises : ");
        ICapacite[] capacite = pokemon.getCapacitesApprises();
        for (ICapacite caps : capacite) {
            System.out.printf("%-32s", "nom : " + caps.getNom() + " | précision : " + caps.getPrecision() + " | puissance : " + caps.getPuissance() + " | PP : " + caps.getPuissance() + " | catégorie : " + caps.getCategorie() + " | type : " + caps.getType() + "\n");
        }
        System.out.println("\n");
    }
}
