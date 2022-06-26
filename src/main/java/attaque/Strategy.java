/**
 * Université Côte d'Azur
 * IUT Côte d'Azur
 * Département Informatique
 *
 * @date Strategy.java
 */
package attaque;

import interfaces.*;
import pokemon.Pokemon;

import java.util.*;


/**
 * Une stratégie est utilisée par les dresseurs non humains (IA) pour prendre les décisions
 * Un DresseurIA possède une référence sur une IStrategy à qui il délègue la prise de décision
 * Un dresseur humain n'utilise pas IStrategy
 * Chaque méthode de IStrategy correspond à la méthode homonyme de IDresseur
 *
 * @author Lacroix Baptiste and Vidal Théo
 */
public class Strategy implements IStrategy {
    private final IDresseur dresseur;
    /**
     * ranch contenant les pokémons du dresseur IA
     */
    private final IPokemon[] ranch;
    /**
     * Objet Random perméttant de générer un nombre aléatoire.
     */
    private final Random rand = new Random();

    /**
     * Création d'une nouvelle instance de la classe CoupsPossibles.
     */
    private CoupsPossibles coupsPossiblesAttaquant = new CoupsPossibles();
    /**
     * Création d'une nouvelle instance de la classe CoupsPossibles.
     */
    private CoupsPossibles coupsPossiblesDefenseur = new CoupsPossibles();

    /**
     * Constructeur de la Strategy
     *
     * @param dresseur dresseur IA
     * @param ranch    ranch contenant les pokémons du dresseur IA
     */
    public Strategy(IDresseur dresseur, IPokemon[] ranch) {
        this.dresseur = dresseur;
        this.ranch = ranch;
    }

    /**
     * La fonction `choisitCombattant()` renvoie un pokémon aléatoire du ranch qui ne s'est pas évanoui et qui combattra
     *
     * @return Un pokémon qui ne s'est pas évanoui qui combattra.
     */
    @Override
    public IPokemon choisitCombattant() {
        IPokemon pokemon = this.ranch[this.rand.nextInt(this.ranch.length)];
        while (pokemon.estEvanoui()) {
            int random = this.rand.nextInt(this.ranch.length);
            pokemon = this.ranch[random];
        }
        return pokemon;
    }

    /**
     * La fonction choisit un pokémon aléatoire du ranch qui ne s'est pas évanoui et qui combattra
     *
     * @param pok le pokémon que l'adversaire utilise
     * @return Un pokémon qui ne s'est pas évanoui qui combattra.
     */
    @Override
    public IPokemon choisitCombattantContre(IPokemon pok) {
        IPokemon pokemon = this.ranch[this.rand.nextInt(this.ranch.length)];
        while (pokemon.estEvanoui()) {
            pokemon = this.ranch[this.rand.nextInt(this.ranch.length)];
        }
        Pokemon pok2 = (Pokemon) pok;
        pok2.addCount();
        return pokemon;
    }

    /**
     * La fonction choisit une attaque aléatoire dans la liste des attaques de l'attaquant ou choisit changer de
     * Pokémon parmis la liste des Pokémon de l'entraîneur
     *
     * @param attaquant Le Pokémon qui attaque
     * @param defenseur Le Pokémon que l'IA attaque
     * @return L'attaque que l'ordinateur va utiliser.
     */
    @Override
    public IAttaque choisitAttaque(IPokemon attaquant, IDresseur dresseurDefenseur, IPokemon defenseur) {
        return MiniMax(new EtatDuJeu(this.dresseur, dresseurDefenseur),
                new Coup(0, null),
                attaquant,
                defenseur, 6,
                Double.NEGATIVE_INFINITY,
                Double.POSITIVE_INFINITY,
                true).getAttaque();
    }

