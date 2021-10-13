package com.code.optimasiproject.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.code.optimasiproject.R;
import com.code.optimasiproject.getset.GetSetProjects;
import com.google.android.material.textview.MaterialTextView;

import java.util.List;

public class AdapterProjects extends RecyclerView.Adapter<AdapterProjects.ViewHolder> {

    private List<GetSetProjects> itemLists;
    private Context context;

    public AdapterProjects(List<GetSetProjects> itemLists, Context context){
        this.itemLists = itemLists;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.templates_projects, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        GetSetProjects getSetItem = itemLists.get(position);

        holder.nama.setText(getSetItem.getNama());
    }

    @Override
    public int getItemCount() {
        return itemLists.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        public MaterialTextView nama;

        public ViewHolder(View view){
            super(view);

            nama = view.findViewById(R.id.nama);
        }
    }

    public void filterList(List<GetSetProjects> filteredList) {
        itemLists = filteredList;
        notifyDataSetChanged();
    }
}
