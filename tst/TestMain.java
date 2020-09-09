import org.junit.Assert;
import org.junit.Test;

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
}
