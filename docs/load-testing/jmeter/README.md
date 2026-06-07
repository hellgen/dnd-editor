# Нагрузочное тестирование DND Editor API через JMeter

В каталоге лежит готовый JMeter-план `dnd-editor-load-test.jmx` для базового нагрузочного теста API приложения. План ходит в `/actuator/health`, `/abilities`, `/races`, `/classes`, `/spells`.

План рассчитан на запуск **без GUI** — так реальные нагрузочные прогоны меньше искажаются ресурсами самого интерфейса JMeter.

## 0. Что изменилось с авторизацией

Если API отвечает `401 Unauthorized`, это значит, что запросы попали в защищённые Spring Security endpoints без JWT-токена.

Чтобы не открывать API полностью в конфигурации безопасности, JMeter-план теперь делает авторизацию внутри сценария:

1. Каждый виртуальный пользователь один раз выполняет `POST /auth/register`.
2. Из JSON-ответа извлекается `accessToken`.
3. Все основные API-запросы отправляются с заголовком `Authorization: Bearer ${accessToken}`.

Поэтому в отчёте JMeter вы увидите не только GET-запросы, но и служебный запрос `POST /auth/register`. Это нормально: он нужен, чтобы получить токен для потока.

## 1. Что нужно заранее

- Запущенное приложение DND Editor API на `http://localhost:8080`.
- Java JDK/JRE, потому что JMeter — Java-приложение.
- Сам Apache JMeter или Docker, если хотите запускать JMeter без установки на машину.

Полезные официальные ссылки:

- Apache JMeter: <https://jmeter.apache.org/>
- Getting Started / Installation: <https://jmeter.apache.org/usermanual/get-started.html>
- Downloads: <https://jmeter.apache.org/download_jmeter.cgi>

## 2. Установить JMeter

### Вариант A — через Docker, без локальной установки JMeter

```bash
docker pull justb4/jmeter:5.6.3
```

Проверка:

```bash
docker run --rm justb4/jmeter:5.6.3 --version
```

### Вариант B — macOS через Homebrew

```bash
brew install openjdk jmeter
```

Проверка:

```bash
java -version
jmeter --version
```

### Вариант C — Linux через архив Apache JMeter

Пример для версии `5.6.3`:

```bash
sudo apt-get update
sudo apt-get install -y openjdk-21-jdk curl tar
curl -L -o /tmp/apache-jmeter-5.6.3.tgz \
  https://archive.apache.org/dist/jmeter/binaries/apache-jmeter-5.6.3.tgz
sudo tar -xzf /tmp/apache-jmeter-5.6.3.tgz -C /opt
sudo ln -sf /opt/apache-jmeter-5.6.3/bin/jmeter /usr/local/bin/jmeter
```

Проверка:

```bash
java -version
jmeter --version
```

### Вариант D — Windows вручную

1. Установите Java JDK, например Temurin/OpenJDK 21.
2. Скачайте binary-архив Apache JMeter с <https://jmeter.apache.org/download_jmeter.cgi>.
3. Распакуйте архив, например в `C:\Tools\apache-jmeter-5.6.3`.
4. Добавьте `C:\Tools\apache-jmeter-5.6.3\bin` в переменную окружения `Path`.
5. Откройте новый PowerShell и проверьте:

```powershell
java -version
jmeter --version
```

## 3. Поднять приложение

Из корня репозитория:

```bash
docker compose up --build
```

Дождитесь, пока API будет доступен на `http://localhost:8080`.

Быстрая проверка в отдельном терминале:

```bash
curl -f http://localhost:8080/actuator/health
```

Если всё хорошо, вы увидите JSON со статусом `UP` или HTTP-ответ `200`.

## 4. Запустить первый smoke-прогон

Smoke-прогон нужен, чтобы проверить, что JMeter видит приложение, регистрирует тестового пользователя, извлекает JWT-токен и получает `200` на API-запросах.

Команду ниже запускайте **из корня репозитория** `dnd-editor`:

