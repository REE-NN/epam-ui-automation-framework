package testCases.security;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.ThreadContext;
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
    static {
        try {
            Files.createDirectories(Path.of("target/logs"));
        } catch (Exception ignore) {
        }
        String runId = java.time.LocalDateTime.now()
                .format(java.time.format.DateTimeFormatter.ofPattern("yyyyMMdd-HHmm ss-SSS"));
        org.apache.logging.log4j.ThreadContext.put("runId", runId);
    }

    private static final Logger log = LogManager.getLogger(LoggingMaskTest.class);

    private static Path logFile() {
        return Path.of("target/logs/test-" + ThreadContext.get("runId") + ".log");
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
