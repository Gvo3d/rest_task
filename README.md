
REST TASK
[![Build Status](https://travis-ci.org/Gvo3d/rest_task.svg?branch=master)](https://travis-ci.org/Gvo3d/rest_task/branches)

Запуск:
1)Запаковать jar-архив с помощью maven
2)установить docker
3)скопировать в одну директорию и запустить ROOT.jar и start.sh
*В случае использования MS Windows переименовать start.sh в start.bat, если докер устанавливается на Win 7, то в application.yml (dataSource.url) вписать валидный адресс docker-контейнера(Например: jdbc:postgresql://192.168.99.100:5432/rest). Эти же настройки надо внести в 
TestJpaConfig(для тестов) и pom.xml(для запуска мавен-таски update).



Запуск тестов:
1)Тестовые настройки соединения находятся в TestJpaConfig.java.
2)У Liqubase модуля под Maven есть goal "update" который отвечает за создание БД на тот случай, если тесты проводятся до первого запуска приложения.

Благодаря preConditions в db-creation.xml ошибок не будет если будет повторная попытка запуска, однако если компилировать на одной машине, а запускать на другой, то надо применять флаг -Dliquibase.should.run=false, иначе будет error при отсутствии instance postgre.

Доп. инфо: http://www.liquibase.org/documentation/maven/generated/update-mojo.html#skip



Опции:
В application.yml есть настройки, которые отвечают за количество добавляемых строк и длинну имени.
application:
  dataQuantity - количество строк, которые будут добавлены в БД
  charsCount - количество символов параметра name(не больше 1000 - ограничение в db-creation.xml)



Виртуализация:
Билдим контейнер из dockerfilе,
при этом вставляем в свойство url(application.yml, pom.xml) название нашего сетевого докер-контейнера: "restgres" вместо "localhost", после чего в одной директории ложим ROOT.jar и Dockerfile, запускаем консоль и из под директории где хранятся оба этих файла запускаем команду.
Команда:
docker build -t yakimov /path/to/a/Dockerfile
1)Первым делом создаём сеть: docker network create -d bridge restnet
2)После этого запускаем постгрес-контейнер: docker run --name rest --network=restnet --network-alias restgres -p 5432:5432 -e POSTGRES_USER=postgres -e POSTGRES_PASSWORD=root -d postgres:9.6
3)После этого запускаем наш образ: docker run --name rest_task --network=restnet --network-alias resttask -p 8080:8080 yakimov

Всё, запуск приложения в двух совмещённых контейнерах завершён. Благодаря тому что порт 8080 прокинут на нашу внешнюю машину, мы можем стучаться прямо на следующий эндпоинт.



Endpoint:
localhost:8080/hello/contacts?nameFilter=



REGEX стринги запросов:
.* - вернуть всё
[A].* - вернуть всё, что начинается с символа А
^\d.* - вернуть всё, что начинается с цифры
[^A]* - вернуть всё, что не начинается с символа А
.*[U] - вернуть всё, что заканчивается символом U
.*[J]$ - аналогично предыдущему варианту
.*[b].* - вернуть всё, где есть символ b
.*[bac].* - вернуть всё, где есть символы b, a, c
.*\d.* - вернуть всё, где есть цифры



Тестирование методик сравнения
На моих настройках(ParsersTest.java):
SimpleMatcher(100 повторений - в м/с): 21-24
PatternMatcher(100 повторений - в м/с): 7-8
ApacheMatcher(100 повторений - в м/с): 7-8

Был выбран метод основанный на библиотеке Apache.



P.S.:
По причине CVE-2016-6816 Tomcat отказывается работать с символами "^", "$" и "\" в строке запроса. У меня было три варианта:
1)Перевести стринги запросов в RequestBody и отправлять данные в json методом POST.
2)Запустить приложение на embedded Jetty
3)В сервис-слое заменять символ "^" на другой символ, например "!" и аналогично для других.

Был протестирован и выбран второй вариант и Jetty успешно работает с символами: ^, \, $.



P.S.2:
Были опробованы две методики итерации - с помощью парралельного стрима и самописный на основе ThreadExecutorPool и Future:

1)List<Contact> contacts = data.parallelStream().filter(c -> regexValidator.isValid(c.getName())).collect(Collectors.toList());

2)Текущая методика на основе класса DataCollector.

Запрос: localhost:8080/hello2/contacts?nameFilter=d.*
Тайминги (в м/с):
Parralel Stream: 426,567,552,1023,364
DataCollector: 253,390,298,196,302

По общей оценке скорости выполнения текущий вариант оказался более быстрым, хотя и более прожорливым к памяти за счёт большего количества объектов.