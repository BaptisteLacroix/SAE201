/**
 * Université Côte d'Azur
 * IUT Côte d'Azur
 * Département Informatique
 *
 * @date Espece.java
 */
package pokemon;

import attaque.Capacite;
import interfaces.ICapacite;
import interfaces.IEspece;
import interfaces.IStat;
import interfaces.IType;
import statsPokemon.Categorie;
import statsPokemon.Stat;
import statsPokemon.Type;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import useLogger.MyLoggerConfiguration;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Level;


/**
 * Classe générant une espèce avec toutes ses spécificités. Implément IEspece
 *
 * @author Lacroix Baptiste and Vidal Théo
 */
public class Espece implements IEspece {
    /**
     * Objet IStat contenant les stats de base de l'espèce
     */
    private final IStat baseStat;
    /**
     * Nom de l'Espece
     */
    private final String nom;
    /**
     * Niveau de base de l'Espece
     */
    private final int niveauDepart;
    /**
     * Expérience de base d'une Espèce
     */
    private final int baseExp;
    /**
     * Objet IStat contenant les EV de l'Espece
     */
    private final IStat gainsStat; //  Stats EV
    /**
     * Tableau de ICapacite contenant les capacités disponible pour cette Espece
     */
    private ICapacite[] capSet;
    /**
     * Tableau de IType contenant les types de l'Espece
     */
    private final IType[] types;


    /**
     * Constructeur de l'Espece
     *
     * @param baseIStat    Base de stats de l'Espece
     * @param nom          Nom de l'Espece
     * @param niveauDepart Niveau de base de l'Espece
     * @param baseExp      Expérience de base de l'Espece
     * @param gainsStat    Stats Ev de l'Espece
     * @param types        Types de l'Espece
     */
    public Espece(IStat baseIStat, String nom, int niveauDepart, int baseExp, IStat gainsStat, IType[] types) {
        this.baseStat = baseIStat;
        this.nom = nom;
        this.niveauDepart = niveauDepart;
        this.baseExp = baseExp;
        this.gainsStat = gainsStat;
        this.types = types;
        this.setCapSet();
    }


    /**
     * Cette fonction renvoie la statistique de base de l'élément
     *
     * @return La baseStat
     */
    @Override
    public IStat getBaseStat() {
        return baseStat;
    }


    /**
     * Cette fonction retourne le nom de l'objet
     *
     * @return Le nom du Pokemon
     */
    @Override
    public String getNom() {
        return nom;
    }


    /**
     * Cette fonction renvoie le niveau de départ du Pokemon
     *
     * @return La variable niveauDepart.
     */
    @Override
    public int getNiveauDepart() {
        return niveauDepart;
    }


    /**
     * Renvoie l'expérience de base du Pokémon.
     *
     * @return L'expérience de base du Pokémon.
     */
    @Override
    public int getBaseExp() {
        return baseExp;
    }


    /**
     * Renvoie la statistique utilisée pour calculer les gains.
     *
     * @return La variable gainsStat.
     */
    @Override
    public IStat getGainsStat() {
        return gainsStat;
    }


    /**
     * Il lit un fichier csv et crée une liste d'objets ICapacite
     */
    private void setCapSet() {
        boolean trouve = false;
        try {
            FileReader file = new FileReader("./resources/listeCapacitesEspeces.csv");
            BufferedReader reader = new BufferedReader(file);
            while (reader.ready() && !trouve) {
                Scanner scanner = new Scanner(reader.readLine()).useDelimiter(";");
                String[] tab = scanner.nextLine().split(";");
                if (tab[0].equals(this.nom)) {
                    this.creatCap(tab);
                    trouve = true;
                }
            }
        } catch (IOException e) {
            MyLoggerConfiguration.printLog(Level.SEVERE, "Erreur lors de la lecture du fichier csv (Espece).\n");
            e.printStackTrace();
        }
    }


    /**
     * Il lit un fichier csv et crée une liste d'objets ICapacite
     *
     * @param tableau le tableau de chaînes contenant le nom, le type et les capacités du pokémon.
     */
    private void creatCap(String[] tableau) {

        boolean trouve = false;
        List<ICapacite> cap = new ArrayList<>();
        for (int i = 1; i < tableau.length; i++) {
            try {
                FileReader file2 = new FileReader("./resources/listeCapacites.csv");
                BufferedReader reader = new BufferedReader(file2);
                reader.readLine();
                while (reader.ready() && !trouve) {
                    Scanner scanner = new Scanner(reader.readLine()).useDelimiter(";");
                    String[] tab = scanner.nextLine().split(";");
                    if (tab[0].equals(tableau[i])) {
                        cap.add(new Capacite(tab[0], Double.parseDouble(tab[2]), Integer.parseInt(tab[1]),
                                Integer.parseInt(tab[3]), Categorie.valueOf(tab[5]), conversionStringType(tab[6]), this.getLvlCap(tab[0])));
                    }
                }
            } catch (IOException e) {
                MyLoggerConfiguration.printLog(Level.SEVERE, "Erreur lors de la lecture du fichier csv (Espece).\n");
                e.printStackTrace();
            }
        }
        this.capSet = new ICapacite[cap.size()];
        for (int i = 0; i < cap.size(); i++) {
            if (cap.get(i) != null) {
                this.capSet[i] = cap.get(i);
            }
        }
    }

