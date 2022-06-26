/**
 * Université Côte d'Azur
 * IUT Côte d'Azur
 * Département Informatique
 *
 * @date Category.java
 */
package statsPokemon;

import interfaces.ICategorie;

import java.util.Objects;

/**
 * Il s'agit de la catégorie d'une capacité :
 * - soit Physique
 * - soit Special
 *
 * @author Lacroix Baptiste and Vidal Théo
 */
public enum Categorie implements ICategorie {
    /**
     * Catégorie de type Physique
     */
    Physique("Physique"),
    /**
     * Catégorie de type Special
     */
    Special("Special");
    /**
     * Nom de la Categorie
     */
    private final String nom;

    /**
     * Constructeur de la Categorie
     *
     * @param nom nom de la Catégorie
     */
    Categorie(String nom) {
        this.nom = nom;
    }


    /**
     * Si le nom de l'objet en cours est égal au nom de l'objet passé en paramètre, alors renvoie vrai, sinon renvoie faux
     *
     * @return Le nom de l'objet.
     */
    @Override
    public boolean isSpecial() {
        return Objects.equals(this.nom, Physique.nom);
    }


    /**
     * Cette fonction renvoie le nom de la Catégorie
     *
     * @return Le nom de la Catégorie.
     */
    @Override
    public String getNom() {
        return this.nom;
    }
}
