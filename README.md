# Magic World

Magic World is a small ActionRPG, which I am developing at the moment.

This is my first ever Java Programm. I decided not to use a Game Engine and instead make my own. (I regret this
decision)

At the moment the game has neither a story nor any interesting gameplay.

## Building

### 1. Donwload the project
Open a terminal and run `git clone https://github.com/athalion/Magic-World.git`. (You need to have git installed in order for this to work)

Alternatively you can click the `Code` button at the top and choose `Download ZIP` or use your IDE of choise to clone the repository.

### 2. Dependencies
The program uses Maven. So you don't have to worry about most dependencies. But since [TinySound](https://github.com/finnkuusisto/TinySound) **is not available** on Maven Central, you will need to download it manualy.
You can get the latest release [here](https://finnkuusisto.github.io/TinySound/releases/tinysound-1.1.1.zip). Then unzip the folder and open a terminal in it.
> on Windows go to the bar which diplays the current location e.g. `C: > Download >` and type `cmd` and press enter. <br>
> If you use Linux you have at least one terminal open by now :))

You can install TinySound in your local maven repo by running `mvn install:install-file -Dfile=TinySound-1.1.1.jar -DgroupId=kuusisto -DartifactId=tinysound -Dversion=1.1.1 -Dpackaging=jar`

### 3. Building
Simply run `mvn clean package` to build the project. You can find the .jar file in the target directory.
Create a new folder (e.g. on your Desktop) and put the .jar file inside. Run the game using `java -jar Game2D-VERSION` (replace with actual file name).

## Credits

This basis of this game was created with the help of a Tutorial by RyiSnow. (Go and check out his
[channel](https://youtube.com/@RyiSnow))
At the moment the game uses some Assets by RyiSnow. I will replace those soon.

### The game uses the following libraries:
[Jamepad](https://github.com/williamahartman/Jamepad) by Will Hartman for easy Controller suport.
[sdl2gdx](https://github.com/electronstudio/sdl2gdx) by Richard Smith. (Required by Jamepad)
[GSON](https://github.com/google/gson) by Google as an easy to use json library.
[TinySound](https://github.com/finnkuusisto/TinySound) by Finn Kuusisto for playing Audio.
