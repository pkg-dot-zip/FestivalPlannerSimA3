# Portfolio Individueel resultaat

### Jesse Krijgsman 2166189 TI 1.3 A3

In dit document wordt het proces van het ontwerpen, maken, testen en alle andere noodzakelijkheden omtrent de festival
planner. Alle bezigheden, moeilijkheden en keuzes die ik en wij hebben moeten maken zijn opgenomen. Alles wat hier
beschreven staat is vanuit het oogpunt van mij, Jesse Krijgsman. In dit portfolio worden lesweek 3 t/m lesweek 9
opgenomen. Ook wordt er een reflectie met onderbouwing op het werken met .JSON bestanden gegeven.

Er worden voor elke lesweek de volgende punten behandeld:

* Een reflectie over mijn bijdrage aan, en het doorlopen proces van, het project.
* Een reflectie over de technische en/of vakinhoudelijke problemen.
    * Wat is de situatie (context)?
    * Welke keuzemogelijkheden heb je?
    * Welke keuze heb je gemaakt?
    * Waarom heb je deze keuze gemaakt?

---

# Week 3

---

### Reflectie proces

Deze week zijn we begonnen met het demonstreren van onze AgendaModule. De Senior was tevreden met hoe het er op het
moment voor staat, de code ziet er goed uit. Er zijn nog wel dingen die opgelost moeten worden. Na de demonstratie
hebben wij een vergadering gehouden met de plannen van deze week.

Als planner binnen de groep heb ik na de vergadering laten zien hoe we gebruik gaan maken van Trello om onze taken bij
te houden. Dit is naast het Excel sheet dat we al gebruiken voor de planning. Ik ben niet georganiseerd te noemen, als
planner probeer ik het deze periode goed te doen. Hieronder is een afbeelding te zien van de nieuwe trello pagina, zodat
we alle taken goed georganiseerd bij kunnen houden.

![](Images/Screenshot_1_Trello.png)

---

### Reflectie vakinhoudelijk

Inhoudelijk zijn we deze week vooral bezig geweest met aanpassingen aan onze agenda module. Na de start van deze week
hebben we onze module gepresenteerd aan de senior Johan, hij was zeer positief over ons resultaat. Maar er stonden nog
wat punten op die toegevoegd moeten worden één voorbeeld hiervan is het aangeven wanneer een artiest een dubbele boeking
heeft staan.

Deze week heb ik mij vooral bezig gehouden met en inzoomen en schalen van AgendaCanvas. Dit was geen verplichting
volgens de opdracht, maar het is wel iets wat wij als groep graag zouden willen hebben. Dit om te voorkomen dat er witte
vlakken ontstaan als het canvas te groot wordt gemaakt

Bij dit proces zijn veel moeilijkheden waar ik er hier graag twee zou willen uitlichten:

+ De functionaliteit van het inzoomen tijdens scrollen.
+ Het meeschalen tijdens het vergroten van de stage.

---

##### De functionaliteit van het inzoomen tijdens scrollen

In de week voorgaand aan week 3 heb ik, samen met teun, gewerkt aan AgendaCanvas. Dit is de klasse waarin het canvas
voor de agenda module staat. Deze klasse heeft een Rooster als attribuut en vertaald deze naar een rooster op het
scherm. Ook zit er in deze klasse de manier om te scrollen. Zowel verticaal als horizontaal.

In deze week zijn we bezig geweest met het toevoegen van inzoomen in het canvas. De eerste vraag die naar boven kwam was
de vraag of het inzoomen zowel horizontaal als verticaal moest zijn, of dat het alleen over de horizontale is gaat. De
keuze is gemaakt om alleen horizontaal in te zoomen, zodat het mogelijk wordt om de hele tijdlijn te zien in één scherm
zonder dat de podia heel klein worden weergeven.

We hadden al de methode `public boolean transformInBounds(AffineTransform transform)` gemaakt voor het implementeren van
het normale scrollen. Daarom was het de planning om het inzoomen ook via deze manier te laten werken. De methode staat
hieronder ook weergeven:

