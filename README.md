#  Architecture Microservices CQRS & Event Sourcing

##  Objectif du Projet

Développement d'une architecture microservices moderne implémentant les patterns **CQRS** (Command Query Responsibility Segregation) et **Event Sourcing** pour la gestion de comptes bancaires avec capacités analytiques en temps réel.

---


### Principes architecturaux

- **Séparation des responsabilités**: Commands (écriture) vs Queries (lecture)
- **Event Sourcing**: Persistance de tous les changements d'état sous forme d'événements
- **Eventual Consistency**: Cohérence à terme entre les services
- **Scalabilité horizontale**: Services indépendants et déployables séparément

---

##  Microservice 1: Account-Service

### Responsabilités

Le service **Account-Service** est le cœur du système, gérant toutes les opérations de commande sur les comptes bancaires.

### Structure du projet

<img width="394" height="500" alt="Capture d&#39;écran 2025-12-30 141714" src="https://github.com/user-attachments/assets/e007ac1d-7a1e-4aef-9e88-d05fe4d890d7" />



### Composants principaux

#### 1. **Commands** (Couche d'écriture)

Les commandes représentent les intentions de modification:

- `AddAccountCommand`: Création d'un nouveau compte
- `CreditAccountCommand`: Crédit d'un montant
- `DebitAccountCommand`: Débit d'un montant

#### 2. **Events** (Événements du domaine)

Événements immuables représentant les faits métier:

- `AccountCreatedEvent`: Compte créé avec solde initial
- `AccountCreditedEvent`: Montant crédité sur le compte
- `AccountDebitedEvent`: Montant débité du compte

#### 3. **Aggregates** (Racine d'agrégat)

`AccountAggregate` maintient la cohérence des règles métier et génère les événements.

#### 4. **Query** (Couche de lecture)

Projections optimisées pour les lectures avec:
- Entités JPA (`Account`, `Operation`)
- Repositories pour l'accès aux données
- Event Handlers pour mettre à jour les projections

### API REST - Account-Service

#### Endpoints de commande

<img width="1365" height="564" alt="Capture d&#39;écran 2025-12-30 142316" src="https://github.com/user-attachments/assets/d0d9d0db-dc4b-4fe9-9a62-81ad3b5f8ca2" />


| Méthode | Endpoint | Description | Payload |
|---------|----------|-------------|---------|
| **POST** | `/commands/accounts/add` | Créer un nouveau compte | `{initialBalance, currency}` |
| **POST** | `/commands/accounts/credit` | Créditer un compte | `{accountId, amount, currency}` |
| **POST** | `/commands/accounts/debit` | Débiter un compte | `{accountId, amount, currency}` |
| **GET** | `/commands/accounts/events/{accountId}` | Récupérer l'historique d'événements | - |

#### Endpoints de requête

| Méthode | Endpoint | Description |
|---------|----------|-------------|
| **GET** | `/query/accounts/list` | Liste tous les comptes |
| **GET** | `/query/accounts/accountStatement/{accountId}` | Relevé de compte détaillé |

### Exemple de réponse - Account Statement

<img width="1339" height="534" alt="Capture d&#39;écran 2025-12-30 142514" src="https://github.com/user-attachments/assets/c32f10a5-2e70-4b8c-8712-f7937acb8d69" />



**Analyse de la réponse**:
- Compte créé avec un solde de 230 MAD
- Une opération de crédit de 10 MAD enregistrée

---

##  Microservice 2: Analytics-Service

### Responsabilités

Le service **Analytics-Service** consomme les événements du Account-Service et génère des vues analytiques en temps réel.

### Structure du projet

<img width="524" height="413" alt="Capture d&#39;écran 2025-12-30 141812" src="https://github.com/user-attachments/assets/f698ff3e-e155-4002-9483-f1b196c37e95" />


### API REST - Analytics-Service

<img width="1365" height="378" alt="Capture d&#39;écran 2025-12-30 142623" src="https://github.com/user-attachments/assets/577da363-dce6-47d0-854f-c4d7a7ce6495" />

| Méthode | Endpoint | Description | Réponse |
|---------|----------|-------------|---------|
| **GET** | `/query/accountAnalytics` | Récupère toutes les analyses | Liste complète |
| **GET** | `/query/accountAnalytics/{accountId}` | Analyse d'un compte spécifique | Détails analytiques |
| **GET** | `/query/accountAnalytics/{accountId}/watch` | Surveillance en temps réel | Stream SSE |

### Exemple de réponse - Analytics

<img width="1332" height="573" alt="image" src="https://github.com/user-attachments/assets/1e259a83-bdc2-4eb0-9e21-7e0d33cc5201" />

  

**Insights**:
- Compte 1: Nouveau compte sans activité (2218 MAD)
- Compte 2: Compte récent sans transactions (440 MAD)
- Compte 3: Compte actif avec 1 débit et 1 crédit (330 MAD)

---



### Phases du traitement

1. **Command Phase**: Validation et émission de commande
2. **Event Sourcing**: Persistance de l'événement immutable
3. **Projection Phase**: Mise à jour des vues de lecture
4. **Analytics Phase**: Calcul des métriques en temps réel

---

##  Stack Technologique

### Backend

| Technologie | Version | Usage |
|-------------|---------|-------|
| **Java** | 17+ | Langage principal |
| **Spring Boot** | 3.x | Framework applicatif |
| **Axon Framework** | 4.x | CQRS & Event Sourcing |
| **Spring Data JPA** | 3.x | Persistance |
| **H2/PostgreSQL** | - | Base de données |
| **Maven** | 3.x | Gestion de dépendances |

### Architecture patterns

- **CQRS**: Séparation Command/Query
- **Event Sourcing**: Source de vérité basée sur événements
- **Aggregate Pattern**: Cohérence transactionnelle
- **Repository Pattern**: Abstraction d'accès aux données
- **DTO Pattern**: Transfert de données

---



##  Conclusion

Cette implémentation démontre une architecture microservices moderne et scalable, utilisant les meilleurs patterns de conception pour les systèmes distribués. L'utilisation de CQRS et Event Sourcing offre une base solide pour des applications nécessitant une forte auditabilité et des capacités analytiques avancées.
Les deux microservices fonctionnent de manière autonome tout en maintenant une cohérence à terme, offrant ainsi le meilleur compromis entre performance, scalabilité et fiabilité.
