import {DateTime} from "luxon";

export const formatIsoDateTime = (isoDateTime: string) => DateTime
  .fromISO(isoDateTime)
  .setLocale("de")
  .setZone("Europe/Berlin")
  .toLocaleString({
    ...DateTime.DATETIME_SHORT,
    day: "2-digit",
    month: "2-digit"
  });
export const formatIsoDate = (isoDateTime: string) => DateTime
  .fromISO(isoDateTime)
  .setLocale("de")
  .setZone("Europe/Berlin")
  .toLocaleString({
    ...DateTime.DATE_SHORT,
    day: "2-digit",
    month: "2-digit"
  });
export const getDateFromIsoDateTime = (isoDateTime: string) => DateTime
  .fromISO(isoDateTime)
  .setLocale("de")
  .setZone("Europe/Berlin")
  .toISODate()

export const getCurrentDate = () => DateTime
  .now()
  .toISODate();

/**
 *
 * @param date format: YYYY.MM.DD or YYYY-MM-DD
 * @throws Error: when format of date is incorrect
 */
export const dateToIsoDateTime = (date: string): string => {
  const format = getDateFormat(date)
  if(!format) {
    throw new Error("incorrect date format")
  }

  return DateTime
    .fromFormat(date, format, {locale: "de", zone: "Europe/Berlin"})
    .toISO();
}
export const getDateFormat = (date: string) => {
  if(/\d{4}\.\d{2}\.\d{2}/g.test(date) ) {
    return "yyyy.MM.dd"
  }
  if(/\d{4}-\d{2}-\d{2}/g.test(date) ) {
    return "yyyy-MM-dd"
  }
  return undefined
}
