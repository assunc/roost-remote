import org.json.*;

import java.io.*;
import java.net.*;

//helper class to interact with the database
public class DB {
    public static JSONArray makeGETRequest(String urlExtensions) {
        BufferedReader rd;
        StringBuilder sb;
        String line;
        try {
            URL url = new URL("https://studev.groept.be/api/a23ib2d05/"+urlExtensions);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.connect();
            rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            sb = new StringBuilder();
            while ((line = rd.readLine()) != null) {
                sb.append(line).append('\n');
            }
            conn.disconnect();
            return new JSONArray(sb.toString());
        } catch (IOException e){
            e.printStackTrace();
        }
        return new JSONArray("");
    }
}
