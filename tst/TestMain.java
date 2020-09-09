import org.junit.Assert;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Scanner;

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
        Assert.assertTrue(cleanWhiteSpaceEndings(studentOutput).equals("Hello World! 4" + System.lineSeparator()));

        ////In real case instead of hard coding the expected string
        ////use below code
        //String expectedStr = dumpFileContentsToString("/blah/blah/expected4.txt");
        //Assert.assertTrue(cleanWhiteSpaceEndings(studentOutput).equals(expectedStr));
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

    //Need to use this method for expected file input
    //Expected file was generated in Windows
    //But to be also used on Linux/Mac use this cleanNewString
    private static String cleanNewLineString(String str) {
        str = str.replaceAll("\n", System.lineSeparator());
        str = str.replaceAll("\r\n", System.lineSeparator());
        str = str.replaceAll("\r", System.lineSeparator());
        return str;
    }

    //Need to use this method for cleaning student's output
    //Some students put some whitespace at the end so trim that down
    //This also cleans line separator issue
    //(Expected file doesn't have the whitespace ending)
    private static String cleanWhiteSpaceEndings(String str) {
        StringBuilder sb = new StringBuilder();
        Scanner scan = new Scanner(str);
        while(scan.hasNextLine()) {
            String oneLine = scan.nextLine();
            sb.append(oneLine.replaceFirst("\\s++$", "")); //remove whitespace endings
            sb.append(System.lineSeparator()); //add proper line separator
        }
        return sb.toString();
    }

    //Use this method to read expected output file
    private static String dumpFileContentsToString(String filePath) {
        try {
            String str = new String(Files.readAllBytes(Paths.get(filePath)), StandardCharsets.UTF_8);
            return cleanNewLineString(str);
        } catch (IOException e) {
            Assert.fail("Could not load file: " + filePath);
            return null;
        }
    }
}
