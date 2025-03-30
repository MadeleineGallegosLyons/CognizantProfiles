package app.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Builder;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SectionDto {
    @JsonProperty("section_name")
    private String sectionName;

    @JsonProperty("section_content")
    private Object sectionContent;  // Can be String, List, or Map
}
