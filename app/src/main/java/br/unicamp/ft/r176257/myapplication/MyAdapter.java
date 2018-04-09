package br.unicamp.ft.r176257.myapplication;

import java.util.Locale;

import android.content.Context;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.util.DisplayMetrics;
import android.content.res.Resources;
import android.content.res.Configuration;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {

    /*
    * Variável para poder selecionar uma das linhas da listas.
     */
    private int selectedPos = RecyclerView.NO_POSITION;
    private Context context;
    private Activity activity;


    public void setActivity(Activity act){
        activity = act;
    }

    /*
        Essa interface serve apenas para que possamos nos comunicar com
        a activity que está em execução. Essa activity deverá ter o método
        onItemClick.
     */
    public interface OnItemClickListener {
        void onItemClick(Idioma idioma);
    }

    private final ArrayList<Idioma> idiomas;
    private final OnItemClickListener listener;

    public MyAdapter(ArrayList<Idioma> idiomas, OnItemClickListener listener) {
        this.idiomas = idiomas;
        this.listener = listener;
    }


    /*
       Este método cria um novo ViewHolder. Será chamada apenas algumas vezes,
       dependendo de quantas linhas cabem na RecyclerView.
     */
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        /*
           Usamos o LayoutInflater para transformar um arquivo XML em uma classe
           java. No caso, estamos o arquivo adapter_layout.xml
         */
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_layout, parent, false);
        context = parent.getContext();
        return new ViewHolder(v);
    }


    /*
       Este método é chamado sempre que uma mudança ocorre na RecyclerView e
       itens precisam ser substituídos.
     */
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        /*
            Colocamos em verde os itens que foram selecionados. Posso deixar para o final
         */
        holder.itemView.setBackgroundColor(selectedPos == position ? Color.rgb(213, 227, 237) : Color.TRANSPARENT);
        holder.itemView.setSelected(selectedPos == position);
        holder.bind(idiomas.get(position), listener);
    }

    @Override
    public int getItemCount() {
        return idiomas.size();
    }


    /*
        Esta classe fornece uma referência para as views dos itens
        que estão na RecyclerView.
     */
    class ViewHolder extends RecyclerView.ViewHolder {
        /*
           Como atributo, colocamos todas as views que estão no layout
           de cada linha da RecyclerView. No nosso caso, são os elementos
           que declaramos em adapter_layout.xml
         */
        private TextView name;
        private ImageView imageView;


        public ViewHolder(View itemView) {
            super(itemView);
            /*
                Populamos os atributos invocando o findViewById dessa linha
                da RecyclerView.
             */
            name = (TextView) itemView.findViewById(R.id.textView);
            imageView = (ImageView) itemView.findViewById(R.id.imageView);
        }

        /*
            Invocado quando acoplamos um objeto do array ao Holder. Note
            que os Holders são reaproveitados, então espera-se que este método
            seja chamado várias vezes.
         */
        public void bind(final Idioma idioma, final OnItemClickListener listener) {
            name.setText(idioma.getIdioma());
            imageView.setImageResource(idioma.resId);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onItemClick(idioma);
                    ViewHolder.this.onClick();
                }
            });
        }

        public void onClick() {
            if (getAdapterPosition() == RecyclerView.NO_POSITION) return;

            notifyItemChanged(selectedPos);

            selectedPos = getLayoutPosition();
            notifyItemChanged(selectedPos);
        }
    }
}