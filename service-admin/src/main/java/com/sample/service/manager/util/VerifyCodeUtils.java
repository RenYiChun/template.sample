//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.sample.service.manager.util;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.OutputStream;
import java.security.SecureRandom;
import javax.imageio.ImageIO;
import org.springframework.util.StringUtils;

public class VerifyCodeUtils {
    public static final String VERIFY_CODES = "23456789ABCDEFGHJKLMNPQRSTUVWXYZ";
    private static final SecureRandom random = new SecureRandom();
    
    public VerifyCodeUtils() {
    }
    
    public static String generateVerifyCode(int verifySize, String sources) {
        if (!StringUtils.hasLength(sources)) {
            sources = VERIFY_CODES;
        }
        int codesLen = sources.length();
        StringBuilder verifyCode = new StringBuilder(verifySize);
        
        for (int i = 0; i < verifySize; ++i) {
            verifyCode.append(sources.charAt(random.nextInt(codesLen - 1)));
        }
        return verifyCode.toString();
    }
    
    public static void outputImage(int w, int h, OutputStream os, String code) throws IOException {
        int verifySize = code.length();
        BufferedImage image = new BufferedImage(w, h, 1);
        Graphics2D g2 = image.createGraphics();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        
        g2.setColor(Color.GRAY);
        g2.fillRect(0, 0, w, h);
        Color c = getRandColor(200, 250);
        g2.setColor(c);
        g2.fillRect(0, 2, w, h - 4);
        float yawpRate = 0.05F;
        int area = (int) (yawpRate * (float) w * (float) h);
        
        int fontSize;
        int i;
        for (fontSize = 0; fontSize < area; ++fontSize) {
            int x = random.nextInt(w);
            int y = random.nextInt(h);
            i = getRandomIntColor();
            image.setRGB(x, y, i);
        }
        
        shear(g2, w, h, c);
        g2.setColor(getRandColor(100, 160));
        fontSize = h - 4;
        String osName = System.getProperty("os.name");
        Font font;
        if (osName.contains("Windows")) {
            font = new Font("Algerian", Font.ITALIC, fontSize);
        } else {
            font = new Font("default", Font.ITALIC, fontSize);
        }
        g2.setFont(font);
        char[] chars = code.toCharArray();
        
        for (i = 0; i < verifySize; ++i) {
            AffineTransform affine = new AffineTransform();
            affine.setToRotation(0.7853981633974483D * random.nextDouble() * (double) (random.nextBoolean() ? 1 : -1),
                                 (double) w / verifySize * i + (double) fontSize / 2,
                                 (double) h / 2
            );
            g2.setTransform(affine);
            g2.drawChars(chars, i, 1, (w - 10) / verifySize * i + 5, h / 2 + fontSize / 2 - 5);
        }
        g2.dispose();
        ImageIO.write(image, "jpg", os);
    }
    
    private static Color getRandColor(int fc, int bc) {
        if (fc > 255) {
            fc = 255;
        }
        if (bc > 255) {
            bc = 255;
        }
        int r = fc + random.nextInt(bc - fc);
        int g = fc + random.nextInt(bc - fc);
        int b = fc + random.nextInt(bc - fc);
        return new Color(r, g, b);
    }
    
    private static int getRandomIntColor() {
        int[] rgb = getRandomRgb();
        int color = 0;
        for (int c : rgb) {
            color <<= 8;
            color |= c;
        }
        return color;
    }
    
    private static void shear(Graphics g, int w1, int h1, Color color) {
        shearX(g, w1, h1, color);
        shearY(g, w1, h1, color);
    }
    
    private static int[] getRandomRgb() {
        int[] rgb = new int[3];
        for (int i = 0; i < 3; ++i) {
            rgb[i] = random.nextInt(255);
        }
        return rgb;
    }
    
    private static void shearX(Graphics g, int w1, int h1, Color color) {
        int period = random.nextInt(2);
        int frames = 1;
        int phase = random.nextInt(2);
        
        for (int i = 0; i < h1; ++i) {
            double d =
                    (double) (0) * Math.sin((double) i / (double) period + 6.283185307179586D * (double) phase / (double) frames);
            g.copyArea(0, i, w1, 1, (int) d, 0);
            g.setColor(color);
            g.drawLine((int) d, i, 0, i);
            g.drawLine((int) d + w1, i, w1, i);
        }
        
    }
    
    private static void shearY(Graphics g, int w1, int h1, Color color) {
        int period = random.nextInt(40) + 10;
        int frames = 20;
        int phase = 7;
        
        for (int i = 0; i < w1; ++i) {
            double d =
                    (double) (period >> 1) * Math.sin((double) i / (double) period + 6.283185307179586D * (double) phase / (double) frames);
            g.copyArea(i, 0, 1, h1, 0, (int) d);
            g.setColor(color);
            g.drawLine(i, (int) d, i, 0);
            g.drawLine(i, (int) d + h1, i, h1);
        }
    }
}
