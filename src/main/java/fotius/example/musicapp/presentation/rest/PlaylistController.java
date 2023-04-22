package fotius.example.musicapp.presentation.rest;

import fotius.example.musicapp.domain.PlaylistService;
import fotius.example.musicapp.domain.mapper.PlaylistMapper;
import fotius.example.musicapp.domain.model.Playlist;
import fotius.example.musicapp.domain.model.ResponsePage;
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

    private final PlaylistMapper playlistMapper;
    private final PlaylistService playlistService;

    @PostMapping
    public ResponseEntity<Playlist> create(@RequestBody Playlist playlist) {
        Playlist createdPlaylist = playlistService.create(playlistMapper.toCreateCommand(playlist));
        return ResponseEntity.created(URI.create("/api/playlists/%d".formatted(createdPlaylist.getId())))
                .body(createdPlaylist);
    }

    @PostMapping("{id}/songs/{songId}")
    public Playlist addSong(@PathVariable("id") long id, @PathVariable("songId") long songId) {
        return playlistService.addSong(id, songId);
    }

    @DeleteMapping("{id}/songs/{songId}")
    public ResponseEntity<Playlist> deleteSong(@PathVariable("id") long id, @PathVariable("songId") long songId) {
        Playlist playlist = playlistService.deleteSong(id, songId);
        return ResponseEntity.ok(playlist);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteById(@PathVariable("id") long id) {
        playlistService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("{id}/songs")
    public List<Song> getSongsById(@PathVariable("id") long id) {
        return playlistService.findAllSongsById(id);
    }

    @GetMapping
    public ResponsePage<Playlist> getAllByAuthor(
            @RequestParam("author") String author,
            @RequestParam(value = "sort", defaultValue = "ASC") Sort.Direction direction,
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "perPage", defaultValue = "10") int perPage) {
        Slice<Playlist> playlists = playlistService.findByAuthorOrderedByCreatedAt(author, page, perPage, direction);
        UriComponents pageUrl = UriComponentsBuilder.fromPath("/api/playlists")
                .queryParam("author", author)
                .queryParam("sort", direction)
                .queryParam("page", "{page}")
                .queryParam("perPage", perPage)
                .build();
        return ResponsePage.fromSlice(playlists, pageUrl);
    }
}
