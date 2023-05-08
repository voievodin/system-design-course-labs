package fotius.example.musicapp.presentation.rest;

import fotius.example.musicapp.domain.PlaylistService;
import fotius.example.musicapp.domain.model.Playlist;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/playlists")
@RequiredArgsConstructor
public class PlaylistController {

    private final PlaylistService playlistService;

    // Can be used as:
    // POST localhost:8080/api/playlists
    @RequestMapping(
            method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @ResponseBody
    public Playlist createPlaylist(@RequestBody PlaylistCreateRequest request) {
        return playlistService
                .create(PlaylistService
                        .CreatePlaylistCommand
                        .builder()
                        .author(request.author)
                        .name(request.name)
                        .build());
    }

    // Can be used as:
    // POST localhost:8080/api/playlists
    @RequestMapping(
            method = RequestMethod.POST,
            path = "{id}/songs/{songId}"
    )
    @ResponseBody
    public Playlist addSongToPlaylist(@PathVariable("id") Long id, @PathVariable("songId") Long songId) {
        return playlistService.addSong(id, songId);
    }

    // Can be used as:
    // DELETE localhost:8080/api/playlists/{id}/songs/{songId}
    @RequestMapping(
            method = RequestMethod.DELETE,
            path = "{id}/songs/{songId}"
    )
    @ResponseBody
    public Playlist deleteSongFromPlaylist(@PathVariable("id") Long id, @PathVariable("songId") Long songId) {
        return playlistService.deleteSong(id, songId);
    }

    // Can be used as:
    // DELETE localhost:8080/api/playlists/{id}
    @RequestMapping(
            method = RequestMethod.DELETE,
            path = "{id}"
    )
    @ResponseBody
    public void deletePlaylist(@PathVariable("id") Long id) {
        playlistService.delete(id);
    }

    // Can be used as:
    // GET localhost:8080/api/playlists/{id}
    @RequestMapping(
            method = RequestMethod.GET,
            path = "{id}"
    )
    @ResponseBody
    public Playlist getAllSongsFromPlaylist(@PathVariable("id") Long id) {
        return playlistService.getById(id);
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class PlaylistCreateRequest {
        private String name;
        private String author;
    }

}