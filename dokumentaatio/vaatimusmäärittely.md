# Vaatimusmäärittely

## Sovelluksen tarkoitus
ohjelman tarkoitus on käyttää open street mapin reittidataa ja tehdä Espoon alueelle sijoittuja reitinhakuohjelma. oppimistavoite on karttadatan käyttäminen.
reittidata jalostetaan qgissiä käyttäen sellaiseen muotoon, että se saadaan ajettua relaatiokantaan ja näin ohjelman sisäiseksi käytettäväksi dataksi.
käyttöliittymä toteutetaan ulkoisen JXMapViewer kirjaston avulla.

## käyttäjäroolit
käyttäjät ovat tasa-arvoisia, järjestelmä ei tarvitse erikseen adminia, paitsi devausvaiheessa

## käyttötapauksia
käyttäjä voi luoda itselleen käyttäjätunnuksen
käyttäjä voi antaa pisteitä kartalta ja ohjelma hakee näitten välille reitin
käyttäjä voi tallentaa etsityn reitin
käyttäjä voi tallentaa pisteitä, jotka hakemalla ohjelma laskee uudestaan näitten välille reitin

## arkkitehtuurisuunnittelu
tarvitaan joku palikka joka kommunikoi karttarajapintojen kanssa
tarvitaan luultavasti joku palikka joka parsii kartan vektoridatan ohjelmassa käytettävään muotoon
tarvitaan joku palikka joka tekee itse reitinetsimisen
tarvitaan joku palikka joka näyttää kartan
tarvitaan käyttöliittymä jolla käyttäjä voi valita pisteitä kartalta
tarvitaan tietokanta johon pisteitä tallennetaan
tarvitaan palikka joka kommunikoi tietokannan kanssa

