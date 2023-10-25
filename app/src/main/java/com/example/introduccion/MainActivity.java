package com.example.introduccion;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.introduccion.Database.DatabaseAux;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
       /* Button buttonTest = findViewById(R.id.BotonEntrar);
        buttonTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(v.getContext(), "BIENVENIDO A SOCCERSCOUT", Toast.LENGTH_SHORT).show();

            }

        });*/

    }

    public void diceAdios(View view){
        // leo lo que ha escrito el ususario
        TextView textocorreo = findViewById(R.id.textoCorreo);
        String correo = textocorreo.getText().toString();
        Toast.makeText(this, "Hola" + correo, Toast.LENGTH_SHORT).show();
    }

    public void changetoInsert(View view){
        Intent nIntent = new Intent(MainActivity.this, Principal.class);
        startActivity(nIntent);
    }

    public void changetoShow(View view){
        Intent nIntent = new Intent(MainActivity.this, Show.class);
        startActivity(nIntent);
    }

    public void insertValues(View v){
        TextView nameTextView = findViewById(R.id.textoCorreo);
        TextView emailTextView = findViewById(R.id.textoContra);

        String nameString = nameTextView.getText().toString();
        String emailString = nameTextView.getText().toString();

        DatabaseAux aux = new DatabaseAux(MainActivity.this);
        SQLiteDatabase db = aux.getWritableDatabase();

        if(db != null && !nameString.isEmpty() && !emailString.isEmpty()) {
            ContentValues values = new ContentValues();
            values.put("name", nameString);
            values.put("email", emailString);

            long res = db.insert("users", null, values);
            if(res >= 0){
                Toast.makeText(this, "Insertado correctamente", Toast.LENGTH_SHORT).show();
                nameTextView.setText("");
                emailTextView.setText("");
            }else{
                Toast.makeText(this, "Error al insertar dartos", Toast.LENGTH_SHORT).show();
            }
            db.close();
        }
    }

    public void delete(View view){

        TextView nameView = findViewById(R.id.textoCorreo);
        TextView emailView = findViewById(R.id.textoContra);

        String nameString = nameView.getText().toString();
        String emailString = emailView.getText().toString();

        /*SQLiteDatabase db = new DatabaseAux(this).getWritableDatabase();

        if (db != null && !nameString.isEmpty() && !emailString.isEmpty()){
            long res = db.delete(
                    "users",
                    "name = '" + nameString + "' and email = '" + emailString + "'",
                    null);

            if (res > 0){
                Toast.makeText(this, "SE HAN BORADO LOS DATOS CORRECTAMENTE", Toast.LENGTH_SHORT).show();

                nameView.setText(" ");
                emailView.setText(" ");
            }else{
                Toast.makeText(this, "SE HA PRODUCIDO UN ERROR", Toast.LENGTH_SHORT).show();
            }
        }else {
            Toast.makeText(this, "Error al acceder. La base de datos está vacía", Toast.LENGTH_SHORT).show();
        }
        db.close();*/

        FirebaseFirestore firestoreDb = FirebaseFirestore.getInstance();
        Map<String, String> users = new HashMap<>();
        users.put("correo", emailString);
        users.put("nombre", nameString);

        firestoreDb.collection("SoccerScout").document(nameString)
                .set(users)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Log.d("DEBUG","TODO OK");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d("ERROR", e.getMessage());
                    }
                });

    }

}