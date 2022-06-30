package pokemon;

import interfaces.IEspece;
import interfaces.IStat;
import interfaces.IType;
import org.junit.Assert;
import org.junit.Test;
import statsPokemon.Categorie;
import statsPokemon.Stat;
import statsPokemon.Type;

/**
 * Une classe qui est utilisée pour tester la classe Espece.
 *
 * @author Lacroix Baptiste
 */
public class EspeceTest {

    /**
     * Il teste la fonction getBaseStat() de la classe Espece
     */
    @Test
    public void testGetBaseStat() {
        IType[] type = new IType[2];
        type[0] = Type.Plante;
        type[1] = Type.Poison;
        IStat stats = new Stat(45, 49, 49, 65, 45);
        IStat evstats = new Stat(0, 0, 0, 1, 0);
        IEspece espece = new Espece(stats, "Bulbizarre", 1, 64, evstats, type);

        // BaseStat
        Assert.assertEquals(45, espece.getBaseStat().getPV());
        Assert.assertEquals(49, espece.getBaseStat().getForce());
        Assert.assertEquals(49, espece.getBaseStat().getDefense());
        Assert.assertEquals(65, espece.getBaseStat().getSpecial());
        Assert.assertEquals(45, espece.getBaseStat().getVitesse());
    }

    /**
     * Il teste la fonction getNom() de la classe Espece
     */
    @Test
    public void testGetNom() {
        IType[] type = new IType[2];
        type[0] = Type.Plante;
        type[1] = Type.Poison;
        IStat stats = new Stat(45, 49, 49, 65, 45);
        IStat evstats = new Stat(0, 0, 0, 1, 0);
        IEspece espece = new Espece(stats, "Bulbizarre", 1, 64, evstats, type);

        // Nom
        Assert.assertEquals("Bulbizarre", espece.getNom());
        Assert.assertNotNull("Bulbizarre", espece.getNom());
        Assert.assertNotEquals("Bulbizae", espece.getNom());
    }

    /**
     * TestGetNiveauDepart() teste la fonction getNiveauDepart().
     */
    @Test
    public void testGetNiveauDepart() {
        IType[] type = new IType[2];
        type[0] = Type.Plante;
        type[1] = Type.Poison;
        IStat stats = new Stat(45, 49, 49, 65, 45);
        IStat evstats = new Stat(0, 0, 0, 1, 0);
        IEspece espece = new Espece(stats, "Bulbizarre", 1, 64, evstats, type);

        // Niveau Départ
        Assert.assertEquals(1, espece.getNiveauDepart());
    }

    /**
     * Il teste que l'expérience de base d'un Bulbizarre est de 64
     */
    @Test
    public void testGetBaseExp() {
        IType[] type = new IType[2];
        type[0] = Type.Plante;
        type[1] = Type.Poison;
        IStat stats = new Stat(45, 49, 49, 65, 45);
        IStat evstats = new Stat(0, 0, 0, 1, 0);
        IEspece espece = new Espece(stats, "Bulbizarre", 1, 64, evstats, type);

        // BaseExp
        Assert.assertEquals(64, espece.getBaseExp());
    }

    /**
     * Il teste la fonction getGainsStat() de la classe Espece
     */
    @Test
    public void testGetGainsStat() {
        IType[] type = new IType[2];
        type[0] = Type.Plante;
        type[1] = Type.Poison;
        IStat stats = new Stat(45, 49, 49, 65, 45);
        IStat evstats = new Stat(0, 0, 0, 1, 0);
        IEspece espece = new Espece(stats, "Bulbizarre", 1, 64, evstats, type);

        // GainsStat
        Assert.assertEquals(0, espece.getGainsStat().getPV());
        Assert.assertEquals(0, espece.getGainsStat().getForce());
        Assert.assertEquals(0, espece.getGainsStat().getDefense());
        Assert.assertEquals(1, espece.getGainsStat().getSpecial());
        Assert.assertEquals(0, espece.getGainsStat().getVitesse());
    }

    /**
     * Il teste la fonction setCapSet()
     */
    @Test
    public void setCapSet() {
        IType[] type = new IType[2];
        type[0] = Type.Plante;
        type[1] = Type.Poison;
        IStat stats = new Stat(45, 49, 49, 65, 45);
        IStat evstats = new Stat(0, 0, 0, 1, 0);
        IEspece espece = new Espece(stats, "Bulbizarre", 1, 64, evstats, type);

        Assert.assertEquals("Coupe-Vent", espece.getCapSet()[0].getNom());
        Assert.assertEquals(80, espece.getCapSet()[0].getPuissance());
        Assert.assertTrue(Math.abs(0.75 - espece.getCapSet()[0].getPrecision()) <= 0.00000001);
        Assert.assertEquals(10, espece.getCapSet()[0].getPP());
        Assert.assertEquals(Categorie.Special, espece.getCapSet()[0].getCategorie());
        Assert.assertEquals(Type.Normal, espece.getCapSet()[0].getType());

        IType[] type2 = new IType[2];
        type2[0] = Type.Normal;
        type2[1] = null;
        IStat stats2 = new Stat(40, 45, 35, 40, 90);
        IStat evstats2 = new Stat(0, 0, 0, 0, 1);
        IEspece espece2 = new Espece(stats2, "Miaouss", 1, 64, evstats2, type2);

        Assert.assertEquals("Morsure", espece2.getCapSet()[6].getNom());
        Assert.assertEquals(60, espece2.getCapSet()[6].getPuissance());
        Assert.assertTrue(Math.abs(1.00 - espece2.getCapSet()[6].getPrecision()) <= 0.00000001);
        Assert.assertEquals(25, espece2.getCapSet()[6].getPP());
        Assert.assertEquals(Categorie.Physique, espece2.getCapSet()[6].getCategorie());
        Assert.assertEquals(Type.Normal, espece2.getCapSet()[6].getType());

        // IEspece espece3 = new Espece(stats2, "Bulbizarre", 1, 64, evstats2, type2);
    }

    /**
     * Il teste la fonction getTypes() de la classe Espece
     */
    @Test
    public void testGetTypes() {
        IType[] type = new IType[2];
        type[0] = Type.Plante;
        type[1] = Type.Poison;
        IStat stats = new Stat(45, 49, 49, 65, 45);
        IStat evstats = new Stat(0, 0, 0, 1, 0);
        IEspece espece = new Espece(stats, "Bulbizarre", 1, 64, evstats, type);

        // Type
        Assert.assertEquals(Type.Plante, espece.getTypes()[0]);
        Assert.assertNotEquals(Type.Eau, espece.getTypes()[0]);
        Assert.assertEquals(Type.Poison, espece.getTypes()[1]);
        Assert.assertNotNull(espece.getTypes()[0]);
    }

    /**
     * Il teste que l'évolution d'un Bulbizarre n'est pas nulle
     */
    @Test
    public void testGetEvolution() {
        IType[] type = new IType[2];
        type[0] = Type.Plante;
        type[1] = Type.Poison;
        IStat stats = new Stat(45, 49, 49, 65, 45);
        IStat evstats = new Stat(0, 0, 0, 1, 0);
        IEspece espece = new Espece(stats, "Bulbizarre", 1, 64, evstats, type);

        // Evolution
        Assert.assertNotNull(espece.getEvolution(1));
    }
}
