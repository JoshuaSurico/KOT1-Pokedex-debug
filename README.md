### 1. L’application refuse de se lancer (Erreur de compilation)
*   **Symptôme :** Android Studio affiche des lignes rouges et refuse de compiler le projet.

<details>
  <summary>💡 Voir l'indice pour l'anomalie 1</summary>
 Un bouton et un TextView sont mal nommés dans le code Kotlin par rapport au fichier XML (ou inversement).
</details>

---

### 2. Crash au démarrage en changeant de langue (Erreur d'exécution / NPE)
*   **Symptôme :** L'application s'ouvre, mais si l'on clique immédiatement sur `FR`, `EN` ou `JP` pendant le chargement, elle se ferme brutalement (crash).

<details>
  <summary>💡 Voir l'indice pour l'anomalie 2</summary>

  Le code tente d'accéder aux données du Pokémon avant qu'elles ne soient reçues de l'API. L'utilisation de l'opérateur de forçage (`!!`) sur une variable encore nulle provoque un crash de type pointeur nul (*NullPointerException*). Il faut sécuriser cet accès avec un opérateur optionnel (`?.`) ou une condition de vérification.
</details>

---

### 3. Le Pokémon ne se charge jamais (Erreur logique / Réseau)
*   **Symptôme :** L'application fonctionne mais affiche en permanence "Erreur de chargement" sans donner plus de détails à l'utilisateur.

<details>
  <summary>💡 Voir l'indice pour l'anomalie 3</summary>

 1. L'appel réseau échoue en arrière-plan à cause d'une mauvaise configuration de l'adresse de l'API (l'URL de base contient une faute de frappe). Pour identifier le problème précis, il est nécessaire d'ajouter des logs d'erreur explicites (`Log.e()`) dans votre bloc catch pour analyser l'exception réseau (`IOException`) directement dans l'onglet **Logcat**.
 2. La structure `try-catch` dans votre code est mal ordonnée : une exception trop générale (comme `Exception`) intercepte tout et bloque l'accès aux exceptions plus spécifiques écrites en dessous. Réorganisez l'ordre de vos blocs `catch`.
</details>

---

### 4. Crash aléatoire avec le bouton dé 🎲 (Erreur de précondition)
*   **Symptôme :** Cliquer sur le bouton aléatoire fonctionne la plupart du temps, mais provoque parfois un crash instantané de l'application.

<details>
  <summary>💡 Voir l'indice pour l'anomalie 4</summary>

  Une fonction de précondition (`require`) bloque volontairement l'exécution si l'identifiant du Pokémon demandé est invalide (hors limites). Inspectez le code du bouton aléatoire : le générateur de nombres produit parfois un ID qui dépasse la limite maximale autorisée par l'application (`MAX_POKEMON_ID`).
</details>
