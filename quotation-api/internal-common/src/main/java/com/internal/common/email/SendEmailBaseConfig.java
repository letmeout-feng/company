package com.internal.common.email;

import com.alibaba.fastjson2.JSON;
import com.internal.common.core.domain.dto.EmailModelInfoConvertDTO;
import com.internal.common.core.domain.dto.EmailSubjectInfoConvertDTO;
import com.internal.common.core.domain.entity.SysUser;
import com.internal.common.utils.StringUtils;
import com.internal.common.utils.TemplateUtil;
import com.sun.mail.util.MailSSLSocketFactory;
import lombok.extern.slf4j.Slf4j;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.mail.*;
import javax.mail.internet.*;
import javax.mail.util.ByteArrayDataSource;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.security.GeneralSecurityException;
import java.util.*;

@Slf4j
public class SendEmailBaseConfig {

    private String flag;    //smtp是否需要认证
    private String host;    //邮件服务器主机名
    private String username;    //用户名
    private String password;    //密码
    private String title;   //标题
    private String content; //发送的内容
    private Boolean listFlag;   //是否发送复杂内容
    private Boolean cc; //是否抄送
    private List<SysUser> copyUser;  //抄送人
    private Address[] copyAddress;  //报错后 抄送人
    private Boolean to; //是否发送
    private List<SysUser> receiveUser;   //发送人
    private Address[] receiveAddress;   //报错后 发送人
    private EmailModelInfoConvertDTO emailModelInfoConvertDTO; // 邮箱模版占位符转换信息
    private EmailSubjectInfoConvertDTO emSubjectInfoConvertDTO; // 邮箱主题占位符转换信息
    private Map<String, byte[]> attachments; // 附件信息

    private static final int MAX_RETRY_COUNT = 3; // 最大重试次数


    //如果其他邮箱验证 在sendEmailToAllUser()，添加if判断


    public SendEmailBaseConfig(String flag, String host, String username, String password, String title, String content, Boolean listFlag,
                               Boolean cc, List<SysUser> copyUser, Address[] copyAddress, Boolean to, List<SysUser> receiveUser, Address[] receiveAddress,
                               EmailModelInfoConvertDTO emailModelInfoConvertDTO, EmailSubjectInfoConvertDTO emSubjectInfoConvertDTO,
                               Map<String, byte[]> attachments) {
        this.flag = flag;
        this.host = host;
        this.username = username;
        this.password = password;
        this.title = title;
        this.content = content;
        this.listFlag = listFlag;
        this.cc = cc;
        this.copyUser = copyUser;
        this.copyAddress = copyAddress;
        this.to = to;
        this.receiveUser = receiveUser;
        this.receiveAddress = receiveAddress;
        this.emailModelInfoConvertDTO = emailModelInfoConvertDTO;
        this.emSubjectInfoConvertDTO = emSubjectInfoConvertDTO;
        this.attachments = attachments;
    }

    //每天定时和crm同步项目名称任务提醒邮件
    public void sendEmailToSyncProjectManager(String contentStr) throws MessagingException, GeneralSecurityException {
        int attempt = 0;
        while (attempt < MAX_RETRY_COUNT) {
            try {
                sendEmailNTES(contentStr); // 执行邮件发送任务
                return; // 发送成功，退出方法
            } catch (Exception e) {
                attempt++;
                log.error("发送邮件时发生错误, 尝试次数: " + attempt, e);
                if (attempt == MAX_RETRY_COUNT) {
                    log.error("发送邮件失败，请稍后重试。"); // 达到最大重试次数，抛出异常
                }
            }
        }

    }


    //每天向所有用户发送邮件
    public void sendEmailToAllUser() {
        int attempt = 0;
        while (attempt < MAX_RETRY_COUNT) {
            try {
                String[] split = username.split("@");
                if ("qq.com".equals(split[1])) {
                    sendEmailQQ(); // 发送QQ邮箱
                    return; // 发送成功，退出方法
                }
                if ("163.com".equals(split[1])) {
                    sendEmailNTES(sendEmailContentStr()); // 发送网易邮箱
                    return; // 发送成功，退出方法
                }
                sendEmailNTES(sendEmailContentStr()); // 发送其他邮箱
                return; // 发送成功，退出方法
            } catch (Exception e) {
                attempt++;
                log.error("发送邮件时发生错误, 尝试次数: " + attempt, e);
                if (attempt == MAX_RETRY_COUNT) {
                    log.error("发送邮件失败，请稍后重试。");
                }
            }
        }
    }

