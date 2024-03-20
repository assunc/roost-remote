import org.json.*;

import java.io.*;
import java.net.*;

public class DB {
    public static String makeGETRequest(String urlExtensions) {
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
            return sb.toString();
        } catch (IOException e){
            e.printStackTrace();
        }
        return "";
    }

    public static Object getValue(String JSON, String value) {
        return new JSONArray(JSON).getJSONObject(0).get(value);
    }
}
