# Vaatimusmäärittely

## Sovelluksen tarkoitus
ohjelman tarkoitus olisi käyttä open street mapin dataa ja tehdä tähän ympäristöön jonkinlainen reitinhakuohjelma
tarkoitus olisi lähinnä tutustua siihen, että miten avointa karttadataa voi käyttää reitinetsimiseen myöhempiä projekteja varten
kommunikaatio open street mapin kanssa toimii luultavasti helpoiten jollain ulkoisella kirjastolla

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

