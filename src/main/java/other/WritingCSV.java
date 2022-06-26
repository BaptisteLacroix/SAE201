package other;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Classe permettant d'écrire dans un csv les capacités disponibles pour chaque espèce
 *
 * @author Lacroix Baptiste
 */
public class WritingCSV {
    /**
     * Il prend le nom du pokémon et le nom des mouvements qui lui sont associés et les écrit dans un fichier csv
     */
    public void writeIntoCsv() {
        int id = 1;
        try {
            OutputStream os = new FileOutputStream("listeCapacitesEspeces.csv");
            PrintWriter writer = new PrintWriter(new OutputStreamWriter(os, StandardCharsets.UTF_8));
            while (id < 152) {
                String[] infoP = this.getAllURLPokemon(id);
                System.out.println("id : " + id + " Pokemon : " + infoP[1]);
                List<String> nameMove = this.recupMoves(infoP[0]);
                StringBuffer csvData = new StringBuffer("");
                csvData.append(infoP[1]);
                writer.write(csvData.toString());
                for (String s : nameMove) {
                    try {
                        FileReader file = new FileReader("./resources/listeCapacites.csv");
                        BufferedReader reader = new BufferedReader(file);
                        reader.readLine();
                        while (reader.ready()) {
                            Scanner scanner = new Scanner(reader.readLine()).useDelimiter(";");
                            String[] tab = scanner.nextLine().split(";");
                            if (tab[0].equals(s)) {
                                csvData = new StringBuffer("");
                                csvData.append(';');
                                csvData.append(tab[0]);
                                csvData.append(';');
                                writer.write(csvData.toString());
                            }
                        }
                        file.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                writer.write("\n");
                id++;
            }
            writer.close();
            System.out.println("Done");
        } catch (FileNotFoundException e1) {
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
     * Il prend une URL en paramètre et renvoie une liste de chaînes
     *
     * @param url l'url du pokémon
     * @return Une liste des Moves
     */
    private List<String> recupMoves(String url) {
        List<String> moveName = new ArrayList<>();
        try {
            JSONObject obj = requestHTTP(url);
            JSONArray modules = (JSONArray) obj.get("moves");
            for (Object m : modules) {
                JSONObject jsonObj = (JSONObject) m;
                JSONObject obj2 = (JSONObject) new JSONParser().parse(String.valueOf(jsonObj.get("move")));
                moveName.add(this.recupFrenchMoves((String) obj2.get("url")));
            }
        } catch (ParseException | IOException e) {
            e.printStackTrace();
        }
        return moveName;
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
