package self.project.qrcode.controller;

import com.google.zxing.WriterException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import self.project.qrcode.service.QRCodeGenerator;
import self.project.qrcode.utils.Common;

import java.io.IOException;
import java.util.logging.Logger;

@Controller
public class MainController {

    private static final Logger logger = Logger.getLogger(MainController.class.getName());


    private final QRCodeGenerator qrCodeGenerator;

    public MainController(){
        qrCodeGenerator = new QRCodeGenerator();
    }
    @GetMapping("/")
    public String home(Model model) {
        logger.info("Visit home page");
        model.addAttribute("pageTitle", "Home Page");
        return "home";
    }


    @PostMapping("/")
    public String generateCode(Model model, @RequestParam(value = "text",defaultValue = "") String text,
                            @RequestParam(value = "codeType",defaultValue = "QRCode") String codeType) {
        logger.info(String.format("Generate %s \"%s\"", codeType, text));
        model.addAttribute("text", text);
        model.addAttribute("codeType", codeType);

        String image = null;
        if (codeType.equals("barcode") && (!Common.isNumeric(text) || text.length() != 12)){
            model.addAttribute("errorMessage", "Text length must be 12 number");
            return "home";
        }

        try {
            // Generate and Return Qr Code in Byte Array
            image = qrCodeGenerator.generateCode(text, codeType);
        }catch (WriterException | IOException ex){
            logger.severe(ex.getCause().toString());
        }
        model.addAttribute("qrcode", image);

        return "home";
    }
}
