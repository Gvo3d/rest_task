
REST TASK 
[![Build Status](https://travis-ci.org/Gvo3d/rest_task.svg?branch=master)](https://travis-ci.org/Gvo3d/rest_task/branches)


!Все запросы были проверены на 100000 объектов.
!Регексы написанные в результатах перед исполнением прогоняются через PatternResolver, который изменяет их для соответствия заданию.
Тесты проводились с помощью флага: -Xloggc:garbage-collection.log


Алгоритм: Выгрузка данных из БД в распределёный кэш Hazelcast(одна нода), получение результата с помощью предикатов, отправка через Jackson.
Старт приложения: 188196 ms.

Branch: hazelcast-jackson
Запрос: localhost:8080/hello/contacts?nameFilter=^[\d].*$
Итерации:
Результаты: Regex: ^[\d].*$ returns list of 83964 elements. With processing time: 3121 ms.
Время доставки на postman: 13719 ms.
Сборщик мусора:
229.113: [GC (Allocation Failure)  424679K->193715K(500736K), 0.0921534 secs]
229.745: [GC (Allocation Failure)  411827K->196541K(498176K), 0.0994548 secs]
230.484: [GC (Allocation Failure)  414653K->204174K(491008K), 0.1198201 secs]
231.294: [GC (Allocation Failure)  398734K->205121K(496128K), 0.1038071 secs]
232.702: [GC (Allocation Failure)  399681K->208339K(487936K), 0.1045611 secs]
238.629: [GC (Allocation Failure)  404947K->207679K(493056K), 0.0369970 secs]
239.590: [GC (Allocation Failure)  404287K->207767K(498688K), 0.0329656 secs]
239.623: [Full GC (Ergonomics)  207767K->141069K(494080K), 0.6937932 secs]
Результаты: Regex: ^[\d].*$ returns list of 83964 elements. With processing time: 3873 ms.
Время доставки на postman: 21501 ms.
Сборщик мусора:
352.374: [GC (Allocation Failure)  347405K->142261K(496128K), 0.0414930 secs]
353.172: [GC (Allocation Failure)  348597K->147752K(497152K), 0.0192655 secs]
354.071: [GC (Allocation Failure)  356136K->154543K(497664K), 0.0366456 secs]
354.939: [GC (Allocation Failure)  362927K->170587K(493568K), 0.0762614 secs]
356.342: [GC (Allocation Failure)  382555K->185886K(495616K), 0.1248126 secs]
367.165: [GC (Allocation Failure)  397854K->186070K(504320K), 0.0318179 secs]
368.038: [GC (Allocation Failure)  409814K->186086K(503808K), 0.0048489 secs]
Результаты: Regex: ^[\d].*$ returns list of 83964 elements. With processing time: 2911 ms.
Время доставки на postman: 21567 ms.
Сборщик мусора:
455.111: [GC (Allocation Failure)  409830K->188594K(505856K), 0.0108092 secs]
455.728: [GC (Allocation Failure)  417970K->194724K(506368K), 0.0235942 secs]
456.323: [GC (Allocation Failure)  424100K->202136K(508416K), 0.0502043 secs]
456.912: [GC (Allocation Failure)  435277K->225753K(508928K), 0.0302938 secs]
456.942: [Full GC (Ergonomics)  225753K->167857K(531968K), 0.6585061 secs]
469.745: [GC (Allocation Failure)  404913K->188729K(536576K), 0.0380987 secs]
470.697: [GC (Allocation Failure)  432441K->188929K(537600K), 0.0386981 secs]

Тот же запрос, но ответ сериализуется конкатенацией строк через StringBuilder: 
Branch: hazelcast-string
Запрос: localhost:8080/hello/contacts?nameFilter=^[\d].*$
Итерации:
Результаты: Regex: ^[\d].*$ returns list of 83964 elements. With processing time: 3564 ms
Время доставки на postman:  8203 ms.
Сборщик мусора:
189.261: [GC (Allocation Failure)  342294K->201922K(425472K), 0.1604166 secs]
189.819: [GC (Allocation Failure)  349378K->202971K(444928K), 0.1156603 secs]
190.236: [GC (Allocation Failure)  334043K->206522K(448512K), 0.1262006 secs]
190.634: [GC (Allocation Failure)  337594K->210170K(437760K), 0.1081517 secs]
191.020: [GC (Allocation Failure)  344314K->215582K(443392K), 0.0846705 secs]
191.105: [Full GC (Ergonomics)  215582K->138909K(451072K), 0.5890395 secs]
192.211: [GC (Allocation Failure)  273053K->152792K(461824K), 0.0302687 secs]
192.821: [GC (Allocation Failure)  302808K->167312K(464384K), 0.0796026 secs]
193.417: [GC (Allocation Failure)  317328K->173335K(459264K), 0.3036940 secs]
194.258: [GC (Allocation Failure)  328983K->179705K(462848K), 0.1484843 secs]
195.147: [GC (Allocation Failure)  331337K->179809K(472064K), 0.8438500 secs]
196.612: [GC (Allocation Failure)  349281K->192373K(473600K), 0.0109413 secs]
Результаты: Regex: ^[\d].*$ returns list of 83964 elements. With processing time: 3468 ms.
Время доставки на postman: 21849 ms.
Сборщик мусора:
296.753: [GC (Allocation Failure)  361845K->211703K(474624K), 0.0291043 secs]
296.782: [Full GC (Ergonomics)  211703K->142151K(482304K), 0.3969014 secs]
297.666: [GC (Allocation Failure)  318279K->146982K(482816K), 0.0152063 secs]
298.365: [GC (Allocation Failure)  323110K->151734K(488960K), 0.0315578 secs]
298.974: [GC (Allocation Failure)  335030K->168622K(424448K), 0.0545567 secs]
299.707: [GC (Allocation Failure)  345979K->176101K(489472K), 0.0434398 secs]
300.416: [GC (Allocation Failure)  362981K->184360K(487424K), 0.0248704 secs]
301.333: [GC (Allocation Failure)  371240K->190762K(493056K), 0.0247905 secs]
301.967: [GC (Allocation Failure)  387370K->203278K(490496K), 0.0134740 secs]
Результаты: Regex: ^[\d].*$ returns list of 83964 elements. With processing time: 3566 ms.
Время доставки на postman: 21873 ms.
Сборщик мусора:
371.956: [GC (Allocation Failure)  399886K->203958K(496128K), 0.0148206 secs]
371.971: [Full GC (Ergonomics)  203958K->140686K(496128K), 0.3563688 secs]
372.915: [GC (Allocation Failure)  347022K->146264K(496128K), 0.0169890 secs]
373.795: [GC (Allocation Failure)  352600K->153077K(504832K), 0.0297807 secs]
374.647: [GC (Allocation Failure)  372213K->169625K(504832K), 0.0605391 secs]
375.624: [GC (Allocation Failure)  388761K->186447K(507392K), 0.0855140 secs]
376.457: [GC (Allocation Failure)  415311K->197504K(506880K), 0.0338117 secs]
377.325: [GC (Allocation Failure)  426368K->210021K(516608K), 0.0123178 secs]



Алгоритм: Выгрузка данных из БД на ходу, парсинг с помощью DataCollector, отправка через Jackson.
Старт приложения: 20469 ms.

Branch: standart-jackson
Запрос: localhost:8080/hello/contacts?nameFilter=^[\d].*$
Итерации:
Результаты: Regex: ^[\d].*$ returns list of 83964 elements. With processing time: 1923 ms.
Время доставки на postman: 35423 ms.
Сборщик мусора:
24.569: [GC (Allocation Failure)  202699K->31598K(245248K), 0.0340312 secs]
26.942: [GC (Allocation Failure)  218478K->39217K(303616K), 0.0433903 secs]
Результаты: Regex: ^[\d].*$ returns list of 83964 elements. With processing time: 3679 ms.
Время доставки на postman: 18797 ms.
Сборщик мусора:
196.173: [GC (Allocation Failure)  280369K->106907K(348160K), 0.3354763 secs]
196.509: [Full GC (Ergonomics)  106907K->91830K(416768K), 1.7863974 secs]
Результаты: Regex: ^[\d].*$ returns list of 83964 elements. With processing time: 1554 ms.
Время доставки на postman: 18842 ms.
Сборщик мусора: сборки мусора не производилось.

Тот же запрос, но ответ сериализуется конкатенацией строк через StringBuilder: 
Branch: standart-string
Запрос: localhost:8080/hello/contacts?nameFilter=^[\d].*$
Итерации:
Результаты: Regex: ^[\d].*$ returns list of 83964 elements. With processing time: 4225 ms.
Время доставки на postman: 20521 ms.
Сборщик мусора:
15.389: [GC (Allocation Failure)  213766K->33297K(327680K), 0.0421523 secs]
40.842: [GC (Allocation Failure)  302097K->68901K(353792K), 0.2024574 secs]
41.045: [Full GC (Ergonomics)  68901K->56307K(402432K), 0.8367093 secs]
Результаты: Regex: ^[\d].*$ returns list of 83964 elements. With processing time: 3099 ms.
Время доставки на postman: 20181 ms.
Сборщик мусора:
111.658: [GC (Allocation Failure)  340979K->128503K(379392K), 0.3514655 secs]
112.010: [Full GC (Ergonomics)  128503K->78966K(428544K), 1.1431988 secs]
Результаты: Regex: ^[\d].*$ returns list of 83964 elements. With processing time: 1582 ms.
Время доставки на postman: 20363 ms.
Сборщик мусора: 
199.638: [GC (Allocation Failure)  326372K->121495K(442880K), 0.1964062 secs]




Выводы:
Тесты показали, что постраничная выгрузка данных для парсинга не имеет практического смысла для решения таких задач как экономия памяти и уменьшения времени работы CPU, наоборот - чаще производится Garbage collection, больше процессорного времени требуется для обработки запроса, хотя при выгрузке неполной коллекции не приходится загружать её одномоментно в память и проводить парсинг. Теоретически постраничная выгрузка может спасти приложение от OutOfMemoryError, но только при выгрузке не всех данных, а их части, т.е. не решает проблему целиком.
Ручная сериализация через StringBuilder, несмотря ни на что, проигрывает в производительности прогону объектов через Jackson mapper.
Я заглянул в GraphQL, но там та же проблема - данные не выгружаются из БД сразу пользователю, они проходят через сервис-layer, что означает немалые расходы памяти на хранение и сериализацию объектов.
Самым лучшим алгоритмом считаю стандартную выгрузку данных одним запросом из БД с последующим прогоном через DataCollector и сериализацией с помощью Jackson mapper.


Регексы согласно задания:
Начинается: ^[a].*$
Содержит: ^.*[a].*$
Кончается: ^.*[a]$
Не начинается: ^[^a].*$
Не содержит: ^.*[^a].*$
Не кончается: ^.*[^a]$


Регексы использующиеся внутри приложения для валидации строк(отличающиеся от таких же согласно задания):
Не начинается: ^[^a].*$
Не содержит: ^[^a]*$




Предыдущая информация:

Замечание:
Докер-контейнер ссылается своим портом 5432 на порт 5433 хост-машины, т.к. Travis данный порт считает используемым:
docker: Error response from daemon: driver failed programming external connectivity on endpoint rest (3fabc0d80ce23b65140193d9c5e7e8fa298f578acff92e77dbe53905bf605eef): Error starting userland proxy: listen tcp 0.0.0.0:5432: bind: address already in use.  

Запуск:
1)Запаковать jar-архив с помощью maven
2)установить docker
3)скопировать в одну директорию ROOT.jar и start.sh, запустить скрипт(как вариант - запустить докер-команду из скрипта и просто запустить приложение из IDE). 
*В случае использования MS Windows переименовать start.sh в start.bat, если докер устанавливается на Win 7, то в application.yml (dataSource.url) вписать валидный адресс docker-контейнера(Например: jdbc:postgresql://192.168.99.100:5433/rest). Эти же настройки надо внести в 
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

*Первоначальное условие про миллион строк я проверял на 10 тысячах, а потом и на 100, т.к. мой компьютер не тянет миллион строк. Предположительное количество оперативной памяти, которое нужно - около 8 гб(только под jvm).



Виртуализация:
Билдим контейнер из dockerfilе,
при этом вставляем в свойство url(application.yml, pom.xml) название нашего сетевого докер-контейнера: "restgres" вместо "localhost" и меняем порт 5433 на 5432, т.к. внутри виртуального контейнера этот порт не занят и связь будет идти по нему, после чего в одной директории ложим ROOT.jar и Dockerfile, запускаем консоль и из под директории где хранятся оба этих файла вводим команды. 

1)Первым делом создаём сеть: docker network create -d bridge restnet
2)После этого запускаем постгрес-контейнер: docker run --name rest --network=restnet --network-alias restgres -p 5433:5432 -e POSTGRES_USER=postgres -e POSTGRES_PASSWORD=root -d postgres:9.6
3)После этого запускаем наш образ: docker build --network=restnet /path/to/a/Dockerfile
4)Вводим docker images и находим в списке наш новосозданный образ.
5)Запускаем: docker run --name rest_task -network=restnet -p 8080:8080 ${id образа}

Всё, запуск приложения в двух совмещённых контейнерах завершён. Благодаря тому что порт 8080 прокинут на нашу внешнюю машину, мы можем стучаться прямо на следующий эндпоинт(в случае Win 7 cтучимся на сетевое имя докер-хоста).



Endpoint:
localhost:8080/hello/contacts?nameFilter=


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