package app.domain;

import jakarta.persistence.*;
import lombok.*;
import app.domain.Profile;

@Entity
@Table(name = "section_contents")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SectionContent {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "profile_id")
    private Profile profile;

    @ManyToOne
    @JoinColumn(name = "section_category_id")
    private SectionCategory sectionCategory;

    @Column(name = "section_content")
    private String sectionContent;
}
