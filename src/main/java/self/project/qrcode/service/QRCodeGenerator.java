package self.project.qrcode.service;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageConfig;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.oned.EAN13Writer;
import com.google.zxing.qrcode.QRCodeWriter;
import self.project.qrcode.utils.Common;
import self.project.qrcode.utils.Converter;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

public class QRCodeGenerator {

    private final int defaultQRCodeSize = 512;
    private final int defaultBarCodeSize = 512;
    public String generateCode(String data, String code) throws WriterException, IOException {
        BufferedImage generatedImg = null;
        switch (code.toLowerCase()){
            case "qrcode":
                generatedImg = this.generateQRCode(data);
                break;
            case "barcode":
                generatedImg = this.generateEAN13BarcodeImage(data);
                break;
            default:
                generatedImg = this.generateQRCode(data);
        }
        return Converter.bufferedImageToBase64(generatedImg, "png");
    }
    private BufferedImage generateQRCode(String data) throws WriterException {
        QRCodeWriter barcodeWriter = new QRCodeWriter();
        BitMatrix bitMatrix = barcodeWriter.encode(data, BarcodeFormat.QR_CODE, this.defaultQRCodeSize, this.defaultQRCodeSize);
        return MatrixToImageWriter.toBufferedImage(bitMatrix);
    }

    public BufferedImage generateEAN13BarcodeImage(String data) throws WriterException {
        EAN13Writer barcodeWriter = new EAN13Writer();
        BitMatrix bitMatrix = barcodeWriter.encode(data, BarcodeFormat.EAN_13, this.defaultBarCodeSize, this.defaultQRCodeSize/3);
        return MatrixToImageWriter.toBufferedImage(bitMatrix);
    }


}