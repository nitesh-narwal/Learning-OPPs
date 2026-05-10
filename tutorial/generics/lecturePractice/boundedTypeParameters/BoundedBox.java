package me.niteshh.OPPs.tutorial.generics.lecturePractice.boundedTypeParameters;

// we can create conditions and boundries about what we can put inside the box
// and we can extend or implement form an interface the box with the bounded type parameters
//
public class BoundedBox<T extends Number> {

    private T box;

    public T getBox() {
        return box;
    }

    public void setBox(T box) {
        this.box = box;
    }

}
