# Magic World

Magic World is a small Action RPG that I am currently developing.

This is my first ever Java program. I decided not to use a game engine and instead make my own (I regret this decision).

At the moment, the game has neither a story nor any interesting gameplay.

## Building

> I am developing this game as a hobby. The latest commit might not always work. There ist no warranty. <br>
> Currently the game is in pre alpha. These are only snapshots.

### Requirements

You must have at least [Java 24](https://www.oracle.com/java/technologies/downloads/) installed. Additionally you need [Maven](https://maven.apache.org/).

You *can* use [Git](https://git-scm.com/) to download the files, but **it is not required**.

### 1. Download the project

Open a terminal and run:

```bash
git clone https://github.com/athalion/Magic-World.git
```
(You need to have Git installed for this to work.)

Alternatively, you can click the `Code` button at the top and choose `Download ZIP` or use your IDE of choice to clone the repository.

### 2. Dependencies

The program uses Maven, so you do not have to worry about most dependencies. However, since [TinySound](https://github.com/finnkuusisto/TinySound) **is not available** on Maven Central, you will need to install it manually.

You can get the latest release [here](https://finnkuusisto.github.io/TinySound/releases/tinysound-1.1.1.zip). Then unzip the folder and open a terminal in it.

> On Windows, go to the address bar which displays the current location (e.g., `C:\Download\`) and type `cmd`, then press Enter.  
> If you use Linux, you probably already have at least one terminal open :))

Install TinySound in your local Maven repository by running:

```bash
mvn install:install-file -Dfile=tinysound-1.1.1.jar -DgroupId=kuusisto -DartifactId=tinysound -Dversion=1.1.1 -Dpackaging=jar
```

### 3. Building

Simply run

```bash
mvn clean package
```

to build the project. You can find the JAR file in the `target` directory.

Create a new folder (e.g., on your Desktop) and put the JAR file inside. Run the game using

```bash
java -jar Magic-World-<version>.jar
```
(Replace `<version>` with the actual file name.)

## Known Issues

### Linux
In some cases audio is output on the wrong device or with wrong volume.

### GNOME
Fullscreen and the screen selector might not work corectly on Linux systems with the GNOME desktop environment.

## Credits

The basis of this game was created with the help of a tutorial by RyiSnow. (Go and check out his [channel](https://youtube.com/@RyiSnow))

At the moment, the game uses some assets by RyiSnow. I will replace those soon.

### The game uses the following libraries:

- [Jamepad](https://github.com/williamahartman/Jamepad) by Will Hartman for easy controller support.
- [sdl2gdx](https://github.com/electronstudio/sdl2gdx) by Richard Smith (required by Jamepad).
- [GSON](https://github.com/google/gson) by Google as an easy-to-use JSON library.
- [TinySound](https://github.com/finnkuusisto/TinySound) by Finn Kuusisto for playing audio.
