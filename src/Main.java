public class Main {

    private static final int SIZE = new Integer(10);

    private static final Integer SIZE2 = 20;

    private static final int SIZE3 = 30; // we cannot change this NO MATTER WHAT (as complier replaces the code (hard-coded) before the class gets loaded

    public static void main(String[] args) {
        System.out.println("Hello World! " + SIZE2);

    }

    public static int getSize(int num) {
        if (num == 1) {
            return SIZE;
        } else if (num == 2) {
            return SIZE2;
        } else {
            return SIZE3;
        }

    }

}