    /**
     * Il lit un fichier JSON et renvoie le niveau auquel un Pokémon peut apprendre un mouvement
     *
     * @param name Le nom du déménagement.
     * @return Le niveau du pokémon auquel il apprend le mouvement.
     */
    private int getLvlCap(String name) {
        int lvl = 0;
        try {
            JSONParser parser = new JSONParser();
            JSONObject obj = (JSONObject) parser.parse(new FileReader("./resources/listeCapacitesEspecesWLVL.json"));
            JSONArray modules = (JSONArray) obj.get("Pokemon");
            for (Object m : modules) {
                JSONObject jsonObj = (JSONObject) m;
                if (jsonObj.get("nom").equals(this.nom)) {
                    JSONArray jsonArray2 = (JSONArray) jsonObj.get("moves");
                    for (Object m3 : jsonArray2) {
                        JSONObject element = (JSONObject) m3;
                        if (element.get("move").equals(name)) {
                            lvl = Integer.parseInt((String) element.get("lvl"));
                        }
                    }
                }
            }
        } catch (IOException | ParseException e) {
            MyLoggerConfiguration.printLog(Level.SEVERE, "Erreur lors de la lecture du fichier JSON (Espece).\n");
            e.printStackTrace();
        }
        return lvl;
    }

    /**
     * Une méthode qui renvoie l'ensemble des capacités disponibles pour cette espèce.
     *
     * @return un objet ICapacite
     */
    @Override
    public ICapacite[] getCapSet() {
        return this.capSet;
    } //ensemble des capacités disponibles pour cette espèce


    /**
     * Une méthode qui renvoie les types du pokémon.
     *
     * @return les types du pokémon
     */
    @Override
    public IType[] getTypes() {
        return types;
    } //une espece de pokemon peut avoir un ou deux types


    /**
     * Renvoie l'évolution si possible du Pokemon actuel
     *
     * @param niveau niveau du Pokemon dont on cherche son évolution
     * @return un objet IEspece
     */
    @Override
    public IEspece getEvolution(int niveau) {
        if (niveau >= 32)
            return null;
        Espece espece = null;
        try {
            boolean trouve = false;
            FileReader file = new FileReader("./resources/listePokemeon1G.csv");
            BufferedReader reader = new BufferedReader(file);
            reader.readLine();
            while (reader.ready() && !trouve) {
                Scanner scanner = new Scanner(reader.readLine()).useDelimiter(";");
                String[] tab = scanner.nextLine().split(";");
                if (tab[1].equals(this.nom) && !tab[17].equals("")) {
                    scanner = new Scanner(reader.readLine()).useDelimiter(";");
                    tab = scanner.nextLine().split(";");
                    IType[] type = new IType[2];
                    IStat stats = new Stat(Integer.parseInt(tab[2]), Integer.parseInt(tab[3]), Integer.parseInt(tab[4]),
                            Integer.parseInt(tab[5]), Integer.parseInt(tab[6]));
                    IStat evstats = new Stat(Integer.parseInt(tab[8]), Integer.parseInt(tab[9]), Integer.parseInt(tab[10]),
                            Integer.parseInt(tab[11]), Integer.parseInt(tab[12]));
                    type[0] = conversionStringType(tab[13]);
                    type[1] = conversionStringType(tab[14]);
                    espece = new Espece(stats, tab[1], Integer.parseInt(tab[15]),
                            Integer.parseInt(tab[7]), evstats, type);
                    trouve = true;
                }
            }
            reader.close();
            file.close();
        } catch (IOException e) {
            MyLoggerConfiguration.printLog(Level.SEVERE, "Erreur lors de la lecture du fichier csv (Espece).\n");
            e.printStackTrace();
        }
        return espece;
    }  //renvoie null si aucune evolution possible

    /**
     * Il convertit une chaîne en un Type
     *
     * @param EspeceType Le type de Pokémon.
     * @return Le type de pokémon
     */
    private static IType conversionStringType(String EspeceType) {
        switch (EspeceType) {
            case "Combat":
                return Type.Combat;
            case "Dragon":
                return Type.Dragon;
            case "Eau":
                return Type.Eau;
            case "Electrik":
                return Type.Electrik;
            case "Feu":
                return Type.Feu;
            case "Glace":
                return Type.Glace;
            case "Insecte":
                return Type.Insecte;
            case "Normal":
                return Type.Normal;
            case "Plante":
                return Type.Plante;
            case "Poison":
                return Type.Poison;
            case "Psy":
                return Type.Psy;
            case "Roche":
                return Type.Roche;
            case "Sol":
                return Type.Sol;
            case "Spectre":
                return Type.Spectre;
            case "Vol":
                return Type.Vol;
            default:
                return null;
        }
    }
}
