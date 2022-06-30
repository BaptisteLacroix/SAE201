/**
 * Université Côte d'Azur
 * IUT Côte d'Azur
 * Département Informatique
 *
 * @date Tour.java
 */
package combat;

import attaque.Echange;
import interfaces.IAttaque;
import interfaces.IPokemon;
import interfaces.ITour;

import java.util.Date;


/**
 * Classe qui gère l'issue d'un tour entre les différentes attaques. Implémente ITour
 *
 * @author Lacroix Baptiste
 */
public class Tour implements ITour {
    /**
     * Pokémon du dresseur 1
     */
    private final IPokemon pokemon1;
    /**
     * Type d'Attaque du dresseur 1
     */
    private final IAttaque attaque1;
    /**
     * Pokémon du dresseur 2
     */
    private final IPokemon pokemon2;
    /**
     * Type d'Attaque du dresseur 2
     */
    private final IAttaque attaque2;
    /**
     * Permet de savoir si le dresseur 1 a fait un echange
     */
    private boolean echange1 = false;
    /**
     * Permet de savoir si le dresseur 1 a fait un echange
     */
    private boolean echange2 = false;
    /**
     * Permet de savoir combien de damage a recu le pokémon du dresseur 1
     */
    private int damageRecuPok1;
    /**
     * Permet de savoir combien de damage a recu le pokémon du dresseur 2
     */
    private int damageRecuPok2;
    /**
     * Objet Date qui permet de connaître la date au moment de l'appel de la classe
     */
    private final Date date = new Date();

    /**
     * Constructeur du Tour
     *
     * @param pokemon1 Pokémon du dresseur 1 qui combattra
     * @param attaque1 Type d'Attaque à appliquer
     * @param pokemon2 Pokémon du dresseur 2 qui combattra
     * @param attaque2 Type d'Attaque à appliquer
     */
    public Tour(IPokemon pokemon1, IAttaque attaque1, IPokemon pokemon2, IAttaque attaque2) {
        this.pokemon1 = pokemon1;
        this.pokemon2 = pokemon2;
        this.attaque1 = attaque1;
        this.attaque2 = attaque2;
        this.damageRecuPok1 = this.pokemon1.getStat().getPV();
        this.damageRecuPok2 = this.pokemon2.getStat().getPV();
    }

    /**
     * La méthode permet de Lancer un tour de combat
     */
    @Override
    public void commence() {
        if (attaque1.getClass() == Echange.class && attaque2.getClass() != Echange.class) {
            this.pokemon1.subitAttaqueDe(pokemon2, attaque2);
            this.damageRecuPok1 -= this.pokemon1.getStat().getPV();
            this.echange1 = true;
            System.out.println(this);
        } else if (attaque1.getClass() != Echange.class && attaque2.getClass() == Echange.class) {
            this.pokemon2.subitAttaqueDe(pokemon1, attaque1);
            this.damageRecuPok2 -= this.pokemon2.getStat().getPV();
            this.echange2 = true;
            System.out.println(this);
        } else if (attaque1.getClass() == Echange.class) {
            this.echange1 = true;
            this.echange2 = true;
            System.out.println(this);
        } else {
            // Si vitesses P1 > P2 alors P1 commence sinon P2
            if (this.pokemon1.getStat().getVitesse() > this.pokemon2.getStat().getVitesse()) {
                this.pokemon2.subitAttaqueDe(pokemon1, attaque1);
                if (!this.pokemon2.estEvanoui())
                    this.pokemon1.subitAttaqueDe(pokemon2, attaque2);
            } else {
                this.pokemon1.subitAttaqueDe(pokemon2, attaque2);
                if (!this.pokemon1.estEvanoui())
                    this.pokemon2.subitAttaqueDe(pokemon1, attaque1);
            }
            this.damageRecuPok1 -= this.pokemon1.getStat().getPV();
            this.damageRecuPok2 -= this.pokemon2.getStat().getPV();
            System.out.println(this);
        }
    }        //Lance un tour de combat

    /**
     * Cette fonction permet d'imprimer le résultat du combat
     *
     * @return La méthode toString() est renvoyée.
     */
    @Override
    public String toString() {
        if (echange1 && !echange2) {
            return this.echange1check();
        } else if (!echange1 && echange2) {
            return this.echange2check();
        } else if (echange1) {
            return date + " : Le Dresseur 1 a changé son Pokémon par : " + this.pokemon1.getNom() + " (lvl : " + this.pokemon1.getNiveau() + ")\n"
                    + date + " : Le Dresseur 2 a changé son Pokémon par : " + this.pokemon2.getNom() + " (lvl : " + this.pokemon2.getNiveau() + ")\n"
                    + date + " : Aucun n'a reçu de damage." + "\n"
                    + date + " : Il reste " + this.pokemon1.getPourcentagePV() + "% a " + this.pokemon1.getNom() + "\n"
                    + date + " : Il reste " + this.pokemon2.getPourcentagePV() + "% a " + this.pokemon2.getNom();
        } else if (this.pokemon1.getStat().getVitesse() > this.pokemon2.getStat().getVitesse()) {
            return this.printWhoIsKo1();
        } else {
            return this.printWhoIsKo2();
        }
    }

