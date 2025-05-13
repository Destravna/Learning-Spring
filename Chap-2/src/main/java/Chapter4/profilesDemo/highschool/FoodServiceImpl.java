package Chapter4.profilesDemo.highschool;

import java.util.List;

import Chapter4.profilesDemo.Food;
import Chapter4.profilesDemo.FoodProviderService;

public class FoodServiceImpl implements FoodProviderService {
    @Override
    public List<Food> provideLunchSet() {
        return List.of(new Food("burger"), new Food("diet coke"));
    }
}
