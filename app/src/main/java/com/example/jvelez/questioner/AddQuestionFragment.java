package com.example.jvelez.questioner;


import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;


/**
 * A simple {@link Fragment} subclass.
 */
public class AddQuestionFragment extends Fragment {

    Button questionButton;
    EditText questionEditText, nameEditText, temaEditText;
    View view;
    DataBaseQuestions dataBaseQuestionsBuilder;

    public AddQuestionFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_add_question,
                container, false);
        questionButton = (Button) view.findViewById(R.id.questionButton);
        questionEditText = (EditText) view.findViewById(R.id.questionEditText);
        nameEditText = (EditText) view.findViewById(R.id.nameEditText);
        temaEditText = (EditText) view.findViewById(R.id.temaEditText);
        dataBaseQuestionsBuilder = new DataBaseQuestions(getContext());

        questionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if ((questionEditText.getText().toString().equals(""))||(nameEditText.getText().toString().equals(""))||(temaEditText.getText().toString().equals(""))){
                    Toast.makeText(getContext(),"Completa todos los campos",Toast.LENGTH_SHORT).show();
                }else {
                    guardar();
                }
            }
        });

        return view;
        // Inflate the layout for this fragment
        //return inflater.inflate(R.layout.fragment_add_question, container, false);
    }

    public void guardar(){

        // Gets the data repository in write mode
        SQLiteDatabase db = dataBaseQuestionsBuilder.getWritableDatabase();

        DateFormat df = new SimpleDateFormat("HH:mm");
        //DateFormat df = new SimpleDateFormat("dd MM yyyy, HH:mm");
        String date = df.format(Calendar.getInstance().getTime());

        // Create a new map of values, where column names are the keys
        ContentValues values = new ContentValues();
        values.put(DataBaseQuestions.DatosTabla.COLUMNA_NOMBRE, nameEditText.getText().toString());
        values.put(DataBaseQuestions.DatosTabla.COLUMNA_TEMA, temaEditText.getText().toString());
        values.put(DataBaseQuestions.DatosTabla.COLUMNA_PREGUNTA, questionEditText.getText().toString());
        values.put(DataBaseQuestions.DatosTabla.COLUMNA_FECHA, date);

        // Insert the new row, returning the primary key value of the new row
        long newRowId = db.insert(DataBaseQuestions.DatosTabla.TABLE_NAME,
                null,
                values);
        questionEditText.setText("");
        nameEditText.setText("");
        temaEditText.setText("");
        Toast.makeText(getContext(),"Tu pregunta ha sido registrada ",Toast.LENGTH_SHORT).show();

    }

}
