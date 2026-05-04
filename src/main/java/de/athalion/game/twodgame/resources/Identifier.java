package de.athalion.game.twodgame.resources;

import de.athalion.game.twodgame.logs.Logger;
import de.athalion.game.twodgame.utility.Requirements;

import java.io.InputStream;
import java.net.URL;

/**
 * Used to identify resources. The {@link Identifier#forPath(String)} method is used to retrieve an identifier
 * for a given path. This method also checks if the resource exists at that location and throws an exception otherwise.
 * <h1>Example</h1>
 * To get an identifier for /resources/lang/de.json:
 * <pre><code>
 *     Identifier identifier = Identifier.forPath("/lang/de.json");
 * </code></pre>
 */
public class Identifier {

    final String path;

    private Identifier(String path) {
        this.path = path;
    }

    private boolean exists() {
        return getClass().getResource(path) != null;
    }

    /**
     * Returns an {@link InputStream} for the path of this identifier.
     * @return An input stream for the file
     */
    public InputStream stream() {
        return getClass().getResourceAsStream(path);
    }

    /**
     * Returns a {@link URL} for the path of this identifier.
     * @return A URL for the file
     */
    public URL getResource() {
        return getClass().getResource(path);
    }

    /**
     * Returns the path which was used to crete the identifier.
     * @return the path of the identifier
     */
    public String getPath() {
        return path;
    }

    /**
     * Creates an identifier for a specified resource and checks if the file exists.
     * If the resource is not found the method will print an error.
     * <br>
     * {@code [12:00] [ERROR] Could not identify resource: /textures/apple.png!}
     * @param path the path of the resource
     * @return the identifier for the given path
     */
    public static Identifier forPath(String path) {
        Identifier identifier = new Identifier(path);
        Requirements.requires(identifier.exists(), "Could not identify resource: " + path + "!");
        Logger.log("Found resource: " + path + "!");
        return identifier;
    }

}
