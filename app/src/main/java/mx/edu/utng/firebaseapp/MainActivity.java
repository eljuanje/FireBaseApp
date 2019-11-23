package mx.edu.utng.firebaseapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {
    EditText etxTema;
    Spinner spiCarrera, spiMateria;
    Button btnRes;
    private DatabaseReference refDiario; //Referencia a FireBase

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //FireBase toma como modelo la clase especificada
        refDiario = FirebaseDatabase.getInstance().getReference("Clase");

        //Asociar los objetos logicos con la vista
        etxTema = (EditText)findViewById(R.id.etxTema);
        spiCarrera = (Spinner)findViewById(R.id.spinCarrera);
        spiMateria = (Spinner)findViewById(R.id.spinMaterias);
        btnRes = (Button)findViewById(R.id.btnRegistrar);

        btnRes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                registrarClase();
            }
        });
    }

    //Crear método de registrarClase
    public void registrarClase(){
        String carrera = spiMateria.getSelectedItem().toString();
        String materia = spiMateria.getSelectedItem().toString();
        String tema = etxTema.getText().toString();

        if(!TextUtils.isEmpty(tema)){
            //Se genera una nueva id
            String id  = refDiario.push().getKey();
            //Definir objeto
            Clase leccion = new Clase(id, carrera, materia, tema);
            //Ingresar a la BD de FireBase
            refDiario.child("Lecciones").child(id).setValue(leccion);
            Toast.makeText(this, "Clase Registrada", Toast.LENGTH_LONG).show();

        }else{
            Toast.makeText(this, "Introducir Tema", Toast.LENGTH_LONG).show();
        }
    }
}
