/**
 * Université Côte d'Azur
 * IUT Côte d'Azur
 * Département Informatique
 *
 * @date Capacite.java
 */
package attaque;

import interfaces.ICapacite;
import interfaces.ICategorie;
import interfaces.IType;
import interfaces.IPokemon;
import pokedex.Pokedex;
import statsPokemon.Type;

import java.util.Random;

/**
 * Une capacité est un type d'attaque que le pokemon peut utilser
 *
 * @author Lacroix Baptiste and Vidal Théo
 */
public class Capacite implements ICapacite {
    /**
     * Nom de la Capacité.
     */
    private final String nom;
    /**
     * Précision de la Capacité.
     */
    private final double precision;
    /**
     * Puissance de la Capacité.
     */
    private final int puissance;
    /**
     * PP de base de la Capacité.
     */
    private final int PP_base;
    /**
     * PP actuel de la Capacité.
     */
    private int PP;
    /**
     * Catégorie de la Capacité.
     */
    private final ICategorie categorie;
    /**
     * Type de la Capacité.
     */
    private final IType type;
    /**
     * Objet Random perméttant de générer un nombre aléatoire.
     */
    private final Random rand = new Random();
    /**
     * Niveau nécessaire pour apprendre la Capacité.
     */
    private final int niveau;

    /**
     * Constructeur de la Capacité
     *
     * @param nom       nom de la capacité
     * @param precision précision de la capacité
     * @param puissance puissance de la capacité
     * @param PP        PP de la capacité
     * @param categorie catégorie de la capacité
     * @param type      type de la capacité
     * @param niveau    niveau de nécessaire de la capacité
     */
    public Capacite(String nom, double precision, int puissance, int PP, ICategorie categorie, IType type, int niveau) {
        this.nom = nom;
        this.precision = precision;
        this.puissance = puissance;
        this.PP_base = PP;
        this.PP = PP;
        this.categorie = categorie;
        this.type = type;
        this.niveau = niveau;
    }

    /**
     * Il calcule les dégâts d'une attaque
     *
     * @param lanceur  Le pokémon qui utilise le mouvement
     * @param receveur Le pokémon qui reçoit l'attaque
     * @return Les dégâts que l'attaque fera.
     */
    @Override
    public int calculeDommage(IPokemon lanceur, IPokemon receveur) {
        if (this.precision < 0 + 1 * rand.nextDouble()) {
            this.utilise();
            return 0;
        }
        int damage = this.caseAttack(lanceur, receveur);
        if (damage == -1) {
            this.utilise();
            double eff = new Pokedex().getEfficacite(this.type, receveur.getEspece().getTypes()[0]);
            double eff2 = new Pokedex().getEfficacite(this.type, receveur.getEspece().getTypes()[1]);
            double pparenthese = lanceur.getNiveau() * 0.4 + 2;
            double numerateur = pparenthese * lanceur.getStat().getForce() * this.puissance;
            double denominateur = (double) receveur.getStat().getDefense() * 50;
            double fraction = numerateur / denominateur + 2;
            if (this.type == lanceur.getEspece().getTypes()[0] || this.type == lanceur.getEspece().getTypes()[1]) {
                double CM = (eff * eff2) * 1.5 * (0.85 + (1 - 0.85) * rand.nextDouble());
                return (int) Math.round(fraction * CM);
            }
            double CM = (eff * eff2) * (0.85 + (1 - 0.85) * rand.nextDouble());
            return (int) Math.round(fraction * CM);
        } else {
            return damage;
        }
    }

