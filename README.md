# Pénzügyi menedzser alkalmazás
![Alt text](https://github.com/AdamTakeow/pt-beadando/blob/master/kepek/welcome.png "Az alkalmazás kezdő képernyője")
##### Az alkalmazás célja a kiadások és bevételek nyomon követésének _megkönnyítése_, valamint néhány praktikus megoldással segítse pénzügyeink szervezését.

### Funkciók
* Kiadás és bevétel vezetése
  * Összeg megadása
  * Kategória megadása
  * Dátum megadása
  * Leírás megadása
  * Megadható hogy ismétlődik-e az adott tranzakció és ha igen akkor hány naponta
  * Törölni lehet egy tranzakciót
* Kategória létrehozása
* Lekötés létrehozása
  * Megadható a lekötés összege
  * Megadható a lekötés kamata
  * Megadható a futamidő
* Be lehet állítani hogy mennyit szeretnénk költeni az aktuális hónapban, és az alkalmazás ezt figyelembe véve tájékoztat arról, hogy mennyit költhet még a felhasználó a hónapban.
* Beállítható a megtekintési időszak
  * Az alkalmazás a kiválasztott időszakban fogja mutatni a tranzakciókat, kiadásokat stb.
* Előzmények követése
  * Minden tranzakció visszakövethető
  * Követhető az összes kiadás és bevétel egy adott időszakra nézve 
  * Szemléletes diagramokon látható a kategóriánkénti kiadás és bevétel
* Több felhasználó elkülönítve használhatja

### Útmutató a használathoz

Az alkalmazás indulásakor a következő képernyő fogad:

![Alt text](https://github.com/AdamTakeow/pt-beadando/blob/master/kepek/bejelentkezo.png "Az alkalmazás bejelentkező képernyője")

Itt lehet bejelentkezni egy meglévő felhasználóval, vagy regisztrálni.
* Regisztrációnál a felhasználónév és jelszó hossza __legalább 4 karakter kell legyen__. Ennek hiányát jelzi is az alkalmazás.
Ha a megadott adatok helyesek, akkor a bejelentkezés gombra kattintva a következő képernyő fogad:

![Alt text](https://github.com/AdamTakeow/pt-beadando/blob/master/kepek/kezdo1.png "Az alkalmazás kezdő képernyője frissen regisztrált felhasználónál")

A fenti ábrán egy frissen regisztrál felhasználó kezdőképernyője látható.

A kezdőképernyőn lehetőségünk van beállítani néhány adatot a _beállítások_ gombra kattintva.

![Alt text](https://github.com/AdamTakeow/pt-beadando/blob/master/kepek/beallitasok.png "Beállítások")

Itt megadható hogy mennyit szeretnénk költeni az aktuális hónapban, valamint jelszó változtatásra is van lehetőség.

Íme egy kiadásra szánt összeg beállítását követő állapot:

![Alt text](https://github.com/AdamTakeow/pt-beadando/blob/master/kepek/beallitasok_kitoltve.png "Beállítás után")

A kezdőképernyőn lehetőség van új lekötés létrehozására, az __új lekötés__ gombra kattintva.

![Alt text](https://github.com/AdamTakeow/pt-beadando/blob/master/kepek/ujlekotes.png "Új tranzakció létrehozása")

Itt értelemszerűen ki kell tölteni a lekötés adatait, majd a __lekötés__ gombra kattintva véglegesíthető a lekötés.
A lekötés várható összegének számításánál _Kamatos kamat_ számítás zajlik.

Itt látható a lekötés miután létrejött:

![Alt text](https://github.com/AdamTakeow/pt-beadando/blob/master/kepek/kezdo2.png "A kezdőképernyő lekötés után")

Mivel még nem volt bevétel ezért automatikusan minuszba ment az egyenleg a lekötés miatt.
Ha éppen van egy aktív lekötése a felhasználónak, akkor nem lehet újat létrehozni, csak a meglévőt lehet megtekinteni, illetve van lehetőség idő lejárta előtti feltörésre.
Ebben az esetben a felhasználó kizárólag a lekötés összegét kapja vissza, a kamatot nem. Azonban ha lejárt a lekötés időtartama, akkor a váható összeget elkönyveli az alkalmazás a felhasználó számára.

A kedőképernyőn lehetőség van még új tranzakció létrehozására, az __új tranzakció__ gombra kattintva.

![Alt text](https://github.com/AdamTakeow/pt-beadando/blob/master/kepek/ujtranzakcio.png "Új tranzakció létrehozása")

Itt ki kell tölteni a tranzakció részleteit amelyekből a kötelező megadni hogy:
* __kiadás vagy bevétel__
* __a tranzakció összegét__

A többi adat opcionális, azaz például ha nem adunk meg kategóriát, automatikusan _Nincs_ kategóriájú lesz.
Ha nem adunk meg dátumot, automatikusan az _aktuális dátum_ lesz beállítva.

Lehetőség van új kategória létrehozására az __Új kategória__ gomb megnyomásával:

![Alt text](https://github.com/AdamTakeow/pt-beadando/blob/master/kepek/ujkategoria.png "Új kategória megadása")

Itt figyelni kell, hogy __létező kategória nevet ne adjunk meg__!

Íme egy kitöltött tranzakciós állapot:

![Alt text](https://github.com/AdamTakeow/pt-beadando/blob/master/kepek/ujtranzakcio_kitoltve.png "Kitöltött tranzakció adatok")

Ha készen vagyunk, kattintsunk a __Mentés__ gombra!

Itt látható a létrehozás utáni állapot:

![Alt text](https://github.com/AdamTakeow/pt-beadando/blob/master/kepek/kezdo3.png "Az alkalmazás kezdő képernyője egy lekötés és egy tranzakció után")

Ha a listában egy tranzakcióra kattintunk, megjelennek a részletei, illetve ott lehet törölni is.

![Alt text](https://github.com/AdamTakeow/pt-beadando/blob/master/kepek/tranzakcioreszletei.png "Tranzakció részletei")

Ismétlődő tranzakció esetén csak ki kell pipálni hogy ismétlődik a tranzakció, valamint meg kell adni hogy hány naponta ahogy az alábbi képen látható:

![Alt text](https://github.com/AdamTakeow/pt-beadando/blob/master/kepek/ujtranzacio_ismetlodo.png "Ismétlődő tranzakció kitöltése")

A tranzakció az ismétlődésnek megfelelően automatikusan hozzáadódik ha szükséges, illetve korábbi dátumra beállítva a tranzakció időpontját visszamenőleg is megtörténnek az ismétlődött tranzakció beszúrások.

### Telepítés

##### Első módszer
1. A tároló leklónozása
2. A leklónozott tároló _penzkezelo_ mappájában kiadni a következő parancsot: _mvn package_
3. Miután lefutott a parancs, a _penzkezelo\_ui\\target\\_ mappában kell kiadni a következő parancsot: _java -jar penzkezelo.jar_
4. Az alkalmazás elindul

#### Második módszer
1. A _penzkezelo.jar_ letöltése a tárolóból
2. Dupla kattintás a _penzkezelo.jar_ állományon az alkalmazás elindítását eredményezi

### Rendszerkövetelmények
* Java 8 JRE megléte szükséges
* Minél gyorsabb számítógép
