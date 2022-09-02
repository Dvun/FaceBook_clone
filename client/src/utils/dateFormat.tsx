
export function getDays(year: number, month: number): number {
  return new Date(year, month, 0).getDate()
}

export function currentMonthName() {
  const date = new Date()
  date.getMonth()
  return date.toLocaleString('en-US', {month: 'short'})
}

export function getMonthNumber(month: string, day: number, year: number): number {
  return new Date(Date.parse(month + day + ',' + year)).getMonth() + 1
}

export function getMonthNames(months: number[]): string[] {
  const date = new Date()
  return months.map(month => {
    date.setMonth(month)
    return date.toLocaleString('en-US', {month: 'short'})
  })
}

export function getDaysArray(year: number, month: string, day: number) {
  return Array.from(new Array(getDays(year, getMonthNumber(month, day, year))),
    (value: number, index: number) => index + 1)
}