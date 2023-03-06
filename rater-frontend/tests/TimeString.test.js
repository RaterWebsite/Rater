const { getTimeString } = require('../src/functions.js')

test("0 minute test", () => {
  expect(getTimeString(0)).toBe("")
});

test("1 minute test", () => {
  expect(getTimeString(1)).toBe("1m")
});

test("30 minute test", () => {
  expect(getTimeString(30)).toBe("30m")
});

test("59 minute test", () => {
  expect(getTimeString(59)).toBe("59m")
});

test("60 minute test", () => {
  expect(getTimeString(60)).toBe("1h")
});

test("61 minute test", () => {
  expect(getTimeString(61)).toBe("1h 1m")
});

test("120 minute test", () => {
  expect(getTimeString(120)).toBe("2h")
});

test("150 minute test", () => {
  expect(getTimeString(150)).toBe("2h 30m")
});