package kz.dev.home.flos.dialogs;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;

import java.util.Objects;

import kz.dev.home.flos.R;

public class TicketDialog extends DialogFragment{

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        View view = Objects.requireNonNull(getActivity()).getLayoutInflater().inflate(R.layout.dialog_ticket, new LinearLayout(getActivity()), false);

        // Retrieve layout elements
        //TextView title = (TextView) view.findViewById(R.id.text_title);

        // Set values
        //title.setText("Not perfect yet");

        // Build dialog
        Dialog builder = new Dialog(getActivity());
        builder.requestWindowFeature(Window.FEATURE_NO_TITLE);
        Objects.requireNonNull(builder.getWindow()).setBackgroundDrawable(new ColorDrawable(Color.GREEN));
        builder.setContentView(view);
        return builder;

    }
}