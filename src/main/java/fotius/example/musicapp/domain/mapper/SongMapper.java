package fotius.example.musicapp.domain.mapper;

import fotius.example.musicapp.domain.SongService;
import fotius.example.musicapp.domain.model.Song;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

@Component
public class SongMapper {

    public SongService.CreateSongCommand toCreateSongCommand(Song song) {
        SongService.CreateSongCommand command = new SongService.CreateSongCommand();
        BeanUtils.copyProperties(song, command);
        return command;
    }

    public SongService.UpdateSongCommand toUpdateSongCommand(Song song) {
        SongService.UpdateSongCommand command = new SongService.UpdateSongCommand();
        BeanUtils.copyProperties(song, command);
        return command;
    }
}
