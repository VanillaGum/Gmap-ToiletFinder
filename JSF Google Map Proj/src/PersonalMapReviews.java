public class PersonalMapReviews {
    public int rating;
    public String username;
    public String comments;

    public PersonalMapReviews() {
    }

    public PersonalMapReviews(int rating, String username, String comments) {
        this.rating = rating;
        this.username = username;
        this.comments = comments;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }
}
