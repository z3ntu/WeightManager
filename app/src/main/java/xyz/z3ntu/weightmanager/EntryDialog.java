package xyz.z3ntu.weightmanager;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.DatePicker;
import android.widget.NumberPicker;

import java.util.Calendar;

import xyz.z3ntu.weightmanager.rest.WeightDataSend;

/**
 * Written by Luca Weiss (z3ntu)
 * https://github.com/z3ntu
 */
public class EntryDialog extends DialogFragment {
    /**
     * @param datePicker
     * @return a java.util.Date
     */
    public static java.util.Date getDateFromDatePicker(DatePicker datePicker) {
        int day = datePicker.getDayOfMonth();
        int month = datePicker.getMonth();
        int year = datePicker.getYear();

        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month, day);

        return calendar.getTime();
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the Builder class for convenient dialog construction
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        final View view = inflater.inflate(R.layout.dialog_entry, null);
        builder.setView(view)
                .setPositiveButton("Submit", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        DatePicker datePicker = (DatePicker) view.findViewById(R.id.datepicker);
                        NumberPicker numberPicker = (NumberPicker) view.findViewById(R.id.numberpicker);

                        WeightDataSend data = new WeightDataSend(numberPicker.getValue(), getDateFromDatePicker(datePicker));

                        ((MainActivity) getActivity()).new CreateHttpRequestTask().execute(data);

                        DataFragment.adapter.addItem(new DateWeightEntry(data.getDate(), data.getWeight()));
                        DataFragment.adapter.notifyDataSetChanged();
                    }
                });
        // Create the AlertDialog object and return it
        return builder.create();
    }
}
