import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class MainTest {

    @Test
    public void testHelloWorldOutput() {
        Main.main(new String[] {});

        assertTrue(true, "Main method should execute without exceptions");
    }

    @Test
    public void testSimpleAddiction() {
        int result = 210468 * 2 + 2;
        assertEquals(420938, result, "Additional result");
    }

    @Test
    public void testConcatenation() {
        String ivan = "Holin Ivan";
        String seva = "Vitylin Vsevolod";
        String dima = "Kosenok Dmitry";
        String result = "The work was done by " + ivan + ", " + seva + " and " + dima;

        assertEquals("The work was done by Holin Ivan, Vitylin Vsevolod and Kosenok Dmitry", result);

        assertTrue(result.contains("Ivan"));
        assertTrue(result.contains("Vsevolod"));
        assertTrue(result.contains("Dmitry"));
    }
}
