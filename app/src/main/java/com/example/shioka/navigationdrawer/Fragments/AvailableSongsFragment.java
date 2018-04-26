package com.example.shioka.navigationdrawer.Fragments;


import android.app.Activity;
import android.content.DialogInterface;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.shioka.navigationdrawer.Activities.MainActivity;
import com.example.shioka.navigationdrawer.Adapters.RecyclerViewAdapter;
import com.example.shioka.navigationdrawer.Models.Song;
import com.example.shioka.navigationdrawer.R;
import com.example.shioka.navigationdrawer.Interfaces.SongServices;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static android.content.ContentValues.TAG;


/**
 * A simple {@link Fragment} subclass.
 */
public class AvailableSongsFragment extends Fragment{

    private List<Song> songs;
    private RecyclerView rView;
    private RecyclerViewAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutM;
    private MediaPlayer mediaPlayer;
    protected static Retrofit retrofit;
    protected static SongServices _songService;
    private Activity main;
    private boolean isPlaying = false;

    public AvailableSongsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_available_songs, container, false);
        getAllSongs(v);
        setHasOptionsMenu(true);
        return v;
    }

    private void getAllSongs(final View v){
        this.main = (MainActivity)getActivity();
        retrofit = new Retrofit.Builder()
                .baseUrl(SongServices.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        _songService = retrofit.create(SongServices.class);
        // Required empty public constructor
        try {
            Call<Song> songCall = _songService.GetSongList();
            songCall.enqueue(new Callback<Song>() {
                @Override
                public void onResponse(Call<Song> call, Response<Song> response) {
                    try {
                        songs = response.body().getSongs();
                        sortSongList();
                        rView = v.findViewById(R.id.recyclerView);
                        mLayoutM = new GridLayoutManager(v.getContext(), 2);
                        mAdapter = new RecyclerViewAdapter(songs, getActivity(), R.layout.recycler_view_item, new RecyclerViewAdapter.onItemClickListener() {
                            @Override
                            public void onItemClick(Song song, int i) {
                                showAlertForCustomDuel("Song Information", song);
                            }
                        });
                        rView.setHasFixedSize(true);
                        rView.setItemAnimator(new DefaultItemAnimator());
                        rView.setLayoutManager(mLayoutM);
                        rView.setAdapter(mAdapter);
                    }catch(NullPointerException npe){
                        System.out.println(npe);
                    }
                }
                @Override
                public void onFailure(Call<Song> call, Throwable t) {
                    System.out.println(t.getMessage());
                    Toast.makeText(getContext(),"Error de Conexion",Toast.LENGTH_SHORT).show();
                }
            });
        }catch (Exception e){
            System.out.println(e);
        }
    }

    private void showAlertForCustomDuel(String title, final Song song){
        AlertDialog.Builder builder = new AlertDialog.Builder(new ContextThemeWrapper(getContext(),R.style.AlertDialogCustom));
        if(title!=null) builder.setTitle(title);
        View vInflater = LayoutInflater.from(getContext()).inflate(R.layout.song_info,null);
        builder.setView(vInflater);
        ImageView songCover = vInflater.findViewById(R.id.imgViewInfo);
        TextView songTitle = vInflater.findViewById(R.id.txtViewTitleInfo);
        TextView songArtist = vInflater.findViewById(R.id.txtViewArtistInfo);
        TextView songAlbum = vInflater.findViewById(R.id.txtViewAlbumInfo);
        Button playPreview = vInflater.findViewById(R.id.btnPreview);
        final Button downloadSong = vInflater.findViewById(R.id.btnDownload);
        songTitle.setText("Title: "+song.getTitle());
        songArtist.setText("Artist: "+song.getArtist());
        songAlbum.setText("Album: "+song.getAlbum());

        if (song.getImage()!=null) {
            Glide.with(getActivity()).load(SongServices.URLGETIMAGESONG+song.getImage())
                    .centerCrop()
                    .placeholder(R.drawable.ic_music_note)
                    .error(R.drawable.ic_music_note)
                    .into(songCover);
        }else {
            Glide.with(getActivity()).load(R.drawable.ic_music_note)
                    .centerCrop()
                    .placeholder(R.drawable.ic_music_note)
                    .error(R.drawable.ic_music_note)
                    .into(songCover);
        }

        downloadSong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(),"Downloading: "+song.getTitle(),Toast.LENGTH_SHORT).show();
                download(song);
            }
        });

        playPreview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isPlaying==false){
                    Toast.makeText(getContext(),"Playing preview of: "+song.getTitle(),Toast.LENGTH_SHORT).show();
                    mediaPlayer = MediaPlayer.create(getContext(),setPreviews(song));
                    mediaPlayer.start();
                    isPlaying =true;
                }else{
                    Toast.makeText(getContext(),"Preview Stopped",Toast.LENGTH_SHORT).show();
                    mediaPlayer.stop();
                    isPlaying=false;
                }
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
        dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                // TODO Auto-generated method stub
                if(isPlaying){
                    mediaPlayer.stop();
                    isPlaying=false;
                }else{

                }
            }
        });
    }

    private int setPreviews(Song song){
        switch (song.getTitle()){
            case "Created Beauty":
                return R.raw.createdbeauty;
            case "MASQUERADE":
                return R.raw.masquerade;
            case "BLEMISH":
                return R.raw.blemish;
            case "TRUE":
                return R.raw.exist;
            case "VIRUS":
                return R.raw.virus;
            case "「70cm Shihou no Madobe」":
                return R.raw.shihou;
            case "Circle Pit":
                return R.raw.circlepit;
            default:
                return 0;
        }
    }

    private void sortSongList(){
        try {
            Collections.sort(songs, new Comparator<Song>() {
                public int compare(Song song1, Song song2) {
                    // TODO Auto-generated method stub
                    return song1.getTitle().compareToIgnoreCase(song2.getTitle());
                }
            });
        }catch (NullPointerException npe){

        }
    }

    private void download(final Song song){
         Retrofit.Builder builder = new Retrofit.Builder()
                 .baseUrl(SongServices.BASE_URL);

         Retrofit retrofit = builder.build();

         final SongServices downloadService =
                 retrofit.create(SongServices.class);

         Call<ResponseBody> call = downloadService.DownloadSong(song.getEntireSong());
         call.enqueue(new Callback<ResponseBody>() {
             @Override
             public void onResponse(Call<ResponseBody> call, final Response<ResponseBody> response) {
                 if (response.isSuccessful()) {
                     Log.d(TAG, "server contacted and has file");
                     new AsyncTask<Void, Void, Void>() {
                         boolean isDownloaded = false;
                         @Override
                         protected Void doInBackground(Void... voids) {
                             isDownloaded=writeResponseBodyToDisk(response.body(),song);
                             return null;
                         }
                         @Override
                         protected void onPostExecute(Void aVoid) {
                             super.onPostExecute(aVoid);
                             if (isDownloaded) {
                                 Toast.makeText(getContext(), "Song downloaded successfully", Toast.LENGTH_SHORT).show();
                             }else{
                                 Toast.makeText(getContext(),"Failed to download the song",Toast.LENGTH_SHORT).show();
                             }
                         }
                     }.execute();
                 }
                 else {
                     Log.d(TAG, "server contact failed");
                 }
             }
             @Override
             public void onFailure(Call<ResponseBody> call, Throwable t) {
                 Log.e(TAG, "error");
             }
         });
    }

    private boolean writeResponseBodyToDisk(ResponseBody body, Song song) {

        try {
            new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)+"/UnderSound").mkdir();
            File destinationFile = new File( Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)
                    +"/UnderSound/"+song.getTitle().toString()+".mp3");
            System.out.println(destinationFile.toString());

            InputStream is = null;
            OutputStream os = null;

            try {
                Log.d(TAG, "File Size=" + body.contentLength());

                is = body.byteStream();
                os = new FileOutputStream(destinationFile);

                byte data[] = new byte[4096];
                int count;
                int progress = 0;
                while ((count = is.read(data)) != -1) {
                    os.write(data, 0, count);
                    progress +=count;
                    Log.d(TAG, "Progress: " + progress + "/" + body.contentLength() + " >>>> " + (float) progress/body.contentLength());
                }

                os.flush();

                Log.d(TAG, "File saved successfully!");
                return true;
            } catch (IOException e) {
                e.printStackTrace();
                Log.d(TAG, "Failed to save the file!");
                return false;
            } finally {
                if (is != null) is.close();
                if (os != null) os.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
            Log.d(TAG, "Failed to save the file!");
            return false;
        }
    }

    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        menu.clear();
        getActivity().getMenuInflater().inflate(R.menu.menu, menu);
        MenuItem item = menu.findItem(R.id.menu_search);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(item);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                mAdapter.getFilter().filter(newText.toString().toLowerCase());
                return true;
            }
        });
    }
    
}
