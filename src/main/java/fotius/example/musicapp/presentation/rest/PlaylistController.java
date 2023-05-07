package fotius.example.musicapp.presentation.rest;

import fotius.example.musicapp.domain.PlaylistService;
import fotius.example.musicapp.domain.model.Playlist;
import fotius.example.musicapp.domain.model.Song;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/playlists")
@RequiredArgsConstructor
public class PlaylistController {

    private final PlaylistService playlistService;

    @PostMapping
    public Playlist create(@RequestBody PlaylistService.CreatePlaylistCommand request) {
        return playlistService.create(request);
    }

    @PatchMapping("/{id}")
    public Playlist addSong(
            @PathVariable("id") Long playlistId,
            @RequestParam("song_id") Long songId
    ) {
        return playlistService.addSong(playlistId, songId);
    }

    @DeleteMapping("/{playlist_id}/songs/{song_id}")
    public Playlist deleteSong(
            @PathVariable("playlist_id") Long playlistId,
            @PathVariable("song_id") Long songId
    ) {
        return playlistService.deleteSong(playlistId, songId);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(
            @PathVariable("id") Long id
    ) {
        playlistService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping( "/{id}")
    public List<Song> getAllSongs(
            @PathVariable("id") Long id
    ) {
        return playlistService.getById(id).getSongs();
    }
}