    /**
     * Il calcule les dommages causés par un mouvement
     *
     * @param lanceur  le pokémon qui utilise l'attaque
     * @param receveur le pokémon attaqué
     * @return Les dégâts que l'attaque fera.
     */
    private int caseAttack(IPokemon lanceur, IPokemon receveur) {
        switch (this.puissance) {
            case -1:
                if (this.precision < 0 + 1 * rand.nextDouble()) {
                    this.utilise();
                    return 0;
                }
                return receveur.getStat().getPV();
            case -2:
                if (this.precision < 0 + 1 * rand.nextDouble()) {
                    this.utilise();
                    return 0;
                }
                if (receveur.getEspece().getTypes()[0] == Type.Spectre || receveur.getEspece().getTypes()[1] == Type.Spectre) {
                    this.utilise();
                    return 0;
                }
                this.utilise();
                return 20;
            case -4:
                if (this.precision < 0 + 1 * rand.nextDouble()) {
                    this.utilise();
                    return 0;
                }
                this.utilise();
                return lanceur.getNiveau();
            case -5:
                if (this.precision < 0 + 1 * rand.nextDouble()) {
                    this.utilise();
                    return 0;
                }
                return 40;
            case -6:
                if (this.precision < 0 + 1 * rand.nextDouble()) {
                    this.utilise();
                    return 0;
                }
                if (receveur.getEspece().getTypes()[0] == Type.Normal || receveur.getEspece().getTypes()[1] == Type.Normal) {
                    this.utilise();
                    return 0;
                }
                this.utilise();
                return lanceur.getNiveau();
            case -8:
                if (this.precision < 0 + 1 * rand.nextDouble()) {
                    this.utilise();
                    return 0;
                }
                this.utilise();
                return lanceur.getNiveau() * ((rand.nextInt(6 - 1 + 1) + 1) + 5) / 10;
            case -9:
                if (this.precision < 0 + 1 * rand.nextDouble()) {
                    this.utilise();
                    return 0;
                }
                this.utilise();
                if (receveur.getStat().getPV() == 1)
                    return 1;
                return receveur.getStat().getPV() / 2;
            default:
                return -1;
        }
    }

    /**
     * Cette fonction renvoie la valeur de la variable niveau
     *
     * @return La valeur de la variable niveau.
     */
    public int getNiveau() {
        return niveau;
    }

    /**
     * Cette fonction diminue le PP de 1.
     */
    @Override
    public void utilise() {
        this.PP--;
    }

    /**
     * Cette fonction renvoie le nom de la Capacité
     *
     * @return Le nom de la capacité.
     */
    @Override
    public String getNom() {
        return this.nom;
    }


    /**
     * Cette fonction renvoie la précision de la capacité
     *
     * @return La précision de la capacité.
     */
    @Override
    public double getPrecision() {
        return this.precision;
    }


    /**
     * Cette fonction retourne la valeur de l'attribut puissance
     *
     * @return La puissance de la capacité.
     */
    @Override
    public int getPuissance() {
        return this.puissance;
    }

    /**
     * Cette fonction renvoie le PP restant de la capacité.
     *
     * @return Le PP de la capacité.
     */
    @Override
    public int getPP() {
        return this.PP;
    }


    /**
     * Réinitialise le PP de la capacité à sa valeur de base.
     */
    @Override
    public void resetPP() {
        this.PP = this.PP_base;
    }


    /**
     * Cette fonction renvoie la catégorie de la capacité
     *
     * @return La catégorie du produit.
     */
    @Override
    public ICategorie getCategorie() {
        return this.categorie;
    }


    /**
     * Renvoie le type de la Capacité
     *
     * @return Le type de la Capacité.
     */
    @Override
    public IType getType() {
        return this.type;
    }

    /**
     * Il renvoie une chaîne qui contient le nom de la classe, le nom de l'objet et les valeurs de tous les attributs de
     * l'objet
     *
     * @return Le nom, la précision, la puissance, le PP de base, le PP, la catégorie et le type de mouvement.
     */
    @Override
    public String toString() {
        return "Capacite{" +
                "nom : '" + nom + '\'' +
                ", precision : " + precision +
                ", puissance : " + puissance +
                ", PP : " + PP +
                ", categorie : " + categorie +
                ", type : " + type +
                ", niveau nécessaire : " + niveau +
                '}';
    }
}
