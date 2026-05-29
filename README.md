# MVpunishments

Ein professionelles Bestrafungssystem für OPBANDE Minecraft Server (1.21.x).

## Features

- ✅ **Temporäre & Permanente Bans** mit verschiedenen Gründen
- ✅ **IP-Ban System** mit `/punship`
- ✅ **Automatische Rassismus-Erkennung** 
- ✅ **Ban-Verwaltung** mit `/looking`
- ✅ **Schöne Ban-Screens** mit Server-Informationen
- ✅ **Persistente Datenspeicherung**

## Installation

### Schritt 1: Voraussetzungen
- Java 21+ installiert
- Maven installiert ([Download](https://maven.apache.org/download.cgi))
- Spigot/Paper Server 1.21.x

### Schritt 2: Projekt kompilieren
```bash
git clone https://github.com/opabnde/MVpunishments.git
cd MVpunishments
mvn clean install
```

### Schritt 3: Plugin installieren
1. Die generierte JAR-Datei aus `target/` kopieren
2. In den `plugins/` Ordner deines Servers einfügen
3. Server neu starten

## Commands

### /punish
Bestraft einen Spieler mit einem definierten Grund.
```
/punish <Spielername> <Grund>
```
**Beispiel:** `/punish MaxMustermann Cheating`

### /punship
Bannt die IP eines Spielers permanent.
```
/punship <Spielername>
```
**Beispiel:** `/punship MaxMustermann`

### /looking
Zeigt alle Bestrafungen eines Spielers an.
```
/looking <Spielername>
```
**Beispiel:** `/looking MaxMustermann`

## Bestrafungsgründe

| Grund | Dauer | Status |
|-------|-------|--------|
| **Auszeit** | 15 Minuten | Temporär |
| **Bauwerk** | 2 Tage | Temporär |
| **Cheating** | 7 Tage | Temporär |
| **Scamming** | 30 Tage | Temporär |
| **Rechtsbruch** | Permanent | ⚠️ Permanent |
| **Multiaccount** | Permanent | ⚠️ Permanent |
| **Username_Skin** | Permanent | ⚠️ Permanent |
| **Servermanipulation** | Permanent | ⚠️ Permanent |
| **Echtgeldhandel** | Permanent | ⚠️ Permanent |
| **Hausverbot** | Permanent | ⚠️ Permanent |
| **Serververbot** | Permanent | ⚠️ Permanent |

## Ban-Screen Beispiele

### Temporär (z.B. Cheating - 7 Tage)
```
                                    OPBANDE
           Du wurdest für 7 Tag(e) vom Netzwerk ausgeschlossen.
                          Grund: Cheating

Website: Opbande.lovable.app         Unban: s.opbande@gmx.de
```

### Permanent
```
                                    OPBANDE
               Du wurdest permanent vom Netzwerk ausgeschlossen.
                          Grund: Rechtsbruch

Website: Opbande.lovable.app         Unban: s.opbande@gmx.de
```

## Rassismus-Erkennung

Das System erkennt automatisch rassistische, sexistische und diskriminierende Inhalte und bannt den Spieler sofort für **"Rechtsbruch"** (permanent).

**Erkannte Kategorien:**
- Rassistische Beleidigungen
- Antisemitische Ausdrücke
- Homophobe Begriffe
- Behindertenfeindliche Sprache
- Weitere diskriminierende Inhalte

## Datenspeicherung

Alle Bestrafungen werden in `plugins/MVpunishments/punishments.dat` gespeichert und persistent zwischen Serverneustarts beibehalten.

IP-Bans werden in `plugins/MVpunishments/ip-bans.txt` gespeichert.

## Technische Details

- **Sprache:** Java 21
- **Framework:** Spigot API 1.21
- **Build-Tool:** Maven
- **Speicher:** Serialisierte Java-Objekte

## Unterstützung & Kontakt

📧 **Kontakt:** s.opbande@gmx.de  
🌐 **Website:** Opbande.lovable.app

---

**Lizenz:** OPBANDE © 2024