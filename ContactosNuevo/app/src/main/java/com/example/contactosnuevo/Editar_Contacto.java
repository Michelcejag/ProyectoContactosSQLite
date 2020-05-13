package com.example.contactosnuevo;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

import tablas.Contacto;
import utilidades.Utilidades;

public class Editar_Contacto extends AppCompatActivity {

private EditText nombre_editar, telefono_editar;
private Spinner contacto_Confianza;
private FloatingActionButton editar;
private ArrayList <Contacto> list_ContactoConfianza;
private ArrayList <String> lista ;
ConexionSQLite conn;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_editar);

        conn=new ConexionSQLite(getApplicationContext(),"bd_Contactos",null,1);

        nombre_editar=findViewById(R.id.editText_nombre_Actualizar);
        telefono_editar=findViewById(R.id.editText_num_Actualizar);
        editar=findViewById(R.id.ft_Editar_final);
        contacto_Confianza= (Spinner) findViewById(R.id.spinner_editar);


        Consultar_Contacto_Confianza();
        ArrayAdapter<CharSequence> adaptador= new ArrayAdapter(this,android.R.layout.simple_spinner_item,lista);
        contacto_Confianza.setAdapter(adaptador);

        Consulta();

        editar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nombree=getIntent().getStringExtra("nombre");

                Actualizar(nombree);

                Intent  intent= new Intent(getApplicationContext(),MainActivity.class);
                startActivity(intent);

            }
        });
    }

    private void Consulta() {
        SQLiteDatabase db = conn.getReadableDatabase();
        String nombre=getIntent().getStringExtra("nombre");
        Cursor cursor = db.rawQuery("SELECT * FROM "+ Utilidades.TAABLA_CONTACTO+" WHERE "
                +Utilidades.CAMPO_NOMBRE+" = '"+nombre+"'",null);
             cursor.moveToFirst();
            nombre_editar.setText(cursor.getString(0));
            telefono_editar.setText(cursor.getString(1));
    }

    private void Consultar_Contacto_Confianza(){
        SQLiteDatabase db=conn.getReadableDatabase();

        Contacto persona=null;
        list_ContactoConfianza=new ArrayList<Contacto>();
        //select*from contactos
        Cursor cursor = db.rawQuery("SELECT * FROM "+Utilidades.TAABLA_CONTACTO,null);

        while (cursor.moveToNext()){
            persona=new Contacto();
            persona.setNombre(cursor.getString(0));

            list_ContactoConfianza.add(persona);
        }

        obtenerLista();
    db.close();
    }

    private void obtenerLista() {

        lista=new ArrayList<String>();
        lista.add("Selecciona un contacto de confianza");

        for(int i=0; i<list_ContactoConfianza.size();i++){
            lista.add(list_ContactoConfianza.get(i).getNombre().toString());
        }

    }

    private void Actualizar(String nombre){

        SQLiteDatabase db=conn.getWritableDatabase();
        ContentValues values=new ContentValues();


String[] parametros={nombre};
        values.put(Utilidades.CAMPO_NOMBRE, nombre_editar.getText().toString());
        values.put(Utilidades.CAMPO_TELEFONO, telefono_editar.getText().toString());
        values.put(Utilidades.CAMPO_CONTACTO_CONFIANZA, contacto_Confianza.getSelectedItem().toString());

        db.update(Utilidades.TAABLA_CONTACTO,values,Utilidades.CAMPO_NOMBRE+"=?",parametros);



                db.close();

    }


}
