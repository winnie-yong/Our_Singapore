package sg.edu.rp.c346.id20013783.oursingapore;

import java.io.Serializable;

public class Island implements Serializable {
    private int id;
    private String title;
    private String description;
    private int area;
    private int stars;

    public Island(String title, String description, int area, int stars) {
        this.title = title;
        this.description = description;
        this.area = area;
        this.stars = stars;
    }

    public Island(int id, String title, String description, int area, int stars) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.area = area;
        this.stars = stars;
    }
    public int getId() {
        return id;
    }

    public Island setId(int id) {
        this.id = id;
        return this;
    }

    public String getTitle() {
        return title;
    }

    public Island setTitle(String title) {
        this.title = title;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public Island setDescription(String description) {
        this.description = description;
        return this;
    }

    public int getArea() {
        return area;
    }

    public Island setArea(int area) {
        this.area = area;
        return this;
    }

    public int getStars() {
        return stars;
    }

    public Island setStars(int stars) {
        this.stars = stars;
        return this;
    }

    @Override
    public String toString() {
        String starsString = "";
        if (stars == 5){
            starsString = "* * * * *";
        } else if (stars == 4){
            starsString = "* * * *";
        } else if(stars ==3){
            starsString = "* * *";
        }else if(stars ==2){
            starsString = "* *";
        }
        else{
            starsString = "*";
        }


        return starsString;

    }
    public String thearea(){
        String area = "";
        area = String.valueOf(area);
        return area;
    }
}
