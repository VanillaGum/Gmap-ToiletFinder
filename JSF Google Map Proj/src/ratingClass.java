public class ratingClass {
    private int rating;
    private int toiletId;
    private int type;
    //Type 0 = Suggestion toilets
    //Type 1 = Approved toilets

    public ratingClass(int rating, int toiletId, int type) {
        this.rating = rating;
        this.toiletId = toiletId;
        this.type = type;
    }
}
