package fotius.example.musicapp.domain.mapper;

import fotius.example.musicapp.domain.PlaylistService;
import fotius.example.musicapp.domain.model.Playlist;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

@Component
public class PlaylistMapper {

    public PlaylistService.CreatePlaylistCommand toCreateCommand(Playlist playlist) {
        PlaylistService.CreatePlaylistCommand createPlaylistCommand = new PlaylistService.CreatePlaylistCommand();
        BeanUtils.copyProperties(playlist, createPlaylistCommand);
        return createPlaylistCommand;
    }
}
