/**
 * Université Côte d'Azur
 * IUT Côte d'Azur
 * Département Informatique
 *
 * @date Pokemon.java
 */
package pokemon;

import attaque.Capacite;
import interfaces.IEspece;
import interfaces.IPokemon;
import interfaces.IStat;
import interfaces.IAttaque;
import interfaces.ICapacite;
import statsPokemon.Stat;
import useLogger.MyLoggerConfiguration;

import java.util.Random;
import java.util.logging.Level;

/**
 * Classe générant un Pokémon avec toutes ses spécificités. implément IPokemon
 *
 * @author Lacroix Baptiste
 */
public class Pokemon implements IPokemon {
    /**
     * id du Pokémon
     */
    private final int id;
    /**
     * Nom du Pokemon
     */
    private final String nom;
    /**
     * Permet de connaître l'ancien niveau avant évolution
     */
    private int ancien_niveau;
    /**
     * Niveau du Pokémon
     */
    private int niveau;
    /**
     * Stat du Pokémon
     */
    private IStat stat;
    /**
     * Expérience du Pokémon
     */
    private double experience;
    /**
     * Pourcentage de PV du Pokémon
     */
    private double pourcentagePV;
    /**
     * Espece du Pokémon
     */
    private IEspece espece;
    /**
     * Tableau de Capacités apprisent par le Pokémon
     */
    private final ICapacite[] capacites = new ICapacite[4];
    /**
     * Dv du Pokémon
     */
    private IStat DV;
    /**
     * Objet Random qui permet de générer des nombres aléatoire
     */
    private final Random rand = new Random();
    /**
     * Déclarer une variable appelée compteur et l'initialiser à 0.
     */
    private int counter = 0;

    /**
     * Construteur du Pokémon
     *
     * @param id     id du Pokémon
     * @param nom    nom du Pokémon
     * @param niveau niveau du Pokémon
     * @param espece Espece du Pokémon
     */
    public Pokemon(int id, String nom, int niveau, IEspece espece) {
        this.id = id;
        this.nom = nom;
        this.ancien_niveau = niveau;
        this.niveau = niveau;
        this.stat = this.copyStats(espece.getBaseStat());
        this.experience = 0.0;
        this.pourcentagePV = 100.0;
        this.espece = espece;
        this.setDV();
        this.miseAjourStats();
    }

    /**
     * Cette fonction ajoute un au compteur.
     */
    public void addCount() {
        this.counter++;
    }

    /**
     * Cette fonction renvoie la valeur de la variable compteur.
     *
     * @return Le nombre de fois où la méthode a été appelée.
     */
    public int getCount() {
        return this.counter;
    }

    /**
     * Il crée un nouvel objet Stat avec les mêmes valeurs que l'objet Stat donné
     *
     * @param stats Les statistiques du pokémon
     * @return Un nouvel objet Stat avec les mêmes statistiques que celui transmis.
     */
    private IStat copyStats(IStat stats) {
        return new Stat(stats.getPV(), stats.getForce(), stats.getDefense(), stats.getSpecial(), stats.getVitesse());
    }


    /**
     * Il met à jour les stats du pokémon
     */
    private void miseAjourStats() {
        if (this.niveau == 1)
            this.stat.setPV(calculGainStatPV());
        else
            this.stat.setPV(calculGainStatPV() - this.stat.getPV());
        this.stat.setForce(calculGainStat(this.stat.getForce(), this.espece.getGainsStat().getForce(), espece.getGainsStat().getForce()));
        this.stat.setDefense(calculGainStat(this.stat.getDefense(), this.espece.getGainsStat().getDefense(), espece.getGainsStat().getDefense()));
        this.stat.setSpecial(calculGainStat(this.stat.getSpecial(), this.espece.getGainsStat().getSpecial(), espece.getGainsStat().getSpecial()));
        this.stat.setVitesse(calculGainStat(this.stat.getVitesse(), this.espece.getGainsStat().getVitesse(), espece.getGainsStat().getVitesse()));
    }

    /**
     * Calcule le gain de statistiques gagné par un pokémon lorsqu'il monte de niveau
     *
     * @param stat La statistique de base du Pokémon.
     * @param dv   La statistique de base du Pokémon.
     * @param ev   Valeur d'effort
     * @return Le gain d'une stat.
     */
    private int calculGainStat(int stat, int dv, int ev) {
        return (int) Math.round(((2 * (stat + dv) + ev / 4.0) * niveau) / 100 + 5);
    }

