package org.example.pioneer.qr;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.common.BitMatrix;

import javax.imageio.ImageIO;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Base64;

public class QRCodeGenerator {

    public static String generateQRCode(String text) throws WriterException, IOException {
        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        BitMatrix bitMatrix = qrCodeWriter.encode(text, BarcodeFormat.QR_CODE, 200, 200);

        BufferedImage qrImage = toBufferedImage(bitMatrix);

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        ImageIO.write(qrImage, "png", outputStream);

        return Base64.getEncoder().encodeToString(outputStream.toByteArray());
    }

    private static BufferedImage toBufferedImage(BitMatrix matrix) {
        int width = matrix.getWidth();
        int height = matrix.getHeight();
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                image.setRGB(x, y, (matrix.get(x, y) ? Color.BLACK.getRGB() : Color.WHITE.getRGB()));
            }
        }

        return image;
    }
}
