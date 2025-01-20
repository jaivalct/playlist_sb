package com.spotify.application;

public class Song {
    private String songname;
    private String songartist;
    

    public Song(String songName, String songArtist) {
        this.songname = songName;
        this.songartist = songArtist;
    }

    public String getSongartist() {
        return songartist;
    }

    public void setSongartist(String songartist) {
        this.songartist = songartist;
    }

    public String getSongname() {
        return songname;
    }

    public void setSongname(String songname) {
        this.songname = songname;
    }
}