/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import model.DataTable;

/**
 *
 * @author JimmyYang
 */
public class Table_Display {
    //產生Html表格類別_分頁

    //產生html表格_自訂表格名稱
    public String show_Table(String[] title, DataTable table, int page) {
        StringBuilder result = new StringBuilder();
        //表格_頭
        String head = "<table>";
        result.append(head);
        //表格標題
        String[] column_name = table.getColumn_Name();
        result.append("<tr>");
        result.append("<th>").append("項數").append("</th>");
        for (String column : title) {
            result.append("<th>").append(column).append("</th>");
        }
        result.append("</tr>");
        //表格_內容
        int total_page = table.getRow() / 10;
        int total_end = table.getRow() % 10;
        int page_start = page * 10 - 10;
        int page_end = page_start + 9;
        if (page == 1) {
            page_start = 0;
        }
        if (page < total_page) {
            //內容頁
            for (int i = page_start; i <= page_end; i++) {
                result.append("<tr>");
                result.append("<td>").append(i + 1).append("</td>");
                for (String column : column_name) {
                    result.append("<td>").append(table.getColume(i, column)).append("</td>");
                }
                result.append("</tr>");
            }
        } else if (page == total_page) {
            //最後一頁_內容頁
            if (total_end != 0) {
                page_end += 1;
            }
            for (int i = page_start; i < page_end; i++) {
                result.append("<tr>");
                result.append("<td>").append(i + 1).append("</td>");
                for (String column : column_name) {
                    result.append("<td>").append(table.getColume(i, column)).append("</td>");
                }
                result.append("</tr>");
            }
        } else if (page == total_page + 1) {
            //最後一頁_餘數頁面
            for (int i = table.getRow() - total_end; i < table.getRow(); i++) {
                result.append("<tr>");
                result.append("<td>").append(i + 1).append("</td>");
                for (String column : column_name) {
                    result.append("<td>").append(table.getColume(i, column)).append("</td>");
                }
                result.append("</tr>");
            }
        } else {
            //超出頁面範圍
            result.append("<tr>");
            result.append("<td>").append("超出頁面範圍").append("</td>");
            result.append("</tr>");
        }
        //表格_尾
        result.append("</table>");
        return result.toString();
    }

    //產生html表格_預設表格名稱
    public String show_Table(DataTable table, int page) {
        StringBuilder result = new StringBuilder();
        //表格_頭
        String head = "<table>";
        result.append(head);
        //表格標題
        String[] column_name = table.getColumn_Name();
        result.append("<tr>");
        result.append("<th>").append("項數").append("</th>");
        for (String column : column_name) {
            result.append("<th>").append(column).append("</th>");
        }
        result.append("</tr>");
        //表格_內容
        int total_page = table.getRow() / 10;
        int total_end = table.getRow() % 10;
        int page_start = page * 10 - 10;
        int page_end = page_start + 9;
        if (page == 1) {
            page_start = 0;
        }
        if (page < total_page) {
            //內容頁
            for (int i = page_start; i <= page_end; i++) {
                result.append("<tr>");
                result.append("<td>").append(i + 1).append("</td>");
                for (String column : column_name) {
                    result.append("<td>").append(table.getColume(i, column)).append("</td>");
                }
                result.append("</tr>");
            }
        } else if (page == total_page) {
            //最後一頁_內容頁
            if (total_end != 0) {
                page_end += 1;
            }
            for (int i = page_start; i < page_end; i++) {
                result.append("<tr>");
                result.append("<td>").append(i + 1).append("</td>");
                for (String column : column_name) {
                    result.append("<td>").append(table.getColume(i, column)).append("</td>");
                }
                result.append("</tr>");
            }
        } else if (page == total_page + 1) {
            //最後一頁_餘數頁面
            for (int i = table.getRow() - total_end; i < table.getRow(); i++) {
                result.append("<tr>");
                result.append("<td>").append(i + 1).append("</td>");
                for (String column : column_name) {
                    result.append("<td>").append(table.getColume(i, column)).append("</td>");
                }
                result.append("</tr>");
            }
        } else {
            //超出頁面範圍
            result.append("<tr>");
            result.append("<td>").append("超出頁面範圍").append("</td>");
            result.append("</tr>");
        }
        //表格_尾
        result.append("</table>");
        return result.toString();
    }

    //產生分頁按鈕
    public String create_page_link(String link, DataTable table, int now_page) {
        StringBuilder result = new StringBuilder();
        int total_page = table.getRow() / 10;
        int page_start = 1;

        //前十頁
        if (now_page >= 10) {
            int to_page = now_page / 10 * 10 - 10;
            if (to_page == 0) {
                to_page = 1;
            }
            result.append("<a href=\"").append(link).append("?page=").append(to_page)
                    .append("\">")
                    .append("上十頁").append("</a>").append("  ");
        }

        //前一頁
        if (now_page > 1) {
            result.append("<a href=\"").append(link).append("?page=").append(now_page - 1)
                    .append("\">")
                    .append("上一頁").append("</a>").append("  ");
        }

        //產生頁數按鈕_START
        if (total_page > 10) {
            //大於10頁_test
            int start = (now_page / 10) * 10;
            int end = (now_page / 10) * 10 + 10;
            int total_page_temp = total_page;
            if (start == 0) {
                start = 1;
            }
            if (table.getRow() % 10 != 0) {
                total_page_temp += 1;
            }
            if ((total_page / 10) * 10 == (now_page / 10) * 10) {
                if (total_page % 10 == 0) {
                    end = start + total_page % 10;
                } else {
                    end = start + total_page % 10 + 1;
                }
                for (int i = start; i <= end; i++) {
                    result.append("<a href=\"").append(link).append("?page=").append(i)
                            .append("\">")
                            .append(i).append("</a>").append("  ");
                }
            } else if (now_page != total_page_temp) {
                for (int i = start; i <= end; i++) {
                    result.append("<a href=\"").append(link).append("?page=").append(i)
                            .append("\">")
                            .append(i).append("</a>").append("  ");
                }
            }
        } else if (table.getRow() % 10 == 0) {
            for (int i = page_start; i <= total_page; i++) {
                result.append("<a href=\"").append(link).append("?page=").append(i)
                        .append("\">")
                        .append(i).append("</a>").append("  ");
            }
        } else {
            for (int i = page_start; i <= total_page + 1; i++) {
                result.append("<a href=\"").append(link).append("?page=").append(i)
                        .append("\">")
                        .append(i).append("</a>").append("  ");
            }
        }
        //產生頁數按鈕_END

        //下一頁
        if (now_page < total_page) {
            result.append("<a href=\"").append(link).append("?page=").append(now_page + 1)
                    .append("\">")
                    .append("下一頁").append("</a>").append("  ");
        }

        //下10頁
        if (total_page > 10) {
            if (table.getRow() % 10 != 0) {
                total_page += 1;
            }
            if ((now_page / 10) * 10 != (total_page / 10) * 10) {
                result.append("<a href=\"").append(link).append("?page=").append((now_page / 10) * 10 + 10)
                        .append("\">")
                        .append("下十頁").append("</a>").append("  ");
            }
        }

        result.append("   <a>目前頁數:").append(String.valueOf(now_page)).append("</a>");

        //總共頁數
        if (total_page == 0) {
            total_page = 1;
        }
        result.append("   <a>共").append(String.valueOf(total_page)).append("頁").append("</a>");
        return result.toString();
    }
}
