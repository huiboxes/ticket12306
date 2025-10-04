package com.codenlog.ticket.member.config.sms;

import com.codenlog.ticket.member.config.captcha.CaptchaProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author: devhui@foxmail.com
 * @Date: 2025/10/04/8:11 PM
 */
@Component
public class SmsProviderFactory {

    private final Map<String, SmsProvider> providers = new HashMap<>();
    private final String defaultProvider;
    private final boolean testMode;
    private final String testPhoneNumber;
    private final SmsProvider testProvider;

    @Autowired
    public SmsProviderFactory(List<SmsProvider> providerList, CaptchaProperties properties) {
        SmsProvider testProviderTemp = null;
        
        // 注册所有短信提供商
        for (SmsProvider provider : providerList) {
            providers.put(provider.getName(), provider);

            if ("test".equals(provider.getName())) {
                testProviderTemp = provider;
            }
        }

        // 获取配置
        this.defaultProvider = properties.getSms().getDefaultProvider();
        this.testMode = properties.getSms().isTestMode();
        this.testPhoneNumber = properties.getSms().getTestPhoneNumber();

        if (!providers.containsKey(defaultProvider)) {
            throw new IllegalArgumentException("默认短信提供商不存在: " + defaultProvider);
        }
        
        this.testProvider = testProviderTemp;
    }

    /**
     * 获取指定名称的短信提供商
     */
    public SmsProvider getProvider(String name) {
        SmsProvider provider = providers.get(name);
        if (provider == null) {
            throw new IllegalArgumentException("短信提供商不存在: " + name);
        }
        return provider;
    }

    /**
     * 获取默认短信提供商
     */
    public SmsProvider getDefaultProvider() {
        return getProvider(defaultProvider);
    }

    /**
     * 根据环境和手机号获取合适的短信提供商
     */
    public SmsProvider getProviderForPhone(String phoneNumber) {
        if (testMode) {
            // 如果指定了测试手机号，只有该手机号使用真实提供商
            if (testPhoneNumber != null && testPhoneNumber.equals(phoneNumber)) {
                return getDefaultProvider();
            }
            // 其他手机号使用测试提供商
            return getProvider("test");
        }

        // 非测试模式下使用默认提供商
        return getDefaultProvider();
    }

}