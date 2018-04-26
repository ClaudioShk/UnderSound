package com.example.shioka.navigationdrawer.Models;

import java.util.List;

/**
 * Created by Shioka on 28/10/2017.
 */

public class Song {

    private String title;
    private String artist;
    private String album;
    private String image;
    private int preview;
    private String entireSong;
    private List<Song> songs;

    /*public Song(){}

    public Song(String name, String artist, String album, int image, int preview){
        setTitle(name);
        setArtist(artist);
        setAlbum(album);
        setImage(image);
        setPreview(preview);
    }*/

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public String getAlbum() {
        return album;
    }

    public void setAlbum(String album) {
        this.album = album;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public int getPreview() {
        return preview;
    }

    public void setPreview(int preview) {
        this.preview = preview;
    }

    public List<Song> getSongs() {
        return songs;
    }

    public void setSongs(List<Song> songs) {
        this.songs = songs;
    }

    public String getEntireSong() {
        return entireSong;
    }

    public void setEntireSong(String entireSong) {
        this.entireSong = entireSong;
    }
}
