# Нагрузочное тестирование DND Editor API через JMeter

В каталоге лежит готовый JMeter-план `dnd-editor-load-test.jmx` для базового нагрузочного теста публичных GET-эндпоинтов приложения: `/actuator/health`, `/abilities`, `/races`, `/classes`, `/spells`.

## 1. Поднять приложение

Из корня репозитория:

```bash
docker compose up --build
```

Дождитесь, пока API будет доступен на `http://localhost:8080`.

Быстрая проверка:

```bash
curl -f http://localhost:8080/actuator/health
```

## 2. Запустить JMeter без GUI

Если JMeter установлен локально:

```bash
mkdir -p docs/load-testing/jmeter/results docs/load-testing/jmeter/report
jmeter -n \
  -t docs/load-testing/jmeter/dnd-editor-load-test.jmx \
  -l docs/load-testing/jmeter/results/dnd-editor-load-test.jtl \
  -e -o docs/load-testing/jmeter/report \
  -Jhost=localhost \
  -Jport=8080 \
  -Jprotocol=http \
  -Jthreads=20 \
  -JrampUp=30 \
  -Jloops=10
```

Вариант через Docker-образ JMeter:

```bash
mkdir -p docs/load-testing/jmeter/results docs/load-testing/jmeter/report
docker run --rm --network host \
  -v "$PWD/docs/load-testing/jmeter:/tests" \
  justb4/jmeter:5.6.3 \
  -n -t /tests/dnd-editor-load-test.jmx \
  -l /tests/results/dnd-editor-load-test.jtl \
  -e -o /tests/report \
  -Jhost=localhost \
  -Jport=8080 \
  -Jprotocol=http \
  -Jthreads=20 \
  -JrampUp=30 \
  -Jloops=10
```

> На macOS/Windows Docker Desktop вместо `--network host` обычно удобнее использовать `-Jhost=host.docker.internal` и убрать `--network host`.

## 3. Подобрать профиль нагрузки

Параметры передаются через `-J...`:

| Параметр | Значение по умолчанию | Что означает |
| --- | ---: | --- |
| `host` | `localhost` | Хост API |
| `port` | `8080` | Порт API |
| `protocol` | `http` | Протокол |
| `threads` | `20` | Количество виртуальных пользователей |
| `rampUp` | `30` | За сколько секунд JMeter постепенно запустит всех пользователей |
| `loops` | `10` | Сколько раз каждый пользователь выполнит сценарий |

Рекомендуемый порядок прогона:

1. **Smoke**: `-Jthreads=1 -JrampUp=1 -Jloops=1` — убедиться, что план и окружение работают.
2. **Baseline**: `-Jthreads=10 -JrampUp=30 -Jloops=20` — получить базовые задержки и throughput.
3. **Step-up**: повторить прогоны с `20`, `50`, `100` потоками, сравнивая p95/p99, ошибки и CPU/RAM/DB.

## 4. Читать результаты

После прогона доступны:

- `docs/load-testing/jmeter/results/dnd-editor-load-test.jtl` — сырые результаты.
- `docs/load-testing/jmeter/report/index.html` — HTML-отчёт JMeter.

Минимальные критерии успешного прогона:

- `Error % = 0` или объяснимые единичные ошибки только при стресс-тесте.
- p95 latency укладывается в ваш SLA.
- Throughput растёт при увеличении потоков до точки насыщения.
- Приложение, PostgreSQL и Docker host не уходят в OOM/перезапуски.

## 5. Что добавить следующим шагом

Текущий план безопасно нагружает публичные read-only эндпоинты. Для полной картины можно добавить отдельные сценарии:

- авторизация: `POST /auth/register`, `POST /auth/login`, extraction `accessToken`;
- authenticated flow создания персонажа через `POST /characters`;
- CSV Data Set Config с заранее подготовленными пользователями/персонажами;
- Backend Listener для отправки метрик в InfluxDB/Prometheus/Grafana.
