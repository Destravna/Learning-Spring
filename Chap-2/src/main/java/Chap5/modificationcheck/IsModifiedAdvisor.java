package Chap5.modificationcheck;

import org.springframework.aop.support.DefaultIntroductionAdvisor;

public class IsModifiedAdvisor extends DefaultIntroductionAdvisor{
    public IsModifiedAdvisor() {
        super(new IsModifiedMixin());
    }
}
