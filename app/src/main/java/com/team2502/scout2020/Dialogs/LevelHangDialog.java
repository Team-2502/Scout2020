package com.team2502.scout2020.Dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.DialogFragment;

public class LevelHangDialog extends DialogFragment {
    //TODO This should probably be refactored https://stackoverflow.com/a/36139523

    public interface LevelHangDialogListener {
        void onDialogLevelHangClick(DialogFragment dialog);
        void onDialogNotLevelHangClick(DialogFragment dialog);
    }

    private LevelHangDialogListener listener;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the Builder class for convenient dialog construction
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage("Level Hang?")
                .setPositiveButton("Yes", (dialog, id) -> listener.onDialogLevelHangClick(LevelHangDialog.this))
                .setNegativeButton("No", (dialog, id) -> listener.onDialogNotLevelHangClick(LevelHangDialog.this));
        setCancelable(false);
        return builder.create();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        listener = (LevelHangDialogListener) context;
    }

    @Override
    public void onResume(){
        super.onResume();
        getDialog().getWindow().setLayout(400, 200);
    }
}