    /**
     * Cette fonction permet d'afficher le résultat de l'échange du premier joueur
     *
     * @return La méthode renvoie une chaîne contenant le nom du pokémon qui a été modifié, les dégâts que le pokémon a
     * reçus et les hp restants du pokémon.
     */
    private String echange1check() {
        if (this.pokemon1.estEvanoui()) {
            return date + " : Le Dresseur 1 a changé son Pokémon par : " + this.pokemon1.getNom() + " (lvl : " + this.pokemon1.getNiveau() + ")\n"
                    + date + " : " + this.pokemon1.getNom() + " a subit " + this.damageRecuPok1 + " de damage par " + this.pokemon2.getNom() + " (lvl : " + this.pokemon2.getNiveau() + ")\n"
                    + date + " : " + this.pokemon1.getNom() + " est ko !" + "\n"
                    + date + " : " + "Il reste " + this.pokemon2.getPourcentagePV() + "% a " + this.pokemon2.getNom();
        } else {
            return date + " : Le Dresseur 1 a changé son Pokémon par : " + this.pokemon1.getNom() + " (lvl : " + this.pokemon1.getNiveau() + ")\n"
                    + date + " : " + this.pokemon1.getNom() + " a subit " + this.damageRecuPok1 + " de damage par " + this.pokemon2.getNom() + " (lvl : " + this.pokemon2.getNiveau() + ")\n"
                    + date + " : " + "Il reste " + this.pokemon1.getPourcentagePV() + "% a " + this.pokemon1.getNom() + "\n"
                    + date + " : " + "Il reste " + this.pokemon2.getPourcentagePV() + "% a " + this.pokemon2.getNom();
        }
    }

    /**
     * Cette fonction permet d'afficher le message lorsque le joueur 2 change de pokémon
     *
     * @return La méthode renvoie une chaîne contenant le nom du pokémon qui a été modifié, les dégâts que le pokémon a
     * subis et le pv restant du pokémon.
     */
    private String echange2check() {
        if (this.pokemon2.estEvanoui()) {
            return date + " : Le Dresseur 2 a changé son Pokémon par : " + this.pokemon2.getNom() + " (lvl : " + this.pokemon2.getNiveau() + ")\n"
                    + date + " : " + this.pokemon2.getNom() + " a subit " + this.damageRecuPok2 + " de damage par " + this.pokemon1.getNom() +
                    " (lvl : " + this.pokemon1.getNiveau() + ")\n"
                    + date + " : " + this.pokemon2.getNom() + " est ko !" + "\n"
                    + date + " : " + "Il reste " + this.pokemon1.getPourcentagePV() + "% a " + this.pokemon1.getNom();
        } else {
            return date + " : Le Dresseur 2 a changé son Pokémon par : " + this.pokemon2.getNom() + " (lvl : " + this.pokemon2.getNiveau() + ")\n"
                    + date + " : " + this.pokemon2.getNom() + " a subit " + this.damageRecuPok2 + " de damage par " + this.pokemon1.getNom() + " (lvl : " + this.pokemon1.getNiveau() + ")\n"
                    + date + " : " + "Il reste " + this.pokemon2.getPourcentagePV() + "% a " + this.pokemon2.getNom() + "\n"
                    + date + " : " + "Il reste " + this.pokemon1.getPourcentagePV() + "% a " + this.pokemon1.getNom();
        }
    }

