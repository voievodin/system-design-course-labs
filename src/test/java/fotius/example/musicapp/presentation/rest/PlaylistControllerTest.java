package fotius.example.musicapp.presentation.rest;

import fotius.example.musicapp.domain.model.Playlist;
import fotius.example.musicapp.domain.model.PlaylistPage;
import fotius.example.musicapp.domain.model.Song;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class PlaylistControllerTest {

    @Autowired
    TestRestTemplate restTemplate;

    @Test
    void createsAPlaylist() {
        final ResponseEntity<Playlist> response = restTemplate.postForEntity(
            "/api/playlists",
            Playlist.builder()
                    .name("My Playlist")
                    .author("Author")
                    .build(),
            Playlist.class
        );

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getBody().getId());
        assertEquals(response.getBody().getName(), "My Playlist");
        assertEquals(response.getBody().getAuthor(), "Author");
    }

    @Test
    void addsASongToAPlaylist() {
        // create song
        final ResponseEntity<Song> songResponse = restTemplate.postForEntity(
            "/api/songs",
            Song.builder()
                    .name("My Song")
                    .artist("Artist")
                    .location("Location")
                    .build(),
            Song.class
        );

        assertEquals(HttpStatus.CREATED, songResponse.getStatusCode());
        assertNotNull(songResponse.getBody().getId());
        assertEquals(songResponse.getBody().getName(), "My Song");
        assertEquals(songResponse.getBody().getArtist(), "Artist");
        assertEquals(songResponse.getBody().getLocation(), "Location");

        // create playlist
        final ResponseEntity<Playlist> playlistResponse = restTemplate.postForEntity(
            "/api/playlists",
            Playlist.builder()
                    .name("My Playlist")
                    .author("Author")
                    .build(),
            Playlist.class
        );

        assertEquals(HttpStatus.CREATED, playlistResponse.getStatusCode());
        assertNotNull(playlistResponse.getBody().getId());
        assertEquals(playlistResponse.getBody().getName(), "My Playlist");
        assertEquals(playlistResponse.getBody().getAuthor(), "Author");

        // add song to playlist
        final ResponseEntity<Playlist> addSongResponse = restTemplate.postForEntity(
            "/api/playlists/" + playlistResponse.getBody().getId() + "/songs/" + songResponse.getBody().getId(),
            null,
            Playlist.class
        );

        assertEquals(HttpStatus.OK, addSongResponse.getStatusCode());
        assertNotNull(addSongResponse.getBody().getId());
        assertEquals(addSongResponse.getBody().getName(), "My Playlist");
        assertEquals(addSongResponse.getBody().getAuthor(), "Author");
        assertEquals(addSongResponse.getBody().getSongs().size(), 1);

        // delete song from playlist
        restTemplate.delete("/api/playlists/" + playlistResponse.getBody().getId() + "/songs/" + songResponse.getBody().getId());

        // check if song is in playlist
        final ResponseEntity<Playlist> getPlaylistResponse = restTemplate.getForEntity(
                "/api/playlists/" + playlistResponse.getBody().getId(),
                Playlist.class
        );

        assertEquals(HttpStatus.OK, getPlaylistResponse.getStatusCode());
        assertNotNull(getPlaylistResponse.getBody().getId());
        assertEquals(getPlaylistResponse.getBody().getName(), "My Playlist");
        assertEquals(getPlaylistResponse.getBody().getAuthor(), "Author");
        assertEquals(getPlaylistResponse.getBody().getSongs().size(), 0);
    }

    @Test
    void deletesAPlaylist() {
        // create playlist
        final ResponseEntity<Playlist> playlistResponse = restTemplate.postForEntity(
            "/api/playlists",
            Playlist.builder()
                    .name("My Playlist")
                    .author("Author")
                    .build(),
            Playlist.class
        );

        assertEquals(HttpStatus.CREATED, playlistResponse.getStatusCode());
        assertNotNull(playlistResponse.getBody().getId());
        assertEquals(playlistResponse.getBody().getName(), "My Playlist");
        assertEquals(playlistResponse.getBody().getAuthor(), "Author");

        // delete playlist
        restTemplate.delete("/api/playlists/" + playlistResponse.getBody().getId());

        // check if playlist is deleted
        final ResponseEntity<Playlist> getPlaylistResponse = restTemplate.getForEntity(
                "/api/playlists/" + playlistResponse.getBody().getId(),
                Playlist.class
        );

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, getPlaylistResponse.getStatusCode());
    }

    @Test
    void getsAPlaylist() {
        // create playlist
        final ResponseEntity<Playlist> playlistResponse = restTemplate.postForEntity(
            "/api/playlists",
            Playlist.builder()
                    .name("My Playlist")
                    .author("Author")
                    .build(),
            Playlist.class
        );

        assertEquals(HttpStatus.CREATED, playlistResponse.getStatusCode());
        assertNotNull(playlistResponse.getBody().getId());
        assertEquals(playlistResponse.getBody().getName(), "My Playlist");
        assertEquals(playlistResponse.getBody().getAuthor(), "Author");

        // get playlist
        final ResponseEntity<Playlist> getPlaylistResponse = restTemplate.getForEntity(
                "/api/playlists/" + playlistResponse.getBody().getId(),
                Playlist.class
        );

        assertEquals(HttpStatus.OK, getPlaylistResponse.getStatusCode());
        assertNotNull(getPlaylistResponse.getBody().getId());
        assertEquals(getPlaylistResponse.getBody().getName(), "My Playlist");
        assertEquals(getPlaylistResponse.getBody().getAuthor(), "Author");
    }

    @Test
    void getsOrderedSongsInPlaylist(){
        // create playlist
        final ResponseEntity<Playlist> playlistResponse = restTemplate.postForEntity(
            "/api/playlists",
            Playlist.builder()
                    .name("My Playlist")
                    .author("Author")
                    .build(),
            Playlist.class
        );

        assertEquals(HttpStatus.CREATED, playlistResponse.getStatusCode());
        assertNotNull(playlistResponse.getBody().getId());
        assertEquals(playlistResponse.getBody().getName(), "My Playlist");
        assertEquals(playlistResponse.getBody().getAuthor(), "Author");

        // get playlist
        final ResponseEntity<PlaylistPage> getPlaylistResponse = restTemplate.getForEntity(
                "/api/playlists?page=0&sort_type=ASC",
                PlaylistPage.class
        );

        assertEquals(HttpStatus.OK, getPlaylistResponse.getStatusCode());
        assertNotNull(getPlaylistResponse.getBody().getContent());
        assertEquals(getPlaylistResponse.getBody().getPageNumber(), 0);
        assertEquals(getPlaylistResponse.getBody().getContent().get(0).getName(), "My Playlist");
        assertEquals(getPlaylistResponse.getBody().getContent().get(0).getAuthor(), "Author");

        // delete playlist
        restTemplate.delete("/api/playlists/" + playlistResponse.getBody().getId());

        // check if playlist is deleted
        final ResponseEntity<Playlist> getPlaylistResponse2 = restTemplate.getForEntity(
                "/api/playlists/" + playlistResponse.getBody().getId(),
                Playlist.class
        );

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, getPlaylistResponse2.getStatusCode());
    }

}
