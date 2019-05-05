# Määrittelydokumentointi

## Sovelluksen tarkoitus

Sovelluksen avulla on tarkoitus pitää kirjaa tehtävistä ja erityisesti usein sellaisista, jotka toistuvat suhteellisen säännöllisin väliajoin, kuten kotitöistä. Sovelluksen avulla voi automatisoida usein toistuvien töiden ylläpito. Kysymyksessä siis eräänlainen tuunattu todo-sovellus soveltuen ympäristöihin, joissa on paljon usein toistuvia tehtäviä, kuten perhearkeen tai opiskeluun.

## Käyttäjät

Sovelluksella on vain yhdenlaisia käyttäjiä, normaalikäyttäjiä, jotka voivat pitää kirjaa omista tehtävistään käyttäjätunnuksen luonnin jälkeen. Sovellusta voi käyttää useampi rekisteröinyt käyttäjä, joilla on omat tehtävälistansa sekä yksilöllisesti säädettävät oletusasetukset.


## Suunnitellut toiminnallisuudet

### Ennen kirjautumista

* Käyttäjä voi luoda uuden tunnuksen järjestelmään
* Käyttäjä voi kirjautua järjestelmään

### Kirjautumisen jälkeen

* käyttäjä näkee tekemättömät tehtävät
* Käyttäjä näkee tehdyt työt arkistossa
* käyttäjä voi lisätä uusia tehtäviä
  * uudet tehtävät voi merkitä toistuviksi tai kertaluonteisesti
    * niille määritellään toistumistiheys
    * tehdyiksi merkityt muuttuvat avoimiksi automaattisesti seuraavana päivänä
* käyttäjä voi merkitä työn tehdyksi tai tekemättömäksi
* käyttäjä voi poistaa tehtäviä
* käyttäjä voi muokata tehtävien erääntymisiä
* käyttäjä voi päivittää uusien tehtävien oletusasetuksia
  * normaaleille tehtäville voi asettaa oletuspituuden erääntymiselle
  * toistuville tehtäville lisäksi oletusuusiutumisvälin
* käyttäjä voi kirjautua ulos 

## Jatkokehitysideoita
* uusi rooli käyttäjielle - ryhmät - joihin käyttäjät voivat liittää toisiaan
  * toistuvien tehtävien tekijät kiertää jäsenten välillä
* uusi rooli tehtäville - projektit - jolloin tehtäviä voi liittää osatehtäviksi
* käyttäjälle määriteltäväksi kuinka monta päivää ennen erääntymistä toistuvat tehtävät muuttuvat avoimiksi
* tehtävän voi lisätä yksityiseksi tai ryhmälle
* tehtävistä voi luoda kalenteritapahtuman ".ical"-tiedoston
* salasanojen säilöminen salattuna
* lisää muokkausmahdollisuus luoduille tehtäville
  * muokkaa tehtävän nimeä
* lisää suotimia / tageja tehtäville
* pidemmällä aikavälillä siirto android-sovellukseen.
