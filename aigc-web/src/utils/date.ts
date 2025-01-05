export function timestampToYMD(timestamp: number): string {
  // 创建一个Date对象并传入时间戳
  const date = new Date(timestamp * 1000);

  // 获取年、月、日
  const year = date.getFullYear();
  const month = date.getMonth() + 1; // 月份从0开始，需要加1
  const day = date.getDate();

  const hours = date.getHours();
  const minutes = date.getMinutes();
  const seconds = date.getSeconds();

  // 如果月份或日期是个位数，可以在前面补零
  const monthStr = month < 10 ? `0${month}` : month.toString();
  const dayStr = day < 10 ? `0${day}` : day.toString();

  const padZero = (num: number): string => (num < 10 ? `0${num}` : num.toString());

  // 返回年月日格式字符串
  return `${year}年 ${padZero(month)}月 ${padZero(day)}日 ${padZero(hours)}时 ${padZero(minutes)}分`;
}

export function dateStrToYMD(dateStr: number): string {
  return timestampToYMD(new Date(dateStr).getTime() / 1000)
}

export function timestampToYMD2(timestamp: number): string {
  return "";
}
