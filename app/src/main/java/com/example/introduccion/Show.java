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






}