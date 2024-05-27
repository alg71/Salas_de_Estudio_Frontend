package es.albertolopez.salas_estudio;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements OnMapReadyCallback {

    private TextView tvAforo;
    private Spinner spinnerSalas;
    private GoogleMap mMap;
    private List<Sala> listaSalas = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Se agregan salas manualmente a la lista
        listaSalas.add(new Sala(1, "SME Alquerías - Aforo total: 50", 50));
        listaSalas.add(new Sala(2, "SME Santomera - Aforo total: 50", 50));
        listaSalas.add(new Sala(3, "SME Cobatillas - Aforo total: 40", 40));

        tvAforo = findViewById(R.id.tvAforo);
        spinnerSalas = findViewById(R.id.spinnerSalas);

        // Se obtiene una referencia al fragmento del mapa
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        // Se crea un ArrayAdapter para el Spinner con los nombres de las salas
        ArrayAdapter<String> adaptador = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, obtenerNombresSalas());
        adaptador.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerSalas.setAdapter(adaptador);

        // Configurar el listener para el Spinner
        spinnerSalas.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // Obtención del ID de la sala seleccionada
                int idSala = listaSalas.get(position).getId();
                // Se crea una instancia de ObtenerAforo y se ejecuta con el ID de la sala
                new ObtenerAforo(tvAforo).execute(idSala);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                /* Solo se usaría este método en el caso de no seleccionar ninguna sala en la lista Spinner.
                 No es nuestro caso. No se necesita realizar ninguna acción*/
            }
        });

    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        mMap = googleMap;

        // Configuración de la posición inicial del mapa (Murcia)
        LatLng murcia = new LatLng(37.9922, -1.1307);
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(murcia, 10));

        // Controles de zoom en el mapa
        mMap.getUiSettings().setZoomControlsEnabled(true);

        // Añadir marcadores para las salas
        LatLng sala1 = new LatLng(38.02049, -1.03335);
        mMap.addMarker(new MarkerOptions().position(sala1).title("SME Alquerías - Aforo total: 50"));

        LatLng sala2 = new LatLng(38.06215, -1.04843);
        mMap.addMarker(new MarkerOptions().position(sala2).title("SME Santomera - Aforo total: 50"));

        LatLng sala3 = new LatLng(38.05466, -1.08094);
        mMap.addMarker(new MarkerOptions().position(sala3).title("SME Cobatillas - Aforo total: 40"));

        // Configuración de un listener para los marcadores
        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(com.google.android.gms.maps.model.Marker marker) {
                // Obtener el título del marcador
                String titulo = marker.getTitle();

                // Buscar la sala correspondiente al título del marcador
                Sala salaSeleccionada = null;
                for (Sala sala : listaSalas) {
                    if (titulo.contains(sala.getNombre())) {
                        salaSeleccionada = sala;
                        break;
                    }
                }

                // Mostrar el aforo correspondiente en el TextView tvAforo
                if (salaSeleccionada != null) {
                    int salaId = salaSeleccionada.getId();
                    // Crear una instancia de ObtenerAforoTask y ejecutarla con el ID de la sala
                    new ObtenerAforo(tvAforo).execute(salaId);
                } else {
                    tvAforo.setText("Aforo no disponible para esta sala");
                }

                return false;
            }
        });

    }

    // Método para obtener los nombres de las salas
    private List<String> obtenerNombresSalas() {
        List<String> nombres = new ArrayList<>();
        for (Sala sala : listaSalas) {
            nombres.add(sala.getNombre());
        }
        return nombres;
    }
}
