package com.example.jvelez.questioner;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class AnswersActivity extends AppCompatActivity {

    ListView answersList;
    TextView noAnswersText;
    Button answerButton;
    EditText answerEditText;
    EditText nameEditText;
    DataBaseAnswers dataBaseAnswersBuilder;
    int questionSelected;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_answers);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        dataBaseAnswersBuilder = new DataBaseAnswers(getApplicationContext());
        questionSelected = getIntent().getExtras().getInt("id");
        answerButton = (Button)findViewById(R.id.answerButton);
        answerEditText = (EditText)findViewById(R.id.answerEditText);
        nameEditText = (EditText)findViewById(R.id.nameEditText);
        answersList = (ListView)findViewById(R.id.answersList);
        noAnswersText = (TextView)findViewById(R.id.noAnswersText);

        cargarRespuestas();

        answerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if ((answerEditText.getText().toString().equals(""))||(nameEditText.getText().toString().equals(""))){
                    Toast.makeText(getApplicationContext(),"Completa todos los campos",Toast.LENGTH_SHORT).show();
                }else {
                    guardar(v);
                    cargarRespuestas();
                    answerEditText.setText("");
                    nameEditText.setText("");
                    Toast.makeText(getApplicationContext(),"Tu respuesta ha sido registrada ",Toast.LENGTH_SHORT).show();
                    //Intent intent = new Intent(getApplicationContext(),TabsActivity.class);
                    //startActivity(intent);
                }
            }
        });
    }

    public void guardar(View view){

        // Gets the data repository in write mode
        SQLiteDatabase db = dataBaseAnswersBuilder.getWritableDatabase();
        DateFormat df = new SimpleDateFormat("HH:mm");
        String date = df.format(Calendar.getInstance().getTime());

        // Create a new map of values, where column names are the keys
        ContentValues values = new ContentValues();
        values.put(DataBaseAnswers.DatosTabla.COLUMNA_NOMBRE, nameEditText.getText().toString());
        values.put(DataBaseAnswers.DatosTabla.COLUMNA_RESPUESTA, answerEditText.getText().toString());
        values.put(DataBaseAnswers.DatosTabla.COLUMNA_RELACION, "" + questionSelected);
        values.put(DataBaseAnswers.DatosTabla.COLUMNA_FECHA, date);

        // Insert the new row, returning the primary key value of the new row
        long newRowId = db.insert(DataBaseAnswers.DatosTabla.TABLE_NAME,
                null,
                values);

    }

    public void cargarRespuestas(){

        ArrayList<String> arrayList = new ArrayList<>();
        SQLiteDatabase db = dataBaseAnswersBuilder.getReadableDatabase();
        String [] argumento = {Integer.toString(questionSelected)};
        String [] datos = {DataBaseAnswers.DatosTabla.COLUMNA_NOMBRE,
                DataBaseAnswers.DatosTabla.COLUMNA_RESPUESTA,
                DataBaseAnswers.DatosTabla.COLUMNA_RELACION,
                DataBaseAnswers.DatosTabla.COLUMNA_FECHA};
        Cursor c = db.query(
                DataBaseAnswers.DatosTabla.TABLE_NAME,
                datos,
                DataBaseAnswers.DatosTabla.COLUMNA_RELACION+"=?",
                argumento,
                null,
                null,
                null);
        int fila = c.getCount();
        if (fila != 0){
            noAnswersText.setVisibility(View.GONE);
        }
        for(int i=0; i<fila; i++){
            c.moveToNext();
            String nombre = c.getString(0);
            String respuesta = c.getString(1);
            String relacion = c.getString(2);
            String fecha = c.getString(3);
            arrayList.add(respuesta + ": " + fecha + " (" + nombre + ")");
        }
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this,
                R.layout.text_in_cells,
                arrayList
        );
        answersList.setAdapter(arrayAdapter);
        answersList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //Toast.makeText(view.getContext(),""+ position,Toast.LENGTH_SHORT).show();

            }
        });

    }

}
