package fotius.example.musicapp.domain.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Value;
import org.springframework.data.domain.Slice;
import org.springframework.web.util.UriComponents;

import java.util.List;

@Value
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResponsePage<E> {

    List<E> content;
    String previous;
    String next;

    public static <E> ResponsePage<E> fromSlice(Slice<E> slice, UriComponents pageUrl) {
        return ResponsePage.<E>builder()
                .content(slice.getContent())
                .next(slice.hasNext() ? pageUrl.expand(slice.getNumber() + 1).toUriString() : null)
                .previous(slice.hasPrevious() ? pageUrl.expand(slice.getNumber() - 1).toUriString() : null)
                .build();
    }
}
