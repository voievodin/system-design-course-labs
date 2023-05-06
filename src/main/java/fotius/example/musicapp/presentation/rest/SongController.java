package fotius.example.musicapp.presentation.rest;

import fotius.example.musicapp.domain.SongService;
import fotius.example.musicapp.domain.model.Song;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Objects;

@RestController
@RequestMapping("/api/songs")
@RequiredArgsConstructor
public class SongController {
    private final SongService songService;

    @PostMapping
    public Song create(@RequestBody SongService.CreateSongCommand request) {
        return songService.create(request);
    }

    @GetMapping("/{id}")
    public Song getById(
        @PathVariable("id") Long id
    ) {
        return songService.getById(id);
    }

    @PatchMapping("/{id}")
    public Song update(
        @PathVariable("id") Long id,
        @RequestBody SongService.UpdateSongCommand request
    ) {
        return songService.update(id, request);
    }

    @DeleteMapping( "/{id}")
    public ResponseEntity<Void> delete(
            @PathVariable Long id
    ) {
        songService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<Slice<Song>> getAllByAuthor(
            @RequestParam(value = "author", required = false) String author,
            @RequestParam(value = "sort", defaultValue = "ASC") Sort.Direction direction,
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "perPage", defaultValue = "10") int perPage
    ) {
        Slice<Song> songs = Objects.isNull(author)
                ? songService.findAllOrderedByAddedAt(page, perPage, direction)
                : songService.findByArtistOrderedByAddedAt(author, page, perPage, direction);

        HttpHeaders headers = new HttpHeaders();
        if (songs.hasNext()) {
            headers.add(
                    "next",
                    UriComponentsBuilder.fromPath("/api/songs")
                            .queryParam("author", author)
                            .queryParam("sort", direction)
                            .queryParam("page", page + 1)
                            .queryParam("perPage", perPage)
                            .build().toUriString()
            );
        }
        if (songs.hasPrevious()) {
            headers.add(
                    "prev",
                    UriComponentsBuilder.fromPath("/api/songs")
                            .queryParam("author", author)
                            .queryParam("sort", direction)
                            .queryParam("page", page - 1)
                            .queryParam("perPage", perPage)
                            .build().toUriString()
            );
        }

        return ResponseEntity
                .ok()
                .headers(headers)
                .body(songs);
    }
}
