import { saveAs } from "file-saver";
import * as XLSX from "xlsx";

// 日期格式化
export function parseTime(time, pattern) {
  if (arguments.length === 0 || !time) {
    return null;
  }
  const format = pattern || "{y}-{m}-{d} {h}:{i}:{s}";
  let date;
  if (typeof time === "object") {
    date = time;
  } else {
    if (typeof time === "string" && /^[0-9]+$/.test(time)) {
      time = parseInt(time);
    } else if (typeof time === "string") {
      time = time
        .replace(new RegExp(/-/gm), "/")
        .replace("T", " ")
        .replace(new RegExp(/\.[\d]{3}/gm), "");
    }
    if (typeof time === "number" && time.toString().length === 10) {
      time = time * 1000;
    }
    date = new Date(time);
  }
  const formatObj = {
    y: date.getFullYear(),
    m: date.getMonth() + 1,
    d: date.getDate(),
    h: date.getHours(),
    i: date.getMinutes(),
    s: date.getSeconds(),
    a: date.getDay(),
  };
  const time_str = format.replace(/{(y|m|d|h|i|s|a)+}/g, (result, key) => {
    let value = formatObj[key];
    // Note: getDay() returns 0 on Sunday
    if (key === "a") {
      return ["日", "一", "二", "三", "四", "五", "六"][value];
    }
    if (result.length > 0 && value < 10) {
      value = "0" + value;
    }
    return value || 0;
  });
  return time_str;
}

// 转换字符串，undefined,null等转化为""
export function parseStrEmpty(str) {
  if (!str || str == "undefined" || str == "null") {
    return "";
  }
  return str;
}

// 验证是否为blob格式
export function blobValidate(data) {
  return data.type !== "application/json";
  // return data instanceof Blob;
}

// 通用下载方法
const baseURL = process.env.VUE_APP_BASE_API;
export function download(url, params, filename) {
  return fetch(baseURL + url, {
    method: "POST",
    headers: {
      "Content-Type": "application/json",
      Authorization: "Bearer " + localStorage.getItem("token"),
    },
    body: JSON.stringify(params),
  })
    .then(async (data) => {
      const blob = await data.blob();
      saveAs(blob, filename);
    })
    .catch((r) => {
      console.error(r);
    });
}

// 格式化日期为 yyyy-MM-dd
export function formatYMDDate(date) {
  const year = date.getFullYear();
  const month = (date.getMonth() + 1).toString().padStart(2, "0");
  const day = date.getDate().toString().padStart(2, "0");
  return `${year}-${month}-${day}`;
}

// 前端导出列表为excel
export function exportToExcel(exportData, title) {
  // 创建一个工作簿并添加数据
  const worksheet = XLSX.utils.json_to_sheet(exportData);

  // 创建一个工作簿
  const workbook = XLSX.utils.book_new();
  XLSX.utils.book_append_sheet(workbook, worksheet, title);

  // 导出为 Excel 文件
  XLSX.writeFile(workbook, `${title}_${formatYMDDate(new Date())}.xlsx`);
}

// status显示
export const statusMap = {
  0: "报价申请",
  1: "未更新",
  2: "待销售报价",
  3: "待报价审批",
  4: "销售已报价",
  5: "待签约",
  6: "已签约",
  7: "丢单",
  8: "报价审批未通过",
  9: "签约审核未通过",
  10: "待签约审批",
  11: "废弃",
  12: "关闭",
  13: "待更新成本报价",
  14: "待确认丢单",
  15: "丢单审批未通过",
};
