import moment from "moment-timezone";
export const formatIsoDateTime = (isoDateTime: string) => moment(isoDateTime).format("DD.MM.YYYY, HH:mm"); // FIXME: add tests
export const formatIsoDate = (isoDateTime: string) => moment(isoDateTime).format("DD.MM.YYYY"); // FIXME: add tests

export const getCurrentDate = () =>  moment().format("DD.MM.YYYY")

/**
 *
 * @param date format: YYYY.MM.DD
 */
export const dateToIsoDateTime = (date: string): string => { // FIXME: add tests
  const reformattedDate = date.replace(".", "-")
  return moment(reformattedDate).tz("Europe/Berlin").utc().format()
}
