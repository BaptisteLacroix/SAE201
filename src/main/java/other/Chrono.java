package other;

/**
 * Classe permettant de chronométrer
 */
public class Chrono {

    /**
     * Temps de départ
     */
    private long tempsDepart = 0;
    /**
     * Temps de fin
     */
    private long tempsFin = 0;
    /**
     * Temps mis en pause
     */
    private long pauseDepart = 0;
    /**
     * fin de la pause
     *
     */
    private long pauseFin = 0;
    /**
     * Durée du chronomètre
     */
    private long duree = 0;

    /**
     * La fonction start() définit l'heure de début sur l'heure actuelle et définit l'heure de fin, l'heure de début de la
     * pause, l'heure de fin de la pause et la durée sur 0
     */
    public void start() {
        tempsDepart = System.currentTimeMillis();
        tempsFin = 0;
        pauseDepart = 0;
        pauseFin = 0;
        duree = 0;
    }

    /**
     * Permet de mettre en pause le chronomètre
     */
    public void pause() {
        if (tempsDepart == 0) {
            return;
        }
        pauseDepart = System.currentTimeMillis();
    }

    /**
     * permet continuer le chronomètre ayant été mis en pause
     */
    public void resume() {
        if (tempsDepart == 0) {
            return;
        }
        if (pauseDepart == 0) {
            return;
        }
        pauseFin = System.currentTimeMillis();
        tempsDepart = tempsDepart + pauseFin - pauseDepart;
        tempsFin = 0;
        pauseDepart = 0;
        pauseFin = 0;
        duree = 0;
    }

    /**
     * Permet d'arréter le chronomètre
     */
    public void stop() {
        if (tempsDepart == 0) {
            return;
        }
        tempsFin = System.currentTimeMillis();
        duree = (tempsFin - tempsDepart) - (pauseFin - pauseDepart);
        tempsDepart = 0;
        tempsFin = 0;
        pauseDepart = 0;
        pauseFin = 0;
    }

    /**
     * Il renvoie la durée en secondes
     *
     * @return La durée en secondes.
     */
    public long getDureeSec() {
        return duree / 1000;
    }

    /**
     * Il renvoie la durée en millisecondes
     *
     * @return La durée de la chanson en millisecondes.
     */
    public long getDureeMs() {
        return duree % 1000;
    }

    /**
     * renvoie le temps sous format txt HH:MM:SS:MS
     *
     * @return Le temps en heures, minutes et secondes, plus les millisecondes.
     */
    public String getDureeTxt() {
        return timeToHMS(getDureeSec()) + " " + getDureeMs() + " ms";
    }

    /**
     * Prend un certain nombre de secondes et renvoie une chaîne de la forme "1 h 26 min 3 s"
     *
     * @param tempsS le temps en secondes
     * @return Un string
     */
    public static String timeToHMS(long tempsS) {

        // IN : (long) temps en secondes
        // OUT : (String) temps au format texte : "1 h 26 min 3 s"

        int h = (int) (tempsS / 3600);
        int m = (int) ((tempsS % 3600) / 60);
        int s = (int) (tempsS % 60);

        String r = "";

        if (h > 0) {
            r += h + " h ";
        }
        if (m > 0) {
            r += m + " min ";
        }
        if (s > 0) {
            r += s + " s";
        }

        return r;
    }

} // class Chrono