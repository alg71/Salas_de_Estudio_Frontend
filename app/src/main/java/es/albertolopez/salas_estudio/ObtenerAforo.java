package es.albertolopez.salas_estudio;

import android.os.AsyncTask;
import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class ObtenerAforo extends AsyncTask<Integer, Void, Integer> {

    private static final String TAG = "ObtenerAforo";
    private TextView tvAforo;

    private EditText editTextDireccionIP;

    public ObtenerAforo(TextView tvAforo) {
        this.tvAforo = tvAforo;
    }

    @Override
    protected Integer doInBackground(Integer... ids) {
        if (ids.length == 0) {
            return null;
        }

        Integer salaId = ids[0];
        Integer aforo = null;


        HttpURLConnection urlConnection = null;
        try {
            URL url = new URL("http://192.168.12.191:8080/api/salas/" + salaId + "/aforo");
            URL url2 = new URL("http://192.168.18.107:8080/api/salas/" + salaId + "/aforo");
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");

            int responseCode = urlConnection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                BufferedReader reader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
                StringBuilder response = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    response.append(line.trim());
                }
                reader.close();

                // Convertimos la respuesta a un entero
                try {
                    aforo = Integer.parseInt(response.toString());
                } catch (NumberFormatException e) {
                    Log.e(TAG, "Error al convertir la respuesta del servidor a entero", e);
                }
            } else {
                Log.e(TAG, "Error al obtener el aforo. Código de respuesta: " + responseCode);
            }
        } catch (IOException e) {
            Log.e(TAG, "Error al obtener el aforo", e);
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
        }

        return aforo;
    }


    @Override
    protected void onPostExecute(Integer aforo) {
        if (aforo != null) {
                tvAforo.setText(String.valueOf(aforo));
        } else {
            tvAforo.setText("0");
            Log.e(TAG, "No se pudo obtener el aforo");
        }
    }


}
