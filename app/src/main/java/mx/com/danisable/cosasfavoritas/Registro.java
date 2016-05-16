package mx.com.danisable.cosasfavoritas;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link Registro.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link Registro#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Registro extends Fragment implements AdapterView.OnItemSelectedListener, View.OnClickListener, SeekBar.OnSeekBarChangeListener{

    //Objetos referenciados de los campos para obtener datos de lo que se va a guardar
    Spinner mSpinner;
    Button btnSave;
    //Objetos que se van a guardar en la BD
    String valorSpin;
    EditText etTitulo;
    EditText etCreador;
    EditText etDate;
    SeekBar Calif;
    TextView valorSeek;
    int cal;

    //variable traida desde el login para almacenar los registros de ese usuario
    String user = LoginActivity.u;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public Registro() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Registro.
     */
    // TODO: Rename and change types and number of parameters
    public static Registro newInstance(String param1, String param2) {
        Registro fragment = new Registro();
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
        View view = inflater.inflate(R.layout.fragment_registro, null);
        //referenciamos los objetos de la vista con el id para darles funcionalidad
        mSpinner = (Spinner) view.findViewById(R.id.spTipo);
        btnSave = (Button) view.findViewById(R.id.btnSave);
        etCreador = (EditText) view.findViewById(R.id.etCreador);
        etTitulo = (EditText) view.findViewById(R.id.etTitulo);
        etDate = (EditText) view.findViewById(R.id.etDate);

        Calif = (SeekBar) view.findViewById(R.id.sbCalif);
        Calif.setMax(10);
        valorSeek = (TextView) view.findViewById(R.id.tvValor);


        // le decimos que escuche los metodos correspondientes
        mSpinner.setOnItemSelectedListener(this);
        btnSave.setOnClickListener(this);
        Calif.setOnSeekBarChangeListener(this);

        //se crea el adaptador para el spinner (el adaptador viene del archivo arrays.xml ubicado en values, ahi estan los valores que tendrá el spinner)
        ArrayAdapter spinner_adapter = ArrayAdapter.createFromResource( this.getContext(), R.array.tipos , android.R.layout.simple_spinner_item);
        spinner_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSpinner.setAdapter(spinner_adapter);

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

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

        valorSpin = parent.getItemAtPosition(position).toString();

        if (valorSpin.equals("Pelicula")){

            etCreador.setHint("Director");

        }else if (valorSpin.equals("Album")) {

            etCreador.setHint("Banda / Artista");

        }else if (valorSpin.equals("Videojuego")) {

            etCreador.setHint("Desarrolladora");

        }

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }


    //metodo del boton guardar, para almacenar datos en la bd
    @Override
    public void onClick(View v) {


        String titulo = etTitulo.getText().toString();
        String creador = etCreador.getText().toString();
        String anio = etDate.getText().toString();

        if(titulo.equals("")){
            Toast.makeText(getContext(), "No puedes dejar campos en blanco", Toast.LENGTH_SHORT).show();
        }else if(creador.equals("")){
            Toast.makeText(getContext(), "No puedes dejar campos en blanco", Toast.LENGTH_SHORT).show();
        }else if(creador.equals("")){
            Toast.makeText(getContext(), "No puedes dejar campos en blanco", Toast.LENGTH_SHORT).show();
        }else{

            String sqlInsert = "INSERT INTO favoritos (id_usuario, titulo, creador, año_l, calificacion, tipo)\n" +
                    "VALUES ("+user+", '"+titulo+"', '"+creador+"', '"+anio+"', "+cal+" ,'"+valorSpin+"');";
            LoginActivity.db.execSQL(sqlInsert);
            Toast.makeText(getContext(), "El registro se almaceno con exito", Toast.LENGTH_SHORT).show();

        }
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

        valorSeek.setText(""+progress);
        cal = progress;

    }


    //metodos cuando inicia y finaliza la manipulacion del seek bar, en este caso no los usaré
    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
