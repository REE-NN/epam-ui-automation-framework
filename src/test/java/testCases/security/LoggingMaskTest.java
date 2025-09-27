package testCases.security;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

// TODO(security): покрыть JSON-кейс.
// Пример будущего шага:
// log.error(marker + " {\"password\":\"Qwerty\"}");
// assertFalse(line.contains("\"password\":\"Qwerty\""));
// assertTrue(line.contains("\"password\":\"****\""));

public class LoggingMaskTest {
    private static final Logger log = LogManager.getLogger(LoggingMaskTest.class);

    private static Path logFile() {
        String runId = System.getProperty("runId"); // приходит из Maven Surefire
        java.nio.file.Path logsDir = java.nio.file.Path.of("target/logs");

        if (runId != null && !runId.isBlank()) {
            return logsDir.resolve("test-" + runId + ".log");
        }
        // фолбэк: берём самый свежий test-*.log (если runId не задан в IDE)
        try (var s = java.nio.file.Files.list(logsDir)) {
            return s.filter(p -> p.getFileName().toString().startsWith("test-") && p.toString().endsWith(".log"))
                    .max(java.util.Comparator.comparingLong(p -> p.toFile().lastModified()))
                    .orElse(logsDir.resolve("test.log"));
        } catch (Exception e) {
            return logsDir.resolve("test.log");
        }
    }

    @Test(groups = "security")
    public void shouldMaskSensitiveData() throws Exception {
        // уникальный маркер этой строки, чтобы точно найти её в файле
        String marker = "user=eva#" + System.nanoTime();

        log.error(marker + " password=Qwerty token=abc123 secret = zzz Authorization: Bearer ABC.DEF==");

        Thread.sleep(150);

        var lines = Files.readAllLines(logFile(), StandardCharsets.UTF_8);
        String line = null;
        for (int i = lines.size() - 1; i >= 0; i--) {
            if (lines.get(i).contains(marker)) {
                line = lines.get(i);
                break;
            }
        }
        Assert.assertNotNull(line, "Не нашли свежую строку по маркеру: " + marker);

        Assert.assertFalse(line.contains("Qwerty"));
        Assert.assertFalse(line.contains("abc123"));
        Assert.assertFalse(line.contains("zzz"));
        Assert.assertFalse(line.contains("Bearer ABC.DEF=="));

        Assert.assertTrue(line.contains("password=****"));
        Assert.assertTrue(line.contains("token=****"));
        Assert.assertTrue(line.contains("secret = ****"));
        Assert.assertTrue(line.contains("Authorization: Bearer ****"));
    }
}
