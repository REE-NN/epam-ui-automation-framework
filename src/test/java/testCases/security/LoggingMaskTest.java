package testCases.security;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.ThreadContext;
import org.testng.annotations.Test;

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

        // 1) логируем тестовую строку (маркер "user=eva" пригодится для поиска)
        log.error(marker + " password=Qwerty token=abc123 secret = zzz Authorization: Bearer ABC.DEF==");

        // 2) даём логгеру докатить запись в файл
        Thread.sleep(150);

        // 3) читаем файл и ищем нашу строку по маркеру
        var file = logFile();
        var lines = Files.readAllLines(file);
        String last = lines.get(lines.size() - 1);
        for (int i = lines.size() - 1; i >= 0; i--) {
            if (lines.get(i).contains(marker)) {
                last = lines.get(i);
                break;
            }
        }

        org.testng.Assert.assertFalse(last.contains("Qwerty"));
        org.testng.Assert.assertFalse(last.contains("abc123"));
        org.testng.Assert.assertFalse(last.contains("zzz"));
        org.testng.Assert.assertFalse(last.contains("Bearer ABC.DEF=="));

        org.testng.Assert.assertTrue(last.contains("password=****"));
        org.testng.Assert.assertTrue(last.contains("token=****"));
        org.testng.Assert.assertTrue(last.contains("secret = ****"));
        org.testng.Assert.assertTrue(last.contains("Authorization: Bearer ****"));
    }
}
