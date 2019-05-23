package com.example.listviewsample2;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.widget.Toast;

public class OrderConfirmDialogFragment extends DialogFragment {
    public String menuItem = "";

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        super.onCreateDialog(savedInstanceState);

        AlertDialog.Builder builder =
                new AlertDialog.Builder(getContext());
        builder.setTitle(R.string.dialog_title);
        //getString is not necessary
        builder.setMessage(getString(R.string.dialog_msg) + menuItem);
        builder.setPositiveButton(R.string.dialog_btn_ok,
                new DialogButtonClickListener());
        builder.setNegativeButton(R.string.dialog_btn_ng,
                new DialogButtonClickListener());
        builder.setNeutralButton(R.string.dialog_btn_nu,
                new DialogButtonClickListener());
        AlertDialog dialog = builder.create();
        //return super.onCreateDialog(savedInstanceState);
        return dialog;
    }//onCreateDialog

    private class DialogButtonClickListener
            implements DialogInterface.OnClickListener {

        @Override
        public void onClick(DialogInterface dialog, int which) {
            String msg = "";
            switch (which) {
                case DialogInterface.BUTTON_POSITIVE:
                    msg = getString(R.string.dialog_ok_toast);
                    break;
                case DialogInterface.BUTTON_NEGATIVE:
                    msg = getString(R.string.dialog_btn_ng);
                    break;
                case DialogInterface.BUTTON_NEUTRAL:
                    msg = getString(R.string.dialog_btn_nu);
                    break;
            }//switch
            Toast.makeText(getContext(), msg, Toast.LENGTH_LONG).show();
        }//onClick
    }//DialogButtonClickListener
}
