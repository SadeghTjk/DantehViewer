package net.danteh.dantehviewer.adapters;

import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.parse.DeleteCallback;
import com.parse.ParseException;
import com.parse.ParseObject;

import net.danteh.dantehviewer.R;
import net.danteh.dantehviewer.fragments.EditLinkDialog;

import java.util.ArrayList;
import java.util.List;

public class LinkRVAdapter extends RecyclerView.Adapter<LinkRVAdapter.linksviewholder> {
    Context context;
    List<ParseObject> links = new ArrayList<>();
    FragmentManager manager;

    public LinkRVAdapter(Context context, List<ParseObject> links, FragmentManager manager) {
        this.context = context;
        this.links = links;
        this.manager = manager;
    }

    @NonNull
    @Override
    public linksviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.recycler_links_item, parent, false);
        return new linksviewholder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull linksviewholder holder, int position) {
        ParseObject model = links.get(position);
        holder.linkName.setText(model.getString("urlName"));
        holder.editLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context, "Edit Link", Toast.LENGTH_SHORT).show();
                EditLinkDialog dialog = new EditLinkDialog(position, model.getObjectId());
                dialog.setCancelable(false);
                dialog.show(manager, "edit");

            }
        });
        holder.deleteLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AlertDialog.Builder builder1 = new AlertDialog.Builder(context);
                builder1.setMessage("واقعاً میخوای پاکش کنی؟");
                builder1.setCancelable(true);
                builder1.setPositiveButton(
                        "آره",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                links.remove(position);
                                model.deleteInBackground(new DeleteCallback() {
                                    @Override
                                    public void done(ParseException e) {
                                        if (e == null)
                                            Toast.makeText(context, "پاک شد!", Toast.LENGTH_SHORT).show();
                                        else
                                            Toast.makeText(context, e.getCode() + " : " + e.getMessage(), Toast.LENGTH_SHORT).show();
                                    }
                                });
                                notifyItemRemoved(position);
                                notifyItemRangeChanged(position, links.size());
                                Toast.makeText(context, "Deleted Link", Toast.LENGTH_SHORT).show();
                            }
                        })
                        .setNegativeButton(
                                "نه",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        dialog.cancel();
                                    }
                                });

                AlertDialog alert11 = builder1.create();
                alert11.show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return links.size();
    }

    class linksviewholder extends RecyclerView.ViewHolder {
        TextView linkName;
        ImageButton editLink, deleteLink;

        public linksviewholder(@NonNull View itemView) {
            super(itemView);
            linkName = itemView.findViewById(R.id.linkname_tv);
            editLink = itemView.findViewById(R.id.editlink_btn);
            deleteLink = itemView.findViewById(R.id.deletelink_btn);
        }
    }

}
