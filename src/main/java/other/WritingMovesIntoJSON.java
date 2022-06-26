package other;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.*;

/**
 * Classe permettant d'écrire dans un JSON les capacités ainsi que leur niveau d'acquisition pour chaques espèces
 *
 * @author Lacroix Baptiste
 */
public class WritingMovesIntoJSON {
    /**
     * Il écrit les mouvements dans un fichier JSON
     */
    public static void main(String[] args) throws IOException {
        WritingMovesIntoJSON writingMovesWLVL = new WritingMovesIntoJSON();
        Chrono chrono = new Chrono();
        chrono.start(); // démarrage du chrono
        writingMovesWLVL.writeIntoJson();
        chrono.stop(); // arrêt
        System.out.println(chrono.getDureeTxt()); // affichage au format "1 h 26 min 32 s"
    }

    /**
     * Cette fonction crée un fichier JSON contenant la liste de tous les coups de tous les Pokémon, avec leur niveau
     * d'acquisition
     */
    public void writeIntoJson() {
        int id = 1;
        try {
            FileWriter file = new FileWriter("listeCapacitesEspecesWLVL.json");
            JSONObject containerPrincipal = new JSONObject(); // (Objet) Conteneur princiapl ->  { Pokemon [
            JSONArray listeContainerSecondaire = new JSONArray();  // (Liste) Contient une liste de conteneurs secondaires
            while (id < 152) {
                String[] infoP = this.getAllURLPokemon(id);
                System.out.println("id : " + id + " Pokemon : " + infoP[1]);
                Map<String, String> dictionnaire = this.recupMoves(infoP[0]);
                // System.out.println("dictionnaire en sortie : " + dictionnaire);

                JSONObject containerTertiaire = new JSONObject(); // (Objet -> Liste) Conteneur tertiaire -> nom : dsds, moves [ {
                JSONArray listeContainerQuatrieme = new JSONArray(); // (Liste) Contient une liste de 2 éléments dont un conteneur et une liste
                JSONObject containerQuatrieme; // (Objet -> Liste) Conteneur tertiaire -> nom : dsds, moves [ {

                for (Map.Entry<String, String> values : dictionnaire.entrySet()) {
                    try {
                        FileReader fileCap = new FileReader("./resources/listeCapacites.csv");
                        BufferedReader reader = new BufferedReader(fileCap);
                        reader.readLine();
                        while (reader.ready()) {
                            Scanner scanner = new Scanner(reader.readLine()).useDelimiter(";");
                            String[] tab = scanner.nextLine().split(";");
                            if (tab[0].equals(values.getKey())) {
                                containerQuatrieme = new JSONObject();
                                containerQuatrieme.put("lvl", values.getValue());
                                containerQuatrieme.put("move", values.getKey());
                                listeContainerQuatrieme.add(containerQuatrieme);
                            }
                        }
                        fileCap.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                containerTertiaire.put("moves", listeContainerQuatrieme);
                containerTertiaire.put("nom", infoP[1]);
                listeContainerSecondaire.add(containerTertiaire);
                id++;
            }
            try {
                containerPrincipal.put("Pokemon", listeContainerSecondaire);
                System.out.println("JSON file created : " + containerPrincipal);
                file.write(containerPrincipal.toJSONString());
            } catch (IOException e) {
                e.printStackTrace();
            }
            file.close();
        } catch (IOException e1) {
            e1.printStackTrace();
        }
    }

    /**
     * Il obtient l'URL du pokemon et le nom du pokemon
     *
     * @param id L'identifiant du pokémon dont vous souhaitez obtenir les informations.
     * @return L'URL du pokémon et le nom du pokémon.
     */
    private String[] getAllURLPokemon(int id) {
        String[] pokemonurl = new String[2];
        try {
            JSONObject obj = requestHTTP("https://pokeapi.co/api/v2/pokemon-species/" + id + "/");
            JSONArray modules = (JSONArray) obj.get("varieties");
            JSONObject jsonObj = (JSONObject) modules.get(0);
            JSONObject obj2 = (JSONObject) jsonObj.get("pokemon");
            pokemonurl[0] = (String) obj2.get("url");

            JSONArray modules2 = (JSONArray) obj.get("names");
            JSONObject jsonObj2 = (JSONObject) modules2.get(4);
            pokemonurl[1] = (String) jsonObj2.get("name");
        } catch (ParseException | IOException e) {
            e.printStackTrace();
        }
        return pokemonurl;
    }

    /**
     * Il prend une URL comme paramètre et renvoie une Map<String, String> contenant le nom du mouvement comme clé et le
     * niveau auquel le mouvement est appris comme valeur
     *
     * @param url l'url du pokémon
     * @return Une Map avec le nom du coup comme clé et le niveau auquel le pokémon l'apprend comme valeur.
     */
    private Map<String, String> recupMoves(String url) {
        Map<String, String> dictionnaire = new HashMap<>();
        Map<Integer, String> moveName = new HashMap<>();
        List<String> lvl = new ArrayList<>();
        List<Integer> counter = new ArrayList<>();
        try {
            JSONObject obj = requestHTTP(url);
            JSONArray modules = (JSONArray) obj.get("moves");
            int id = 0;
            for (Object m : modules) {
                JSONObject jsonObj = (JSONObject) m;
                JSONObject obj2 = (JSONObject) new JSONParser().parse(String.valueOf(jsonObj.get("move")));
                moveName.put(id, this.recupFrenchMoves((String) obj2.get("url")));
                id++;
            }
            modules = (JSONArray) obj.get("moves");
            id = 0;
            for (Object m : modules) {
                JSONObject jsonObj = (JSONObject) m;
                JSONArray obj2 = (JSONArray) new JSONParser().parse(String.valueOf(jsonObj.get("version_group_details")));
                for (Object m2 : obj2) {
                    JSONObject jsonObj2 = (JSONObject) m2;
                    JSONObject jsonObj3 = (JSONObject) new JSONParser().parse(String.valueOf(jsonObj2.get("version_group")));
                    // System.out.println("name : " + jsonObj3.get("name"));
                    // System.out.println("equals : " + jsonObj3.get("name").equals("red-blue"));
                    if (jsonObj3.get("name").equals("red-blue")) {
                        JSONObject ob3 = (JSONObject) obj2.get(0);
                        // System.out.println("OB3 : " + ob3.get("level_learned_at"));
                        lvl.add(Long.toString((Long) ob3.get("level_learned_at")));
                        // System.out.println(lvl);
                        counter.add(id);
                    }
                }
                id++;
            }
        } catch (ParseException | IOException e) {
            e.printStackTrace();
        }
        // System.out.println(moveName.size() + " -> " + lvl.size());

        for (int i = 0; i < lvl.size(); i++) {
            // System.out.println("moveName.get(counter.get(i)) : " + moveName.get(counter.get(i)) + " -> lvl.get(i) : " + lvl.get(i));
            dictionnaire.put(moveName.get(counter.get(i)), lvl.get(i));
        }
        return dictionnaire;
    }

    /**
     * Il prend une url en paramètre et retourne le nom du move en français
     *
     * @param url l'url du move dont vous voulez obtenir le nom
     * @return Le nom du Move en français.
     */
    private String recupFrenchMoves(String url) {
        String moveName = "";
        try {
            JSONObject obj = requestHTTP(url);
            JSONArray modules = (JSONArray) obj.get("names");
            JSONObject jsonObj = (JSONObject) modules.get(3);
            moveName = (String) jsonObj.get("name");
        } catch (ParseException | IOException e) {
            e.printStackTrace();
        }
        return moveName;
    }

    /**
     * Il prend une URL sous forme de chaîne, ouvre une connexion à cette URL, lit la réponse et renvoie un JSONObject
     *
     * @param url L'URL du point de terminaison de l'API.
     * @return Un objet JSON
     */
    private JSONObject requestHTTP(String url) throws IOException, ParseException {
        URL http = new URL(url);
        HttpURLConnection hpCon = (HttpURLConnection) http.openConnection();
        hpCon.connect();

        BufferedReader streamReader = new BufferedReader(new InputStreamReader(hpCon.getInputStream()));
        StringBuilder responseStrBuilder = new StringBuilder();

        String inputStr;
        while ((inputStr = streamReader.readLine()) != null) {
            responseStrBuilder.append(inputStr);
        }
        inputStr = responseStrBuilder.toString();
        streamReader.close();

        return (JSONObject) new JSONParser().parse(inputStr);
    }
}
