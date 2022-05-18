# projecte1-movil
Repositori de l'aplicació movi del primero projecte de DAM2A

RUTES I COMANDES PER aL GITBASH
========== ruta al repositori ==========
https://github.com/fbaguem2021/MusicQuiz.git
-
========== archiu JAVA ==========
main:
app\src\main\java\com\example\musicquiz\MainActivity.java
-
========== arxius XML ==========
imatges:
app\src\main\res\drawable\nom_imatge
colors:
app\src\main\res\values\colors.xml
strings:
app\src\main\res\values\strings.xml
themes:
app\src\main\res\values\themes.xml
dimens:
app\src\main\res\values\dimens.xml
-
========== comandes GIT ==========
Guia web amb informació sobre les diferents comandes de git
https://github.com/git-guides/
-
Descargar contingut del repositori a la carpeta on estigui el git
git clone
-
Afegir/ actualitzar el contingut de l'arxiu especificat
git add "ruta\del\arxiu.exemple" ¡¡¡(mai utilitzar git add .)!!!
-
Sincronitzar projecte amb el repositori ed github
git remote add origin  https://github.com/fbaguem2021/MusicQuiz.git
(es possible que demani usuari i contrasenya)
-
Descargar els documents del projecte que hagin estat modificats
git pull "nom branca"
-
Pujar al github totes les modificacions que hagis afegit amb el "git add"
git push "nom branca"
-
Comentar quins son tots els canvis que es pujin amb el git push
git commit -m "contingut del commit"
-
Mostrar els noms de les branques
git branch -a
-
Canviar de branca a la especificada
git checkout "nom de branca"
(si la branca existeix canviara a la especificada, si no, creara una de nova)
-
Juntar/fusionar contingut de les branques
git merge
