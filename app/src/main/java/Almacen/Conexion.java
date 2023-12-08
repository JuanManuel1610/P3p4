package Almacen;

import android.app.Activity;
import android.content.Context;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;

import Conversiones.Conversion;
import DTOs.Datos;

public class Conexion {
    private final String NomArch="Datos.txt";
    String cadena;
     Conversion conversion= new Conversion();
     List<Datos> ldatos = new ArrayList<>();
    public boolean Grabar(Context context, List<Datos> datos){
        try {
            OutputStreamWriter archivo = new OutputStreamWriter(context.openFileOutput(NomArch, Activity.MODE_PRIVATE));
            for (Datos dato : datos){
                cadena = conversion.Cjson(dato);
                archivo.write(cadena+"\n");
            }
            archivo.flush();
            archivo.close();
        }catch (Exception ex){
            return false;
        }
        return true;

    }
    public boolean Leer(Context context){
        try{
            InputStreamReader archivo = new InputStreamReader(context.openFileInput(NomArch));
            BufferedReader br = new BufferedReader(archivo);
            while ((cadena = br.readLine())!=null){
                ldatos.add(conversion.Cdto(cadena));
            }
        }catch (Exception ex){
            return false;
        }
        return true;
    }

    public List<Datos>  getDatos(){
        return ldatos;
    }
}
