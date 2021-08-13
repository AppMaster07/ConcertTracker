package sg.edu.rp.c346.id20002369.concerttracker;

import java.io.Serializable;

public class Concerts implements Serializable {

    private int id;
    private String name;
    private String loc;
    private String desc;
    private int year;
    private int rating;

    public Concerts(String name, String desc, String loc, int year, int rating) {
        this.name = name;
        this.desc = desc;
        this.loc = loc;
        this.year = year;
        this.rating = rating;
    }

    public Concerts(int id, String name, String desc, String loc, int year, int rating) {
        this.id = id;
        this.name = name;
        this.desc = desc;
        this.loc = loc;
        this.year = year;
        this.rating = rating;
    }

    public int getId() {
        return id;
    }

    public Concerts setId(int id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public Concerts setName(String name) {
        this.name = name;
        return this;
    }

    public String getDesc() {
        return desc;
    }

    public Concerts setDesc(String desc) {
        this.desc = desc;
        return this;
    }

    public String getLoc() {
        return loc;
    }

    public Concerts setLoc(String loc) {
        this.loc = loc;
        return this;
    }

    public int getYear() {
        return year;
    }

    public Concerts setYear(int year) {
        this.year = year;
        return this;
    }

    public int getRating() {
        return rating;
    }

    public Concerts setRating(int rating) {
        this.rating = rating;
        return this;
    }

    @Override
    public String toString() {
        String ratingString = "";
        for(int i = 0; i < rating; i++){
            ratingString += "*";
        }
        return name + "\n" + desc + " \n " + loc + "\n" + year + "\n" + ratingString;

    }
}
