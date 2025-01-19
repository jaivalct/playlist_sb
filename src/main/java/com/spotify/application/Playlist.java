package com.spotify.application;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.util.ArrayList;
import org.springframework.stereotype.Component;
import org.springframework.context.annotation.Scope;

@Component
@Scope("prototype")
public class Playlist {
    private ArrayList<Song> playlist;

    public Playlist() {
        playlist = new ArrayList<>(); // Initialize playlist
    }

    public void readFromCsv(String filepath) {
        String line;

        try (BufferedReader br = new BufferedReader(new FileReader(filepath))) {
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");
                if (values.length == 2) { // Ensure proper format
                    Song song = new Song(values[0], values[1]);
                    playlist.add(song);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void writeToCsv(String filepath) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(filepath,true))) {
            for (Song song : playlist) {
                bw.write(song.getSongname() + "," + song.getSongartist());
                bw.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public ArrayList<Song> getPlaylist() {
        return playlist;
    }

    public void addSong(Song song) {
        playlist.add(song);
    }

    public int addSongsFromCsv(String filepath) {
        String line;
        int songsAdded = 0;

        try (BufferedReader br = new BufferedReader(new FileReader(filepath))) {
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");
                if (values.length == 2) { // Ensure proper format
                    Song song = new Song(values[0].trim(), values[1].trim());
                    playlist.add(song);
                    songsAdded++;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return songsAdded;
    }
}
