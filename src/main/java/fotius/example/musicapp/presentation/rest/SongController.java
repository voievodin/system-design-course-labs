package fotius.example.musicapp.presentation.rest;

import fotius.example.musicapp.domain.SongService;
import fotius.example.musicapp.domain.model.Song;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.cassandra.CassandraProperties;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.concurrent.ThreadLocalRandom;

@RestController
@RequestMapping("/api/songs")
@RequiredArgsConstructor
public class SongController {

    private final SongService songService;

    @PostMapping(
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @ResponseBody
    public Song create(@RequestBody SongService.CreateSongCommand request) {
        return songService.create(request);
    }

    @GetMapping(
        path = "/{id}",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @ResponseBody
    public Song getById(@PathVariable("id") Long id) {
        return songService.getById(id);
    }

    @PatchMapping(
            path = "/{id}",
            produces = MediaType.APPLICATION_JSON_VALUE

    )
    @ResponseBody
    public Song update(
            @PathVariable("id") Long id,
            @RequestBody SongService.UpdateSongCommand request
            ) {
        return  songService.update(id, request);
    }

    @DeleteMapping(path = "/{id}",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<Void> delete(@PathVariable("id") Long id) {
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
            UriComponents nextPageUri = UriComponentsBuilder.fromPath("/api/songs")
                    .queryParam("sort", direction)
                    .queryParam("page", page + 1)
                    .queryParam("perPage", perPage)
                    .build();
            if (Objects.nonNull(author)) {
                nextPageUri.getQueryParams().add("author", author);
            }
            headers.add(
                    "nextPage",
                    nextPageUri.toUriString()
            );
        }
        if (songs.hasPrevious()) {
            UriComponents prevPageUri = UriComponentsBuilder.fromPath("/api/songs")
                    .queryParam("sort", direction)
                    .queryParam("page", page - 1)
                    .queryParam("perPage", perPage)
                    .build();
            if (Objects.nonNull(author)) {
                prevPageUri.getQueryParams().add("author", author);
            }
            headers.add(
                    "prevPage",
                    prevPageUri.toUriString()
            );
        }

        return ResponseEntity
                .ok()
                .headers(headers)
                .body(songs);
    }




}
