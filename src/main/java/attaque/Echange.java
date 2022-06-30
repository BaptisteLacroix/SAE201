/**
 * Université Côte d'Azur
 * IUT Côte d'Azur
 * Département Informatique
 *
 * @date Echange.java
 */
package attaque;

import interfaces.IEchange;
import interfaces.IPokemon;

/**
 * C'est un autre type d'attaque
 * Correspond à l'échange du Pokemon du combat avec un autre Pokemon du ranch
 *
 * @author Lacroix Baptiste
 */
public class Echange implements IEchange {
    /**
     * Nom du Pokémon actuel.
     */
    private IPokemon pokemon;

    /**
     * Constructeur de l'attaque Echange
     *
     * @param pokemon Pokémon actuel
     */
    public Echange(IPokemon pokemon) {
        this.pokemon = pokemon;
    }

    /**
     * Cette fonction définit le pokémon sur le pokémon passé en paramètre.
     *
     * @param pok le Pokémon qui va remplacer
     */
    public void setPokemon(IPokemon pok) //choisit le Pokemon remplaçant
    {
        this.pokemon = pok;
    }

    /**
     * Il renvoie le pokémon.
     *
     * @return Le nouveau Pokémon
     */
    @Override
    public IPokemon echangeCombattant()  //active le remplacement (et renvoie le nouveau pokemon)
    {
        return this.pokemon;
    }

    /**
     * Cette fonction renvoie 0, car lors d'un échange, il n'y a 0 damage effectué.
     *
     * @param lanceur  Le pokémon qui utilise le mouvement
     * @param receveur Le pokémon qui reçoit l'attaque.
     * @return 0
     */
    @Override
    public int calculeDommage(IPokemon lanceur, IPokemon receveur) {
        return 0;
    }

    /**
     * Cette fonction ne fait rien.
     */
    @Override
    public void utilise() {
    }
}
