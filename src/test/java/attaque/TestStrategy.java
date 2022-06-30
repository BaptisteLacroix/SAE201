package attaque;

import dresseur.DresseurIA;
import interfaces.IPokedex;
import org.junit.Assert;
import org.junit.Test;
import pokedex.Pokedex;
import pokemon.Pokemon;

/**
 * Une classe qui est utilisée pour tester la classe Strategy.
 *
 * @author Lacroix Baptiste
 */
public class TestStrategy {
    /**
     * Créer un nouvel objet DresseurIA et l'affecter à la variable IA.
     */
    private IPokedex pokedex = new Pokedex();
    private final DresseurIA IA = new DresseurIA("IA1", pokedex);

    /**
     * Test que la fonction chositCombattant renvoie bien un Type Pokemon
     */
    @Test
    public void testChoisitCombattant() {
        Assert.assertEquals(IA.choisitCombattant().getClass(), Pokemon.class);
    }

    /**
     * Test que la fonction combattantContre renvoie bien un Type Pokemon
     */
    @Test
    public void testChoisitCombattantContre() {
        Assert.assertEquals(IA.choisitCombattantContre(IA.getPokemon(1)).getClass(), Pokemon.class);
    }
}
