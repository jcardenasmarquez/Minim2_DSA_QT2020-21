package com.jcardenas.example.minim2_qt2020;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.jcardenas.example.minim2_qt2020.models.Repo;

import java.util.List;

public class RVAdapter extends RecyclerView.Adapter<RVAdapter.MyViewHolder> {

    private List<Repo> listaRepos;
    private Context context;

    public RVAdapter(Context ct, List<Repo> repos){
        context = ct;
        listaRepos = repos;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.repos, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.nombreRepo.setText(listaRepos.get(position).getLanguage());
        holder.numero.setText(listaRepos.get(position).getName());
    }

    @Override
    public int getItemCount() {
        return listaRepos.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView numero;
        TextView nombreRepo;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            numero = itemView.findViewById(R.id.numeroTextView);
            nombreRepo = itemView.findViewById(R.id.nombreRepoTextView);
        }
    }
}
