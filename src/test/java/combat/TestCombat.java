package combat;

import attaque.Echange;
import dresseur.DresseurIA;
import interfaces.IPokedex;
import org.junit.Assert;
import org.junit.Test;
import pokedex.Pokedex;

/**
 * Une classe qui est utilisée pour tester la classe Combat.
 *
 * @author Lacroix baptiste and Vidal Théo
 */
public class TestCombat {

    /**
     * Il crée un nouvel objet de type `Pokedex` et l'affecte à la variable `pokedex`.
     */
    private IPokedex pokedex = new Pokedex();
    /**
     * Création d'un nouvel objet DresseurIA avec le nom "IA1".
     */
    private final DresseurIA IA1 = new DresseurIA("IA1", pokedex);
    /**
     * Création d'un nouvel objet DresseurIA avec le nom "IA2".
     */
    private final DresseurIA IA2 = new DresseurIA("IA2", pokedex);
    /**
     * Création d'un nouvel objet de type `Echange` avec le pokémon de `IA1`.
     */
    private final Echange echange1 = new Echange(IA1.getPokemon(1));
    /**
     * Création d'un nouvel objet Echange avec le pokemon de IA2.
     */
    private final Echange echange2 = new Echange(IA2.getPokemon(1));
    /**
     * Il crée un nouvel objet de combat avec les deux entraîneurs IA1 et IA2.
     */
    private final Combat combat = new Combat(IA1, IA2);

    /**
     * Test de la méthode `nouveauTour` dans la classe `Combat`
     */
    @Test
    public void testNouveauTour() {
        Assert.assertEquals(combat.nouveauTour(IA1.getPokemon(0), echange1, IA2.getPokemon(1), echange2).getClass(), Tour.class);
    } //Crée un tour du combat

    /**
     * Cette fonction teste la fonction getDresseur1() de la classe Combat
     */
    @Test
    public void testGetDresseur1() {
        Assert.assertEquals(combat.getDresseur1(), IA1);
    }

    /**
     * Cette fonction teste la fonction getDresseur2() de la classe Combat
     */
    @Test
    public void testGetDresseur2() {
        Assert.assertEquals(combat.getDresseur2(), IA2);
    }
}
