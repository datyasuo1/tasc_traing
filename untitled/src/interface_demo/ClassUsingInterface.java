package interface_demo;

public class ClassUsingInterface implements DemoInterface{
    @Override
    public int sum(int a, int b) {
        return a + b;
    }

    @Override
    public int minus(int a, int b) {
        return a - b;
    }

    @Override
    public boolean compareUsingEquals(int a) {
        if (a == a) {
            return true;
        }
        return false;
    }
}