    /**
     * Il imprime le résultat du combat entre deux pokemon
     *
     * @return La méthode renvoie une chaîne contenant le nom du pokémon, les dégâts reçus par l'adversaire et les PV
     * restants du pokémon.
     */
    private String printWhoIsKo1() {
        if (this.pokemon1.estEvanoui() && !this.pokemon2.estEvanoui()) {
            return date + " : " + this.pokemon1.getNom() + " attaque le premier en infligeant " + this.damageRecuPok2 + " de damage (lvl : " + this.pokemon1.getNiveau() + ")\n"
                    + date + " : " + this.pokemon2.getNom() + " attaque le deuxième en infligeant " + this.damageRecuPok1 + " de damage (lvl : " + this.pokemon2.getNiveau() + ")\n"
                    + date + " : " + this.pokemon1.getNom() + " est ko !" + "\n"
                    + date + " : " + "Il reste " + this.pokemon2.getPourcentagePV() + "% a " + this.pokemon2.getNom();
        } else if (this.pokemon2.estEvanoui() && !this.pokemon1.estEvanoui()) {
            return date + " : " + this.pokemon1.getNom() + " attaque le premier en infligeant " + this.damageRecuPok2 + " de damage (lvl : " + this.pokemon1.getNiveau() + ")\n"
                    + date + " : " + "Il reste " + this.pokemon1.getPourcentagePV() + "% a " + this.pokemon1.getNom() + "\n"
                    + date + " : " + this.pokemon2.getNom() + " est ko !";
        } else if (this.pokemon1.estEvanoui() && this.pokemon2.estEvanoui()) {
            return date + " : " + this.pokemon1.getNom() + " attaque le premier en infligeant " + this.damageRecuPok2 + " de damage (lvl : " + this.pokemon1.getNiveau() + ")\n"
                    + date + " : " + this.pokemon2.getNom() + " est ko ! \n"
                    + date + " : " + "Il reste " + this.pokemon1.getPourcentagePV() + "% a " + this.pokemon1.getNom();
        } else {
            return date + " : " + this.pokemon1.getNom() + " attaque le premier en infligeant " + this.damageRecuPok2 + " de damage (lvl : " + this.pokemon1.getNiveau() + ")\n"
                    + date + " : " + this.pokemon2.getNom() + " attaque le deuxième en infligeant " + this.damageRecuPok1 + " de damage (lvl : " + this.pokemon2.getNiveau() + ")\n"
                    + date + " : " + "Il reste " + this.pokemon1.getPourcentagePV() + "% a " + this.pokemon1.getNom() + "\n"
                    + date + " : " + "Il reste " + this.pokemon2.getPourcentagePV() + "% a " + this.pokemon2.getNom();
        }
    }

    /**
     * Il imprime qui est ko et qui ne l'est pas
     *
     * @return La méthode renvoie une chaîne contenant le nom du pokémon qui attaque en premier, les dégâts qu'il inflige,
     * le nom du pokémon qui attaque en second, les dégâts qu'il inflige et les PV restants du pokémon qui attaque en
     * premier et en second.
     */
    private String printWhoIsKo2() {
        if (this.pokemon1.estEvanoui() && !this.pokemon2.estEvanoui()) {
            return date + " : " + this.pokemon2.getNom() + " attaque le premier en infligeant " + this.damageRecuPok1 + " de damage (lvl : " + this.pokemon2.getNiveau() + ")\n"
                    + date + " : " + "Il reste " + this.pokemon2.getPourcentagePV() + "% a " + this.pokemon2.getNom() + "\n"
                    + date + " : " + this.pokemon1.getNom() + " est ko !";
        } else if (this.pokemon2.estEvanoui() && !this.pokemon1.estEvanoui()) {
            return date + " : " + this.pokemon2.getNom() + " attaque le premier en infligeant " + this.damageRecuPok1 + " de damage (lvl : " + this.pokemon2.getNiveau() + ")\n"
                    + date + " : " + this.pokemon1.getNom() + " attaque le deuxième en infligeant " + this.damageRecuPok2 + " de damage (lvl : " + this.pokemon1.getNiveau() + ")\n"
                    + date + " : " + this.pokemon2.getNom() + " est ko !" + "\n"
                    + date + " : " + "Il reste " + this.pokemon1.getPourcentagePV() + "% a " + this.pokemon1.getNom();
        } else if (this.pokemon1.estEvanoui() && this.pokemon2.estEvanoui()) {
            return date + " : " + this.pokemon2.getNom() + " attaque le premier en infligeant " + this.damageRecuPok1 + " de damage (lvl : " + this.pokemon2.getNiveau() + ")\n"
                    + date + " : " + this.pokemon1.getNom() + " est ko !" + "\n"
                    + date + " : " + "Il reste " + this.pokemon2.getPourcentagePV() + "% a " + this.pokemon2.getNom();
        } else {
            return date + " : " + this.pokemon2.getNom() + " attaque le premier en infligeant " + this.damageRecuPok1 + " de damage (lvl : " + this.pokemon2.getNiveau() + ")\n"
                    + date + " : " + this.pokemon1.getNom() + " attaque le deuxième en infligeant " + this.damageRecuPok2 + " de damage (lvl : " + this.pokemon1.getNiveau() + ")\n"
                    + date + " : " + "Il reste " + this.pokemon2.getPourcentagePV() + "% a " + this.pokemon2.getNom() + "\n"
                    + date + " : " + "Il reste " + this.pokemon1.getPourcentagePV() + "% a " + this.pokemon1.getNom();
        }
    }
}