    /**
     * La fonction renvoie le meilleur coup pour le joueur actuel, compte tenu de l'état actuel du jeu
     *
     * @param etatDuJeu        L'état actuel du jeu.
     * @param position         le poste actuel
     * @param attaquant        le pokémon attaquant
     * @param defenseur        Le pokémon défenseur
     * @param profondeur       la profondeur de l'arbre
     * @param alpha            la meilleure valeur que le joueur maximisant peut actuellement garantir à ce niveau ou plus.
     * @param beta             la meilleure valeur que le joueur maximisant peut actuellement garantir à ce niveau ou plus.
     * @param maximizingPlayer vrai si c'est le tour de l'IA, faux si c'est le tour de l'adversaire
     * @return Le meilleur coup pour le joueur.
     */
    public Coup MiniMax(EtatDuJeu etatDuJeu, Coup position, IPokemon attaquant, IPokemon defenseur, int profondeur, double alpha, double beta, boolean maximizingPlayer) {
        if (etatDuJeu.pvDresseurAttaquant() == 0)
            return new Coup(0, null);
        if (etatDuJeu.pvDresseurDefenseur() == 0)
            return new Coup(1, null);
        if (profondeur == 0) {
            return position;
        }
        this.addCoupsPossiblesAttaquant(etatDuJeu, attaquant);
        this.addCoupsPossiblesDefenseur(etatDuJeu, defenseur);

        if (maximizingPlayer) {
            Coup coupAttaquant = null;
            Coup coupMax = new Coup(position.getProbabilite(), position.getAttaque());
            for (Coup coupA : this.coupsPossiblesAttaquant.getCoupsPossibles()) {
                Coup eval = MiniMax(etatDuJeu, coupA, attaquant, defenseur, profondeur - 1, alpha, beta, false);
                eval.setProbabilite((etatDuJeu.pvDresseurAttaquant() / (double) (etatDuJeu.pvDresseurAttaquant() + etatDuJeu.pvDresseurDefenseur())));

                if (coupMax.getProbabilite() >= eval.getProbabilite()) {
                    coupAttaquant = coupMax;
                } else {
                    coupAttaquant = new Coup(eval.getProbabilite(), coupA.getAttaque());
                    coupMax = coupAttaquant;
                }
                alpha = Math.max(alpha, eval.getProbabilite());
                if (beta <= alpha)
                    break;
            }
            return coupAttaquant;
        } else {
            Coup coupDefenseur = null;
            Coup coupMin = new Coup(position.getProbabilite(), position.getAttaque());
            for (Coup coupD : this.coupsPossiblesDefenseur.getCoupsPossibles()) {
                Coup eval = MiniMax(etatDuJeu, coupD, attaquant, defenseur, profondeur - 1, alpha, beta, true);
                eval.setProbabilite((etatDuJeu.pvDresseurAttaquant() / (double) (etatDuJeu.pvDresseurAttaquant() + etatDuJeu.pvDresseurDefenseur())));

                if (coupMin.getProbabilite() <= eval.getProbabilite()) {
                    coupDefenseur = coupMin;
                } else {
                    coupDefenseur = new Coup(eval.getProbabilite(), coupD.getAttaque());
                    coupMin = coupDefenseur;
                }
                beta = Math.max(beta, eval.getProbabilite());
            }
            return coupDefenseur;
        }
    }


    /**
     * Cette fonction ajoute tous les coups possibles que l'attaquant peut effectuer à la liste des coups possibles
     *
     * @param X                l'état actuel du jeu
     * @param pokemonAttaquant Le pokémon attaquant
     */
    private void addCoupsPossiblesAttaquant(EtatDuJeu X, IPokemon pokemonAttaquant) {
        this.coupsPossiblesAttaquant = new CoupsPossibles();
        for (ICapacite cap : pokemonAttaquant.getCapacitesApprises())
            coupsPossiblesAttaquant.addCoup(pokemonAttaquant, new Coup(0, cap));
        for (IPokemon pok : X.getPokemonsAttaquant()) {
            coupsPossiblesAttaquant.addCoup(pokemonAttaquant, new Coup(0, new Echange(pok)));
        }
    }


    /**
     * Il ajoute tous les coups possibles que le défenseur peut effectuer à la liste des coups possibles
     *
     * @param X                l'état actuel du jeu
     * @param pokemonDefenseur Le pokémon qui défend actuellement.
     */
    private void addCoupsPossiblesDefenseur(EtatDuJeu X, IPokemon pokemonDefenseur) {
        this.coupsPossiblesDefenseur = new CoupsPossibles();
        for (ICapacite cap : pokemonDefenseur.getCapacitesApprises())
            coupsPossiblesDefenseur.addCoup(pokemonDefenseur, new Coup(0, cap));
        for (IPokemon pok : X.getPokemonsDefenseur()) {
            coupsPossiblesDefenseur.addCoup(pokemonDefenseur, new Coup(0, new Echange(pok)));
        }
    }
}

/**
 * C'est une classe qui représente un mouvement qu'un Pokémon peut faire
 *
 * @author Lacroix Baptiste and Vidal Théo
 */
class Coup {
    /**
     * La probabilité de gagner le jeu.
     */
    private double probabilite;
    /**
     * Une variable privée utilisée pour stocker l'attaque que l'ordinateur utilisera.
     */
    private IAttaque attaque;

