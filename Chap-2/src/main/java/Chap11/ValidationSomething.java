package Chap11;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import Chap11.PropertyEditingSomething.Address;
import Chap11.PropertyEditingSomething.Blogger;
import Chap11.PropertyEditingSomething.BloggerWithAddress;

public class ValidationSomething {
    public static void main(String[] args) {

    }

    // just too avoid creating a new file for each validator, its tedious af
    @Component
    public static class SimpleBloggerValidator implements Validator {
        @Override
        public boolean supports(Class<?> clazz) {
            return Blogger.class.equals(clazz);
        }

        @Override
        public void validate(Object target, Errors errors) {
            ValidationUtils.rejectIfEmpty(errors, "firstName", "firstName.required");
        }
    }

    @Component
    public static class ComplexBloggerValidator implements Validator {
        @Override
        public boolean supports(Class<?> clazz) {
            return Blogger.class.equals(clazz);
        }

        @Override
        public void validate(Object target, Errors errors) {
            var b = (Blogger) target;
            if (!StringUtils.hasLength(b.firstName()) || !StringUtils.hasLength(b.lastName())) {
                errors.rejectValue("firstName", "first/firstName required");
                errors.rejectValue("lastName", "first/lastName required");
            }

            if (b.birthDate().isAfter(LocalDate.of(2007, 12, 31))) {
                errors.rejectValue("birthDate", "you are a minor, go away");
            }
        }
    }

    @Component
    public static class AddressValidator implements Validator {
        @Override
        public boolean supports(Class<?> clazz) {
            return Address.class.equals(clazz);
        }

        @Override
        public void validate(Object target, Errors errors) {
            ValidationUtils.rejectIfEmpty(errors, "city", "city.empty");
            ValidationUtils.rejectIfEmpty(errors, "country", "country.empty");

            var address = (Address) target;
            if (!org.apache.commons.lang3.StringUtils.isAlpha(address.city())) {
                errors.rejectValue("city", "city.onlyLettersAllowed");
            }
            if (!org.apache.commons.lang3.StringUtils.isAlpha(address.country())) {
                errors.rejectValue("country", "country.onlyLettersAllowed");
            }
        }
    }

    @Component
    public static class BloggerWithAddressValidator implements Validator {
        private @Autowired AddressValidator addressValidator;

        @Override
        public boolean supports(Class<?> clazz) {
            return BloggerWithAddress.class.equals(clazz);
        }

        @Override
        public void validate(Object target, Errors errors) {
            ValidationUtils.rejectIfEmptyOrWhitespace(errors, "address", "address.required");
            ValidationUtils.rejectIfEmptyOrWhitespace(errors, "personalSite", "personalSite.required");
            var b = (BloggerWithAddress) target;
            if (!StringUtils.hasLength(b.getFirstName()) || !StringUtils.hasText(b.getLastName())) {
                errors.rejectValue("firstName", "firstNameOrLastName.required");
                errors.rejectValue("lastName", "firstNameOrLastName.required");
            }
            try{
                errors.pushNestedPath("address"); //basically tells spring that for the next validations we are inside the address field of the blogger. 
                ValidationUtils.invokeValidator(this.addressValidator, b.getAddress(), errors);
            } finally {
                errors.popNestedPath();
            }
        }
    }

}
