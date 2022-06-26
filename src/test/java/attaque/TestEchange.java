package attaque;

import dresseur.DresseurIA;
import interfaces.IPokedex;
import org.junit.Assert;
import org.junit.Test;
import pokedex.Pokedex;

/**
 * Une classe qui est utilisée pour tester la classe Echange.
 *
 * @author Lacroix baptiste and Vidal Théo
 */
public class TestEchange {

    /**
     * Créer un nouvel objet DresseurIA de nom "IA1" et l'affecter à la variable IA.
     */
    private IPokedex pokedex = new Pokedex();
    private final DresseurIA IA = new DresseurIA("IA1", pokedex);
    /**
     * Création d'une nouvelle instance de la classe Echange.
     */
    private final Echange echange = new Echange(IA.getPokemon(1));

    /**
     * Cette fonction teste l'échange de pokemon entre le joueur et l'IA
     */
    @Test
    public void testEchangePokemon() {
        echange.setPokemon(IA.getPokemon(0));
        Assert.assertNotEquals(IA.getPokemon(1), echange.echangeCombattant());
        Assert.assertEquals(IA.getPokemon(0), echange.echangeCombattant());
    }

    /**
     * >Cette fonction teste le calcul des dommages de la classe d'échange
     */
    @Test
    public void testCalculeDommage() {
        Assert.assertEquals(0, echange.calculeDommage(IA.getPokemon(1), IA.getPokemon(0)));
    }
}
