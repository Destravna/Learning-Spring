package Chap3.FieldInjection;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class Inspiration {
    private String lyric = "I can keep the door cracked open, to let light through";

    public Inspiration(@Value("Never gonna let you down, never gonna give you up") String value){
        this.lyric = lyric;
    }

    public String getLyric() {
        return lyric;
    }

    public void setLyric(String lyric) {
        this.lyric = lyric;
    }
}
