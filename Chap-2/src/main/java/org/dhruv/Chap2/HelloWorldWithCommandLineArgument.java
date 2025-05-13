package org.dhruv.Chap2;


// problem 2 here -> same component is responsible for obtaining the messsage and rendering/printing the message/ 
// also changing the renderer code i.e this code means changing the class that launches the application 
//we should refactor to have different component for message rendering and message retrieving. 
//to make even more flexible, our renderer and retriver should implement interfacesf
public class HelloWorldWithCommandLineArgument {
    public static void main(String... args) {
        if(args.length > 0){
            System.out.println(args[0]);
        }
        else System.out.println("Hello World");
    }
}
