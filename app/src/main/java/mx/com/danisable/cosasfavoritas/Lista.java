package mx.com.danisable.cosasfavoritas;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link Lista.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link Lista#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Lista extends Fragment implements AdapterView.OnItemSelectedListener{

    //creamos los objetos que vamos a utilizar
    Spinner spTIPO;
    LinearLayout lLayout;
    ListView listV;
    List<String> datos;
    String user = LoginActivity.u;

    //Traemos la variable de la base de datos
    SQLiteDatabase db = LoginActivity.db;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public Lista() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Lista.
     */
    // TODO: Rename and change types and number of parameters
    public static Lista newInstance(String param1, String param2) {
        Lista fragment = new Lista();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        //Creamos una vista parapoder manejar los objetos en el fragment
        View view = inflater.inflate(R.layout.fragment_lista, null);

        spTIPO = (Spinner) view.findViewById(R.id.spTIPO);
        lLayout = (LinearLayout) view.findViewById(R.id.lLista);
        listV = (ListView) view.findViewById(R.id.lvFavoritos);

        //se crea el adaptador para el spinner (el adaptador viene del archivo arrays.xml ubicado en values, ahi estan los valores que tendrá el spinner)
        ArrayAdapter spinner_adapter = ArrayAdapter.createFromResource( this.getContext(), R.array.tipos , android.R.layout.simple_spinner_item);
        spinner_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spTIPO.setAdapter(spinner_adapter);

        //se asigna el listener
        spTIPO.setOnItemSelectedListener(this);


        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    String val;

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {


        //obtiene el valor del spinner para saber que favorito va a traer
        val = parent.getItemAtPosition(position).toString();

        if (val.equals("Pelicula")){

            lLayout.setBackgroundColor(Color.YELLOW);
            listarFavoritos();

        }else if (val.equals("Album")) {

            lLayout.setBackgroundColor(Color.GREEN);
            listarFavoritos();

        }else if (val.equals("Videojuego")) {

            lLayout.setBackgroundColor(Color.CYAN);
            listarFavoritos();

        }

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    public void listarFavoritos(){
        Cursor c = db.rawQuery("SELECT * FROM favoritos WHERE tipo = '"+ val +"' AND id_usuario = "+ user +";", null);
        datos = new ArrayList<String>(); //arraylist donde se guardaran los datos que traemos de la consulta
        String titulo ;
        String creador;
        String anio;
        String cal;

        if(c.moveToFirst()){
            do{
                titulo = c.getString(2);
                creador = c.getString(3);
                anio = c.getString(4);
                cal = c.getString(5);
                datos.add(titulo+" "+creador+" "+anio+" "+cal);

            }while (c.moveToNext());
        }
        //creamos el adaptador array adapter para llenar el listview
        ArrayAdapter<String> list_adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1);
        list_adapter.addAll(datos);
        listV.setAdapter(list_adapter);

    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
