package ru.mail.park.lecture5;

public abstract class Item {
    public enum Type {HEADER, CHEESE}

    private Type type;
    private String title;

    public Item(Type type, String title) {
        this.type = type;
        this.title = title;
    }

    public Type getType() {
        return type;
    }

    public String getTitle() {
        return title;
    }

}
