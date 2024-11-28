# Template-Repository für folgende Aufgaben
- ADT List in Java
- Stack und Queue
- ADT Set über ADT List implementieren
- Binäre Suchbäume und ADT SortedSet
- ADT Map mit ListSet und TreeSet implementieren
- Graphen

Im Paket fpinjava finden Sie diverse Klassen aus dem Lehrbuch wie z.B. Result.

Die Datei src/test/java/BUILD können Sie löschen,
es sei denn Sie nutzen Bazel als Build-System.

Die Hilfsklasse `list.JqwikUtils` benötigt die Klasse
`SerializationUtils` der Apache Commons Lang 3.9 API!

## Mindestanforderungen für Abnahme des Codes:

- Ihre Programme enthalten keine der folgenden Anweisungen: Schleifen (while, for),  ++, --, +=, -=
- Alle Variablen sind final.
- Alle Datenfelder sind final.
- Alle Konstruktoren sind final und private.
- Keine Methode hat den Rückgabetyp void.
- Jede if-Anweisung hat einen else-Zweig.
  (Am Besten Sie verwenden immer den ternären Ausdruck: ...?...:... )
- Sie müssen erklären können,
  welche Nachteile Ihre Programme haben,
  wenn sie die o.g. Anforderungen nicht erfüllen!
