package pokemon;

import interfaces.*;
import org.junit.Assert;
import attaque.Capacite;
import org.junit.Test;
import statsPokemon.Categorie;
import statsPokemon.Stat;
import statsPokemon.Type;


/**
 * Une classe qui est utilisée pour tester la classe Pokemon.
 *
 * @author Lacroix baptiste and Vidal Théo
 */
public class PokemonTest {

    /**
     * Il teste la fonction getStat() de la classe Pokemon
     */
    @Test
    public void testGetStat() {
        IType[] type = new IType[2];
        type[0] = Type.Plante;
        type[1] = Type.Poison;
        IStat stats = new Stat(45, 49, 49, 65, 45);
        IStat evstats = new Stat(0, 0, 0, 1, 0);
        IEspece espece = new Espece(stats, "Bulbizarre", 1, 64, evstats, type);
        IPokemon pokemon = new Pokemon(1, "Bulbizarre", 1, espece);

        // Stat
        System.out.println(pokemon.getStat());
        Assert.assertEquals(12, pokemon.getStat().getPV());
        Assert.assertEquals(6, pokemon.getStat().getForce());
        Assert.assertEquals(6, pokemon.getStat().getDefense());
        Assert.assertEquals(6, pokemon.getStat().getSpecial());
        Assert.assertEquals(6, pokemon.getStat().getVitesse());
    }

    /**
     * Il teste que l'expérience d'un pokémon est égale à l'expérience de base de l'espèce
     */
    @Test
    public void testPeutChnagerNiveau() {
        IType[] type = new IType[2];
        type[0] = Type.Plante;
        type[1] = Type.Poison;
        IStat stats = new Stat(45, 49, 49, 65, 45);
        IStat evstats = new Stat(0, 0, 0, 1, 0);
        IEspece espece = new Espece(stats, "Bulbizarre", 1, 64, evstats, type);
        IPokemon pokemon = new Pokemon(1, "Bulbizarre", 1, espece);

        // Experience
        Assert.assertTrue(Math.abs(pokemon.getExperience() - pokemon.getExperience()) <= 0.00000001);
    }

    /**
     * Il teste la fonction getNiveau() de la classe Pokemon
     */
    @Test
    public void testGetNiveau() {
        IType[] type = new IType[2];
        type[0] = Type.Plante;
        type[1] = Type.Poison;
        IStat stats = new Stat(45, 49, 49, 65, 45);
        IStat evstats = new Stat(0, 0, 0, 1, 0);
        IEspece espece = new Espece(stats, "Bulbizarre", 1, 64, evstats, type);
        IPokemon pokemon = new Pokemon(1, "Bulbizarre", 1, espece);

        // Niveau
        Assert.assertEquals(1, pokemon.getNiveau());
    }

    /**
     * Il teste que l'id d'un pokémon est le même que celui donné dans le constructeur
     */
    @Test
    public void testGetId() {
        IType[] type = new IType[2];
        type[0] = Type.Plante;
        type[1] = Type.Poison;
        IStat stats = new Stat(45, 49, 49, 65, 45);
        IStat evstats = new Stat(0, 0, 0, 1, 0);
        IEspece espece = new Espece(stats, "Bulbizarre", 1, 64, evstats, type);
        IPokemon pokemon = new Pokemon(1, "Bulbizarre", 64, espece);

        // id
        Assert.assertEquals(1, pokemon.getId());
    }

    /**
     * Il teste la fonction getNom() de la classe Pokemon
     */
    @Test
    public void testGetNom() {
        IType[] type = new IType[2];
        type[0] = Type.Plante;
        type[1] = Type.Poison;
        IStat stats = new Stat(45, 49, 49, 65, 45);
        IStat evstats = new Stat(0, 0, 0, 1, 0);
        IEspece espece = new Espece(stats, "Bulbizarre", 1, 64, evstats, type);
        IPokemon pokemon = new Pokemon(1, "Bulbizarre", 64, espece);

        // Nom
        Assert.assertEquals("Bulbizarre", pokemon.getNom());
        Assert.assertNotNull("Bulbizarre", pokemon.getNom());
        Assert.assertNotEquals("Bulbizae", pokemon.getNom());
    }

    /**
     * Il teste que le pourcentage de HP d'un Pokemon est de 100% lors de sa création
     */
    @Test
    public void testGetPourcentagePV() {
        IType[] type = new IType[2];
        type[0] = Type.Plante;
        type[1] = Type.Poison;
        IStat stats = new Stat(45, 49, 49, 65, 45);
        IStat evstats = new Stat(0, 0, 0, 1, 0);
        IEspece espece = new Espece(stats, "Bulbizarre", 1, 64, evstats, type);
        IPokemon pokemon = new Pokemon(1, "Bulbizarre", 64, espece);

        // Pourcentage
        Assert.assertTrue(Math.abs(100.0 - pokemon.getPourcentagePV()) <= 0.00000001);
    }

    /**
     * Il teste que la méthode getEspece() renvoie la bonne espèce
     */
    @Test
    public void testGetEspece() {
        IType[] type = new IType[2];
        type[0] = Type.Plante;
        type[1] = Type.Poison;
        IStat stats = new Stat(45, 49, 49, 65, 45);
        IStat evstats = new Stat(0, 0, 0, 1, 0);
        IEspece espece = new Espece(stats, "Bulbizarre", 1, 64, evstats, type);
        IPokemon pokemon = new Pokemon(1, "Bulbizarre", 64, espece);

        // Espece
        Assert.assertEquals(espece, pokemon.getEspece());
    }

