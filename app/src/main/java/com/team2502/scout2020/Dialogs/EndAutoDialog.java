package com.team2502.scout2020.Dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.DialogFragment;

public class EndAutoDialog extends DialogFragment {
    //TODO This should probably be refactored https://stackoverflow.com/a/36139523

    public interface EndAutoDialogListener {
        void onDialogEndAutoClick(DialogFragment dialog);
        void onDialogStayAutoClick(DialogFragment dialog);
    }

    private EndAutoDialogListener listener;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the Builder class for convenient dialog construction
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage("Auto Run Finished?")
                .setPositiveButton("Yes", (dialog, id) -> listener.onDialogEndAutoClick(EndAutoDialog.this))
                .setNegativeButton("No", (dialog, id) -> listener.onDialogStayAutoClick(EndAutoDialog.this));
        setCancelable(false);
        return builder.create();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        listener = (EndAutoDialogListener) context;
    }

    @Override
    public void onResume(){
        super.onResume();
        getDialog().getWindow().setLayout(400, 200);
    }
}