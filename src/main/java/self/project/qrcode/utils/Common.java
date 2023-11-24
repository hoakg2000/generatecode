package self.project.qrcode.utils;

public class Common {

    public static boolean isNumeric(String str) {
        // Using Java 8 streams to check if all characters are digits
        return str.chars().allMatch(Character::isDigit);
    }
}