```bash
mkdir -p docs/load-testing/jmeter/results docs/load-testing/jmeter/report
rm -f docs/load-testing/jmeter/results/smoke.jtl
rm -rf docs/load-testing/jmeter/report/smoke
jmeter -n \
  -t docs/load-testing/jmeter/dnd-editor-load-test.jmx \
  -l docs/load-testing/jmeter/results/smoke.jtl \
  -e -o docs/load-testing/jmeter/report/smoke \
  -Jhost=localhost \
  -Jport=8080 \
  -Jprotocol=http \
  -Jthreads=1 \
  -JrampUp=1 \
  -Jloops=1
```

Если терминал уже находится в папке `docs/load-testing/jmeter`, используйте короткие пути:

```bash
mkdir -p results report
rm -f results/smoke.jtl
rm -rf report/smoke
jmeter -n \
  -t dnd-editor-load-test.jmx \
  -l results/smoke.jtl \
  -e -o report/smoke \
  -Jhost=localhost \
  -Jport=8080 \
  -Jprotocol=http \
  -Jthreads=1 \
  -JrampUp=1 \
  -Jloops=1
```

## 5. Запустить JMeter через Docker

### Linux

```bash
mkdir -p docs/load-testing/jmeter/results docs/load-testing/jmeter/report
rm -f docs/load-testing/jmeter/results/smoke.jtl
rm -rf docs/load-testing/jmeter/report/smoke
docker run --rm --network host \
  -v "$PWD/docs/load-testing/jmeter:/tests" \
  justb4/jmeter:5.6.3 \
  -n \
  -t /tests/dnd-editor-load-test.jmx \
  -l /tests/results/smoke.jtl \
  -e -o /tests/report/smoke \
  -Jhost=localhost \
  -Jport=8080 \
  -Jprotocol=http \
  -Jthreads=1 \
  -JrampUp=1 \
  -Jloops=1
```

### macOS/Windows Docker Desktop

На macOS/Windows контейнеру обычно нужно обращаться к приложению на хосте через `host.docker.internal`:

```bash
mkdir -p docs/load-testing/jmeter/results docs/load-testing/jmeter/report
rm -f docs/load-testing/jmeter/results/smoke.jtl
rm -rf docs/load-testing/jmeter/report/smoke
docker run --rm \
  -v "$PWD/docs/load-testing/jmeter:/tests" \
  justb4/jmeter:5.6.3 \
  -n \
  -t /tests/dnd-editor-load-test.jmx \
  -l /tests/results/smoke.jtl \
  -e -o /tests/report/smoke \
  -Jhost=host.docker.internal \
  -Jport=8080 \
  -Jprotocol=http \
  -Jthreads=1 \
  -JrampUp=1 \
  -Jloops=1
```

## 6. Запустить обычный baseline-прогон

После успешного smoke-прогона можно дать умеренную нагрузку: 10 виртуальных пользователей, плавный разгон за 30 секунд, 20 повторов сценария на пользователя.

```bash
mkdir -p docs/load-testing/jmeter/results docs/load-testing/jmeter/report
rm -f docs/load-testing/jmeter/results/baseline.jtl
rm -rf docs/load-testing/jmeter/report/baseline
jmeter -n \
  -t docs/load-testing/jmeter/dnd-editor-load-test.jmx \
  -l docs/load-testing/jmeter/results/baseline.jtl \
  -e -o docs/load-testing/jmeter/report/baseline \
  -Jhost=localhost \
  -Jport=8080 \
  -Jprotocol=http \
  -Jthreads=10 \
  -JrampUp=30 \
  -Jloops=20
```

Что означают ключи команды:

| Ключ | Значение |
| --- | --- |
| `-n` | Запустить JMeter в non-GUI режиме. |
| `-t ...jmx` | Путь к тест-плану. |
| `-l ...jtl` | Файл сырых результатов. Файл должен быть новым или удалённым перед запуском. |
| `-e` | Сгенерировать HTML-отчёт после прогона. |
| `-o ...` | Папка HTML-отчёта. Папка должна не существовать или быть пустой. |
| `-Jhost=localhost` | Хост тестируемого API. |
| `-Jport=8080` | Порт тестируемого API. |
| `-Jprotocol=http` | Протокол тестируемого API. |
| `-Jthreads=10` | Количество виртуальных пользователей. |
| `-JrampUp=30` | За сколько секунд JMeter постепенно запустит всех пользователей. |
| `-Jloops=20` | Сколько раз каждый пользователь выполнит сценарий. |

