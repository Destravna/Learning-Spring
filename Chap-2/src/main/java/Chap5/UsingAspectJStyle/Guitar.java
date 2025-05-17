package Chap5.UsingAspectJStyle;

import org.apache.commons.lang3.builder.ToStringBuilder;

public class Guitar {
    private String brand;

    public Guitar(String brandName) {
        brand = brandName;
    }

    public String play() {
        return "playing guitar";
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("brand", brand).toString();
    }
}
