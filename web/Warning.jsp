<%-- 
    Document   : Warning
    Created on : 2017/5/11, 下午 09:33:39
    Author     : JimmyYang
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <%
            //檢查帳號有無登入，未登入則返回登入畫面
            String user_acount = (String) session.getAttribute("user_acount");
            if (user_acount == null) {
                out.print("<script>top.window.location.replace(\"/Lotto/index.html\");</script>");
            }
        %>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link href="css/login1.css" rel="stylesheet" type="text/css">
        <title>本站協議線上協議規則</title>
    </head>
    <body>
        <h1>我在根目錄../Warning</h1>
        <table width="830px" border="border" align="center" cellpadding="0" cellspacing="0">
            <tr><td colspan="8" border="0" class="title1">本站線上協議規則</td></tr>
            <tr><td class="line2" colspan="8"></td></tr>
            <tr><td class="line1" colspan="8"></td></tr>
            <tr><td class="line2" colspan="8"></td></tr>
        </table>
        <table width="830" border="0" align="center" cellpadding="0" cellspacing="1" class="right_tab" >
            <tr><td class="row1"><table width="830" border="0" align="center" cellpadding="0" cellspacing="1" class="right_tab" >
                        <tr>
                            <td class="row1"> 致會員：<br></td>
                        </tr>
                        <tr>
                            <td class="agree"><br />
                                <ol>
                                    <li>為避免爭議，請會員於開始投注前，詳細閱讀本公司所定之各項規則。會員一經<font class="font_red">『我同意』</font>進入本網站進行投注時，即被視為已接受所有之規定。 </li>
                                    <li>為確保雙方權益，若有發生不可抗拒之災害導致網站故障、硬體損壞等情況，本公司將以<font class="font_red">最後備份之資料</font>及會員投注後所儲存的資料網站內<font class="font_red">&quot;下注明細&quot;</font>所記載，作為最後處理的依據。</li>
                                    <li>會員在投注完成後，到"下注明細"中確認該筆注單是否<font class="font_red">&quot;下注成功&quot;</font>，且應仔細核對該筆注單之下注內容與金額．本公司僅以會員<font class="font_red">&quot;下注明細&quot;</font>的內容為計算結果之依據。</li>
                                    <li>所有賠率受多種因素影響調整變動漲跌。所有注單將以顯示<font class="font_red">&quot;下注成功&quot;</font>後的注單為準。</li>
                                    <li>公佈賠率時，出現任何的打字錯誤或非故意人為疏失，本公司將保留<font class="font_red">刪除任何錯誤賠率注單</font>之權利，並以<font class="font_red">&quot;跑馬燈&quot;</font>公佈之，不個別另行通知。</li>
                                    <li>如因系統錯誤，而造成異常不合理注單，本公司有刪除任何錯誤注單的權利並追溯公司之前之損失。</li>
                                    <li>經公司發現於該注單為「<font class="font_red">獎號開出後</font>」下注之注單一率取消，不便之處，敬請見諒。</li>
                                    <li>任何的投訴必須在開獎之前提出，本站不會受理任何開獎後的投訴。</li>
                                    <li>本公司若公告之文字訊息輸入錯誤將有權修正錯誤。</li>
                                </ol></td>
                        </tr>
                        <tr>
                            <td class="row1">投注重要須知</td>
                        </tr>
                        <tr class="agree">
                            <td><br />
                                <ol>
                                    <li>本系統適用於 Google Chrome 或 IE( <font class="font_red">IE 8以上</font>)瀏覽器，使用其它瀏覽器可能會造成部份功能無法運作。</li>
                                    <li>建議使用 Google Chrome 操作，可取得較佳效能。</li>
                                    <li>所有投注皆含本金。</li>
                                    <li>當您在投注之後，請等待<font class="font_red">&quot;下注成功&quot;</font>的顯示。</li>
                                    <li><font class="font_blue">本系統之所有賠率(價位)皆為浮動，請於下注前點選詳細內容之查看賠率(價位)調整，派彩結果將以確認投注時之注單賠率(價位)為依據。</font></li>
                                    <li>會員已投注注單，如超過公司規定"下注撤單時間"，本公司將不接受「刪單」。</li>
                                    <li>本公司一概不接受任何刪單或修改之要求，請各位會員按照正常程序下注。</li>
                                    <li>客戶有責任確保自己的帳戶及登入資料的保密性。在本網站上以個人的使用者名稱及密碼進行的任何網上投注將被視為有效。<br />
                                    <li>如因在本網站投注觸犯當地法律，本公司概不負責。</li>
                                    <li>若遭駭客入侵破壞或發生不可抗拒之災害導致網站故障或資料遺失等情況，本公司將以備份資料為最後處理依據。
                                        為確保雙方利益，公司只接受投注後有列印下注明細的會員作投訴並會盡速處理。</li>
                                </ol></td>
                        </tr>
                        <tr>
                            <td class="m_ti">對於以上由本站所明列的協議和規則本人已詳細閱讀並且</td>
                        </tr>
                        <tr>
                            <td class="row_last1"><input type="button" id="Submit" value="我同意" class=""  onclick="window.location = 'Game_index.jsp'"/>
                                &nbsp;&nbsp;&nbsp;
                                <input type="button" id="Submit2" value="我不同意" class="za_but_agr"  onclick="window.location = 'System/logout.jsp'" /></td>
                        </tr>
                    </table></td></tr>
        </table>
    </body>
</html>
