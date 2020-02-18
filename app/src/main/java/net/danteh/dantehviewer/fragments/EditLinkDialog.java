package net.danteh.dantehviewer.fragments;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.SaveCallback;

import net.danteh.dantehviewer.R;

public class EditLinkDialog extends DialogFragment {

    EditText urlname_input,url_input;
    int position;
    String objectId;
    public EditLinkDialog(int position,String objectId) {
        this.position =position;
        this.objectId = objectId;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(requireActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.edit_link_dialog, null);

        builder.setView(view)
                .setTitle("ویرایش لینک")
                .setNegativeButton("بیخیال", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                })
                .setPositiveButton("حله", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String urlName = urlname_input.getText().toString();
                        String URL = url_input.getText().toString();
                        //listener.applyTexts(username, password);

                        ParseQuery<ParseObject> query = ParseQuery.getQuery("Links");

                        query.getInBackground(objectId, new GetCallback<ParseObject>() {
                            public void done(ParseObject link, ParseException e) {
                                if (e == null) {
                                    Log.e("TAAAAAG", "done???: " );
                                    link.put("urlname", urlName);
                                    link.put("URL", URL);
                                    link.saveInBackground(new SaveCallback() {
                                        @Override
                                        public void done(ParseException e) {
                                            Log.e("TAAAAAG", "WTF HAPENNED???: " );
                                        }
                                    });
                                }
                                else Log.e("TAAAAAG", " NOT done???: " );
                            }
                        });

                    }
                });

        urlname_input = view.findViewById(R.id.urlname_input);
        url_input = view.findViewById(R.id.url_input);

        return builder.create();
    }
}
