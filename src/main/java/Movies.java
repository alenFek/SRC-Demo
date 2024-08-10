import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.validation.constraints.NotBlank;

import java.util.List;

@Entity
public class Movies {

    @Id
    @NotBlank
    private long imdbID;

    @NotBlank
    private String title;

    private int year;

    private String description;

    @ElementCollection
    @Lob
    private List<byte[]> images;

    public Movies() {}

    public Movies(long imdbID, String title, int year, String description, List<byte[]> images) {
        this.imdbID = imdbID;
        this.title = title;
        this.year = year;
        this.description = description;
        this.images = images;
    }

    public long getImdbID() {
        return imdbID;
    }

    public void setImdbID(long imdbID) {
        this.imdbID = imdbID;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<byte[]> getImages() {
        return images;
    }

    public void setImages(List<byte[]> images) {
        this.images = images;
    }
}

