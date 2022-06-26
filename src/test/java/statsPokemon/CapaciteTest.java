package statsPokemon;

import attaque.Capacite;
import interfaces.*;
import org.junit.Assert;
import org.junit.Test;
import pokemon.Espece;
import pokemon.Pokemon;

/**
 * Une classe qui est utilisée pour tester la classe Capacite.
 *
 * @author Lacroix baptiste and Vidal Théo
 */
public class CapaciteTest {

    /**
     * Il teste la méthode calculeDommage() de la classe Capacite
     */
    @Test
    public void testCalculeDommage() {
        IType[] type = new IType[2];
        type[0] = Type.Plante;
        type[1] = Type.Poison;
        IStat stats = new Stat(45, 49, 49, 65, 45);
        IStat evstats = new Stat(0, 0, 0, 1, 0);
        IEspece espece = new Espece(stats, "Bulbizarre", 1, 64, evstats, type);
        IPokemon pokemon = new Pokemon(1, "Bulbizarre", 1, espece);

        ICapacite[] capacite = new Capacite[4];
        capacite[0] = new Capacite("Plaquage", 1.00, 40,
                35, Categorie.Physique, Type.Normal, 0);

        capacite[1] = new Capacite("Damoclès", 1.00, 50,
                25, Categorie.Physique, Type.Normal, 0);

        capacite[2] = new Capacite("Méga-Sangsu", 1.00, 75,
                15, Categorie.Physique, Type.Feu, 0);

        capacite[3] = new Capacite("Patience", 0.30, -1,
                5, Categorie.Physique, Type.Normal, 0);

        pokemon.apprendCapacites(capacite);

        // System.out.println(pokemon.getCapacitesApprises()[0].calculeDommage(pokemon, pokemon));
        Assert.assertNotNull(pokemon.getCapacitesApprises()[0].calculeDommage(pokemon, pokemon));
    }

    /**
     * Il teste que le PP d'un mouvement est diminué de un à chaque fois qu'il est utilisé
     */
    @Test
    public void testUtilise() {
        IType[] type = new IType[2];
        type[0] = Type.Plante;
        type[1] = Type.Poison;
        IStat stats = new Stat(45, 49, 49, 65, 45);
        IStat evstats = new Stat(0, 0, 0, 1, 0);
        IEspece espece = new Espece(stats, "Bulbizarre", 1, 64, evstats, type);
        IPokemon pokemon = new Pokemon(1, "Bulbizarre", 1, espece);

        ICapacite[] capacite = new Capacite[4];
        capacite[0] = new Capacite("Plaquage", 1.00, 40,
                35, Categorie.Physique, Type.Normal, 0);

        capacite[1] = new Capacite("Damoclès", 1.00, 50,
                25, Categorie.Physique, Type.Normal, 0);

        capacite[2] = new Capacite("Méga-Sangsu", 1.00, 75,
                15, Categorie.Physique, Type.Feu, 0);

        capacite[3] = new Capacite("Patience", 0.30, -1,
                5, Categorie.Physique, Type.Normal, 0);

        pokemon.apprendCapacites(capacite);

        pokemon.getCapacitesApprises()[0].utilise();
        pokemon.getCapacitesApprises()[0].utilise();
        pokemon.getCapacitesApprises()[0].utilise();
        Assert.assertEquals(32, pokemon.getCapacitesApprises()[0].getPP());
    }

    /**
     * Il teste que le PP du Bulbizarre est de 35
     */
    @Test
    public void testGetPP() {
        IType[] type = new IType[2];
        type[0] = Type.Plante;
        type[1] = Type.Poison;
        IStat stats = new Stat(45, 49, 49, 65, 45);
        IStat evstats = new Stat(0, 0, 0, 1, 0);
        IEspece espece = new Espece(stats, "Bulbizarre", 1, 64, evstats, type);
        IPokemon pokemon = new Pokemon(1, "Bulbizarre", 1, espece);

        ICapacite[] capacite = new Capacite[4];
        capacite[0] = new Capacite("Plaquage", 1.00, 40,
                35, Categorie.Physique, Type.Normal, 0);

        capacite[1] = new Capacite("Damoclès", 1.00, 50,
                25, Categorie.Physique, Type.Normal, 0);

        capacite[2] = new Capacite("Méga-Sangsu", 1.00, 75,
                15, Categorie.Physique, Type.Feu, 0);

        capacite[3] = new Capacite("Patience", 0.30, -1,
                5, Categorie.Physique, Type.Normal, 0);

        pokemon.apprendCapacites(capacite);
        Assert.assertEquals(35, pokemon.getCapacitesApprises()[0].getPP());
    }

    /**
     * Il teste que la fonction resetPP() de la classe Capacite fonctionne correctement
     */
    @Test
    public void testResetPP() {
        IType[] type = new IType[2];
        type[0] = Type.Plante;
        type[1] = Type.Poison;
        IStat stats = new Stat(45, 49, 49, 65, 45);
        IStat evstats = new Stat(0, 0, 0, 1, 0);
        IEspece espece = new Espece(stats, "Bulbizarre", 1, 64, evstats, type);
        IPokemon pokemon = new Pokemon(1, "Bulbizarre", 1, espece);

        ICapacite[] capacite = new Capacite[4];
        capacite[0] = new Capacite("Plaquage", 1.00, 40,
                35, Categorie.Physique, Type.Normal, 0);

        capacite[1] = new Capacite("Damoclès", 1.00, 50,
                25, Categorie.Physique, Type.Normal, 0);

        capacite[2] = new Capacite("Méga-Sangsu", 1.00, 75,
                15, Categorie.Physique, Type.Feu, 0);

        capacite[3] = new Capacite("Patience", 0.30, -1,
                5, Categorie.Physique, Type.Normal, 0);

        pokemon.apprendCapacites(capacite);

        pokemon.getCapacitesApprises()[0].utilise();
        pokemon.getCapacitesApprises()[0].utilise();
        pokemon.getCapacitesApprises()[0].utilise();
        Assert.assertEquals(32, pokemon.getCapacitesApprises()[0].getPP());
        pokemon.getCapacitesApprises()[0].resetPP();
        Assert.assertEquals(35, pokemon.getCapacitesApprises()[0].getPP());
    }
}