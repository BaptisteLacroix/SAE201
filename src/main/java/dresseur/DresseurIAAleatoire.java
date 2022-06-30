/**
 * Université Côte d'Azur
 * IUT Côte d'Azur
 * Département Informatique
 *
 * @date DresseurIA.java
 */
package dresseur;

import attaque.Echange;
import interfaces.*;
import pokemon.Pokemon;
import useLogger.MyLoggerConfiguration;

import java.util.Random;
import java.util.logging.Level;

/**
 * Classe qui gère la création d'un dresseur IA. Implémente IDresseur
 * Un DresseurIA possède une référence sur une IStrategy à qui il délègue la prise de décision.
 *
 * @author Lacroix Baptiste
 */
public class DresseurIAAleatoire implements IDresseur {
    /**
     * Nom du Pokémon
     */
    private final String nom;
    /**
     * Contient les Pokémons du dresseur IA
     */
    private final IPokemon[] ranch;
    /**
     * Génère un nombre random.
     */
    private final Random rand = new Random();

    /**
     * Constructeur du Dresseur IA
     *
     * @param nom Niom du dresseur IA
     */
    public DresseurIAAleatoire(String nom, IPokedex pokedex) {
        this.nom = nom;
        MyLoggerConfiguration.printLog(Level.INFO, "création du dresseur (Dresseur IAAleatoire).\n");
        MyLoggerConfiguration.printLog(Level.INFO, "création du ranch (Dresseur IAAleatoire).\n");
        this.ranch = pokedex.engendreRanch();
        MyLoggerConfiguration.printLog(Level.INFO, "génération du ranch terminé (Dresseur IAAleatoire).\n");
    }

    @Override
    public IPokemon[] getRanchCopy() {
        IPokemon[] copy = new IPokemon[this.ranch.length];
        System.arraycopy(this.ranch, 0, copy, 0, this.ranch.length);
        return copy;
    }


    /**
     * Une méthode qui renvoie le nom du formateur.
     */
    @Override
    public String getNom() {
        return this.nom;
    }//Nom du dresseur

    /**
     * Cette fonction renvoie le tableau ranch.
     *
     * @return Le tableau du ranch.
     */
    public IPokemon[] getRanch() {
        return this.ranch;
    }

    /**
     * C'est une méthode qui renvoie le niveau du formateur.
     */
    public int getNiveau() {
        int niveau = 0;
        for (IPokemon p : ranch) {
            niveau += p.getNiveau();
        }
        return niveau;
    }

    /**
     * C'est une méthode qui retourne le ième pokémon du ranch.
     */
    @Override
    public IPokemon getPokemon(int i) {
        return ranch[i];
    }//Récupère le i-eme Pokemon du Ranch

    /**
     * Donne au pokemon pok les capacites caps
     *
     * @param pok  pokemonn a qui on va enseigner les capacités
     * @param caps tableau de capacités a enseigner
     */
    @Override
    public void enseigne(IPokemon pok, ICapacite[] caps) {
        ICapacite[] capSet = pok.getEspece().getCapSet();
        int cmp = 0;
        for (int i = 0; i < 4; i++) {
            if (capSet[i].getNom().equals(caps[i].getNom())) {
                cmp++;
            }
        }
        if (cmp == 4) {
            pok.apprendCapacites(caps);
        } else {
            MyLoggerConfiguration.printLog(Level.SEVERE, "Erreur ! Il manque des capacités (Dresseur IAAleatoire).\n");
            throw new NullPointerException("Erreur ! Il manque des capacités.");
        }
    }//Donne au pokemon pok les capacites caps

    /**
     * C'est une méthode qui soigne tous les pokémons du ranch.
     */
    @Override
    public void soigneRanch() {
        for (IPokemon p : this.ranch) {
            p.soigne();
        }
    } //Redonne à tous les pokemon du Ranch leur PV max

    /**
     * Cette méthode est utilisée pour choisir le premier pokémon qui combattra.
     *
     * @return Pokemon qui combattra
     */
    @Override
    public IPokemon choisitCombattant() {
        IPokemon pokemon = this.ranch[this.rand.nextInt(this.ranch.length)];
        while (pokemon.estEvanoui()) {
            int random = this.rand.nextInt(this.ranch.length);
            pokemon = this.ranch[random];
        }
        return pokemon;
    }

    /**
     * Cette méthode est utilisée pour choisir le pokémon qui combattra le pokémon adverse.
     *
     * @param pok pokemon de l'adversaire
     * @return Pokemon qui combattra
     */
    @Override
    public IPokemon choisitCombattantContre(IPokemon pok) {
        IPokemon pokemon = this.ranch[this.rand.nextInt(this.ranch.length)];
        while (pokemon.estEvanoui()) {
            pokemon = this.ranch[this.rand.nextInt(this.ranch.length)];
        }
        Pokemon pok2 = (Pokemon) pok;
        pok2.addCount();
        return pokemon;
    }

    /**
     * Cette méthode permet de choisir l'attaque qui sera utilisée contre le pokémon défenseur.
     *
     * @param attaquant pokemon de l'attaquant
     * @param dresseurDefenseur Dresseur qui défend
     * @param defenseur pokemon du defenseur
     * @return Une Instance de Attaque
     */
    @Override
    public IAttaque choisitAttaque(IPokemon attaquant, IDresseur dresseurDefenseur, IPokemon defenseur) {
        int choixAttaque = rand.nextInt(2 + 1) + 1;  // Read user input
        if (choixAttaque == 1) {
            return attaquant.getCapacitesApprises()[this.rand.nextInt(4)];
        } else {
            IEchange echange = new Echange(attaquant);
            echange.setPokemon(this.choisitCombattantContre(defenseur));
            return echange; // Change de Pokemon
        }
    }
}