    /**
     * Un constructeur pour la classe Coup.
     *
     * @param probabilite probabilité de gagner le jeu.
     * @param attaque     l'attaque que l'ordinateur utilisera.
     */
    public Coup(double probabilite, IAttaque attaque) {
        this.probabilite = probabilite;
        this.attaque = attaque;
    }

    /**
     * Cette fonction renvoie la probabilité de l'événement
     *
     * @return La probabilité de l'événement.
     */
    public double getProbabilite() {
        return probabilite;
    }

    /**
     * Cette fonction renvoie l'attaque du Pokémon
     *
     * @return L'attaque des Pokémon
     */
    public IAttaque getAttaque() {
        return attaque;
    }

    /**
     * Cette fonction définit l'attaque du personnage.
     *
     * @param attaque L'attaque à utiliser.
     */
    public void setAttaque(IAttaque attaque) {
        this.attaque = attaque;
    }

    /**
     * Cette fonction fixe la probabilité de l'objet courant à la valeur du paramètre
     *
     * @param probabilite La probabilité que l'événement se produise.
     */
    public void setProbabilite(double probabilite) {
        this.probabilite = probabilite;
    }
}

/**
 * C'est un conteneur pour une liste de mouvements possibles qu'un Pokémon peut faire
 *
 * @author Lacroix Baptiste and Vidal Théo
 */
class CoupsPossibles {
    /**
     * La liste des mouvements possibles.
     */
    private final List<Coup> coups = new ArrayList<>();
    /**
     * La liste des mouvements possibles.
     */
    private IPokemon pokemon;

    /**
     * Un constructeur pour la classe CoupsPossibles.
     *
     * @return La liste des mouvements possibles.
     */
    public List<Coup> getCoupsPossibles() {
        return coups;
    }

    /**
     * Un constructeur pour la classe CoupsPossibles.
     *
     * @param pokemon le Pokémon qui peut faire des mouvements.
     * @param coup    le mouvement que le Pokémon peut faire.
     */
    public void addCoup(IPokemon pokemon, Coup coup) {
        this.pokemon = pokemon;
        this.coups.add(coup);
    }

    /**
     * Cette fonction renvoie le Pokémon qui peut faire des mouvements.
     *
     * @return le Pokémon qui peut faire des mouvements.
     */
    public IPokemon getPokemon() {
        return pokemon;
    }
}

/**
 * C'est une classe qui contient l'état du jeu
 *
 * @author Lacroix Baptiste and Vidal Théo
 */
class EtatDuJeu {
    /**
     * Le dresseur attaquant.
     */
    private final IDresseur dresseurAttaquant;
    /**
     * Le dresseur défenseur.
     */
    private final IDresseur dresseurDefenseur;

    /**
     * La liste des Pokémon du dresseur attaquant.
     *
     * @param dresseurAttaquant le dresseur attaquant.
     * @param dresseurDefenseur le dresseur défenseur.
     */
    public EtatDuJeu(IDresseur dresseurAttaquant, IDresseur dresseurDefenseur) {
        this.dresseurAttaquant = dresseurAttaquant;
        this.dresseurDefenseur = dresseurDefenseur;
    }

    /**
     * Cette fonction renvoie le dresseur attaquant.
     *
     * @return le dresseur attaquant.
     */
    public int pvDresseurAttaquant() {
        int total = 0;
        for (IPokemon p : this.dresseurAttaquant.getRanchCopy()) {
            total += p.getStat().getPV();
        }
        return total;
    }

    /**
     * Cette fonction renvoie le dresseur défenseur.
     *
     * @return le dresseur défenseur.
     */
    public int pvDresseurDefenseur() {
        int total = 0;
        for (IPokemon p : this.dresseurDefenseur.getRanchCopy()) {
            total += p.getStat().getPV();
        }
        return total;
    }

    /**
     * Cette fonction renvoie la liste des Pokémon du dresseur attaquant.
     *
     * @return la liste des Pokémon du dresseur attaquant.
     */
    public IPokemon[] getPokemonsAttaquant() {
        return this.dresseurAttaquant.getRanchCopy();
    }

    /**
     * Cette fonction renvoie la liste des Pokémon du dresseur défenseur.
     *
     * @return la liste des Pokémon du dresseur défenseur.
     */
    public IPokemon[] getPokemonsDefenseur() {
        return this.dresseurDefenseur.getRanchCopy();
    }
}