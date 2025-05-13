package Chapter4.PropertEditorSeekho;

import java.beans.PropertyEditorSupport;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.CustomEditorConfigurer;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

class Fullname {
    private String firstName;
    private String lastName;

    public Fullname(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;

    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
}

@Component
class Person {
    @Value("DHRUV SINGH")
    Fullname name;

    public Fullname getName() {
        return name;
    }
    public void setName(Fullname name) {
        this.name = name;
    }
}

@Configuration
@ComponentScan
class CustomPropertyConfig {

    @Bean
    CustomEditorConfigurer customEditorConfigurer(){
        var cust = new CustomEditorConfigurer();
        cust.setCustomEditors(Map.of(Fullname.class, NamePropertyEditor.class));
        return cust;
    }
}


class NamePropertyEditor extends PropertyEditorSupport {

    @Override
    public void setAsText(String text) throws IllegalArgumentException {
        String[] name = text.split("\\s");
        setValue(new Fullname(name[0], name[1]));
    }


}

public class CustomPropertyEditor {
    private static Logger log = LoggerFactory.getLogger(CustomPropertyEditor.class);
    public static void main(String[] args) {
        AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(CustomPropertyConfig.class);
        var person = ctx.getBean(Person.class);
        log.info("person name {}", person.getName().getFirstName());
    }

}
