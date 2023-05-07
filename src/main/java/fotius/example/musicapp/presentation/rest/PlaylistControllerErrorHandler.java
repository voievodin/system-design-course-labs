package fotius.example.musicapp.presentation.rest;

import fotius.example.musicapp.domain.error.SongIsAlreadyInPlaylistException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;
import java.util.Collections;

@ControllerAdvice
public class PlaylistControllerErrorHandler {
    @ExceptionHandler(SongIsAlreadyInPlaylistException.class)
    public ResponseEntity<ApiError> handleSongIsAlreadyInPlaylistException(SongIsAlreadyInPlaylistException ex) {
        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(
                        new ApiError(
                                ex.getMessage(),
                                LocalDateTime.now(),
                                Collections.emptyList()
                        )
                );
    }
}
