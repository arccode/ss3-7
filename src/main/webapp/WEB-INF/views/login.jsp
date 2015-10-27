<%--
  Created by IntelliJ IDEA.
  User: arccode
  Date: 15-3-15
  Time: 0:06
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>登陆页面</title>
</head>
<body>

    <form action="login.do" method="post">

        <table>
            <tr>
                <td>用户名: </td>
                <td>
                    <input type="text" name="username" value="user"/>
                </td>
            </tr>
            <tr>
                <td>密码: </td>
                <td>
                    <input type="text" name="password" value="user"/>
                </td>
            </tr>
            <tr>
                <td colspan="2" align="center">
                    <input type="submit" value="登陆"/>
                    <input type="reset" value="重置"/>
                </td>
            </tr>
        </table>
    </form>
</body>
</html>
