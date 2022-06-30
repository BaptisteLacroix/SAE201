package pokedex;

import interfaces.IPokemon;
import interfaces.IType;
import org.junit.Assert;
import org.junit.Test;
import statsPokemon.Type;

/**
 * Une classe qui est utilisée pour tester la classe Pokedex.
 *
 * @author Lacroix Baptiste
 */
public class PokedexTest {

    /**
     * Création d'une nouvelle instance de la classe Pokedex.
     */
    private final Pokedex pokedex = new Pokedex();

    /**
     * Il teste que la méthode `getRanch()` de la classe `Pokedex` renvoie un tableau de 6 objets `IPokemon`
     */
    @Test
    public void engendreRanch() {
        IPokemon[] p = pokedex.engendreRanch();
        for (IPokemon pok : p) {
            Assert.assertNotNull(pok);
        }
        Assert.assertEquals(6, p.length);
    }

    /**
     * Il vérifie que la fonction getInfo() renvoie les informations correctes pour Bulbizarre, et qu'elle renvoie null
     * pour Bulbizare
     */
    @Test
    public void getInfo() {
        Assert.assertNotNull(pokedex.getInfo("Bulbizarre"));
        Assert.assertNull(pokedex.getInfo("Bulbizare"));
    }

    /**
     * Il teste que la méthode getEfficacite renvoie la valeur correcte pour les paramètres typeA et typeD
     */
    @Test
    public void getEfficacite() {
        IType typeA = Type.Insecte;
        IType typeD = Type.Sol;
        Assert.assertNotNull(pokedex.getEfficacite(typeA, typeD));
        Assert.assertTrue(Math.abs(0.5 - pokedex.getEfficacite(typeA, typeD)) <= 0.00000001);
    }

    /**
     * Il teste que la fonction getCapacite renvoie l'objet Capacite correct
     */
    @Test
    public void getCapacite() {
        Assert.assertEquals("Écras'Face", pokedex.getCapacite("Écras'Face").getNom());
        Assert.assertEquals("Écras'Face", pokedex.getCapacite(1).getNom());
    }
}