```java
    /**
 Calculates if the given translate will fit within the set bounds.
 <p>
 Currently only works on translations, scale not yet implemented.
 @param transform  AffineTransform that is proposed
  * @return true if the given translate is in bounds
 */
private boolean cameraInBounds(AffineTransform transform){
        return(this.cameraTransform.getTranslateX()+transform.getTranslateX()<=1&&
        this.cameraTransform.getTranslateX()+transform.getTranslateX()>=-(this.endX-this.startX-this.canvas.getWidth())&&
        this.cameraTransform.getTranslateY()+transform.getTranslateY()<=1&&
        this.cameraTransform.getTranslateY()+transform.getTranslateY()>=-(this.endY-this.startY-this.canvas.getHeight())
        );
        }
```

Helaas kwam ik in de knoei met het inzoomen via `AffineTransform`. Het schalen was geen probleem, het probleem kwam met
het checken of het schalen wel binnen het veld past. De waarden waarop het canvas begint of in afgelopen veranderen
flink als je gaat inzoomen. Ik kreeg het niet voor elkaar om dit goed te krijgen. Het gebeurde steeds dat, of ik kon
veel te ver naar links of rechts doorscrollen na het inzoomen, of de camera wou niet meer verder dan 11 uur door
scrollen.

Ik heb besloten om het inzoomen via een andere manier te doen, niet via een `AffineTransform`. `AgendaCanvas` Heeft nu
een attribuut die het inzoomen bijhoudt als een factor, hiermee voorkomen we het inBounds probleem. Dit omdat er tijdens
het tekenen van het canvas rekening wordt gehouden met deze factor en de breedte van het canvas dus automatisch mee
schaalt.

Het resultaat van het inzoomen staat hieronder in deze mooie gif gedemonstreerd.

![](Images/Zooming%20with%20alt+scroll.gif)

---

##### De functionaliteit van het mee schalen tijdens het vergroten van het window.

Het tweede probleem waar we meezaten is het feit dat: wanneer het windows wordt vergroot tot buiten de grenzen van de
agenda het verder wit blijft. Dit is geen mooie oplossing en breekt ook een beetje de illusie van het programma. Het
probleem is ook duidelijk te zien in de gif die hieronder staat.

![](Images/Out%20of%20bounds.gif)

In het klasse `AgendaCanvas` zat al een methode die de bounds van het canvas berekend, deze heb ik de weken hiervoor
gemaakt. Deze methode deed op het moment niks anders de de bounds naar statische waarden zetten. Nu is de methode
aangepast.

Eerst berekend de methode een schalings factor, dit is gebaseerd op een tijdlijn van 24 uur (zoals de opdracht) en een
standaard breedte van een uur van 60 pixels (dit is eerder gekozen om de minuten goed te representeren). Door deze
deling wordt er bepaald welke schaal er nodig is om te zorgen dat het gehele scherm gevuld is met de periode van 0 tot
24 uur.

```java
double monitorToScale=(this.canvas.getWidth()+startX)/(24*60*scale);
```

Hierna wordt er door de methode gekeken of de schaal groter is dan 1. Als dit het geval is dan valt het canvas dus
binnen het veld van het venster. In dat geval moet de schaal worden aangepast door de methode.

```java
if(monitorToScale>1){
        this.scale=(this.canvas.getWidth()+startX)/(24*60);
        }
```

De nieuwe bounds worden op basis van de schaal berekend en het scalen werkt volledig. Hieronder staat de volledig
methode ook aangegeven:

```java
/**
 * Sets the boundaries of the agendaCanvas.
 * <p>
 * Initializes <code>this.startX</code>, <code>this.endX</code>, <code>this.startY</code>, <code>this.endY</code> based on the calculated boundaries.
 */
private void calculateBounds(){
        this.startX=-100;
        this.startY=-50;

        double monitorToScale=(this.canvas.getWidth()+startX)/(24*60*scale);
        if(monitorToScale>1){
        this.scale=(this.canvas.getWidth()+startX)/(24*60);
        }

        if(this.canvas.getHeight()>this.usedStages.size()*80){
        this.endY=(int)this.canvas.getHeight();
        }else{
        this.endY=this.usedStages.size()*80+50;
        }

        this.endX=(int)(60*this.scale*24);
        }
```

