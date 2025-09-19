package test.java;

import main.java.Main;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.*;

public class MainTest {
    private final InputStream originalSystemIn = System.in;
    private final PrintStream originalSystemOut = System.out;
    private final PrintStream originalSystemErr = System.err;
    private ByteArrayOutputStream outputStream;
    private ByteArrayOutputStream errorStream;

    @BeforeEach
    void setUp() {
        outputStream = new ByteArrayOutputStream();
        errorStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));
        System.setErr(new PrintStream(errorStream));
    }

    @AfterEach
    void tearDown() {
        System.setIn(originalSystemIn);
        System.setOut(originalSystemOut);
        System.setErr(originalSystemErr);
    }

    @Test
    void testMainWithValidInput() {
        String input = "10\n20\n30\n^D\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));
        Main.main(new String[]{});
        String output = outputStream.toString();
        assertTrue(output.contains("Общее число ошибок в программной системе: 0"));
        assertTrue(output.contains("Время до появления следующей ошибки: 0"));
        assertTrue(output.contains("Время до окончания тестирования: 0"));
    }

    @Test
    void testMainWithEmptyInput() {
        String input = "^D\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));
        Main.main(new String[]{});
        String output = outputStream.toString();
        assertTrue(output.contains("Общее число ошибок в программной системе: 0"));
    }

    @Test
    void testMainWithNegativeNumber() {
        String input = "-5\n^D\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));
        Main.main(new String[]{});
        String errorOutput = errorStream.toString();
        assertTrue(errorOutput.contains("Предупреждение: число -5 вне диапазона 0-2000000000"));
    }

    @Test
    void testMainWithNumberAboveRange() {
        String input = "2000000001\n^D\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));
        Main.main(new String[]{});
        String errorOutput = errorStream.toString();
        assertTrue(errorOutput.contains("Предупреждение: число 2000000001 вне диапазона 0-2000000000"));
    }

    @Test
    void testMainWithInvalidFormat() {
        String input = "abc\n^D\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));
        Main.main(new String[]{});
        String errorOutput = errorStream.toString();
        assertTrue(errorOutput.contains("Ошибка: неверный формат числа - 'abc'"));
    }

    @Test
    void testMainWithMixedInput() {
        String input = "10\n1\n20\n1\n30\n^D\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));
        assertDoesNotThrow(() -> Main.main(new String[]{}));

    }

    @Test
    void testMainWithWhitespaceInput() {
        String input = "  10  \n  20  \n  \n  30  \n^D\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));
        Main.main(new String[]{});
        String output = outputStream.toString();
        assertTrue(output.contains("Общее число ошибок в программной системе: 0"));
    }
}
