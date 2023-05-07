package fotius.example.musicapp.presentation.rest;

import fotius.example.musicapp.domain.PlaylistService;
import fotius.example.musicapp.domain.model.Playlist;
import fotius.example.musicapp.domain.model.Song;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/playlists")
@RequiredArgsConstructor
public class PlaylistController {
    private final PlaylistService playlistService;

    @PostMapping(
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @ResponseBody
    public Playlist create(@RequestBody PlaylistService.CreatePlaylistCommand request) {
        return playlistService.create(request);
    }

    @PatchMapping(
            path = "/{id}",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @ResponseBody
    public Playlist addSong(
            @PathVariable("id") Long playlistId,
            @RequestParam("song_id") Long songId
    ) {
        return playlistService.addSong(playlistId, songId);
    }

    @DeleteMapping (
            path = "/{playList_id}/songs/{song_id}",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @ResponseBody
    public Playlist deleteSong(
            @PathVariable("playList_id") Long playlistId,
            @PathVariable("song_id") Long songId
    ) {
        return playlistService.deleteSong(playlistId, songId);
    }

    @GetMapping(
            path = "/{id}",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public List<Song> getallSongs(
            @PathVariable("id") Long id
    ) {
        return playlistService.getById(id).getSongs();
    }

}
