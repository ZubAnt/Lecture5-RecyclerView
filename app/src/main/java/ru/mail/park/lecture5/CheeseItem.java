package ru.mail.park.lecture5;

public class CheeseItem extends Item {
    private int imageResId;

    public CheeseItem(String title, int imageRes){
        super(Type.CHEESE, title);
        imageResId = imageRes;
    }

    public int getImageResId() {
        return imageResId;
    }
}
