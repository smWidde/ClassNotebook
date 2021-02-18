package com.example.classnotebook;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;

public class OkDialogFragment extends DialogFragment {
    private DialogAction act;
    public OkDialogFragment() {super();}
    public OkDialogFragment(DialogAction act) {
        super();
        this.act = act;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        return builder.setPositiveButton("Ok", (dialog, which) -> act.Action()).setMessage("Saved!").create();
    }
}
