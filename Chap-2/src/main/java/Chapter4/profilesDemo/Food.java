package Chapter4.profilesDemo;

import org.apache.commons.lang3.builder.ToStringBuilder;

public class Food {
    private String name;

    public Food() {}

    public Food(String name){
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("name", name)
                .toString();
    }
}