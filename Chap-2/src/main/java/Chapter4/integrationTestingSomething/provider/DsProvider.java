package Chapter4.integrationTestingSomething.provider;

import org.dhruv.Chap2.decoupled.MessageProvider;
import org.springframework.stereotype.Component;


@Component
public class DsProvider implements MessageProvider {
    @Override
    public String getMessage() {
        return "Dhruv Singh";
    }
}
