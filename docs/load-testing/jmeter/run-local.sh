#!/usr/bin/env bash
set -euo pipefail

SCRIPT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"
PROFILE="${1:-smoke}"

case "${PROFILE}" in
  smoke)
    DEFAULT_THREADS=1
    DEFAULT_RAMP_UP=1
    DEFAULT_LOOPS=1
    ;;
  baseline)
    DEFAULT_THREADS=10
    DEFAULT_RAMP_UP=30
    DEFAULT_LOOPS=20
    ;;
  *)
    DEFAULT_THREADS=20
    DEFAULT_RAMP_UP=60
    DEFAULT_LOOPS=30
    ;;
esac

HOST="${HOST:-localhost}"
PORT="${PORT:-8080}"
PROTOCOL="${PROTOCOL:-http}"
THREADS="${THREADS:-${DEFAULT_THREADS}}"
RAMP_UP="${RAMP_UP:-${DEFAULT_RAMP_UP}}"
LOOPS="${LOOPS:-${DEFAULT_LOOPS}}"

JMX_FILE="${SCRIPT_DIR}/dnd-editor-load-test.jmx"
RESULT_FILE="${SCRIPT_DIR}/results/${PROFILE}.jtl"
REPORT_DIR="${SCRIPT_DIR}/report/${PROFILE}"

mkdir -p "${SCRIPT_DIR}/results" "${SCRIPT_DIR}/report"
rm -f "${RESULT_FILE}"
rm -rf "${REPORT_DIR}"

echo "Running JMeter profile '${PROFILE}' against ${PROTOCOL}://${HOST}:${PORT}"
echo "threads=${THREADS}, rampUp=${RAMP_UP}, loops=${LOOPS}"

jmeter -n \
  -t "${JMX_FILE}" \
  -l "${RESULT_FILE}" \
  -e -o "${REPORT_DIR}" \
  -Jhost="${HOST}" \
  -Jport="${PORT}" \
  -Jprotocol="${PROTOCOL}" \
  -Jthreads="${THREADS}" \
  -JrampUp="${RAMP_UP}" \
  -Jloops="${LOOPS}"

echo "Raw results: ${RESULT_FILE}"
echo "HTML report: ${REPORT_DIR}/index.html"
