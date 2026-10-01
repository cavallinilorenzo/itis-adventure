<h1 align="center">
  <img src="docs/images/title.png" alt="ITIS ADVENTURE" width="640">
</h1>

A 2D JavaFX game set in the ITIS E. Fermi school.
Explore the building, find the **10 notebook pages** and reach the exit to win.

> This project was the summer holiday assignment for the **Computer Science**
> ("Informatica") course, 4th year class, completed between the 4th and 5th year.

## Requirements

| Software | Version | Notes |
|----------|---------|-------|
| JDK      | 17 or later | e.g. [Eclipse Temurin](https://adoptium.net/) |
| Maven    | 3.8 or later | [maven.apache.org](https://maven.apache.org/download.cgi) |
| Git      | any | only needed to clone the repository |

You do **not** need to install JavaFX manually: Maven automatically downloads
the right libraries for your operating system (Windows, macOS, Linux).

To check what you already have installed:

```bash
java -version
mvn -v
```

### Installing the requirements

**macOS** (with [Homebrew](https://brew.sh/)):
```bash
brew install --cask temurin
brew install maven
```

**Windows** (with winget):
```powershell
winget install EclipseAdoptium.Temurin.21.JDK
winget install Apache.Maven
```
Alternatively, download the installers from the websites above and add Maven to your `PATH`.

**Linux** (Debian/Ubuntu):
```bash
sudo apt install openjdk-21-jdk maven
```

## Installation and launch

1. Clone the repository (or download and extract the ZIP):
   ```bash
   git clone <repository-url> itis-adventure
   cd itis-adventure
   ```

2. Build and launch the game:
   ```bash
   mvn javafx:run
   ```
   On the first run Maven downloads the dependencies, so an internet connection
   is required and it may take a few minutes. Subsequent runs start immediately.

To build only, without launching:
```bash
mvn compile
```

To delete the build output (the `target/` folder):
```bash
mvn clean
```

## Opening the project in an IDE

The project uses the standard Maven layout, so any IDE can open it directly:

- **IntelliJ IDEA**: `File → Open` and select the project folder (or the `pom.xml` file).
- **NetBeans**: `File → Open Project` and select the project folder.
- **Eclipse**: `File → Import → Maven → Existing Maven Projects`.
- **VS Code**: install the *Extension Pack for Java* and open the folder.

To launch the game from the IDE, run the Maven goal `javafx:run`
(all the IDEs above have a Maven panel).

## Controls

| Key | Action |
|-----|--------|
| ↑ ↓ ← → | Move the character |
| Shift (hold) | Run |

## Project structure

```
itis-adventure/
├── pom.xml                         # Maven configuration (dependencies, build, launch)
├── README.md
├── docs/images/                    # Images used by this README
└── src/main/
    ├── java/itisgame/              # Source code
    │   ├── ItisGame.java           # Main class: window, input, game logic
    │   ├── TileManager.java        # Map loading and rendering
    │   ├── Tile.java
    │   ├── PagesManager.java       # Collectible notebook pages
    │   ├── Pages.java
    │   ├── DialogueManager.java    # Character speech bubbles
    │   └── Dialogue.java
    └── resources/
        ├── maps/                   # Room maps (.txt, 16x12 grid of tile IDs)
        ├── images/
        │   ├── tiles/              # Floors, walls, doors, stairs, furniture
        │   ├── characters/         # Characters placed on the maps
        │   ├── dialogues/          # Dialogue speech bubbles
        │   ├── player/             # Player sprites
        │   └── items/              # Collectible items
        ├── fonts/                  # PressStart2P font
        └── sounds/                 # Music and sound effects
```

All resources are loaded from the classpath, so the game works regardless of
the folder it is launched from.

## Troubleshooting

- **`mvn: command not found`**: Maven is not installed or not on your `PATH`.
- **`invalid target release: 17`** or similar errors: Maven is using a JDK that
  is too old. Check which Java it uses with `mvn -v` and, if needed, set the
  `JAVA_HOME` environment variable to a JDK 17+ installation.
- **The window does not open on Linux**: make sure you are in a graphical
  session (X11/Wayland) and not in a remote terminal without a display.
