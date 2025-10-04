package com.codenlog.ticket.common.util;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Base64;
import java.util.Random;

/**
 * @Author: devhui@foxmail.com
 * @Date: 2025/10/04/8:49 PM
 */
public final class CaptchaImageUtil {

    private static final Random RANDOM = new Random();

    private CaptchaImageUtil() {}

    /**
     * 生成验证码文本（纯数字）
     */
    public static String genText(int length) {
        StringBuilder sb = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            sb.append(RANDOM.nextInt(10));
        }
        return sb.toString();
    }

    /**
     * 根据文本画一张 PNG 并返回 data:image/png;base64,xxx
     */
    public static String drawBase64(String text, int width, int height) {
        BufferedImage img = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics2D g = img.createGraphics();

        // 抗锯齿
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // 背景
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, width, height);

        // 画干扰线
        g.setColor(Color.LIGHT_GRAY);
        for (int i = 0; i < 8; i++) {
            int x1 = RANDOM.nextInt(width);
            int y1 = RANDOM.nextInt(height);
            int x2 = RANDOM.nextInt(width);
            int y2 = RANDOM.nextInt(height);
            g.drawLine(x1, y1, x2, y2);
        }

        // 画字符串
        g.setFont(new Font("Arial", Font.BOLD, 28));
        FontMetrics fm = g.getFontMetrics();
        int baseLine = (height - fm.getHeight()) / 2 + fm.getAscent();
        for (int i = 0; i < text.length(); i++) {
            g.setColor(new Color(RANDOM.nextInt(180), RANDOM.nextInt(180), RANDOM.nextInt(180)));
            String ch = String.valueOf(text.charAt(i));
            int x = (width - fm.stringWidth(text)) / 2 + i * fm.stringWidth(String.valueOf(text.charAt(0)));
            g.drawString(ch, x, baseLine);
        }

        g.dispose();

        try (ByteArrayOutputStream out = new ByteArrayOutputStream()) {
                ImageIO.write(img, "png", out);
            return "data:image/png;base64," + Base64.getEncoder().encodeToString(out.toByteArray());
        } catch (IOException e) {
            throw new RuntimeException("生成验证码图片失败", e);
        }
    }

}
