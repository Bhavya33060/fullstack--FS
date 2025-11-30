package travel.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "trip")
@JsonInclude(JsonInclude.Include.NON_NULL)  // ✅ ensures JSON returns all fields
public class Trip {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String destination;
    private String dateRange;
    private String tripType;
    private int travelers;
    private long estimatedCost;
    private long budget;
    @ElementCollection(fetch = FetchType.EAGER)
   
    @CollectionTable(name = "trip_tags", joinColumns = @JoinColumn(name = "trip_id"))
    @Column(name = "tag")
    private List<String> tags = new ArrayList<>();

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "trip_days", joinColumns = @JoinColumn(name = "trip_id"))
    @Column(name = "day")
    private List<String> days = new ArrayList<>();
}