## 7. Подобрать профиль нагрузки

Параметры передаются через `-J...` и переопределяют значения по умолчанию внутри `dnd-editor-load-test.jmx`:

| Параметр | Значение по умолчанию | Что означает |
| --- | ---: | --- |
| `host` | `localhost` | Хост API. |
| `port` | `8080` | Порт API. |
| `protocol` | `http` | Протокол. |
| `threads` | `20` | Количество виртуальных пользователей. |
| `rampUp` | `30` | За сколько секунд JMeter постепенно запустит всех пользователей. |
| `loops` | `10` | Сколько раз каждый пользователь выполнит сценарий. |

Рекомендуемый порядок прогона:

1. **Smoke**: `-Jthreads=1 -JrampUp=1 -Jloops=1` — убедиться, что план и окружение работают.
2. **Baseline**: `-Jthreads=10 -JrampUp=30 -Jloops=20` — получить базовые задержки и throughput.
3. **Step-up 20 users**: `-Jthreads=20 -JrampUp=60 -Jloops=30` — проверить рост нагрузки.
4. **Step-up 50 users**: `-Jthreads=50 -JrampUp=120 -Jloops=30` — найти первые признаки насыщения.
5. **Stress**: `-Jthreads=100 -JrampUp=180 -Jloops=50` — запускать только если предыдущие этапы стабильны.

Не увеличивайте нагрузку резким скачком. Сравнивайте p95/p99, процент ошибок, CPU/RAM приложения и PostgreSQL между прогонами.

## 8. Где смотреть результаты

После прогона доступны:

- `docs/load-testing/jmeter/results/*.jtl` — сырые результаты.
- `docs/load-testing/jmeter/report/<profile>/index.html` — HTML-отчёт JMeter.

Откройте отчёт в браузере:

```bash
open docs/load-testing/jmeter/report/baseline/index.html
```

В отчёте в первую очередь смотрите:

- **Error %** — процент ошибок. Для smoke/baseline ожидаем `0%`.
- **Throughput** — сколько запросов в секунду выдерживает API.
- **Response Times Percentiles** — особенно p90/p95/p99.
- **Active Threads Over Time** — действительно ли нагрузка разгонялась как ожидается.
- **Response Codes per Second** — нет ли всплесков `4xx`/`5xx`.

Минимальные критерии успешного прогона:

- `Error % = 0` или объяснимые единичные ошибки только при стресс-тесте.
- p95 latency укладывается в ваш SLA.
- Throughput растёт при увеличении потоков до точки насыщения.
- Приложение, PostgreSQL и Docker host не уходят в OOM/перезапуски.

## 9. Частые проблемы

### JMeter пишет, что `.jtl` уже существует

Удалите старый файл результата или укажите новое имя:

```bash
rm -f docs/load-testing/jmeter/results/baseline.jtl
```

### JMeter пишет, что папка отчёта не пустая

Удалите старый отчёт или укажите новую папку:

```bash
rm -rf docs/load-testing/jmeter/report/baseline
```

### `Connection refused`

Проверьте, что приложение запущено и отвечает:

```bash
curl -f http://localhost:8080/actuator/health
```

Если JMeter запущен в Docker на macOS/Windows, используйте `-Jhost=host.docker.internal`.

### `401 Unauthorized`

Проверьте в отчёте запрос `POST /auth/register`:

- если он упал, JMeter не получил `accessToken`, и все следующие запросы пойдут без валидного токена;
- если он успешный, но GET-запросы всё равно дают `401`, откройте sampler `GET ...` и проверьте, что в request headers есть `Authorization: Bearer ...`;
- если тест запускается много раз, старые `.jtl` и папку отчёта лучше удалять перед новым запуском, чтобы не смотреть устаревшие ошибки.

## 10. Что добавить следующим шагом

Текущий план регистрирует тестового пользователя и нагружает read-only эндпоинты. Для полной картины можно добавить отдельные сценарии:

- authenticated flow создания персонажа через `POST /characters`;
- CSV Data Set Config с заранее подготовленными пользователями/персонажами;
- Backend Listener для отправки метрик в InfluxDB/Prometheus/Grafana.
