# Comment contribuer au projet

Nous utilisons **Git** pour manipuler le projet, les idées, les avancements, les problèmes et les erreurs.

- Pour télécharger Git : [https://git-scm.com/downloads](https://git-scm.com/downloads)

Nous utilisons GitHub pour y stocker le dépôt du projet, l’historiques des différentes versions, pour communiquer avec les autres membres du projet… [https://github.com/IUT-DEPT-INFO-UCA/pokae-g2-bl-tv](https://github.com/IUT-DEPT-INFO-UCA/pokae-g2-bl-tv)

Nous utilisons l’environnement de développement JetBrains IntelliJ pour écrire le programme [https://www.jetbrains.com/fr-fr/idea/download/](https://www.jetbrains.com/fr-fr/idea/download/)

## Pour proposer une idée, une fonctionnalité au projet

### Si vous avez une idée de fonctionnalité :

Cherchez parmi les proposition d’idées existantes pour ne pas créer de doublons [https://github.com/IUT-DEPT-INFO-UCA/pokae-g2-bl-tv/pulls](https://github.com/IUT-DEPT-INFO-UCA/pokae-g2-bl-tv/pulls)

Ensuite, proposez votre idée en décrivant son fonctionnement final et a quoi cela pourrait ressembler, vous pouvez vous mettre à la place de l’utilisateur final pour une meilleure compréhension.

Vous pouvez y ajouter des **captures d’écrans** et des images pour mieux illustrer vos propos.

D’autres personnes pourront poser des questions dans les commentaires.

Notez cependant que nous devrons valider votre idée avant d'accepter votre code.

## Pour aider au développement du projet ou corriger un problème

Pour développer une fonctionnalité, la meilleure façon de faire est de vous rendre sur l'outil de gestion de projet sur GitHub : [https://github.com/IUT-DEPT-INFO-UCA/pokae-g2-bl-tv/projects/1](https://github.com/IUT-DEPT-INFO-UCA/pokae-g2-bl-tv/projects/1)

**Les tâches présentes dans l’onglet “A faire” sont à faire en priorité.**

Si vous vous lancez dans la complétion d’une tache à faire, indiquez aux autres développeurs en ajoutant un commentaire, puis lorsque vous avez fini, transférez la tache dans la colonne “Fait”.

Pour corriger un problème, la première chose à faire est de vérifier que ce problème n'a pas déjà été résolu en regardant parmi la listes des Issues : [https://github.com/IUT-DEPT-INFO-UCA/pokae-g2-bl-tv/issues](https://github.com/IUT-DEPT-INFO-UCA/pokae-g2-bl-tv/issues)

Dès lors, vous pourrez signaler le problème en créant un nouveau topic (”issue”) en cliquant sur “*New Issue*” et y indiquer une description du problème.

Si vous pensez pouvoir régler ce problème, vous pouvez créer un “*Fork”* (une copie du projet dont vous avez le plein contrôle), y faire les modifications nécessaires et nous soumettre vos modifications. Elles seront ensuite vérifiées et peut-être fusionnées avec le projet principal.

Pour cela :

- Créez un Fork du projet vers votre espace personnel : [https://docs.github.com/en/github/getting-started-with-github/fork-a-repo](https://docs.github.com/en/github/getting-started-with-github/fork-a-repo)
- Depuis Git, apportez vos modifications vers une nouvelle branche git :
    
    `git checkout -b ma-branche dev`
    
    **Votre nouvelle version doit comprendre des scénarios de test appropriés avec JUnit et une documentation JavaDoc complète.**
    
- Enregistrez vos modifications avec un message descriptif qui résume les modifications faites (important)
    
    `git commit -am "description"`
    
- Une fois terminé, soumettez votre version du projet
    
    `git push origin ma-branche`
    

### **Stratégie de Gestion de Projet**

Pour ce projet, nous avons choisi d'utiliser 4 branches.

- La première `main` c'est celle qui contiendra le code stable (fonctionnel) c'est celle-ci qui contiendra le programme livrable.
- La deuxième `dev` c'est celle qui contiendra le code en cours de développement de tous les participants de ce projet.
- La troisième `devLacroix` et `devVidal` sont les branches personnelles de chaque contributeurs.

## Règles de conduite

Toute interaction avec le projet/membres du projet doit pouvoir suivre ces règles de conduite :

- Utiliser un langage accueillant et inclusif
- Être respectueux des points de vue et des expériences différents
- Accepter les critiques constructives

<hr>

### Utilisation du système de logs

Nous avons opté pour un système de log qui enregistre les actions de l'utilisateur dans un fichier logs.txt et logs.html.
Ainsi que le déroulement des Combats pour prévenir de tout bugs potentiel.
