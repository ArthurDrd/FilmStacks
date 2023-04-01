FilmStacks

FilmStacks est une application de cinéma qui utilise l'API de The Movie Database pour afficher une liste de films populaires et leurs détails. Voici une explication de chaque classe de l'application et de son fonctionnement.

GenreConverter
Cette classe est un convertisseur de type utilisé par Room pour stocker et récupérer les genres des films dans la base de données locale. Il convertit un tableau d'entiers en une chaîne de caractères séparée par des virgules lorsqu'il est stocké dans la base de données, et inverse la conversion lorsqu'il est récupéré.

MovieDao
Cette classe est une interface utilisée pour définir les méthodes qui permettent d'effectuer des opérations de base de données sur la table des films. Elle utilise les annotations de Room pour générer automatiquement le code de mise en œuvre des méthodes.

MovieDatabase
Cette classe est une sous-classe de RoomDatabase qui définit la base de données locale utilisée par l'application. Elle contient une instance de MovieDao et utilise les annotations de Room pour définir les entités de base de données, les versions de la base de données et les migrations.

MovieEntity
Cette classe est une entité de base de données qui représente un film dans la base de données locale. Elle utilise les annotations de Room pour définir les colonnes de la table des films.

ApiService
Cette classe est utilisée pour effectuer des appels à l'API de The Movie Database à l'aide de Retrofit. Elle définit les endpoints de l'API et utilise les annotations de Retrofit pour générer automatiquement le code de requête.

MovieResponse
Cette classe est un modèle de données utilisé pour stocker la réponse de l'API de The Movie Database lors de l'appel à l'endpoint des films populaires. Elle utilise les annotations de Gson pour désérialiser la réponse JSON en un objet Java.

MovieRepository
Cette classe est utilisée pour gérer la récupération des données de l'API et de la base de données locale. Elle utilise les instances de MovieDao et ApiService pour effectuer les opérations de base de données et les appels à l'API, et utilise des corroutines pour gérer les opérations asynchrones.

AppModule
Cette classe est un module Dagger qui fournit les instances de MovieDao, ApiService et MovieRepository à l'application.

MainActivity
Cette classe est l'activité principale de l'application. Elle affiche la liste des films populaires et permet à l'utilisateur de cliquer sur un film pour afficher ses détails. Elle utilise un RecyclerView pour afficher la liste des films et utilise des corroutines pour gérer les opérations asynchrones.

MyApplication
Cette classe est la sous-classe d'Application utilisée par l'application. Elle initialise la base de données locale et le module Dagger utilisé par l'application.

Ces classes travaillent ensemble pour fournir une application de cinéma fonctionnelle qui récupère les données de l'API de The Movie Database et les stocke localement dans une base de données Room.
