package Chap8.views;

import org.apache.commons.lang3.builder.ToStringBuilder;

public record SingerSummary(String firstName, String lastName, String title ) {
    @Override
    public final String toString() {
        return new ToStringBuilder(SingerSummary.class)
                        .append("first-name", firstName)
                        .append("last-name", lastName)
                        .append("titile", title).toString();
    }
}
