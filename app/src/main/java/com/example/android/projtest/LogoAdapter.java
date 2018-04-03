package com.example.android.projtest;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

/**
 * Created by nazaif on 24/2/18.
 */

public class LogoAdapter extends RecyclerView.Adapter<LogoAdapter.LogoAdapterViewHolder> {
    Context context;
    private int holderCount;
    ClickListener clickListener;

    public LogoAdapter(Context mContext,ClickListener listener){
        this.context = mContext;
        this.clickListener = listener;
        holderCount = 0;
    }

    public interface ClickListener{
        void OnViewClick(int index);
    }

    @Override
    public LogoAdapterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card,parent,false);
        LogoAdapterViewHolder viewHolder = new LogoAdapterViewHolder(view);
        int textViewColor = ColorUtils.getViewHolderBackgroundColorFromInstance(context,holderCount);
        viewHolder.teamName.setBackgroundColor(textViewColor);
        holderCount++;
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(LogoAdapterViewHolder holder, int position) {
        Picasso.with(context)
                .load(LogoAddress.logoAddresses[position])
                .error(R.drawable.error)
                .into(holder.teamLogo);
        holder.teamName.setText(LogoAddress.teamNames[position]);
    }

    @Override
    public int getItemCount() {
        return LogoAddress.logoAddresses.length;
    }

    public class LogoAdapterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView teamName;
        ImageView teamLogo;
        public LogoAdapterViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            teamLogo = (ImageView) itemView.findViewById(R.id.logoImageView);
            teamName = (TextView) itemView.findViewById(R.id.teamName);
        }

        @Override
        public void onClick(View view) {
            clickListener.OnViewClick(getAdapterPosition());
        }
    }
}
