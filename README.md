# testTaskSample
/src/main/resources/dump.sql дамп бд

Запуск программы mvn test

/test/java/executor/ExecutorServiceExampleTest - точка запуска программы


/src/main/java/executor/ExecutorServiceExample
Класс запускает два потока
1) /src/main/java/provider/ProviderTask - класс поставщик, создает задачу и записывает в хранилище
  (интерфейс ListNameEmail имитирует получения Имени и Электронной почты для рассылки, класс TaskList реализует интерфейс ListNameEmail)
2) /src/main/java/consumer/ConsumerTask - класс потребитель, получает первую не обработанную задачу и отправляет ее в лог, меняя статус на READY
(Внутренний статический класс EmailAggregator пишет в лог запись об успешно обработанной задачи)

/src/logs/reslt.log - результат работы программы
