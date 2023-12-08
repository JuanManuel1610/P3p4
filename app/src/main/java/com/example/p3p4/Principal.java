package com.example.p3p4;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import Almacen.Conexion;
import DTOs.Datos;

public class Principal extends AppCompatActivity {

    EditText txtnombre, txtedad, txtcorreo;
    GridView gvdatos;
    Button btnleer, btnguarda;
    List<Datos> ldatos = new ArrayList<>();

    ArrayList<String> contenidogv = new ArrayList<>();

    ArrayAdapter adaptador;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);

        txtnombre= findViewById(R.id.txtnombre);
        txtedad = findViewById(R.id.txtedad);
        txtcorreo = findViewById(R.id.txtcorreo);
        btnleer = findViewById(R.id.btnlee);
        btnguarda = findViewById(R.id.btnguardar);
        gvdatos = findViewById(R.id.gvdatos);



        btnleer.setOnClickListener(v -> {
            lee();
        });
        btnguarda.setOnClickListener(v -> {
            grabar();
        });

        gvdatos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (position>2 && (position % 3==0)){
                    Toast.makeText(Principal.this,"Selecciono: " + contenidogv.get(position), Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
    private void lee(){
        String cadena="";
        Conexion conexion = new Conexion();
        if (conexion.Leer(this)){
            ldatos = conexion.getDatos();
            contenidogv.add("Nombre");
            contenidogv.add("Edad");
            contenidogv.add("Correo");
            if (ldatos.size()>0){
                for (Datos datos : ldatos){
                   contenidogv.add(datos.getNombre());
                   contenidogv.add(String.valueOf(datos.getEdad()));
                   contenidogv.add(datos.getCorreo());
                }
            }
            adaptador = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, contenidogv);
            gvdatos.setAdapter(adaptador);
        }
    }

    private void grabar(){
        Datos datos = new Datos(txtnombre.getText().toString(),txtcorreo.getText().toString(), Integer.parseInt(txtedad.getText().toString()));
        ldatos.add(datos);
        Conexion conexion = new Conexion();
        if (conexion.Grabar(this,ldatos)) {
            Toast.makeText(this, "Se grabo conexito", Toast.LENGTH_SHORT).show();
        }else {
            Toast.makeText(this, "Error al grabar", Toast.LENGTH_SHORT).show();
        }
    }
}