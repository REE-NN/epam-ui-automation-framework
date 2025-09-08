# EPAM UI Automation Framework

Production-level UI automation framework developed at EPAM as an internal engineering project for automating Yandex Mail scenarios — to support EPAM teams implementing integration solutions with Yandex services.

---

## Overview

Проект является фреймвором для автоматизации сценариев Яндекс.Почты — для поддержки команд EPAM, реализующих интеграционные решения с сервисами Яндекса.

---

## Features

- Авторизация пользователя, создание/удаление/отправка писем, работа с черновиками и корзиной, выход из аккаунта.
- Использование Selenium WebDriver, Actions API, JavaScript Executor.
- Реализация Page Object Pattern, Singleton, Driver Factory.
- TestNG с DataProvider.
- Гибкое управление ожиданиями, обработка динамических элементов и нестандартных багов UI.
- Логирование, отчётность.
- Сборка и запуск тестов через Maven.

---

## Project Structure


src/
├── pages/ # Page Object модели для интерфейса
├── tests/ # Автоматизированные сценарии тестирования
├── utils/ # Фабрики, драйверы, утилиты, дата-провайдеры
├── resources/ # Тестовые данные и вспомогательные ресурсы
└── testng.xml # Конфигурация тест-сьюита

Notes

В качестве объекта тестирования выбрана Яндекс.Почта — для моделирования сложных UI-сценариев и отработки интеграционных задач, аналогичных боевым продуктам.
Проект реализован на Java, с использованием Selenium, TestNG, Maven.
Структура и подход легко адаптируются для любых корпоративных web-приложений.
