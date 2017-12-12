package br.edu.ifspsaocarlos.agenda.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import br.edu.ifspsaocarlos.agenda.R;
import br.edu.ifspsaocarlos.agenda.model.Contato;


public class ContatoAdapter extends RecyclerView.Adapter<ContatoAdapter.ContatoViewHolder> {

    private static final String TAG = "ContatoAdapter";

    private List<Contato> contatos;
    private Context context;

    private static ItemClickListener clickListener;
    private static FavoritoClickListener favoritoClickListener;


    public ContatoAdapter(List<Contato> contatos, Context context) {
        this.contatos = contatos;
        this.context = context;
    }

    @Override
    public ContatoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.contato_celula, parent, false);
        return new ContatoViewHolder(view);
    }


    @Override
    public void onBindViewHolder(ContatoViewHolder holder, int position) {
        Contato contato = contatos.get(position);
        holder.nome.setText(contato.getNome());
        holder.favorito.setImageResource(contato.isFavorito().booleanValue() == true ? R.drawable.icone_favorito_on : R.drawable.icone_favorito_off);
    }

    @Override
    public int getItemCount() {
        return contatos.size();
    }


    public void setClickListener(ItemClickListener itemClickListener) {
        clickListener = itemClickListener;
    }

    public void setFavoritoClickListener(FavoritoClickListener itemClickListener) {
        this.favoritoClickListener = itemClickListener;
    }

    public class ContatoViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        final TextView nome;
        final ImageView favorito;

        ContatoViewHolder(View view) {
            super(view);
            nome = (TextView) view.findViewById(R.id.nome);
            favorito = (ImageView) view.findViewById(R.id.favorito_image);
            view.setOnClickListener(this);
            favorito.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {

            if (clickListener != null) {
                if (R.id.favorito_image == view.getId()) {
                    favoritoClickListener.onItemClick(getAdapterPosition());
                } else {
                    clickListener.onItemClick(getAdapterPosition());
                }
            }
        }
    }


    public interface ItemClickListener {
        void onItemClick(int position);
    }

    public interface FavoritoClickListener {
        void onItemClick(int position);
    }

}