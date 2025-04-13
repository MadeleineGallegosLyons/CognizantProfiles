package app.dto;

import lombok.Data;

@Data
public class ProfileSearchResultDto {
    private Long id;
    private String name;
    private String sharePointRef;
    private String executiveSummary;
}