    //初始化配置数据
    private Properties initProperties() {
        return new Properties();
    }


    //通过QQ邮箱发送
    private void sendEmailQQ() {
        try {
            Properties properties = initProperties();
            properties.setProperty("mail.host", host);
            properties.setProperty("mail.transport.protocol", "smtp");
            properties.setProperty("mail.smtp.auth", flag);

            MailSSLSocketFactory sf = new MailSSLSocketFactory();
            sf.setTrustAllHosts(true);
            properties.put("mail.smtp.ssl.enable", "true");
            properties.put("mail.smtp.ssl.socketFactory", sf);

            Session session = Session.getDefaultInstance(properties, new Authenticator() {
                @Override
                public PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(username, password);
                }
            });

            session.setDebug(true);
            Transport transport = session.getTransport();
            transport.connect(host, username, password);

            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(username));

            try {
                message.setSubject(new String(title.getBytes(StandardCharsets.UTF_8), "UTF-8"));
            } catch (UnsupportedEncodingException e) {
                log.error("邮件主题编码转换异常:", e);
            }

            if (listFlag) {
                content = sendEmailContentStr();
            }

            if (attachments != null) {
                MimeMultipart multipart = new MimeMultipart();
                MimeBodyPart textPart = new MimeBodyPart();
                textPart.setContent(content, "text/html;charset=utf-8");
                multipart.addBodyPart(textPart);
                for (Map.Entry<String, byte[]> entry : attachments.entrySet()) {
                    MimeBodyPart attachmentPart = new MimeBodyPart();
                    DataSource dataSource = new ByteArrayDataSource(entry.getValue(), "application/octet-stream");
                    attachmentPart.setDataHandler(new DataHandler(dataSource));
                    // 对文件名进行编码，避免乱码
                    try {
                        String encodedFileName = MimeUtility.encodeText(entry.getKey(), "UTF-8", "B");
                        attachmentPart.setFileName(encodedFileName);
                    } catch (UnsupportedEncodingException e) {
                        // 如果编码失败，使用原始文件名
                        attachmentPart.setFileName(entry.getKey());
                        log.error("编码文件名失败:{}，使用原始文件名。", JSON.toJSONString(e));
                    }

                    multipart.addBodyPart(attachmentPart);
                }
                message.setContent(multipart);
            }else{
                message.setContent(content, "text/html;charset=utf-8");
            }

            message.setSentDate(new Date());

            if (cc) {
                if (copyUser.size() > 0) {
                    message.setRecipients(Message.RecipientType.CC, ccAddresses(copyUser));
                }
            }
            if (to) {
                message.setRecipient(Message.RecipientType.CC, new InternetAddress(username));
                if (receiveUser.size() > 0) {
                    message.setRecipients(Message.RecipientType.TO, toAddresses(receiveUser));
                }
            }

