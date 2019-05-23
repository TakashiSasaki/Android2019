package com.gmail.takashi316.listviewsample2;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.widget.Toast;

public class OrderConfirmDialogFragment extends DialogFragment {

    private class DialogButtonClickListener implements DialogInterface.OnClickListener {

        @Override
        public void onClick(DialogInterface dialogInterface, int which) {
            String msg = "";
            switch (which) {
                case DialogInterface.BUTTON_POSITIVE:
                    msg = getString(R.string.dialog_ok_toast);
                    break;
                case DialogInterface.BUTTON_NEGATIVE:
                    msg = getString(R.string.dialog_ng_toast);
                    break;
                case DialogInterface.BUTTON_NEUTRAL:
                    msg = getString(R.string.dialog_nu_toast);
                    break;
            }//switch

            Toast.makeText(getContext(), msg, Toast.LENGTH_LONG).show();
        }//onClick
    }//DialogButtonClickListener


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle(R.string.dialog_title);
        builder.setMessage(R.string.dialog_msg);

        builder.setPositiveButton(R.string.dialog_btn_ok, new DialogButtonClickListener());
        builder.setNegativeButton(R.string.dialog_btn_ng, new DialogButtonClickListener());
        builder.setNeutralButton(R.string.dialog_btn_ng, new DialogButtonClickListener());

        AlertDialog dialog = builder.create();
        //return super.onCreateDialog(savedInstanceState);
        return dialog;
    }//onCreateDialog
}
