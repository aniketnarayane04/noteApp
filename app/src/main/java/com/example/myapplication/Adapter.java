package com.example.myapplication;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder> implements Filterable {

    private static final String TAG = "Adapter";

    LayoutInflater inflater;
    List<Model_database> notes;
    List<Model_database> backup;

    Adapter(Context context, List<Model_database> note) {
        this.inflater = LayoutInflater.from(context);
        this.notes = note;
        this.backup = note;
    }

    @NonNull
    @Override
    public Adapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.recyclerview_item, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Adapter.ViewHolder holder, int position) {
        String title = backup.get(position).getTitle();
        String content = backup.get(position).getContent();
        String date = backup.get(position).getDate();
        String time = backup.get(position).getTime();

        holder.nTitle.setText(title);
        holder.nContent.setText(content);
        holder.nDate.setText(date);
        holder.nTime.setText(time);
    }

    @Override
    public int getItemCount() {
        return backup.size();
    }

    @Override
    public Filter getFilter() {
        return filter;
    }

    Filter filter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {
            List<Model_database> filtereddata = new ArrayList<Model_database>();
            backup = notes;

            for(Model_database model_database:backup){
                Log.d(TAG, "Adapter:backup 111111111111111111111111111"+model_database.getTitle());
            }
            for(Model_database model_database:filtereddata){
                Log.d(TAG, "Adapter:filterdata 111111111111111111111111111"+model_database.getTitle());
            }

            if ( charSequence.toString().isEmpty())
                filtereddata.addAll(backup);
            else {
                for (Model_database obj : backup) {
                    if (obj.getTitle().toLowerCase().contains(charSequence.toString().toLowerCase()))
                        filtereddata.add(obj);
                }
            }

            FilterResults results = new FilterResults();
            results.values = filtereddata;
            for(Model_database model_database:backup){
                Log.d(TAG, "Adapter:backup 222222222222222222222222"+model_database.getTitle());
            }
            for(Model_database model_database:filtereddata){
                Log.d(TAG, "Adapter:filterdata 22222222222222222222222"+model_database.getTitle());
            }
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            notes.clear();
            for(Model_database model_database:backup){
                Log.d(TAG, "Adapter:backup 33333333333333333333333333"+model_database.getTitle());
            }
            List<Model_database> b = (List<Model_database>) results.values;
            for(Model_database model_database:b){
                Log.d(TAG, "Adapter:filterdata 33333333333333333333"+model_database.getTitle());
            }
            notes.addAll((List<Model_database>) results.values);
            notifyDataSetChanged();
        }
    };

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView nTitle, nContent, nDate, nTime;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            nTitle = itemView.findViewById(R.id.recycler_title);
            nContent = itemView.findViewById(R.id.recycler_edit);
            nDate = itemView.findViewById(R.id.recycler_datetime);
            nTime = itemView.findViewById(R.id.recycler_time);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(v.getContext(), edit.class);
                    int temp = (int) notes.get(getAdapterPosition()).getId();
                    i.putExtra("ID", temp);
                    v.getContext().startActivity(i);
                }
            });

            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    return true;
                }
            });

        }
    }
}