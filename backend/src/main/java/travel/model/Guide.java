package travel.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "guides")
public class Guide {

    @Id
    @Column(length = 100)
    private String id;

    @Column(nullable = false, length = 250)
    private String title;

    @Column(length = 200)
    private String location;

    @ElementCollection
    @CollectionTable(name = "guide_tags", joinColumns = @JoinColumn(name = "guide_id"))
    @Column(name = "tag")
    private List<String> tags = new ArrayList<>();

    @Column(length = 1024)
    private String thumbnail;

    @Column(length = 1024)
    private String hero;

    @Column(columnDefinition = "TEXT")
    private String excerpt;

    private String author;
    private String duration;
    private Double rating;
    private Boolean favorite;

    @Column(columnDefinition = "TEXT")
    private String directions;

    private Double coordsLat;
    private Double coordsLng;

    private String status;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    public Guide() {}

    @PrePersist
    public void onInsert() {
        if (createdAt == null) createdAt = LocalDateTime.now();
        if (status == null) status = "pending";
        if (favorite == null) favorite = false;
        if (tags == null) tags = new ArrayList<>();
    }

    // GETTERS & SETTERS
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getLocation() { return location; }
    public void setLocation(String location) { this.location = location; }

    public List<String> getTags() { return tags; }
    public void setTags(List<String> tags) { this.tags = tags; }

    public String getThumbnail() { return thumbnail; }
    public void setThumbnail(String thumbnail) { this.thumbnail = thumbnail; }

    public String getHero() { return hero; }
    public void setHero(String hero) { this.hero = hero; }

    public String getExcerpt() { return excerpt; }
    public void setExcerpt(String excerpt) { this.excerpt = excerpt; }

    public String getAuthor() { return author; }
    public void setAuthor(String author) { this.author = author; }

    public String getDuration() { return duration; }
    public void setDuration(String duration) { this.duration = duration; }

    public Double getRating() { return rating; }
    public void setRating(Double rating) { this.rating = rating; }

    public Boolean getFavorite() { return favorite; }
    public void setFavorite(Boolean favorite) { this.favorite = favorite; }

    public String getDirections() { return directions; }
    public void setDirections(String directions) { this.directions = directions; }

    public Double getCoordsLat() { return coordsLat; }
    public void setCoordsLat(Double coordsLat) { this.coordsLat = coordsLat; }

    public Double getCoordsLng() { return coordsLng; }
    public void setCoordsLng(Double coordsLng) { this.coordsLng = coordsLng; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
}
