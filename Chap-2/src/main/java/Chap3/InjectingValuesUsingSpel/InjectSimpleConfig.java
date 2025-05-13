package Chap3.InjectingValuesUsingSpel;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.stereotype.Component;

@Component
public class InjectSimpleConfig {
    private String name = "Dhruv Singh";
    private int age = 40;
    private float height = 1.92f;
    private boolean developer = true;
    private Long ageInSeconds = 1_241_401_112L;
    private List<Integer> nums = new ArrayList<>();

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    public float getHeight() {
        return height;
    }

    public boolean isDeveloper(){
        return developer;
    }
    

    public Long getAgeInSeconds() {
        return ageInSeconds;
    }
    
    public List<Integer> getNums() {
        if(nums.isEmpty()){
            nums.addAll(Arrays.asList(1, 2, 3));
        }
        return nums;
    }

}