Nu schaal het rooster mee met het venster als deze te groot wordt. Het restultaat staat hieronder met een gifje. Beloofd
dat ik voor deze week geen gif bestanden meer zal gebruiken, het begint toch wat te druk te worden ;)

![](Images/Out_of%20_Bounds_Fixed.gif)

---
De kat van week 3 is (All royalties to Max van Gils):

![](Images/CatOfTheWeek/CatWeek3.png)

---

# Week 4

---

### Reflectie proces

Deze project week zijn wij begonnen met een Tutor gesprek. Deze week stond op de planning om het ontwerp te maken voor
de rest van het project. Het simulator gedeelte moest hierbij worden uitgedacht. Na de vergadering en het opdelen van de
taken hebben zijn we met de hele groep bezig geweest. Later hebben Jason en ik de andere twee klasse diagrammen die op
de planning stonden gemaakt. De Klasse diagrammen die gemaakt moesten worden zijn:

+ Klasse Diagram met de TileMap en onderliggende code
+ Klasse Diagram met de logica achter het aansturen van applicatie
+ Klasse Diagram met de opbouw en werking van de GUI applicaties

Later in de week zijn wij er achter gekomen dat objecten ook uit een Json bestand moeten worden gelezen. Hierna heb ik
deze ook kort toegevoegd in het TileMap klasse diagram

De Klasse diagrammen die wij hebben gemaakt in deze ontwerpfase staat hieronder gegeven:

![](Images/KlasseDiagram%20GUI%20+%20TIleMap.png)
![](Images/KlasseDiagram%20Logic.png)

Na het senior gesprek zijn er een paar kleine aanpassingen gemaakt, maar hij was grotendeels positief.

---

### Reflectie proces

Achtergrond voor deze week: Deze week zijn wij bezig met het ontwerp zoals in het vorige stukje uitgelegd.
Vakinhoudelijk zijn wij deze week bezig geweest met en implementeren van de TileMap klassen die wij hiervoor hebben
ontworpen. Hierna moest onze gemaakt Tilemap Json bestand worden uitgelezen.

Ik wil het in de reflectie van deze week hebben over één probleem waar ik tegen aan liep tijdens het uitlezen van een
Json bestand. In ons ontwerp is er voor gekozen om alle uitgelezen tiles in een in een HashMap op te slaan Hierbij wordt
als key het Id nummer van de Tile opgeslagen en als value de Tile zelf. Het idee hier achter is dat wij dus niet elke
tile hoeven in te laden. Dit scheelt in tijd een zeker ook in het gebruik van het werkgeheugen.

Hieronder staat een stukje uit de klasse die deze tiles moet uitlezen:

```java
JsonArray tileSets = root.getJsonArray("tilesets");

        //loops through all tileSets
        for (int i = 0; i < tileSets.size(); i++) {
        JsonObject tileSet = tileSets.getJsonObject(i);

        int tileWidth = tileSet.getInt("tilewidth");
        int tileHeight = tileSet.getInt("tileheight");
        int collums = tileSet.getInt("columns");

        //Loading image in tileSet
        BufferedImage tileImage = ImageIO.read(getClass().getResourceAsStream(tileSet.getString("image")));
        
        //only loop through used images for efficiency
        JsonArray tiles = tileSet.getJsonArray("tiles");
        for (int q = 0; q < tiles.size(); q++) {
            JsonObject tile = tiles.getJsonObject(q);
            int id = tile.getInt("id");
            
            //only loops through used images for efficiency
            Tile tileObject = new Tile(tileImage.getSubimage(
                    (tileWidth + 1) * (id % collums),        //
                    (tileHeight + 1) * (id / collums),       // Voodoo magic splitting the images based on id
                    tileWidth,                               //
                    tileHeight), id);                        //

                    tileManager.addTile(id, tileObject);
                }
            }
```

In de code is te zien 