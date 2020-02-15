package net.danteh.dantehviewer.adapters;

import android.content.Context;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import net.danteh.dantehviewer.Links;
import net.danteh.dantehviewer.R;

import java.util.ArrayList;
import java.util.List;

public class LinkRVAdapter extends RecyclerView.Adapter<LinkRVAdapter.linksviewholder> {
    Context context;
    List<Links> links = new ArrayList<>();

    public LinkRVAdapter(Context context, List<Links> links) {
        this.context = context;
        this.links = links;
    }

    @NonNull
    @Override
    public linksviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.recycler_links_item,parent,false);
        return new linksviewholder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull linksviewholder holder, int position) {
        Links model = links.get(position);
        holder.linkName.setText(model.getName());
        holder.editLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context, "Edit Link", Toast.LENGTH_SHORT).show();
            }
        });
        holder.deleteLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                links.remove(position);
                notifyItemRemoved(position);
                notifyItemRangeChanged(position, links.size());
                Toast.makeText(context, "Deleted Link", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return links.size();
    }

    class linksviewholder extends RecyclerView.ViewHolder{
        TextView linkName;
        ImageButton editLink,deleteLink;
        public linksviewholder(@NonNull View itemView) {
            super(itemView);
            linkName = itemView.findViewById(R.id.linkname_tv);
            editLink = itemView.findViewById(R.id.editlink_btn);
            deleteLink = itemView.findViewById(R.id.deletelink_btn);
        }
    }
}
