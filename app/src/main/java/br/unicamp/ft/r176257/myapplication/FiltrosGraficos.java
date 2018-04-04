package br.unicamp.ft.r176257.myapplication;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class FiltrosGraficos {

    private Calendar calendarInicio = Calendar.getInstance();
    private Calendar calendarFim = Calendar.getInstance();
    private EditText edttxtDataInicio;
    private EditText edttxtDataFim;
    private Activity activity;
    private Button btn30Dias;
    private Button btnMes;
    private Button btnAno;
    private Button btnSempre;


    public void instanciar(Bundle savedInstanceState, Activity act, int tipo) {
        activity = act;
        edttxtDataInicio = (EditText) activity.findViewById(R.id.edttxt_data_inicio);
        edttxtDataFim = (EditText) activity.findViewById(R.id.edttxt_data_fim);
        btn30Dias = (Button) activity.findViewById(R.id.btnFiltro30Dias);
        btnMes = (Button) activity.findViewById(R.id.btnFiltroMes);
        btnAno = (Button) activity.findViewById(R.id.btnFiltroAno);
        btnSempre = (Button) activity.findViewById(R.id.btnFiltroSempre);
        addOnClickBtnFiltros();
        if (savedInstanceState == null) {
            switch (tipo) {
                case 0: // Donut
                    activity.getFragmentManager().beginTransaction().add(R.id.container, new GraficoDonut.PlaceholderFragment()).commit();
                    break;
                case 1: // Linhas
                    activity.getFragmentManager().beginTransaction().add(R.id.container, new GraficoLinhas.PlaceholderFragment()).commit();
                    break;
            }
        }


        final DatePickerDialog.OnDateSetListener dateInicio = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                calendarInicio.set(Calendar.YEAR, year);
                calendarInicio.set(Calendar.MONTH, monthOfYear);
                calendarInicio.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateDataInicio();
            }

        };

        final DatePickerDialog.OnDateSetListener dateFim = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                calendarFim.set(Calendar.YEAR, year);
                calendarFim.set(Calendar.MONTH, monthOfYear);
                calendarFim.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateDataFim();
            }

        };

        edttxtDataInicio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog dp = new DatePickerDialog(activity, dateInicio, calendarInicio
                        .get(Calendar.YEAR), calendarInicio.get(Calendar.MONTH),
                        calendarFim.get(Calendar.DAY_OF_MONTH));
                dp.getDatePicker().setMaxDate(calendarFim.getTimeInMillis());
                dp.show();
            }
        });

        edttxtDataFim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog dp = new DatePickerDialog(activity, dateFim, calendarFim
                        .get(Calendar.YEAR), calendarFim.get(Calendar.MONTH),
                        calendarFim.get(Calendar.DAY_OF_MONTH));
                dp.getDatePicker().setMaxDate(Calendar.getInstance().getTimeInMillis());
                dp.show();
            }
        });

        updateDataFim();
        calendarInicio.set(Calendar.DAY_OF_MONTH, 1);
        updateDataInicio();
    }

    private void addOnClickBtnFiltros() {
        btn30Dias.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calendarInicio = Calendar.getInstance();
                calendarInicio.add(Calendar.MONTH, -1);
                calendarFim = Calendar.getInstance();
                updateDataInicio();
                updateDataFim();
            }
        });
        btnMes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calendarInicio = Calendar.getInstance();
                calendarInicio.set(Calendar.DAY_OF_MONTH, 1);
                calendarFim = Calendar.getInstance();
                updateDataInicio();
                updateDataFim();
            }
        });
        btnAno.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calendarInicio = Calendar.getInstance();
                calendarInicio.set(Calendar.DAY_OF_YEAR, 1);
                calendarFim = Calendar.getInstance();
                updateDataInicio();
                updateDataFim();
            }
        });
        btnSempre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    private void updateDataInicio() {
        String myFormat = "dd/MM/yyyy";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        edttxtDataInicio.setText(sdf.format(calendarInicio.getTime()));
    }

    private void updateDataFim() {
        if (calendarFim.before(calendarInicio)) {
            calendarInicio = (Calendar) calendarFim.clone();
            updateDataInicio();
        }
        String myFormat = "dd/MM/yyyy";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        edttxtDataFim.setText(sdf.format(calendarFim.getTime()));
    }
}