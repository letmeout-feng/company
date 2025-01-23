package com.internal.common.utils.http;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetAddress;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.regex.Pattern;

/**
 * 通用http发送方法
 *
 * @author every
 */
public class HttpUtils {
    private static final Logger log = LoggerFactory.getLogger(HttpUtils.class);

    // 定义允许访问的白名单域名或IP
    private static final String[] ALLOWED_DOMAINS = {
            "example.com",
            "api.internal.com",
            "whois.pconline.com.cn"
    };

    // 定义正则表达式以匹配可疑的 URL 模式
    private static final Pattern SUSPICIOUS_URL_PATTERN = Pattern.compile("([\\?&=])(?:[A-Za-z0-9%_-]{1,}){0,}[/\\\\](\\w+)([\\?&=]|$)");


    /**
     * 检查URL是否在允许的域名列表中，并且符合安全规则
     *
     * @param url 要检查的URL
     * @return 是否允许访问
     */
    private static boolean isUrlAllowed(String url) {
        try {
            URL parsedUrl = new URL(url);
            String host = parsedUrl.getHost();
            String protocol = parsedUrl.getProtocol();

            // 检查是否使用了支持的协议
            if (!"http".equalsIgnoreCase(protocol) && !"https".equalsIgnoreCase(protocol)) {
                log.error("不允许使用的协议: {}", protocol);
                return false;
            }

            // 检查是否访问本地地址
            if (isLocalAddress(host)) {
                log.error("禁止访问本地地址: {}", host);
                return false;
            }

            // 检查是否在白名单中
            for (String allowedDomain : ALLOWED_DOMAINS) {
                if (host.equals(allowedDomain)) {
                    return true;
                }
            }

            // 检查是否包含可疑路径或编码
            if (SUSPICIOUS_URL_PATTERN.matcher(url).find()) {
                log.error("检测到可疑的URL路径或编码: {}", url);
                return false;
            }

            return false;
        } catch (Exception e) {
            log.error("URL解析错误: {}", url, e);
            return false;
        }
    }

    /**
     * 判断是否是本地地址
     *
     * @param host 主机名
     * @return 是否是本地地址
     */
    private static boolean isLocalAddress(String host) {
        try {
            InetAddress address = InetAddress.getByName(host);
            return address.isLoopbackAddress() || address.isLinkLocalAddress() || address.isSiteLocalAddress();
        } catch (UnknownHostException e) {
            log.error("无法解析主机地址: {}", host, e);
            return false;
        }
    }
}