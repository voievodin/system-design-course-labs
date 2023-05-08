package fotius.example.musicapp.presentation.rest;

import fotius.example.musicapp.domain.SongService;
import fotius.example.musicapp.domain.model.Song;
import lombok.*;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Sort;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/api/songs")
@RequiredArgsConstructor
public class SongController {

    private final SongService songService;

    // Can be used as:
    // POST localhost:8080/api/songs
    @RequestMapping(
            method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @ResponseBody
    public Song createSong(@RequestBody SongCreationRequest request) {
        return songService
                .create(SongService
                        .CreateSongCommand
                        .builder()
                        .artist(request.artist)
                        .location(request.location)
                        .lyrics(request.lyrics)
                        .name(request.name)
                        .build());
    }

    // Can be used as:
    // GET localhost:8080/api/songs/123
    @RequestMapping(
            method = RequestMethod.GET,
            path = "{id}",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @ResponseBody
    public Song getSong(@PathVariable("id") Long id) {
        return songService.getById(id);
    }

    // Can be used as:
    // PATCH localhost:8080/api/songs/123
    @RequestMapping(
            method = RequestMethod.PATCH,
            path = "{id}",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @ResponseBody
    public Song updateSong(@PathVariable("id") Long id, @RequestBody() SongUpdateRequest request) {
        return songService
                .update(id, SongService.
                        UpdateSongCommand
                        .builder()
                        .location(request.location)
                        .addedLikes(Integer.parseInt(request.likes))
                        .lyrics(request.lyrics)
                        .build());
    }

    // Can be used as:
    // DELETE localhost:8080/api/songs/123
    @RequestMapping(
            method = RequestMethod.DELETE,
            path = "{id}"
    )
    @ResponseBody
    public void deleteSong(@PathVariable("id") Long id) {
        songService.delete(id);
    }

    // Can be used as:
    // GET localhost:8080/api/songs?artist=name&sort=ASC&page=2&perPage=10
    @RequestMapping(
            method = RequestMethod.GET
    )
    @ResponseBody
    public SongsPage getAllWithArtist(
            @RequestParam(name = "artist", defaultValue = "") String artist,
            @RequestParam(name = "sort", defaultValue = "ASC") Sort.Direction sort,
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "perPage", defaultValue = "10") int perPage
    ) {
        Slice<Song> serviceResponse = !Objects.equals(artist, "")
                ? songService.findByArtistOrderedByAddedAt(artist, page, perPage, sort) :
                songService.findAllOrderedByAddedAt(page, perPage, sort);

        UriComponents currentUrl = UriComponentsBuilder.fromPath("/api/songs")
                .queryParam("artist", artist)
                .queryParam("sort", sort)
                .queryParam("page", "{page}")
                .build();

        return SongsPage
                .builder()
                .songs(serviceResponse.getContent())
                .pageNumber(page)
                .nextPageUrl(serviceResponse.hasNext()
                        ? currentUrl.expand(page + 1).toUriString()
                        : null)
                .previousPageUrl(serviceResponse.hasPrevious()
                        ? currentUrl.expand(page - 1).toUriString()
                        : null)
                .build();
    }

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class SongsPage {
        private List<Song> songs;
        private int pageNumber;
        private String nextPageUrl;
        private String previousPageUrl;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class SongCreationRequest {
        private String name;
        private String artist;
        private String location;
        private String lyrics;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class SongUpdateRequest {
        private String location;
        private String likes;
        private String lyrics;
    }

}