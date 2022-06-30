/**
 * Université Côte d'Azur
 * IUT Côte d'Azur
 * Département Informatique
 *
 * @date MyLoggerConfiguration.java
 */
package useLogger;

import java.io.IOException;
import java.util.logging.*;


/**
 * Cette classe est utilisée pour configurer le logger
 *
 * @author Lacroix Baptiste
 */
public class MyLoggerConfiguration {
    // Creating a logger object.
    /**
     * Il crée un objet logger.
     */
    private static Logger LOGGER = Logger.getLogger(MyLoggerConfiguration.class.getName());
    /**
     * Création d'un gestionnaire de fichiers pour le fichier HTML.
     */
    private static FileHandler fileHandlerHTML = null;
    /**
     * Création d'un nouvel objet formateur.
     */
    private static MyHTMLFormatter formatter = null;
    /**
     * Création d'un objet gestionnaire de fichiers.
     */
    private static FileHandler fileHandler = null;

    /**
     * Cette méthode permet de configurer le logger
     *
     * @throws IOException Si le fichier est introuvable
     */
    private static void setup() {
        try {
            if (LOGGER == null || fileHandler == null) {
                LOGGER = Logger.getLogger(MyLoggerConfiguration.class.getName());
                fileHandlerHTML = new FileHandler("logs.html");
                formatter = new MyHTMLFormatter();
                fileHandler = new FileHandler("logs.txt");
            }
        } catch (SecurityException | IOException e) {
            e.printStackTrace();
        }
    }


    /**
     * La fonction prend un niveau et un message, puis enregistre le message dans le fichier journal
     *
     * @param level   Le niveau du message.
     * @param message Le message que vous souhaitez enregistrer.
     */
    public static void printLog(Level level, String message) {
        try {
            setup();
            // create a HTML formatter
            fileHandler.setFormatter(new SimpleFormatter());
            fileHandlerHTML.setFormatter(formatter);
            fileHandlerHTML.setLevel(Level.INFO);
            LOGGER.addHandler(fileHandlerHTML);
            LOGGER.setUseParentHandlers(false);
            fileHandlerHTML.setFormatter(formatter);
            LOGGER.log(level, message);
            LOGGER.addHandler(fileHandler);
        } catch (Exception exception) {
            LOGGER.log(Level.SEVERE, "Cannot read logger file", exception);
        }
    }
}