    /**
     * Calcule la quantité de HP gagnée par un Pokémon lorsqu'il monte de niveau
     *
     * @return la valeur des PV du pokémon.
     */
    private int calculGainStatPV() {
        return (int) Math.round(((2 * (this.stat.getPV() + this.espece.getGainsStat().getPV()) + espece.getGainsStat().getPV() / 4.0) * niveau) / 100 + niveau + 10);
    }

    /**
     * Création du DV du Pokémon lors de sa création
     */
    private void setDV() {
        int[] lowWeight = new int[4];
        int force;
        int defense;
        int vitesse;
        int special;
        force = rand.nextInt(16);
        defense = rand.nextInt(16);
        vitesse = rand.nextInt(16);
        special = rand.nextInt(16);
        lowWeight[0] = this.decimalToBinary(force)[3];
        lowWeight[1] = this.decimalToBinary(defense)[3];
        lowWeight[2] = this.decimalToBinary(vitesse)[3];
        lowWeight[3] = this.decimalToBinary(special)[3];

        this.DV = new Stat(this.binaryToDecimal(lowWeight), force, defense, special, vitesse);
    }


    /**
     * Il convertit un nombre décimal en binaire.
     *
     * @param num Le nombre à convertir en binaire
     * @return La représentation binaire du nombre.
     */
    private int[] decimalToBinary(int num) {
        int[] binary = new int[4];
        int i = 0;

        while (num > 0) {
            binary[i++] = num % 2;
            num = num / 2;
        }

        return binary;
    }

    /**
     * Il prend un tableau de nombres binaires et renvoie l'équivalent décimal
     *
     * @param binary le nombre binaire à convertir
     * @return La valeur décimale du nombre binaire.
     */
    private int binaryToDecimal(int[] binary) {
        int decimal = 0;
        for (int i = 0; i < binary.length - 1; i++) {
            if (binary[i] == 1) {
                decimal += Math.pow(2, i);
            }
        }
        return decimal;
    }

    /**
     * Cette fonction renvoie les statistiques de l'objet courant.
     *
     * @return L'objet statistique.
     */
    @Override
    public IStat getStat() {
        return stat;
    }


    /**
     * Cette fonction renvoie l'expérience du joueur.
     *
     * @return La variable d'expérience est renvoyée.
     */
    @Override
    public double getExperience() {
        return experience;
    }


    /**
     * Cette fonction retourne le niveau du Pokemon
     *
     * @return Le niveau du Pokemon
     */
    @Override
    public int getNiveau() {
        return niveau;
    }


    /**
     * Cette fonction renvoie l'identifiant du Pokemon.
     *
     * @return L'identifiant de l'objet.
     */
    @Override
    public int getId() {
        return id;
    }

    /**
     * Cette fonction retourne le nom du Pokemon
     *
     * @return Le nom du Pokemon
     */
    @Override
    public String getNom() {
        return nom;
    }


    /**
     * Il renvoie le pourcentage de la santé du Pokemon.
     *
     * @return Le pourcentage de la santé du Pokemon.
     */
    @Override
    public double getPourcentagePV() {
        return pourcentagePV;
    }


    /**
     * Il renvoie l'espèce du pokemon.
     *
     * @return L'espèce du pokemon.
     */
    @Override
    public IEspece getEspece() {
        return espece;
    }


    /**
     * Une méthode qui change l'espèce du pokémon.
     *
     * @param esp Nouvelle espèce du Pokemon
     */
    @Override
    public void vaMuterEn(IEspece esp) {
        this.espece = esp;
        this.niveau = this.espece.getNiveauDepart();
        this.experience = this.espece.getBaseExp();
        this.pourcentagePV = 100.0;
        this.stat = new Stat(this.espece.getBaseStat().getPV(), this.espece.getBaseStat().getForce(),
                this.espece.getBaseStat().getDefense(), this.espece.getBaseStat().getSpecial(),
                this.espece.getBaseStat().getVitesse());
    }   //Modifie l'espèce du Pokemon en esp


    /**
     * Un getter pour l'attribut `capacites`.
     */
    @Override
    public ICapacite[] getCapacitesApprises() {
        return this.capacites;
    }    //Tableau des capacités que le Pokemon peut utiliser


