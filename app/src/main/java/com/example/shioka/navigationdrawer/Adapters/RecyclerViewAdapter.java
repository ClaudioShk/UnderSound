package com.example.shioka.navigationdrawer.Adapters;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.shioka.navigationdrawer.Models.Song;
import com.example.shioka.navigationdrawer.R;
import com.example.shioka.navigationdrawer.Interfaces.SongServices;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Shioka on 28/10/2017.
 */

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> implements Filterable{

    private onItemClickListener onItemClickListener;
    private List<Song> songList;
    private List<Song> filteredSongList;
    private int layout;
    private Activity activity;

    public RecyclerViewAdapter(List<Song> songs,Activity act,int layout,onItemClickListener oi){
        this.songList=songs;
        this.filteredSongList=songs;
        this.layout=layout;
        this.onItemClickListener=oi;
        this.activity=act;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(activity).inflate(layout,parent,false);
        ViewHolder vH =  new ViewHolder(v);
        return vH;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.bind(filteredSongList.get(position),onItemClickListener);
    }

    @Override
    public int getItemCount() {
        if(filteredSongList != null)
            return filteredSongList.size();
        else
            return 0;
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String charString = charSequence.toString();
                if(charString.isEmpty()) {
                    filteredSongList = songList;
                }else{
                    List<Song> filteredList = new ArrayList<>();
                    for(Song song:songList) {
                        if(song.getTitle().toLowerCase().contains(charString)||song.getAlbum().toLowerCase().contains(charString)
                                ||song.getArtist().toLowerCase().contains(charString)) {
                            filteredList.add(song);
                        }
                    }
                    filteredSongList = filteredList;
                }
                FilterResults filterResults = new FilterResults();
                filterResults.values = filteredSongList;
                return filterResults;
            }
            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                filteredSongList = (List<Song>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }

    public class ViewHolder extends  RecyclerView.ViewHolder{
        public TextView title,artist,album;
        public ImageView coverImage;
        public ViewHolder(View v){
            super(v);
            coverImage = v.findViewById(R.id.imgViewHeader);
            title = v.findViewById(R.id.txtViewTitle);
            artist = v.findViewById(R.id.txtViewArtist);
            album = v.findViewById(R.id.txtViewAlbum);
        }
        public void bind(final Song song, final onItemClickListener listener){
            title.setText(song.getTitle());
            artist.setText(song.getArtist());
            album.setText(song.getAlbum());
            if (song.getImage()!=null) {
                Glide.with(activity).load(SongServices.URLGETIMAGESONG+song.getImage())
                        .centerCrop()
                        .placeholder(R.drawable.ic_music_note)
                        .error(R.drawable.ic_music_note)
                        .into(coverImage);
            }else {
                Glide.with(activity).load(R.drawable.ic_music_note)
                        .centerCrop()
                        .placeholder(R.drawable.ic_music_note)
                        .error(R.drawable.ic_music_note)
                        .into(coverImage);
            }
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.onItemClick(song,getAdapterPosition());
                }
            });
        }
    }

    public interface onItemClickListener{
        void onItemClick(Song song, int i);
    }
}
