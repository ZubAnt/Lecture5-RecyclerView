package ru.mail.park.lecture5;

public class Cheese extends Item {
    private int imageResId;

    public Cheese(String title, int imageRes){
        super(Type.CHEESE, title);
        imageResId = imageRes;
    }

    public int getImageResId() {
        return imageResId;
    }

    public int getPrice() { return Math.abs(getTitle().hashCode() % 1000);}
}
