package mx.com.danisable.cosasfavoritas;

import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class Reg extends AppCompatActivity {

    EditText etName;
    EditText etPaterno;
    EditText etMaterno;
    EditText etHobbie;
    EditText etPasatiempo;
    EditText etUser;
    EditText etPass;
    EditText etPass2;

    String nombre;
    String paterno;
    String materno;
    String hobbie;
    String pasat;
    String user;
    String pass;
    String pass2;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reg);

        etName = (EditText) findViewById(R.id.etNombre);
        etPaterno = (EditText) findViewById(R.id.etPaterno);
        etMaterno = (EditText) findViewById(R.id.etMaterno);
        etHobbie = (EditText) findViewById(R.id.etHobbie);
        etPasatiempo = (EditText) findViewById(R.id.etPasatimepo);
        etUser = (EditText) findViewById(R.id.etUser);
        etPass = (EditText) findViewById(R.id.etPass);
        etPass2 = (EditText) findViewById(R.id.etPass2);

    }

    public void saveInfo(View v){

        //obtengo los datos y los paso a cadenas
        nombre = etName.getText().toString();
        paterno = etPaterno.getText().toString();
        materno = etMaterno.getText().toString();
        hobbie = etHobbie.getText().toString();
        pasat = etPasatiempo.getText().toString();
        user = etUser.getText().toString();
        pass = etPass.getText().toString();
        pass2 = etPass2.getText().toString();

        //validamos que los campos no esten vacios
        if(nombre.equals("")){
            Toast.makeText(getApplicationContext(),"No puedes dejar campos vacios",Toast.LENGTH_SHORT).show();
        }else if (paterno.equals("")){
            Toast.makeText(getApplicationContext(),"No puedes dejar campos vacios",Toast.LENGTH_SHORT).show();
        }else if (materno.equals("")){
            Toast.makeText(getApplicationContext(),"No puedes dejar campos vacios",Toast.LENGTH_SHORT).show();
        }else if (hobbie.equals("")){
            Toast.makeText(getApplicationContext(),"No puedes dejar campos vacios",Toast.LENGTH_SHORT).show();
        }else if (pasat.equals("")){
            Toast.makeText(getApplicationContext(),"No puedes dejar campos vacios",Toast.LENGTH_SHORT).show();
        }else if (user.equals("")){
            Toast.makeText(getApplicationContext(),"No puedes dejar campos vacios",Toast.LENGTH_SHORT).show();
        }else if (pass.equals("")){
            Toast.makeText(getApplicationContext(),"No puedes dejar campos vacios",Toast.LENGTH_SHORT).show();
        }else if (pass2.equals("")){
            Toast.makeText(getApplicationContext(),"No puedes dejar campos vacios",Toast.LENGTH_SHORT).show();
        }else{

            if (pass.equals(pass2)){ //evalua si las contraseñas coinciden

                if(evaluaUsuario()) {

                    if(LoginActivity.db != null){
                        String sql = "INSERT INTO usuarios (nombre, a_paterno, a_materno, hobbies, pasatiempos, usuario, password) " +
                                     "VALUES ('"+nombre+"', '"+paterno+"', '"+materno+"', '"+hobbie+"', '"+pasat+"', '"+user+"', '"+pass+"');   ";
                        LoginActivity.db.execSQL(sql);
                        //db.close();
                    }
                    finish();
                    Toast.makeText(getApplicationContext(),"Se guardo correctamente al usuario",Toast.LENGTH_LONG).show();
                }
            }else{
                Toast.makeText(getApplicationContext(),"Las contraseñas no coinciden",Toast.LENGTH_SHORT).show();
            }
        }
    }

    public boolean evaluaUsuario(){

        String u;
        //validamos que el usuario no exista en la bd
        Cursor c = LoginActivity.db.rawQuery("SELECT usuario FROM usuarios;", null);
        if (c.moveToFirst()) {
            do {
                u = c.getString(0);
                if(user.equals(u)){
                    Toast.makeText(getApplicationContext(),"Ya existe ese usuario, Intenta con otro",Toast.LENGTH_SHORT).show();
                    return false;
                }
            } while (c.moveToNext());
        }

        return true;
    }
}
