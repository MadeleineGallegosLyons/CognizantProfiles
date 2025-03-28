package app.domain;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "section_categories")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SectionCategory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
}
