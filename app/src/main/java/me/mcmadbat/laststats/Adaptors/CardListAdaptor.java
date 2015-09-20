package me.mcmadbat.laststats.Adaptors;

import android.media.Image;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.List;

import me.mcmadbat.laststats.Helpers.CardInfo;
import me.mcmadbat.laststats.R;

/**
 * Created by David on 2015-09-19.
 *
 * This adaptor helps set the content of the cards
 */
public class CardListAdaptor extends RecyclerView.Adapter<CardListAdaptor.CardListViewHolder> {

    private List<CardInfo> data;

    //the constructor
    public CardListAdaptor(List<CardInfo> w){
        data = w;
    }

    public static class CardListViewHolder extends RecyclerView.ViewHolder {
        protected TextView title;
        protected TextView rank;
        protected TextView count;

        protected ImageView img;


        public CardListViewHolder(View v) {
            super(v);
            title = (TextView) v.findViewById(R.id.txtName);
            rank = (TextView) v.findViewById(R.id.txtRank);
            count = (TextView) v.findViewById(R.id.txtCount);

            img = (ImageView) v.findViewById(R.id.card_img);
        }
    }

    @Override
    public void onBindViewHolder(CardListViewHolder cardListViewHolder, int i) {
        String t = data.get(i).title;
        if (t.length() >= 43) {
            t = t.substring(0,40) + "...";
        }

        //sets the information
        cardListViewHolder.title.setText(t);
        cardListViewHolder.count.setText(data.get(i).count + " plays");
        cardListViewHolder.rank.setText(Integer.toString(i+1));
    }

    @Override
    public CardListViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.
                from(viewGroup.getContext()).
                inflate(R.layout.card_layout, viewGroup, false);

        return new CardListViewHolder(itemView);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }
}


