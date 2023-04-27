package fotius.example.musicapp.presentation.rest;

import fotius.example.musicapp.domain.PlaylistService;
import fotius.example.musicapp.domain.model.Playlist;
import fotius.example.musicapp.domain.model.PlaylistPage;
import fotius.example.musicapp.domain.model.Song;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/playlists")
@RequiredArgsConstructor
public class PlaylistController {
    private final PlaylistService playlistService;

    @GetMapping("{index}")
    public ResponseEntity<Playlist> getById(@PathVariable("index") long index) {
        Playlist playlist = playlistService.getById(index);
        return ResponseEntity.ok(playlist);
    }


    @PostMapping
    public ResponseEntity<Playlist> create(@RequestBody Playlist playlist) {
        Playlist createdPlaylist = playlistService.create(PlaylistService.CreatePlaylistCommand.builder()
                .name(playlist.getName())
                .author(playlist.getAuthor())
                .build());
        return ResponseEntity.created(URI.create("/api/playlists/" + createdPlaylist.getId()))
                .body(createdPlaylist);
    }


    @PostMapping("{index}/songs/{songIndex}")
    public Playlist addSong(@PathVariable("index") long index, @PathVariable("songIndex") long songIndex) {
        return playlistService.addSong(index, songIndex);
    }


    @DeleteMapping("{index}")
    public ResponseEntity<Void> deleteById(@PathVariable("index") long index) {
        playlistService.delete(index);
        return ResponseEntity.noContent().build();
    }


    @DeleteMapping("{index}/songs/{songIndex}")
    public ResponseEntity<Playlist> deleteSong(@PathVariable("index") long index, @PathVariable("songIndex") long songIndex) {
        Playlist playlist = playlistService.deleteSong(index, songIndex);
        return ResponseEntity.ok(playlist);
    }



    @GetMapping()
    public PlaylistPage getAllSorted(
            @RequestParam(value = "author", required = false) String author,
            @RequestParam(value = "sort_type", defaultValue = "ASC") Sort.Direction sort_direction,
            @RequestParam(value = "page", defaultValue = "0") int page) {
        return this.playlistService.getAllSorted(author, sort_direction, page);
    }

}