    /**
     * Il teste la méthode 'apprendCapacites' de la classe 'Pokemon'
     */
    @Test
    public void testApprendCapacites() {
        IType[] type = new IType[2];
        type[0] = Type.Plante;
        type[1] = Type.Poison;
        IStat stats = new Stat(45, 49, 49, 65, 45);
        IStat evstats = new Stat(0, 0, 0, 1, 0);
        IEspece espece = new Espece(stats, "Bulbizarre", 1, 64, evstats, type);
        IPokemon pokemon = new Pokemon(1, "Bulbizarre", 64, espece);

        ICapacite[] capacite = new Capacite[4];
        capacite[0] = new Capacite("Écras'Face", 1.00, 40,
                35, Categorie.Physique, Type.Normal, 0);

        capacite[1] = new Capacite("Poing Karaté", 1.00, 50,
                25, Categorie.Physique, Type.Normal, 0);

        capacite[2] = new Capacite("Poing Feu", 1.00, 75,
                15, Categorie.Physique, Type.Feu, 0);

        capacite[3] = new Capacite("Guillotine", 0.30, -1,
                5, Categorie.Physique, Type.Normal, 0);

        pokemon.apprendCapacites(capacite);

        for (ICapacite cap : pokemon.getCapacitesApprises()) {
            Assert.assertNull(cap);
        }

        capacite[0] = new Capacite("Coupe-Vent", 1.00, 40,
                35, Categorie.Physique, Type.Normal, 0);

        capacite[1] = new Capacite("Coupe", 1.00, 50,
                25, Categorie.Physique, Type.Normal, 0);

        capacite[2] = new Capacite("Étreinte", 1.00, 75,
                15, Categorie.Physique, Type.Feu, 0);

        capacite[3] = new Capacite("Fouet Lianes", 0.30, -1,
                5, Categorie.Physique, Type.Normal, 0);

        pokemon.apprendCapacites(capacite);

        for (ICapacite cap : pokemon.getCapacitesApprises()) {
            Assert.assertNotNull(cap);
        }

        Assert.assertEquals(capacite[0], pokemon.getCapacitesApprises()[0]);
        Assert.assertEquals(capacite[1], pokemon.getCapacitesApprises()[1]);
        Assert.assertEquals(capacite[2], pokemon.getCapacitesApprises()[2]);
        Assert.assertEquals(capacite[3], pokemon.getCapacitesApprises()[3]);
        Assert.assertEquals(4, pokemon.getCapacitesApprises().length);
    }

    /**
     * Il teste si la fonction remplacerCapacite() fonctionne
     */
    @Test
    public void testRemplaceCapacite() {
        try {
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

            pokemon.remplaceCapacite(1, capacite[0]);
            Assert.assertNotEquals(capacite[1], pokemon.getCapacitesApprises()[1]);
            Assert.assertEquals(capacite[0], pokemon.getCapacitesApprises()[1]);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Il teste si un pokémon est évanoui ou non
     */
    @Test
    public void testEstEvanoui() {
        IType[] type = new IType[2];
        type[0] = Type.Plante;
        type[1] = Type.Poison;
        IStat stats = new Stat(45, 49, 49, 65, 45);
        IStat evstats = new Stat(0, 0, 0, 1, 0);
        IEspece espece = new Espece(stats, "Bulbizarre", 1, 64, evstats, type);
        IPokemon pokemon = new Pokemon(1, "Bulbizarre", 64, espece);

        Assert.assertFalse(pokemon.estEvanoui());
    }

    /**
     * Il vérifie si un pokémon a changé de niveau
     */
    @Test
    public void testAChangeNiveau() {
        IType[] type = new IType[2];
        type[0] = Type.Plante;
        type[1] = Type.Poison;
        IStat stats = new Stat(45, 49, 49, 65, 45);
        IStat evstats = new Stat(0, 0, 0, 1, 0);
        IEspece espece = new Espece(stats, "Bulbizarre", 1, 64, evstats, type);
        IPokemon pokemon = new Pokemon(1, "Bulbizarre", 64, espece);

        Assert.assertFalse(pokemon.aChangeNiveau());
    }

    /**
     * Il teste qu'un pokémon peut évoluer
     */
    @Test
    public void testPeutMuter() {
        IType[] type = new IType[2];
        type[0] = Type.Plante;
        type[1] = Type.Poison;
        IStat stats = new Stat(45, 49, 49, 65, 45);
        IStat evstats = new Stat(0, 0, 0, 1, 0);
        IEspece espece = new Espece(stats, "Bulbizarre", 1, 64, evstats, type);
        IPokemon pokemon = new Pokemon(1, "Bulbizarre", 64, espece);

        Assert.assertFalse(pokemon.peutMuter());
    }

    /**
     * Il teste la guérison d'un pokémon
     */
    @Test
    public void testSoigne() {
        IType[] type = new IType[2];
        type[0] = Type.Plante;
        type[1] = Type.Poison;
        IStat stats = new Stat(45, 49, 49, 65, 45);
        IStat evstats = new Stat(0, 0, 0, 1, 0);
        IEspece espece = new Espece(stats, "Bulbizarre", 1, 64, evstats, type);
        IPokemon pokemon = new Pokemon(1, "Bulbizarre", 1, espece);
        System.out.println("Stat Pokemon : " + pokemon.getStat());
        pokemon.getStat().setPV(10);
        System.out.println("[Le Pokémon se fait soigner]");
        pokemon.soigne();
        System.out.println("Affichage des PV suite à sa guérison: ");
        Assert.assertEquals(11, pokemon.getStat().getPV());
    }
}