            transport.sendMessage(message, message.getAllRecipients());
            transport.close();
        } catch (Exception e) {
            log.error("通过QQ邮箱发送邮件时发生错误", e);
        }
    }

    //通过网易邮箱发送
    private void sendEmailNTES(String contentStr) {
        try {
            Properties properties = initProperties();
            properties.setProperty("mail.transport.protocol", "smtp");
            properties.put("mail.smtp.auth", "true");
            properties.put("mail.smtp.host", host);
            properties.put("mail.smtp.port", 25);
            properties.put("mail.user", username);
            properties.put("mail.password", password);

            Authenticator auth = new Authenticator() {
                @Override
                public PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(username, password);
                }
            };

            Session session = Session.getInstance(properties, auth);
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(username));

            if (cc) {
                if (copyUser.size() > 0) {
                    message.setRecipients(Message.RecipientType.CC, ccAddresses(copyUser));
                }
            }
            if (to) {
                if (receiveUser.size() > 0) {
                    message.setRecipients(Message.RecipientType.TO, toAddresses(receiveUser));
                }
            }

            try {
                String encodedSubject = MimeUtility.encodeText(title, "UTF-8", null);
                message.setSubject(encodedSubject);
            } catch (UnsupportedEncodingException e) {
                log.error("邮件主题编码转换异常:", e);
            }

            if (listFlag) {
                content = contentStr;
            }

            if (attachments != null) {
                MimeMultipart multipart = new MimeMultipart();
                MimeBodyPart textPart = new MimeBodyPart();
                textPart.setContent(content, "text/html;charset=utf-8");
                multipart.addBodyPart(textPart);
                for (Map.Entry<String, byte[]> entry : attachments.entrySet()) {
                    MimeBodyPart attachmentPart = new MimeBodyPart();
                    DataSource dataSource = new ByteArrayDataSource(entry.getValue(), "application/octet-stream");
                    attachmentPart.setDataHandler(new DataHandler(dataSource));
                    // 对文件名进行编码，避免乱码
                    try {
                        String encodedFileName = MimeUtility.encodeText(entry.getKey(), "UTF-8", "B");
                        attachmentPart.setFileName(encodedFileName);
                    } catch (UnsupportedEncodingException e) {
                        // 如果编码失败，使用原始文件名
                        attachmentPart.setFileName(entry.getKey());
                        log.error("编码文件名失败:{}，使用原始文件名。", JSON.toJSONString(e));
                    }

                    multipart.addBodyPart(attachmentPart);
                }
                message.setContent(multipart);
            }else{
                message.setContent(content, "text/html;charset=utf-8");
            }

            Transport transport = session.getTransport();
            transport.connect(host, username, password);
            transport.sendMessage(message, message.getAllRecipients());
            transport.close();
        } catch (Exception e) {
            log.error("通过网易邮箱发送邮件时发生错误", e);
        }
    }

    //设置邮件内容，模版转换
    private String sendEmailContentStr() {
        title = TemplateUtil.replaceTemplate(title, emSubjectInfoConvertDTO);
        return TemplateUtil.replaceTemplate(content, emailModelInfoConvertDTO);
    }

    //设置抄送人地址
    private Address[] ccAddresses(List<SysUser> copyUser) throws AddressException {
        // 使用 Set 来跟踪唯一的电子邮件地址
        Set<String> uniqueEmails = new HashSet<>();
        // 使用 List 来存储有效的 InternetAddress
        List<Address> addressesList = new ArrayList<>();

        for (SysUser user : copyUser) {
            String email = user.getEmail();
            // 避免空值和重复
            if (StringUtils.isNotEmpty(email) && uniqueEmails.add(email)) {
                addressesList.add(new InternetAddress(email));
            }
        }
        // 将 List 转换为数组并返回
        return addressesList.toArray(new Address[0]);
    }

    //设置发送人地址
    private Address[] toAddresses(List<SysUser> receiveUser) throws AddressException {
        // 使用 Set 来跟踪唯一的电子邮件地址
        Set<String> uniqueEmails = new HashSet<>();
        // 使用 List 来临时存储有效的 InternetAddress
        List<Address> addressesList = new ArrayList<>();
        for (SysUser user : receiveUser) {
            String email = user.getEmail();
            // 避免空值和重复
            if (email != null && uniqueEmails.add(email)) {
                addressesList.add(new InternetAddress(email));
            }
        }
        // 将 List 转换为数组并返回
        return addressesList.toArray(new Address[0]);
    }

    //接受者邮箱不正确，过滤后得到正确的邮箱
    private void exceptionAddress(Address[] addresses) {
        try {
            if (addresses.length > 0) {
                Address[] address = new InternetAddress[addresses.length];
                for (int i = 0; i < addresses.length; i++) {
                    address[i] = new InternetAddress(addresses[i].toString());
                }
                receiveAddress = address;
                sendMailWhenException();
            }
        } catch (Exception e) {
            log.error("处理异常地址时发生错误", e);
        }
    }

    //给正确的邮箱发送邮件
    private void sendMailWhenException() {
        try {
            String[] split = username.split("@");
            if ("qq.com".equals(split[1])) {
                sendMailQQWhenException();
                return;
            }

            if ("163.com".equals(split[1])) {
                sendMailNTESWhenException();
            }
        } catch (Exception e) {
            log.error("发送异常邮件时发生错误", e);
        }
    }

    //正确的QQ邮箱发邮件
    private void sendMailQQWhenException() {
        try {
            Properties properties = initProperties();
            properties.setProperty("mail.host", host);
            properties.setProperty("mail.transport.protocol", "smtp");
            properties.setProperty("mail.smtp.auth", flag);

            MailSSLSocketFactory sf = new MailSSLSocketFactory();
            sf.setTrustAllHosts(true);
            properties.put("mail.smtp.ssl.enable", "true");
            properties.put("mail.smtp.ssl.socketFactory", sf);

            Session session = Session.getDefaultInstance(properties, new Authenticator() {
                @Override
                public PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(username, password);
                }
            });

            session.setDebug(true);
            Transport transport = session.getTransport();
            transport.connect(host, username, password);

            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(username));

            try {
                message.setSubject(new String(title.getBytes(StandardCharsets.UTF_8), "UTF-8"));
            } catch (UnsupportedEncodingException e) {
                log.error("邮件主题编码错误", e);
            }

            if (listFlag) {
                content = sendEmailContentStr();
            }
            if (attachments != null) {
                MimeMultipart multipart = new MimeMultipart();
                MimeBodyPart textPart = new MimeBodyPart();
                textPart.setContent(content, "text/html;charset=utf-8");
                multipart.addBodyPart(textPart);
                for (Map.Entry<String, byte[]> entry : attachments.entrySet()) {
                    MimeBodyPart attachmentPart = new MimeBodyPart();
                    DataSource dataSource = new ByteArrayDataSource(entry.getValue(), "application/octet-stream");
                    attachmentPart.setDataHandler(new DataHandler(dataSource));
                    // 对文件名进行编码，避免乱码
                    try {
                        String encodedFileName = MimeUtility.encodeText(entry.getKey(), "UTF-8", "B");
                        attachmentPart.setFileName(encodedFileName);
                    } catch (UnsupportedEncodingException e) {
                        // 如果编码失败，使用原始文件名
                        attachmentPart.setFileName(entry.getKey());
                        log.error("编码文件名失败:{}，使用原始文件名。", JSON.toJSONString(e));
                    }

                    multipart.addBodyPart(attachmentPart);
                }
                message.setContent(multipart);
            }else{
                message.setContent(content, "text/html;charset=utf-8");
            }

            message.setSentDate(new Date());

            if (cc) {
                if (copyAddress.length > 0) {
                    message.setRecipients(Message.RecipientType.CC, copyAddress);
                }
            }
            if (to) {
                if (receiveAddress.length > 0) {
                    message.setRecipients(Message.RecipientType.TO, receiveAddress);
                }
            }

            transport.sendMessage(message, message.getAllRecipients());
            transport.close();
        } catch (Exception e) {
            log.error("通过QQ邮箱发送异常邮件时发生错误", e);
        }
    }

    //正确的网易邮箱发数据
    private void sendMailNTESWhenException() {
        try {
            Properties properties = initProperties();
            properties.put("mail.smtp.auth", "true");
            properties.put("mail.smtp.host", host);
            properties.put("mail.smtp.port", 25);
            properties.put("mail.user", username);
            properties.put("mail.password", password);

            Authenticator auth = new Authenticator() {
                @Override
                public PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(username, password);
                }
            };

            Session session = Session.getInstance(properties, auth);
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(username));

            if (cc) {
                if (copyAddress.length > 0) {
                    message.setRecipients(Message.RecipientType.CC, copyAddress);
                }
            }
            if (to) {
                if (receiveAddress.length > 0) {
                    message.setRecipients(Message.RecipientType.TO, receiveAddress);
                }
            }

            try {
                message.setSubject(new String(title.getBytes(StandardCharsets.UTF_8), "UTF-8"));
            } catch (UnsupportedEncodingException e) {
                log.error("邮件主题编码错误", e);
            }

            if (listFlag) {
                content = sendEmailContentStr();
            }

            if (attachments != null) {
                MimeMultipart multipart = new MimeMultipart();
                MimeBodyPart textPart = new MimeBodyPart();
                textPart.setContent(content, "text/html;charset=utf-8");
                multipart.addBodyPart(textPart);
                for (Map.Entry<String, byte[]> entry : attachments.entrySet()) {
                    MimeBodyPart attachmentPart = new MimeBodyPart();
                    DataSource dataSource = new ByteArrayDataSource(entry.getValue(), "application/octet-stream");
                    attachmentPart.setDataHandler(new DataHandler(dataSource));
                    // 对文件名进行编码，避免乱码
                    try {
                        String encodedFileName = MimeUtility.encodeText(entry.getKey(), "UTF-8", "B");
                        attachmentPart.setFileName(encodedFileName);
                    } catch (UnsupportedEncodingException e) {
                        // 如果编码失败，使用原始文件名
                        attachmentPart.setFileName(entry.getKey());
                        log.error("编码文件名失败:{}，使用原始文件名。", JSON.toJSONString(e));
                    }

                    multipart.addBodyPart(attachmentPart);
                }
                message.setContent(multipart);
            }else{
                message.setContent(content, "text/html;charset=utf-8");
            }

            Transport.send(message);
        } catch (Exception e) {
            log.error("通过网易邮箱发送异常邮件时发生错误", e);
        }
    }

}
