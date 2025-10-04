package com.codenlog.ticket.member.config.captcha;


import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.Objects;


/** 验证码配置属性
 *
 * * @Author: devhui@foxmail.com
 * * @Date: 2025/10/04/7:51 PM
 */
@Component
@ConfigurationProperties(prefix = "app.captcha")
public class CaptchaProperties {

    /**
     * 图形验证码配置
     */
    private GraphCaptcha graph = new GraphCaptcha();

    /**
     * 手机验证码配置
     */
    private PhoneCaptcha phone = new PhoneCaptcha();

    /**
     * 防刷策略配置
     */
    private AntiBrush antiBrush = new AntiBrush();

    /**
     * 短信服务配置
     */
    private SmsConfig sms = new SmsConfig();


    public GraphCaptcha getGraph() {
        return graph;
    }

    public void setGraph(GraphCaptcha graph) {
        this.graph = graph;
    }

    public PhoneCaptcha getPhone() {
        return phone;
    }

    public void setPhone(PhoneCaptcha phone) {
        this.phone = phone;
    }

    public AntiBrush getAntiBrush() {
        return antiBrush;
    }

    public void setAntiBrush(AntiBrush antiBrush) {
        this.antiBrush = antiBrush;
    }

    public SmsConfig getSms() {
        return sms;
    }

    public void setSms(SmsConfig sms) {
        this.sms = sms;
    }


    @Override
    public String toString() {
        return "CaptchaProperties{" +
                "graph=" + graph +
                ", phone=" + phone +
                ", antiBrush=" + antiBrush +
                ", sms=" + sms +
                '}';
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CaptchaProperties)) return false;
        CaptchaProperties that = (CaptchaProperties) o;
        return Objects.equals(graph, that.graph) &&
                Objects.equals(phone, that.phone) &&
                Objects.equals(antiBrush, that.antiBrush) &&
                Objects.equals(sms, that.sms);
    }

    @Override
    public int hashCode() {
        return Objects.hash(graph, phone, antiBrush, sms);
    }


    public static class GraphCaptcha {
        private Duration expire = Duration.ofMinutes(2);
        private int width = 120;
        private int height = 40;
        private int length = 4;

        public Duration getExpire() { return expire; }
        public void setExpire(Duration expire) { this.expire = expire; }

        public int getWidth() { return width; }
        public void setWidth(int width) { this.width = width; }

        public int getHeight() { return height; }
        public void setHeight(int height) { this.height = height; }

        public int getLength() { return length; }
        public void setLength(int length) { this.length = length; }

        @Override
        public String toString() {
            return "GraphCaptcha{" +
                    "expire=" + expire +
                    ", width=" + width +
                    ", height=" + height +
                    ", length=" + length +
                    '}';
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof GraphCaptcha)) return false;
            GraphCaptcha that = (GraphCaptcha) o;
            return width == that.width &&
                    height == that.height &&
                    length == that.length &&
                    Objects.equals(expire, that.expire);
        }

        @Override
        public int hashCode() {
            return Objects.hash(expire, width, height, length);
        }
    }

    public static class PhoneCaptcha {
        private Duration expire = Duration.ofMinutes(5);
        private int length = 6;
        private Duration sendInterval = Duration.ofMinutes(1);

        public Duration getExpire() { return expire; }
        public void setExpire(Duration expire) { this.expire = expire; }

        public int getLength() { return length; }
        public void setLength(int length) { this.length = length; }

        public Duration getSendInterval() { return sendInterval; }
        public void setSendInterval(Duration sendInterval) { this.sendInterval = sendInterval; }

        @Override
        public String toString() {
            return "PhoneCaptcha{" +
                    "expire=" + expire +
                    ", length=" + length +
                    ", sendInterval=" + sendInterval +
                    '}';
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof PhoneCaptcha)) return false;
            PhoneCaptcha that = (PhoneCaptcha) o;
            return length == that.length &&
                    Objects.equals(expire, that.expire) &&
                    Objects.equals(sendInterval, that.sendInterval);
        }

        @Override
        public int hashCode() {
            return Objects.hash(expire, length, sendInterval);
        }
    }

    public static class AntiBrush {
        private int maxIpRequests = 10;
        private Duration timeWindow = Duration.ofMinutes(10);
        private int graphCaptchaThreshold = 3;

        public int getMaxIpRequests() { return maxIpRequests; }
        public void setMaxIpRequests(int maxIpRequests) { this.maxIpRequests = maxIpRequests; }

        public Duration getTimeWindow() { return timeWindow; }
        public void setTimeWindow(Duration timeWindow) { this.timeWindow = timeWindow; }

        public int getGraphCaptchaThreshold() { return graphCaptchaThreshold; }
        public void setGraphCaptchaThreshold(int graphCaptchaThreshold) { this.graphCaptchaThreshold = graphCaptchaThreshold; }

        @Override
        public String toString() {
            return "AntiBrush{" +
                    "maxIpRequests=" + maxIpRequests +
                    ", timeWindow=" + timeWindow +
                    ", graphCaptchaThreshold=" + graphCaptchaThreshold +
                    '}';
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof AntiBrush)) return false;
            AntiBrush antiBrush = (AntiBrush) o;
            return maxIpRequests == antiBrush.maxIpRequests &&
                    graphCaptchaThreshold == antiBrush.graphCaptchaThreshold &&
                    Objects.equals(timeWindow, antiBrush.timeWindow);
        }

        @Override
        public int hashCode() {
            return Objects.hash(maxIpRequests, timeWindow, graphCaptchaThreshold);
        }
    }

    public static class SmsConfig {
        private String defaultProvider = "aliyun";
        private boolean testMode = false;
        private String testPhoneNumber;

        public String getDefaultProvider() { return defaultProvider; }
        public void setDefaultProvider(String defaultProvider) { this.defaultProvider = defaultProvider; }

        public boolean isTestMode() { return testMode; }
        public void setTestMode(boolean testMode) { this.testMode = testMode; }

        public String getTestPhoneNumber() { return testPhoneNumber; }
        public void setTestPhoneNumber(String testPhoneNumber) { this.testPhoneNumber = testPhoneNumber; }

        @Override
        public String toString() {
            return "SmsConfig{" +
                    "defaultProvider='" + defaultProvider + '\'' +
                    ", testMode=" + testMode +
                    ", testPhoneNumber='" + testPhoneNumber + '\'' +
                    '}';
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof SmsConfig)) return false;
            SmsConfig smsConfig = (SmsConfig) o;
            return testMode == smsConfig.testMode &&
                    Objects.equals(defaultProvider, smsConfig.defaultProvider) &&
                    Objects.equals(testPhoneNumber, smsConfig.testPhoneNumber);
        }

        @Override
        public int hashCode() {
            return Objects.hash(defaultProvider, testMode, testPhoneNumber);
        }
    }
}