    /**
     * Méthode permettant de rajouter des capacités au Pokemon
     *
     * @param caps Capacités à ajouter au Pokemon
     */
    @Override
    public void apprendCapacites(ICapacite[] caps) {
        for (int i = 0; i < caps.length; i++) {
            for (ICapacite c : this.espece.getCapSet()) {
                if (c == null) {
                    MyLoggerConfiguration.printLog(Level.SEVERE, "Apprend Capacité : Erreur la Capacité est null (Pokemon).\n");
                    throw new NullPointerException("Erreur la Capacité est null");
                }

                Capacite capacite = (Capacite) c;
                if (caps[i].getNom().strip().equalsIgnoreCase(c.getNom().strip()) && this.niveau >= capacite.getNiveau()) {
                    this.capacites[i] = caps[i];
                }
            }
        }
    }    //Enseigne les capacités au Pokemon


    /**
     * Il remplace une capacité de la créature par une autre capacité
     *
     * @param i   l'indice de capacité à remplacer
     * @param cap le nouveau ICapacite pour remplacer l'ancien
     * @throws UnsupportedOperationException Si l'indice n'est pas contenu dans le tableau
     */
    @Override
    public void remplaceCapacite(int i, ICapacite cap) throws UnsupportedOperationException {
        if (i < 0 || i > 4)
            throw new UnsupportedOperationException();
        for (ICapacite c : this.espece.getCapSet()) {
            if (c == null) {
                MyLoggerConfiguration.printLog(Level.SEVERE, "Remplace Capacité : Erreur la Capacité est null (Pokemon).\n");
                throw new NullPointerException("Erreur la Capacité est null");
            }
            Capacite capacite = (Capacite) c;
            if (cap.getNom().equals(c.getNom()) && this.niveau >= capacite.getNiveau()) {
                this.capacites[i] = cap;
            }
        }
    }


    /**
     * Mise à jour de l'expérience du pokémon après avoir vaincu un autre pokémon.
     *
     * @param pok le pokemon vaincu.
     */
    @Override
    public void gagneExperienceDe(IPokemon pok) {
        this.experience = (1.5 * pok.getNiveau() * pok.getEspece().getBaseExp()) / 7;
        while (peutChangerDeNiveau()) {
            this.ancien_niveau = this.niveau;
            this.niveau++;
            this.miseAjourStats();
        }
    } //Met à jour l'exprérience de this suite à la défaite de pok

    private boolean peutChangerDeNiveau() {
        return this.experience >= (0.8 * Math.pow((double) this.niveau + 1, 3));
    }

    /**
     * Une méthode qui met à jour les stats du Pokémon après les dégâts subis par l'attaque atk de pok.
     *
     * @param pok pokemon attaquant
     * @param atk l'attaque subbies
     */
    @Override
    public void subitAttaqueDe(IPokemon pok, IAttaque atk) {
        if (atk instanceof ICapacite) {
            int degats = atk.calculeDommage(pok, this);
            this.stat.setPV(this.stat.getPV() - degats);
            this.pourcentagePV = Math.max((this.stat.getPV() * 100 / this.calculGainStatPV()), 0);
        }
    } //Met à jour les stats de this en tenant compte des dégats subits par l'attaque atk de pok

    /**
     * Une méthode qui renvoie vrai si les points de vie du pokémon sont à 0.
     *
     * @return Un boolean.
     */
    @Override
    public boolean estEvanoui() {
        return this.stat.getPV() <= 0;
    }        //renvoie true si les pointes de vie du pokemon sont 0


    /**
     * Vérifier si le pokémon a changé de niveau.
     *
     * @return Un boolean.
     */
    @Override
    public boolean aChangeNiveau() {
        return this.niveau != this.ancien_niveau;
    }        //renvoie true si le Pokemon vient de changer de niveau

    /**
     * Vérifier si le pokémon peut muter.
     *
     * @return Un boolean.
     */
    @Override
    public boolean peutMuter() {
        IEspece especeM = this.espece.getEvolution(this.niveau);
        if (especeM == null)
            return false;
        else {
            return especeM.getNiveauDepart() >= this.niveau;
        }
    }         //renvoie true si le Pokemon peut muter

    /**
     * Méthode utilisée pour soigner les pokémons.
     */
    @Override
    public void soigne() {
        this.stat.setPV(this.calculGainStatPV());
    }       // Remet les PV au maximum

    /**
     * Il renvoie une chaîne contenant le nom, le niveau, les statistiques, le pourcentage de HP, l'espèce et le DV du
     * Pokémon.
     *
     * @return Le nom, le niveau, les statistiques, le pourcentage de HP, l'espèce et le DV du Pokémon.
     */
    @Override
    public String toString() {
        return "Pokemon{" +
                "nom='" + nom + '\'' +
                ", niveau=" + niveau +
                ", stat=" + stat +
                ", pourcentagePV=" + pourcentagePV +
                ", espece=" + espece +
                ", DV=" + DV +
                '}';
    }
}
