import {dateToIsoDateTime, formatIsoDate, formatIsoDateTime, getDateFormat, getDateFromIsoDateTime} from "./time";

describe("time", () => {
  describe("formatIsoDateTime", () => {
    it("should format IsoDateTime correctly", () => {
      const result = formatIsoDateTime("2009-01-02T12:30:00+01:00");
      expect(result).toBe("02.01.2009, 12:30");
    })
  })

  describe("formatIsoDate", () => {
    it("should format IsoDateTime correctly", () => {
      const result = formatIsoDate("2009-01-02T12:00:00+01:00");
      expect(result).toBe("02.01.2009");
    })
  })
  describe("getDateFromIsoDateTime", () => {
    it("should format IsoDateTime to IsoDate correctly", () => {
      const result = getDateFromIsoDateTime("2009-01-02T12:00:00+01:00");
      expect(result).toBe("2009-01-02");
    })
  })

  describe("dateToIsoDateTime", () => {
    it("should transform time correctly when using . seperated date", () => {
      const result = dateToIsoDateTime("2023.05.23");
      expect(result).toBe("2023-05-23T00:00:00.000+02:00");
    })
    it("should transform time correctly when using - seperated date", () => {
      const result = dateToIsoDateTime("2023-05-23");
      expect(result).toBe("2023-05-23T00:00:00.000+02:00");
    })
    it("should throw error when format is incorrect", () => {
      expect(() => dateToIsoDateTime("2023_05_23")).toThrow(Error);
    })
  });

  describe("getDateFormat", () => {
    it("should return 'yyyy-MM-dd' by correct input", () => {
      expect(getDateFormat("2022-01-01")).toBe("yyyy-MM-dd");
    });
    it("should return 'yyyy.MM.dd' by correct input", () => {
      expect(getDateFormat("2022.01.01")).toBe("yyyy.MM.dd");
    });
    it("should return undefined if input does not match to any formats", () => {
      expect(getDateFormat("2022_01_01")).toBeUndefined();
    });
  })
})

