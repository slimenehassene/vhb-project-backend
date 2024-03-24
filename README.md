# Google Wallet Pass Backend

## Beschreibung:
Das Google Wallet Pass Backend ist eine RESTful-API, die verschiedene Endpunkte bereitstellt, um Passdaten zu verwalten. Die API ermöglicht das Erstellen, Lesen, Aktualisieren und Löschen von Passen verschiedener Typen wie Bordkarten, Eventtickets, Gutscheine und Mitgliedskarten und alles im datenbank speichert.

## Endpunkte:

1. **Bordkarten-Endpunkt:**
   - **POST /bordkarte:** Erstellt eine neue Bordkarte.
   - **GET /bordkarte/{id}:** Ruft die Details einer spezifischen Bordkarte anhand ihrer ID ab.
   - **GET /bordkarte:** Ruft alle gespeicherten Bordkarten ab.
   - **PUT /bordkarte:** Aktualisiert eine vorhandene Bordkarte.

2. **EventTicket-Endpunkt:**
   - **POST /eventTicket:** Erstellt ein neues Eventticket.
   - **GET /eventTicket/{id}:** Ruft die Details eines spezifischen Eventtickets anhand seiner ID ab.
   - **GET /eventTicket:** Ruft alle gespeicherten Eventtickets ab.
   - **PUT /eventTicket:** Aktualisiert ein vorhandenes Eventticket.

3. **Gutscheine-Endpunkt:**
   - **POST /gutscheine:** Erstellt einen neuen Gutschein.
   - **GET /gutscheine/{id}:** Ruft die Details eines spezifischen Gutscheins anhand seiner ID ab.
   - **GET /gutscheine:** Ruft alle gespeicherten Gutscheine ab.
   - **PUT /gutscheine:** Aktualisiert einen vorhandenen Gutschein.

4. **Mitgliedskarten-Endpunkt:**
   - **POST /mitgliedskarte:** Erstellt eine neue Mitgliedskarte.
   - **GET /mitgliedskarte/{id}:** Ruft die Details einer spezifischen Mitgliedskarte anhand ihrer ID ab.
   - **GET /mitgliedskarte:** Ruft alle gespeicherten Mitgliedskarten ab.
   - **PUT /mitgliedskarte:** Aktualisiert eine vorhandene Mitgliedskarte.

## Benutzung:
- Um eine neue Karte zu erstellen, senden Sie eine POST-Anfrage an den entsprechenden Endpunkt mit den erforderlichen Passdaten im JSON-Format.
- Um eine vorhandene Karte abzurufen, senden Sie eine GET-Anfrage an den Endpunkt mit der ID der Karte.
- Um alle vorhandenen Karten abzurufen, senden Sie eine GET-Anfrage an den Endpunkt ohne ID.
- Um eine Karte zu aktualisieren, senden Sie eine PUT-Anfrage an den entsprechenden Endpunkt mit den aktualisierten Passdaten im JSON-Format.
