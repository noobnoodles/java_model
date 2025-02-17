package com.example.auth.util.mail;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class EmailContentBuilder {

    public static String buildRegisterSubject(String code) {
        return "欢迎注册！您的验证码是 " + code + "（有效期5分钟）";
    }

    public static String buildRegisterContent(String code) {
        StringBuilder content = new StringBuilder();
        content.append("尊敬的用户：\n\n");
        content.append("您好！欢迎注册[统一认证中心]，请查收您的账户验证码：\n");
        content.append("验证码：").append(code).append("\n");
        content.append("（该验证码5分钟内有效，仅限本次注册使用）\n\n");
        content.append("为保证账户安全，请勿向任何人泄露此验证码。如非本人操作，请忽略本邮件。\n\n");
        content.append("感谢您的信任，欢迎使用本产品！\n\n\n");
        content.append(getCurrentDate());
        return content.toString();
    }

    public static String buildResetPasswordSubject(String code) {
        return "安全验证：找回密码验证码 " + code + "（有效期5分钟）";
    }

    public static String buildResetPasswordContent(String code) {
        StringBuilder content = new StringBuilder();
        content.append("尊敬的用户：\n\n");
        content.append("您正在申请重置账户密码，请确认是本人操作后使用以下验证码：\n");
        content.append("验证码：").append(code).append("\n");
        content.append("（该验证码5分钟内有效，过期需重新申请）\n\n");
        content.append("⚠️ 安全提醒：\n\n");
        content.append("请勿将验证码告知他人，平台工作人员不会主动索取\n\n");
        content.append("如未发起密码重置请求，请立即修改账户密码\n\n");
        content.append("感谢您对我们的支持！\n\n\n");
        content.append(getCurrentDate());
        return content.toString();
    }

    private static String getCurrentDate() {
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy年MM月dd日");
        return formatter.format(now);
    }
} 