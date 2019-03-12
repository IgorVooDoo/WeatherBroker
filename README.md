# WeatherBroker (Погодный брокер)

Через командную строку запускаем WindFly 15

standalone.sh -c standalone-full.xml

Создаем очереди для передачи названия города и сообщений о погоде, полученных от сервиса Yahoo

--Добавление очереди Город
/subsystem=messaging-activemq/server=default/jms-queue=cityQueue/:add(entries=["java:jboss/queue/cityQueue"])

--Добавление очереди Погоды
/subsystem=messaging-activemq/server=default/jms-queue=weatherQueue/:add(entries=["java:jboss/queue/weatherQueue"])

--Добавление DataSource
data-source add --jndi-name=java:/jdbc/YahooForecastDS --name=YahooForecastDS-Pool --connection-url=jdbc:postgresql://localhost:5432/YahooForecastDB --driver-name=postgresql --user-name=postgresql --password=postgresql

Переходим по ссылке http://localhost:9990/console/index.html#deployments и загружаем модули admin_api.war, yahoo_weather.jar, db_service.jar, weather_service.war.

Для получения погоды от сервиса Yahoo и записи данных в базу - ссылка http://localhost:8080/admin_api/
Для получения прогноза из базы - http://localhost:8080/weather_service/api/weather

