/**
 * Université Côte d'Azur
 * IUT Côte d'Azur
 * Département Informatique
 *
 * @date MyHTMLFormatter.java
 */
package useLogger;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Formatter;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.LogRecord;

/**
 * C'est un formateur qui formate le HTML
 *
 * @author Lacroix Baptiste and Vidal Théo
 */
class MyHTMLFormatter extends Formatter {
    // this method is called for every log records

    /**
     * La fonction prend un enregistrement de journal, le formate et renvoie une chaîne
     *
     * @param rec Enregistrement de journal à formater.
     * @return Une chaîne formatée pour être affichée dans la page HTML.
     */
    @Override
    public String format(LogRecord rec) {
        StringBuffer buf = new StringBuffer(1000);
        // colorize any levels >= WARNING in red
        buf.append("<div class=\"card\">");
        if (rec.getLevel().intValue() >= Level.WARNING.intValue()) {
            buf.append("\t<header style=\"color:red\">");
            buf.append("<b>");
            buf.append(rec.getLevel());
            buf.append("</b>");
        } else {
            buf.append("\t<header>");
            buf.append(rec.getLevel());
        }
        buf.append("</header>\n");
        buf.append("\t<span>");
        buf.append("Message: " + formatMessage(rec));
        buf.append("</span>\n");
        buf.append("\t<footer style=\"color:blue\">");
        buf.append("Time: " + calcDate(rec.getMillis()));
        buf.append("</footer>\n");
        buf.append("</div>\n");
        buf.append("<br>");
        return buf.toString();
    }


    /**
     * Il prend une longue valeur représentant les millisecondes depuis l'époque et renvoie une chaîne représentant la date
     * au format MM/jj, aaaa HH:mm
     *
     * @param millisecs Le temps en millisecondes
     * @return La date au format MM/jj, aaaa HH:mm
     */
    private String calcDate(long millisecs) {
        SimpleDateFormat date_format = new SimpleDateFormat("MM/dd, yyyy HH:mm");
        Date resultdate = new Date(millisecs);
        return date_format.format(resultdate);
    }

    // this method is called just after the handler using this
    // formatter is created
    /**
     * Il renvoie l'en-tête de la page HTML.
     *
     * @param h Gestionnaire utilisé pour traiter la demande.
     * @return Le code HTML de l'en-tête de la page.
     */
    @Override
    public String getHead(Handler h) {
        return "<!DOCTYPE html>\n<head>\n"
                + "<style>"
                + ".card {\n" +
                "            border: solid 1px black;\n" +
                "            margin: 5px 5px 5px 5px;\n" +
                "        }"
                + "</style>\n"
                + "</head>\n"
                + "<body>\n"
                + "<h1>" + (new Date()) + "</h1>\n"
                + "<section border=\"1\" >\n";
    }

    // this method is called just after the handler using this
    // formatter is closed
    /**
     * La fonction `getTail` renvoie le code HTML qui sera ajouté à la fin du fichier journal
     *
     * @param h Le gestionnaire qui appelle le formateur.
     * @return La fin du document HTML.
     */
    @Override
    public String getTail(Handler h) {
        return "</section>\n</body>\n</html>";
    }
}