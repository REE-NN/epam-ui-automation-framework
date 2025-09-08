package model;

import static dataSource.StaticSource.EMAIL_TEST;
import static dataSource.StaticSource.TEST;

public final class Letter {
    private static Long countId = 0L;
    private final Long id;
    private final String address;
    private final String subject;
    private final String body;

    //пусть, если одно из полей - пустое, всё письмо будет переделано на тестовое с помощью validateLetterObject(см.ниже)
    //для проверки раскомментировать в классе CSVReader, строку 22 с данными "csvTestData_path"
    private Letter() {
        this.id = ++countId;
        this.address = EMAIL_TEST;
        this.subject = TEST;
        this.body = TEST;
    }

    private Letter(letterBuilder builder) {
        this.id = ++countId;
        this.address = builder.address;
        this.subject = builder.subject;
        this.body = builder.body;
    }

    public String getAddress() {
        return address;
    }

    public String getSubject() {
        return subject;
    }

    public String getBody() {
        return body;
    }

    @Override
    public String toString() {
        return "LetterBuilder{" +
                "id=" + id +
                ", address='" + address + '\'' +
                ", subject='" + subject + '\'' +
                ", body='" + body + '\'' +
                '}';
    }

    public static letterBuilder builder() {
        return new letterBuilder();
    }

    public static final class letterBuilder {
        private String address;
        private String subject;
        private String body;

        public letterBuilder address(String address) {
            this.address = address;
            return this;
        }

        public letterBuilder subject(String subject) {
            this.subject = subject;
            return this;
        }

        public letterBuilder body(String body) {
            this.body = body;
            return this;
        }

        public Letter build() {
            Letter letter = new Letter(this);
            letter = validateLetterObject(letter);
            return letter;
        }

        private Letter validateLetterObject(Letter letter) {
            if(letter.getAddress().isEmpty()||letter.getSubject().isEmpty()||letter.getBody().isEmpty()) {
                return new Letter();
            }
            return letter;
        }
    }
}

