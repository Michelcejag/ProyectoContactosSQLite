package com.example.contactosnuevo;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import java.util.ArrayList;

import tablas.Contacto;
import utilidades.Utilidades;

public class NuevoActivity extends AppCompatActivity {

    private EditText et_nuevo_nombre, et_nuevo_numero;
    private FloatingActionButton fab_nuevo;

    private Spinner spinnerContactos;
    private ArrayList <Contacto> list_ContactoConfianza;
    private ArrayList <String> lista ;

    ConexionSQLite conn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nuevo);
        Toolbar toolbar = findViewById(R.id.toolbar_nuevo);
        setSupportActionBar(toolbar);

        conn=new ConexionSQLite(getApplicationContext(),"bd_Contactos",null,1);

        //Llamada de variables
        et_nuevo_nombre=findViewById(R.id.et_nuevo_nombre);
        et_nuevo_numero=findViewById(R.id.et_nuevo_numero);
        fab_nuevo=findViewById(R.id.fab_nuevo);


        spinnerContactos = (Spinner)  findViewById(R.id.spinner_contactos); //Spinner



        Consultar_Contacto_Confianza();
        ArrayAdapter <CharSequence> adaptador= new ArrayAdapter(this,android.R.layout.simple_spinner_item,lista);

        spinnerContactos.setAdapter(adaptador);


        //Boton flotante
        fab_nuevo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Registrar();
                Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                startActivity(intent);
            }
        });
    }

  public void Registrar(){
      ConexionSQLite conn= new ConexionSQLite(this,"bd_Contactos",null,1);
      SQLiteDatabase db =conn.getWritableDatabase();
      ContentValues values= new ContentValues();

      values.put(Utilidades.CAMPO_NOMBRE, et_nuevo_nombre.getText().toString());
      values.put(Utilidades.CAMPO_TELEFONO, et_nuevo_numero.getText().toString());
     values.put(Utilidades.CAMPO_CONTACTO_CONFIANZA, spinnerContactos.getSelectedItem().toString());


        Long nombreResultante=db.insert(Utilidades.TAABLA_CONTACTO,Utilidades.CAMPO_NOMBRE,values);



db.close();

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
}
