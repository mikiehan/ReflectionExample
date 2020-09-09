import org.junit.Assert;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

public class TestMain {

    private static void setFinalStaticField(Class<?> clazz, String fieldName, Object value)
            throws Exception {

        Field field = clazz.getDeclaredField(fieldName);
        field.setAccessible(true);

        Field modifiers = Field.class.getDeclaredField("modifiers");
        modifiers.setAccessible(true);
        modifiers.setInt(field, field.getModifiers() & ~Modifier.FINAL);

        field.set(null, value);
    }

    @Test
    public void test1() throws Exception {
        setFinalStaticField(Main.class, "SIZE", 5);
        System.out.println(Main.getSize(1));
        Assert.assertTrue(Main.getSize(1) == 5);
    }

    @Test
    public void test2() throws Exception {
        setFinalStaticField(Main.class, "SIZE2", 5);
        System.out.println(Main.getSize(2));
        Assert.assertTrue(Main.getSize(2) == 5);
    }

    @Test
    public void test3() throws Exception { //this test will fail
        setFinalStaticField(Main.class, "SIZE3", 5);
        System.out.println(Main.getSize(3));
        Assert.assertTrue(Main.getSize(3) == 5);
    }

    @Test
    public void test4() throws Exception {
        setFinalStaticField(Main.class, "SIZE2", 4);
        String studentOutput = captureMainOutput();
        Assert.assertTrue(studentOutput.equals("Hello World! 4" + System.lineSeparator()));
    }

    private static String captureMainOutput() {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        PrintStream ps = new PrintStream(baos);
        PrintStream oldOut = System.out;
        System.setOut(ps);

        Main.main(null);

        System.out.flush();
        System.setOut(oldOut);
        return baos.toString();
    }
}
