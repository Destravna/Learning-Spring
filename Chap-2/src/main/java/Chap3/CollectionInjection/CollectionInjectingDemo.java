package Chap3.CollectionInjection;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;


public class CollectionInjectingDemo {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext apx = new AnnotationConfigApplicationContext(CollectionConfig.class, CollectingBean.class);
        CollectingBean collectingBean = apx.getBean(CollectingBean.class);
        collectingBean.printCollections();
        collectingBean.printCollections2();
    }

}
