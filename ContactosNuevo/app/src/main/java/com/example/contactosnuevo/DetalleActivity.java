package com.example.contactosnuevo;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import tablas.Contacto;
import utilidades.Utilidades;

public class DetalleActivity extends AppCompatActivity {

    private TextView tv_detalle_nombre, tv_detalle_numero, tv_detalle_contactoConfianza;
    private Contacto cont;
    private FloatingActionButton editar_clas;

    ConexionSQLite conn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle);
        Toolbar toolbar = findViewById(R.id.toolbar_detalle);
        setSupportActionBar(toolbar);

        conn=new ConexionSQLite(getApplicationContext(),"bd_Contactos",null,1);



        //Inicializar variables
        tv_detalle_nombre=findViewById(R.id.tv_detalle_nombre);
        tv_detalle_numero=findViewById(R.id.tv_detalle_numero);
        tv_detalle_contactoConfianza=findViewById(R.id.tv_detalle_contactoConfianza);
        editar_clas=findViewById(R.id.ft_Editar);

        cont= new Contacto();


       Consulta();

       editar_clas.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {

               String nombre=getIntent().getStringExtra("nombre");

               Intent detalled = new Intent(getApplicationContext(),Editar_Contacto.class);
               detalled.putExtra("nombre",nombre.toString());
               startActivity(detalled);
           }
       });

    }

    private void Consulta() {
        SQLiteDatabase db = conn.getReadableDatabase();
        String nombre=getIntent().getStringExtra("nombre");
        Cursor cursor = db.rawQuery("SELECT * FROM "+ Utilidades.TAABLA_CONTACTO+" WHERE "
                +Utilidades.CAMPO_NOMBRE+" = '"+nombre+"'",null);

        while (cursor.moveToNext()){
            tv_detalle_nombre.setText(cursor.getString(0));
            tv_detalle_numero.setText(cursor.getString(1));
            tv_detalle_contactoConfianza.setText(cursor.getString(2));

        }

        db.close();


    }



}
