package mx.com.danisable.cosasfavoritas;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Daniel on 14/05/2016.
 */
public class BDFavoritos extends SQLiteOpenHelper {

    String CreateTableUsuarios = "CREATE TABLE usuarios(\n" +
            "  id_usuario INTEGER PRIMARY KEY AUTOINCREMENT,\n" +
            "  nombre text,\n" +
            "  a_paterno text,\n" +
            "  a_materno text,\n" +
            "  hobbies text,\n" +
            "  pasatiempos text,\n" +
            "  usuario text,\n" +
            "  password text\n" +
            ");";
    String createTableFavoritos = "CREATE TABLE favoritos(\n" +
            "  id_favorito INTEGER PRIMARY KEY AUTOINCREMENT,\n" +
            "  id_usuario int, \n" +
            "  titulo text,\n" +
            "  creador text,\n" +
            "  año_l text,\n" +
            "  calificacion int,\n" +
            "  tipo text,\n" +
            "  FOREIGN KEY (id_usuario) REFERENCES usuarios(id_usuario)\n" +
            ");";

    //constructor
    public BDFavoritos(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //aqui se van a ejecutar todas las sentencias sql que se necesiten
        db.execSQL(CreateTableUsuarios);
        db.execSQL(createTableFavoritos);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //aqui se controlaran las versiones de la bd (segun las necesidades)

    }
}
