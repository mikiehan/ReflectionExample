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
        System.out.println(Main.getSize());
        Assert.assertTrue(Main.getSize() == 5);
    }
}
