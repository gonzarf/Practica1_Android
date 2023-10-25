package com.example.introduccion;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.Ach2290.firstdesign.R;
import com.example.introduccion.Database.DatabaseAux;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class Show extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show);
        showElements();
    }

    public void changetoMain(View view){
        Intent nIntent = new Intent(Show.this, MainActivity.class);
        startActivity(nIntent);
    }

    void showElements(){
        SQLiteDatabase db = new DatabaseAux(this).getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT * FROM users", null);
        LinearLayout layout = findViewById(R.id.fillContentShow); //CREAMOS UN LAYOUT DONDE SE VAN A MOSTRAR TODOS LOS DATOS DE LA BD

        if(cursor.moveToFirst()){
            do {
                int id = cursor.getInt(0);
                String name = cursor.getString(1);
                String email = cursor.getString(2);

                //CREAMOS UN TEXTVIEW DONDE SE MUIESTREN LOS DATOS
                TextView data = new TextView(this);
                data.setPadding(100,0,100,0); //PARA QUE EL TEXTO DENTRO DEL TEXTVIEW SALGA CENTRADO
                data.setText("Nombre: " + name + "   Email: " + email);
                layout.addView(data); //INTRODUCIMOS EL TEXTVIEW CREADO EN EL LAYOUT

            }while(cursor.moveToNext());
        }
        db.close();
    }

    public void delete(View view){

        TextView nameView = findViewById(R.id.deleteName);
        TextView emailView = findViewById(R.id.deleteEmail);

        String nameString = nameView.getText().toString();
        String emailString = emailView.getText().toString();

       /* SQLiteDatabase db = new DatabaseAux(this).getWritableDatabase();

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

        FirebaseFirestore firestoredb = FirebaseFirestore.getInstance();
        Map<String, Object> users = new HashMap<>();
        users.put("correo", "g@g.com" );
        users.put("nombre", "alv" );
        firestoredb.collection("SoccerScout").document(nameString)
                .set(users)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Log.d("DEBUG", "TODO OK");

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