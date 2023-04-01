# Filmstacks

Filmstacks est une application Android qui permet de rechercher et de consulter des informations sur des films populaires à partir de l'API The Movie Database (TMDb). Elle utilise une architecture MVVM (Modèle Vue Vue-Modèle) pour organiser les différentes couches de l'application.

## Classes

### GenreConverter

Cette classe est un convertisseur de type utilisé par la bibliothèque Room pour convertir les listes d'entiers (genre_ids) en chaînes de caractères JSON et vice versa pour stocker et récupérer les données des films dans la base de données locale.

### MovieDao

Cette interface Room définit les méthodes d'accès à la base de données pour les entités MovieEntity. Elle fournit des méthodes pour insérer, supprimer et mettre à jour des films dans la base de données locale.

### MovieDatabase

Cette classe est une base de données Room qui contient une table pour les entités MovieEntity. Elle est responsable de la création et de la gestion de la base de données locale pour stocker les données des films.

### MovieEntity

Cette classe est une entité Room qui représente un film dans la base de données locale. Elle contient les attributs nécessaires pour stocker les informations d'un film, tels que l'ID, le titre, la description, la date de sortie, la note, l'affiche, etc.

### ApiService

Cette interface définit les méthodes pour effectuer des appels réseau à l'API TMDb. Elle utilise Retrofit pour gérer les appels HTTP et Moshi pour la conversion de JSON en objets Kotlin.

### MovieResponse

Cette classe est un objet Kotlin qui représente la réponse JSON de l'API TMDb pour la requête des films populaires. Elle contient une liste d'objets Movie qui représentent les films.

### MovieRepository

Cette classe est responsable de la récupération des données des films à partir de l'API TMDb et de leur stockage en base de données locale. Elle encapsule la logique de récupération des données pour fournir une interface simple pour les couches supérieures de l'application.

### AppModule

Cette classe est un module Dagger qui fournit les dépendances nécessaires à l'application. Elle fournit des instances de ApiService, MovieDatabase, MovieDao, MovieRepository, et GenreConverter.

### MainActivity

Cette classe est l'activité principale de l'application. Elle contient la logique d'affichage des fragments et gère la navigation entre les écrans.

### MyApplication

Cette classe est une sous-classe de Application qui initialise Dagger pour la gestion des dépendances et Room pour la base de données.

## Bibliothèques

Cette application utilise les bibliothèques AndroidX pour simplifier le développement d'applications Android. Elle utilise également Retrofit pour effectuer des appels réseau, Moshi pour la conversion de JSON en objets Kotlin, Room pour le stockage et la gestion des données en base de données locale, et Glide pour charger et afficher des images à partir d'URL.

## Configuration

Pour utiliser cette application, vous devez obtenir une clé d'API TMDb en vous inscrivant sur leur site Web. Une fois que vous avez la clé, vous devez la définir dans le fichier gradle.properties de votre projet Android en utilisant la clé "TMDB_API_KEY".

## License

Ce projet est sous licence MIT. Voir le fichier LICENSE pour plus d'informations.
