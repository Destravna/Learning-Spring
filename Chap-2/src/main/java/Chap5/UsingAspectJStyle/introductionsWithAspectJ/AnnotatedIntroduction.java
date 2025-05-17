package Chap5.UsingAspectJStyle.introductionsWithAspectJ;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.DeclareParents;
import org.springframework.stereotype.Component;



// for introducing  Performer interface to any class that 

@Component
@Aspect
public class AnnotatedIntroduction {
    @DeclareParents(value="Chap5.programmaticalyadvice.common.Singer+", defaultImpl = Dancer.class)
    public static Performer performer;
    
}
