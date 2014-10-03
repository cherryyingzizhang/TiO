package com.example.cherry_zhang.androidbeaconexample.Dialogs;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.widget.Toast;

/**
 * Created by Cherry_Zhang on 2014-09-21.
 */
public class BookConfirmationDialog extends AlertDialog.Builder {

    private Context context;

    //variables that would be used if I wasn't so lazy :3
//    private String title;
//    private String message;
//    private String positiveButtonMessage;
//    private String negativeButtonMessage;

    public BookConfirmationDialog(final Context context) {
        super(context);
        this.context = context;
        this.setTitle("Givery");
        this.setMessage("Do you really want to work in this awesome office?");

        this.setPositiveButton("Yes!",new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Toast.makeText(context,"You got it! いつでも,どこでも,働きましょう!",Toast.LENGTH_SHORT)
                        .show();
                //TODO: implement this feature next
            }
        });
    }



}
