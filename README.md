# Ohjelmistotekniikka, harjoitustyö

## familyToDo

Sovelluksen avulla on tarkoitus pitää kirjaa tehtävistä ja erityisesti usein sellaisista, jotka toistuvat suhteellisen säännöllisin väliajoin, kuten kotitöistä. Sovelluksen avulla voi automatisoida usein toistuvien töiden ylläpito. Kysymyksessä siis eräänlainen tuunattu todo-sovellus soveltuen ympäristöihin, joissa on paljon usein toistuvia tehtäviä, kuten perhearkeen tai opiskeluun.

### Dokumentaatio

[Käyttöohje](dokumentointi/Kayttoohje.md)

[Vaatimusmäärittely](dokumentointi/M%C3%A4%C3%A4rittelydokumentointi.md)

[Arkkitehtuurikuvaus](dokumentointi/Arkkitehtuurikuvaus.md)

[Testausdokumentti](dokumentointi/Testausdokumentti.md)

[Työaikakirjanpito](dokumentointi/tyoaikakirjanpito.md)


### Releaset

[Viikko 5](https://github.com/petrioski/ot-harjoitustyo/releases/tag/viikko5)

[Viikko 6](https://github.com/petrioski/ot-harjoitustyo/releases/tag/viikko6)

[Loppupalautus](https://github.com/petrioski/ot-harjoitustyo/releases/tag/loppupalautus)


### Komentorivitoiminnot

Sovellus on tehty Maven-projektina, joten oleellisimmat toiminnot voi suorittaa myös IDEn ulkopuolella komentoriveillä ja tuotokset löytyvät target-kansiosta

#### Testaus

Testaus käynnistyy komennolla
'''
mvn test
'''

#### Testikattavuus

Jacocon testikattavuusraportti on saatavilla html-tiedostona kansiosta target/site/jacoco/index.html

Raportti luodaan komennolla
'''
mvn jacoco:report
'''

#### Jar-tiedosto

Suoritettavan jar-tiedoston luonti komennolla

'''
mvn package
'''

jar-paketti syntyy target-kansioon nimellä familyToDo-1.0-SNAPSHOT.jar

#### JavaDoc

JavaDocin luonti komennolla
'''
mvn javadoc:javadoc
'''

index.html -tiedosto löytyy kansiosta target/site/apidocs

#### Checkstyle

Projektissa on käytetty checkstyle-tyylitiedostoa ja sen mukaiset tarkistukset voi generoida komennolla

'''
mvn jxr:jxr checkstyle:checkstyle
'''
checkstyle.html -tiedosto syntyy kansioon target/site
