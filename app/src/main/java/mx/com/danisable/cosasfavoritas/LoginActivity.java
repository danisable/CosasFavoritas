package mx.com.danisable.cosasfavoritas;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Date;
import java.text.SimpleDateFormat;

public class LoginActivity extends AppCompatActivity {

    //referencias elementos
    EditText mUser;
    EditText mPassword;
    public static String u;

    //referencia a los elementos de la base de datos
    BDFavoritos favoritos;
    static SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mUser = (EditText) findViewById(R.id.etUsuario);
        mPassword = (EditText) findViewById(R.id.etPassword);


        //Creacion de la base de datos (si no existe)
        favoritos = new BDFavoritos(this, "DBFavoritos", null, 1);
        //se crea writable para poder escribir sobre ella
        db = favoritos.getWritableDatabase();

    }

    public static String nombre= null;
    public static String aPaterno= null;
    public static String aMaterno= null;
    public static String hobbies= null;
    public static String pasatiempos= null;



    public void onClickIniciarSesion(View v){

        //campos de la interfaz de usuario
        String user = mUser.getText().toString();
        String pass = mPassword.getText().toString();
        //campos de la consulta de la bd
        String usuario;
        String password;

        //Consulta de datos para traer el registro dependiendo del usuario
        Cursor c = db.rawQuery("SELECT * FROM usuarios WHERE usuario = '"+ user +"';", null);
        if (c.moveToFirst()) {
            //Recorremos el cursor hasta que no haya más registros
            do {
                //este campo almacena en la variable u el id de usuario que inicio sesion, se utiliza mas adelante para almacenar sus registros
                u = c.getString(0);
                //obtenemos todos los valores de este usuario para la pantalla YO
                nombre = c.getString(1);
                aPaterno = c.getString(2);
                aMaterno = c.getString(3);
                hobbies = c.getString(4);
                pasatiempos = c.getString(5);
                //obtenemos los campos de usuario y contraseña
                usuario= c.getString(6);
                password= c.getString(7);
            } while(c.moveToNext());
            //evaluamos si la contraseña es igual a la que corresponde con el usuario en el registro devuelto por la consulta
            if(pass.equals(password)){
                //Si al contraseña es exactamente igual a la del registro que nos devolvio la consulta, se le da acceso al usuario
                Intent principalA = new Intent(this, MainActivity.class);
                startActivity(principalA);

            }else{
                Toast.makeText(getApplicationContext(), "Contraseña incorrecta", Toast.LENGTH_SHORT).show();
            }
        }else{
            Toast.makeText(getApplicationContext(), "No existe ese usuario", Toast.LENGTH_SHORT).show();
        }

    }

    public void createReg(View v){

        Intent regA = new Intent(this, Reg.class);
        startActivity(regA);

    }

}

