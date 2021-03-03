# Portfolio Individueel resultaat
### Stijn van Tilburg 2171448 TI 1.3 A3

In dit document word het leerproces omtrent de proftaak "Festival Planner"
van de student Stijn van Tilburg beschreven. Er zal vanaf de 3de week van de proftaak
wekelijks een update over het leerproces van de student zijn. In deze wekelijkse
reflectie zal worden beschreven tegen welk probleem de student is aangelopen
en hoe de student tot de oplossing is gekomen. Dit zal worden gedaan door dingen te 
beschrijven zoals: 

- Wat is de situatie(context)?
- Welke keuzemogelijkheden heb je?
- Welke keuze heb je gemaakt?
- Waarom heb je deze keuze gemaakt?

Daarnaast zullen er nog 2 pagina's toegevoegd worden rondom de volgende onderwerpen:

+ De student zijn reflectie, inclusief onderbouwing, op één van de volgende 
stellinen: 
  - “In het bedrijfsleven wordt gebruik gemaakt van JavaFX”
  - “In het bedrijfsleven wordt steeds meer in software gesimuleerd”
   
+ een lijst met applicaties die gebruik maken van het JSON formaat. De
  student geeft ook aan waarom dat hij denkt dat JSON wordt gebruikt.
  
Vanaf dit punt zal alles worden beschreven vanuit het oogpunt van de Student Stijn van Tilburg.
  
## Week 3
 
Op dit punt van het project was de agenda kant van ons project al bijna
helemaal af, dus gingen we functies toevoegen om te zorgen dat de gebruiker
minder fouten kon maken. Het probleem waar ik aan werkte was dat het momenteel
mogelijk was om dezelfde artiest op meerdere podia tegelijk in te plannen.

Dan kwam nu de probleem analyse, wat zijn de mogelijke manieren om dit op te lossen?

Oplossing 1:  Als je een show maakt en de tijd aanpast word de lijst van artiesten 
   omgebouwd zodat je alleen de beschikbare artietsen ziet.

Oplossing 2:  Je slaat in elke artiest op wanneer ze spelen en controleert daarmee
of ze al aan het spelen zijn op een gegeven moment.

Oplossing 3:  wanneer en show word gemaakt/bewerkt haal je alle shows op die spelen
 rond een overlappende tijd en haalt de artiesten die dan spelen op.

Natuurlijk heeft elke oplossing zo zijn voordelen en nadelen, daar gaan
we nu naar kijken.

Nadelen:
1. 1. Dit vereist het volledig ombouwen van de manier hoe we onze artiesten
   opslaan voor de ComboBox.
   2.  Vanwege dat we onze tijd parsen uit een textvak zal dit ook elke keer
   moeten worden gedaan zodra je de tijd aanpast om dit te laten werken.
   Dit zou betekenen dat zodra je iets fout tiept je meteen een error krijgt,
   en dat we dus daar een work around voor moeten maken om het goed te laten werken.
   3. Dit kan verwarend zijn voor de gebruiker.
   
2. 1. We moeten de artiets nu naderhand veranderen.
   2. De artiest slaat informatie op die niet heel logisch is om op te
   slaan in een artiest.
   3. Je slaat informatie dubbel op want de agenda weet al in welke shows de
   artiest speelt
3. 1. Minst efficient van alle methodes.
   
Voordelen: 
1.  Voorkomt fouten I.P.V ze aan te geven

2.  Zelfde functionaliteit als de 3de methode maar sneller.

3.  Er hoeft maar in een klasse gewerkt te worden.

Als we kijken naar deze voor- en nadelen Zien we dat het enige echte probleem
van oplossing 3 is dat het niet zo snel werkt als de andere manieren, terwijl
het wel voorkomt dat er in extra in anderman's klasse's moet worden gewerkt.
Dat is een enorm voordeel terwijl het nadeel van efficientie niet van groot
belang is bij dit onderdeel. Hierdoor vallen oplossing 1 en 2 alebei tekort
omdat er teveel onnodig gesleutel is in andere klassen.

   

   