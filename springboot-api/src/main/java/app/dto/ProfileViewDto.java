package app.dto;

import lombok.Data;
import java.util.List;

@Data
public class ProfileViewDto {
    private Long id;
    private String name;
    private String sharePointRef;
    private String email;
    private String jobTitle;
    private List<SectionDto> sections;
}
