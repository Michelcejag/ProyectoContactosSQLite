package com.example.contactosnuevo;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import tablas.Contacto;
import utilidades.Utilidades;

public class MainActivity extends AppCompatActivity {
   private ListView lv_main_contactos;
   private FloatingActionButton fab_main_nuevo;
    ArrayList <String> lista_Contactos_String;
    ArrayList <Contacto> lista_Contacto_Class;
    private TextView tv_nombre_Contacto;

    private Contacto cont;

    ConexionSQLite conn;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar_main);
        setSupportActionBar(toolbar);


        conn=new ConexionSQLite(getApplicationContext(),"bd_Contactos",null,1);


        fab_main_nuevo=findViewById(R.id.fab_main_nuevo);
        lv_main_contactos=(ListView) findViewById(R.id.lv_main_contactos);
        cont=new Contacto();

        consulta_Lista_Contactos();

        obtenerLista();

        ArrayAdapter adapter= new ArrayAdapter(this,android.R.layout.simple_list_item_1,lista_Contactos_String);
        lv_main_contactos.setAdapter(adapter);

        lv_main_contactos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long id) {


                Intent detalle = new Intent(getApplicationContext(),DetalleActivity.class);
                detalle.putExtra("nombre",lista_Contacto_Class.get(i).getNombre());
                startActivity(detalle);
            }
        });


        fab_main_nuevo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent nuevo = new Intent(MainActivity.this, NuevoActivity.class);
                startActivity(nuevo);//Vista para registrar un contacto
            }
        });


    }

    private void consulta_Lista_Contactos(){
        SQLiteDatabase db=conn.getReadableDatabase();

        Contacto contacto=null;

        lista_Contacto_Class = new ArrayList<Contacto>();
        //consulta

        Cursor cursor= db.rawQuery("SELECT * FROM "+ Utilidades.TAABLA_CONTACTO,null);

        while (cursor.moveToNext()){
            contacto=new Contacto();
            contacto.setNombre(cursor.getString(0));
            lista_Contacto_Class.add(contacto);
        }

    }

    private void obtenerLista() {
        lista_Contactos_String = new ArrayList<String>();

        for(int i=0;i<lista_Contacto_Class.size();i++){
            lista_Contactos_String.add(lista_Contacto_Class.get(i).getNombre());
        }
    }

}
