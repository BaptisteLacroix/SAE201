/**
 * Université Côte d'Azur
 * IUT Côte d'Azur
 * Département Informatique
 *
 * @date Type.java
 */
package statsPokemon;

import interfaces.IType;

/**
 * Classe qui permet de définir le type. Implemente IType
 *
 * @author Lacroix Baptiste and Vidal Théo
 */
public enum Type implements IType {
    /**
     * Type Plante
     */
    Plante("Plante"),
    /**
     * Type Feu
     */
    Feu("Feu"),
    /**
     * Type Insecte
     */
    Insecte("Insecte"),
    /**
     * Type Normal
     */
    Normal("Normal"),
    /**
     * Type Electrik
     */
    Electrik("Electrik"),
    /**
     * Type Sol
     */
    Sol("Sol"),
    /**
     * Type Poison
     */
    Poison("Poison"),
    /**
     * Type Eau
     */
    Eau("Eau"),
    /**
     * Type Combat
     */
    Combat("Combat"),
    /**
     * Type Psy
     */
    Psy("Psy"),
    /**
     * Type Roche
     */
    Roche("Roche"),
    /**
     * Type Spectre
     */
    Spectre("Spectre"),
    /**
     * Type Glace
     */
    Glace("Glace"),
    /**
     * Type Dragon
     */
    Dragon("Dragon"),
    /**
     * Type Vol
     */
    Vol("Vol");

    /**
     * Nom du Type
     */
    private final String nom;


    /**
     * Constructeur de l'enum Type.
     *
     * @param nom nom du Type
     */
    Type(String nom) {
        this.nom = nom;
    }


    /**
     * Cette fonction renvoie le nom du type
     *
     * @return Le nom du type.
     */
    @Override
    public String getNom() {
        return this.nom;
    }
}
