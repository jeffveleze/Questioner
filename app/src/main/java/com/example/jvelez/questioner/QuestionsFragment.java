package com.example.jvelez.questioner;


import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class QuestionsFragment extends Fragment implements ViewFragment {

    ListView questionsList;
    TextView noQuestionsText;
    View view;
    DataBaseQuestions dataBaseQuestionsBuilder;

    public QuestionsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_questions,
                container, false);
        questionsList = (ListView) view.findViewById(R.id.questionsList);
        noQuestionsText = (TextView) view.findViewById(R.id.noQuestionsText);
        dataBaseQuestionsBuilder = new DataBaseQuestions(getContext());
        cargarPreguntas();

        return view;
    }

    public void cargarPreguntas() {

        ArrayList<String> arrayList = new ArrayList<>();
        SQLiteDatabase db = dataBaseQuestionsBuilder.getReadableDatabase();
        String[] datos = {DataBaseQuestions.DatosTabla.COLUMNA_NOMBRE,
                DataBaseQuestions.DatosTabla.COLUMNA_TEMA,
                DataBaseQuestions.DatosTabla.COLUMNA_PREGUNTA,
                DataBaseQuestions.DatosTabla.COLUMNA_FECHA};
        Cursor c = db.query(
                DataBaseQuestions.DatosTabla.TABLE_NAME,
                datos,
                null,
                null,
                null,
                null,
                null);
        int fila = c.getCount();
        if (fila != 0){
            noQuestionsText.setVisibility(View.GONE);
        }
        for (int i = 0; i < fila; i++) {
            c.moveToNext();
            String nombre = c.getString(0);
            String tema = c.getString(1);
            String pregunta = c.getString(2);
            String fecha = c.getString(3);
            arrayList.add(pregunta + ": " + fecha + ", " + tema + " (" + nombre + ")");
        }
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(view.getContext(),
                R.layout.text_in_cells,
                arrayList
        );
        questionsList.setAdapter(arrayAdapter);
        questionsList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //Toast.makeText(view.getContext(),""+ position,Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(view.getContext(), AnswersActivity.class);
                intent.putExtra("id", position);
                startActivity(intent);
            }
        });

    }

    @Override
    public void refresh() {
        cargarPreguntas();
    }

}
