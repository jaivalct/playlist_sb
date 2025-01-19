package com.spotify.application.controllers;

import com.spotify.application.Song;
import com.spotify.application.Playlist;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import java.util.Map;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;
import org.springframework.context.ApplicationContext;

@RestController
public class SongController {

    @Autowired
    private ApplicationContext context;

    private Map<String, Playlist> playlists = new HashMap<>();

    @PostMapping("/playlists/create/{playlistName}")
    public ResponseEntity<String> addSongsFromCsv(@PathVariable String playlistName) {
        Playlist playlist = playlists.computeIfAbsent(playlistName, k -> new Playlist());
        int songsAdded = playlist.addSongsFromCsv("addsong.csv");
        playlist.writeToCsv(playlistName + ".csv");
        return ResponseEntity.ok(songsAdded + " songs added successfully from CSV");
    }
    
    @PostMapping("/playlists/addsong/{playlistName}")
    public ResponseEntity<String> addSong(@PathVariable String playlistName, @RequestBody Song song) {
        Playlist playlist = playlists.computeIfAbsent(playlistName, 
            k -> context.getBean(Playlist.class));
        playlist.addSong(song);
        playlist.writeToCsv(playlistName + ".csv");
        return ResponseEntity.ok("Song added successfully");
    }

    @GetMapping("/playlists/{playlistName}")
    public ResponseEntity<Playlist> getPlaylist(@PathVariable String playlistName) {
        Playlist playlist = playlists.get(playlistName);
        if (playlist == null) {
            playlist = new Playlist();
            playlist.readFromCsv(playlistName + ".csv");
            playlists.put(playlistName, playlist);
        }
        return ResponseEntity.ok(playlist);
    }

    @GetMapping("/playlists")
    public ResponseEntity<List<String>> getAllPlaylists() {
        return ResponseEntity.ok(new ArrayList<>(playlists.keySet()));
    }

   
}
