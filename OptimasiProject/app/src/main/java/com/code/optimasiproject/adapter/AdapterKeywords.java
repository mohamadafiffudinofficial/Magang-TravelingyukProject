package com.code.optimasiproject.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.code.optimasiproject.R;
import com.code.optimasiproject.getset.GetSetKeyword;
import com.google.android.material.textview.MaterialTextView;

import java.util.List;

public class AdapterKeywords extends RecyclerView.Adapter<AdapterKeywords.ViewHolder> {

    private List<GetSetKeyword> itemLists;
    private Context context;

    public AdapterKeywords(List<GetSetKeyword> itemLists, Context context){
        this.itemLists = itemLists;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.templates_keyword, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        GetSetKeyword getSetItem = itemLists.get(position);

        holder.textKeyword.setText(holder.textKeyword.getText().toString() + getSetItem.getKeyword());
        holder.textJumlahExplored.setText(holder.textJumlahExplored.getText().toString() + getSetItem.getExplored());
        holder.textTanggalExplored.setText(holder.textTanggalExplored.getText().toString() + getSetItem.getExplored_at());
    }

    @Override
    public int getItemCount() {
        return itemLists.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        public MaterialTextView textKeyword, textJumlahExplored, textTanggalExplored;

        public ViewHolder(View view){
            super(view);

            textKeyword = view.findViewById(R.id.textKeyword);
            textJumlahExplored = view.findViewById(R.id.textJumlahExplored);
            textTanggalExplored = view.findViewById(R.id.textTanggalExplored);
        }
    }

    public void filterList(List<GetSetKeyword> filteredList) {
        itemLists = filteredList;
        notifyDataSetChanged();
    }
}
