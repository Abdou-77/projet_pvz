# Projet Backend : Plantes contre Zombies

## Introduction

Ce projet est une application web Java qui sert de backend pour un jeu de style "Plantes contre Zombies". Il a été développé en utilisant le framework Spring MVC. Ce document fournit une description détaillée du projet, de son architecture, des technologies utilisées, et des instructions pour l'installer et l'exécuter.

## Technologies et Outils Utilisés

*   **Langage de Programmation :** Java
*   **Framework :** Spring MVC
*   **Gestionnaire de Dépendances :** Maven
*   **Base de Données :** MySQL
*   **Serveur d'Application :** Tomcat (ou tout autre serveur compatible avec les applications web Java)
* **API :** OpenAPI

## Architecture

L'application suit le patron d'architecture Modèle-Vue-Contrôleur (MVC) fourni par le framework Spring.

*   **Modèle :** Les classes de modèle représentent les données du jeu (par exemple, `Plante`, `Zombie`, `Map`). Elles sont responsables de la gestion des données et de leur persistance dans la base de données.
*   **Vue :**  Dans ce projet backend, les vues sont représentées par les réponses JSON ou XML renvoyées par les contrôleurs, qui serviront aux clients pour interagir avec l'application.
*   **Contrôleur :** Les contrôleurs (`MapController`, `PlanteController`, `ZombieController`) gèrent les requêtes HTTP entrantes, interagissent avec les services et renvoient les réponses appropriées.
*   **Services :** Les classes de service (`MapService`, `PlanteService`, `ZombieService`) contiennent la logique métier de l'application. Ils interagissent avec les repositories pour accéder aux données.
*   **Repositories :** Les classes de repository (`MapRepository`, `PlanteRepository`, `ZombieRepository`) sont responsables de l'accès aux données de la base de données.
* **DTO:** Les DTO (Data Transfer Objects) sont utilisés pour transférer des données entre les couches de l'application.
* **Mapper:** Les Mappers permettent de mapper les DTO aux entités de la base de données et inversement.

## Fonctionnalités Principales

L'application permet de gérer :

*   **Les cartes :** Création, modification, suppression et récupération des informations sur les cartes.
*   **Les plantes :** Création, modification, suppression et récupération des informations sur les différentes plantes du jeu.
*   **Les zombies :** Création, modification, suppression et récupération des informations sur les différents zombies.
*   **La santé:** point de terminaison permettant de savoir si le backend est opérationnel.

## Installation et Exécution

**Prérequis :**

*   **Environnement Java :** Un environnement d'exécution Java (JRE) doit être installé.
*   **Maven :** Maven doit être installé et configuré pour gérer les dépendances et construire le projet.
*   **MySQL :** Un serveur MySQL doit être installé et accessible.
*   **Serveur d'application web:** Un serveur d'application tel que Tomcat doit être installé et configuré.

**Étapes d'installation :**

1.  **Cloner le projet :**
```
bash
    git clone [URL du dépôt]
    
```
2.  **Configurer la base de données :**
    *   Créer une base de données MySQL.
    *   Modifier le fichier `src/main/resources/database.properties` pour configurer la connexion à la base de données (URL, nom d'utilisateur, mot de passe).
3. **Compiler le projet :**
    * se placer dans le répertoire du projet et utiliser la commande maven suivante:
```
bash
    mvn clean install
    
```
* cette commande va construire le projet et générer le fichier **CoursEpfBack.war** dans le repertoire **target**.
4.  **Déployer l'application :**
    * Copier le fichier `CoursEpfBack.war` dans le dossier `webapps` du serveur Tomcat.
5. **Redémarrez le serveur**

**Exécution :**

* Démarrer le serveur d'application (par exemple, Tomcat).
* L'application sera accessible à l'adresse définie par le serveur (par exemple, `http://localhost:8080/CoursEpfBack`).

**Remarque Importante :**

Cette application est conçue pour fonctionner dans un serveur web avec Java et Maven installés. Les tentatives d'exécution directe du fichier `.war` avec `java -jar` ne fonctionneront pas. Un serveur d'application web compatible, tel que Tomcat, est nécessaire.

## API

L'application utilise **OpenAPI** pour définir l'API. le fichier `src/main/gen/api/openapi.yaml` contient la description de l'API.

## Conclusion

Ce projet constitue une base solide pour un jeu de type "Plantes contre Zombies". Il peut être utilisé pour gérer les données relatives aux cartes, plantes, et zombies.
