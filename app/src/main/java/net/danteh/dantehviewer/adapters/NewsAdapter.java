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

public class NewsAdapter extends RecyclerView.Adapter<net.danteh.dantehviewer.adapters.NewsAdapter.newsviewholder> {
    Context context;
    List<ParseObject> links = new ArrayList<>();

    public NewsAdapter(Context context) {
        this.context = context;

    }

    @NonNull
    @Override
    public newsviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.news_item_layout, parent, false);
        return new newsviewholder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull net.danteh.dantehviewer.adapters.NewsAdapter.newsviewholder holder, int position) {
      //  ParseObject model = links.get(position);
       // holder.linkName.setText(model.getString("urlName"));
    }

    @Override
    public int getItemCount() {
        return 7;
    }

    static class newsviewholder extends RecyclerView.ViewHolder {
        TextView linkName;
        ImageButton editLink, deleteLink;

        public newsviewholder(@NonNull View itemView) {
            super(itemView);

        }
    }

}
