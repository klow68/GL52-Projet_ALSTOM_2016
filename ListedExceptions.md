PB : 

place/position wagon

masse du train

modèle wagon 

nb essieux wagon (validation)

représentation et lien entre type et sous-type

-> pas au niveau UML, création à partir des données du type, puis motif à la mano

# —————————————————————————————————————
# modifiable avant simulation
# —————————————————————————————————————

## ATTELAGE
- place
- L0
- effort max
- course max
- loi course effort

## CONVOI

## LOCOMOTIVE
- Masse de la loco
- Adhérence de freinage maximum
- Nombre de position de distributeurs
- effort pneumatique correspondant à une position

## WAGON
- Modèle de wagon
- Type de wagon
- Nombre d’essieux
- Longueur des Wagons
- Nombre de cylindres de frein
- Volume du distributeur
- Volume du réservoir auxiliaire

# —————————————————————————————————————
# non modifiable en cours de simulation
# —————————————————————————————————————

## ATTELAGE
- place
- L0
- effort max
- course max
- loi course effort

## LOCOMOTIVE
- Masse de la loco


## WAGON
- Modèle de wagon
- Type de wagon
- Nombre d’essieux
- Longueur des Wagons
- Nombre de cylindres de frein
- Volume du distributeur
- Volume du réservoir auxiliaire

# —————————————————————————————————————
# modifiable pendant la simulation
# —————————————————————————————————————

## LOCOMOTIVE

## WAGON
- pourcentage de fuites par litre

# —————————————————————————————————————
# Tests de cohérence
# —————————————————————————————————————

## LOCOMOTIVE
calcul de rampe corrigé ???
effort retardateur, adhérence ???

## WAGON
- Modèle de wagon (?)
- Type de wagon (?)
- Nombre d’essieux (? mais à priori nb>2)
- Taux de remplissage des wagons (entre 0 et 100 %)
- pourcentage de fuites par litre (entre 0 et 100 %)
- Chef aux masses tournantes (entre 0 et 100 %)
- Taux de remplissage wagon (entre 0 et 100 %)

# —————————————————————————————————————
# Envoyé au modèle de simulation du convoi
# —————————————————————————————————————

## LOCOMOTIVE
- Nom de la locomotive


## WAGON

VALEURS :
- Modèle de wagon
- Type de wagon
- Nombre des cylindres de frein
- Volume du distributeur
- Volume du réservoir auxiliaire
- pourcentage de fuites par litre

RESULTATS CALCULS :
- Nombre d’essieux
- Abscisse wagon

# —————————————————————————————————————
# Valeurs à calculer
# —————————————————————————————————————

## CONVOI

### Nom du convoi (par défaut)

si modification d’un précédant
= nom (- éventuel numéro de version) + incrément
sinon
= demander

### Taux de remplissage

si Remplissages_identiques == Vrai
	appliquer sur tous les wagons ou substituer la valeur

## LOCOMOTIVE


## WAGON

### Masse du wagon

= Tare(Type) + Charge_Nominale(Type) * Taux_remplissage(Wagon)

unité : tonne

### Abscisse du wagon

- longueur des wagons

### Consommation d’air
Nb_Cylindres_Frein
% de fuites par litre

### effort wagon
Nb_Cylindres_Frein
Volume_Cylindres_Frein

### Nombre d’essieux total

Valeur = type (Type : Nb_Essieux_Requis) + position wagon