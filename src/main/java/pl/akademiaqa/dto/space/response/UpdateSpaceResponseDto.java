package pl.akademiaqa.dto.space.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
public class UpdateSpaceResponseDto {
    private String id;
    private String name;
}
