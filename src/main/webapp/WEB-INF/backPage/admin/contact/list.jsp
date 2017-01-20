<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<div class="bjui-pageContent">
    <form action="${pageContext.request.contextPath}/admin/profit/contact/save" data-toggle="validate" method="post">
        <div class="alert alert-warning " style="font-size: 14px;margin-top: 16px;margin-bottom: 0px;">注意：下述添加数据时，需要点击右下角的保存按钮，确定保存数据；否则不会保存数据！！！</div>
        <div style="padding: 10px;"></div>
        <input type="hidden" name="id" value="${contact.id}">
         <div class="pageFormContent" data-layout-h="0">
             <table class="table table-condensed table-hover" width="100%" >
                 <thead>
                 <label>联系信息</label>
                 </thead>
                 <tbody>
                       <tr>
                           <td colspan="2" style="width: 50%;">
                               <label class="control-label x150">微信号：</label>
                               <input type="text"  name="weiXin" value="${contact.weiXin}" size="63">
                           </td>
                       </tr>
                 <tr>
                     <td colspan="2">
                         <label class="control-label x150">QQ号：</label>
                         <input type="text" name="qq" value="${contact.qq}" size="63">
                     </td>
                 </tr>
                       <tr>
                           <td colspan="2">
                               <label class="control-label x150">手机号：</label>
                               <input type="text" name="telePhone" value="${contact.telePhone}" size="63">
                           </td>
                       </tr>
                 </tbody>
             </table>
             <div style="padding: 10px;"></div>
         </div>
    </form>
</div>

<div class="bjui-pageFooter">
    <ul>
        <li><button type="button" class="btn-close" data-icon="close">关闭</button></li>
        <li><button type="submit" class="btn-default" data-icon="save">保存</button></li>
    </ul>
</div>

