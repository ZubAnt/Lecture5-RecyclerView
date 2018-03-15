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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Item item = (Item) o;

        if (type != item.type) return false;
        return title != null ? title.equals(item.title) : item.title == null;
    }

    @Override
    public int hashCode() {
        int result = type.hashCode();
        result = 31 * result + (title != null ? title.hashCode() : 0);
        return result;
    }
}
