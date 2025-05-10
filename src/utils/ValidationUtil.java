package utils;

public class ValidationUtil {

    public static boolean isValidName(String name) throws ValidationException {
        if (name == null || !name.matches("^[\\p{L} ]{2,}$")) {
            throw new ValidationException(name + " is not a valid name");
        }
        return true;
    }

    public static boolean isValidEmail(String email) throws ValidationException {
        if (email == null || !email.matches("^[\\w.-]+@[\\w.-]+\\.\\w{2,}$")) {
            throw new ValidationException(email + " is not a valid email");
        }
        return true;
    }

    public static boolean isValidEgyptianPhone(String phone) throws ValidationException {
        if (phone == null || !phone.matches("^01[0-2,5]{1}\\d{8}$")) {
            throw new ValidationException(phone + " is not a valid Egyptian phone number");
        }
        return true;
    }

    public static boolean isValidLicenseNumber(String licenseNumber) throws ValidationException {
        if (licenseNumber == null || !licenseNumber.matches("^\\d{6,15}$")) {
            throw new ValidationException(licenseNumber + " is not a valid license number");
        }
        return true;
    }

    public static boolean isNumeric(String input) throws ValidationException {
        if (input == null) {
            throw new ValidationException("Input is null and not numeric");
        }
        try {
            Integer.parseInt(input);
            return true;
        } catch (NumberFormatException e) {
            throw new ValidationException(input + " is not numeric");
        }
    }

        public static boolean isValidPassword(String password) throws ValidationException {
        if (password == null || !password.matches("^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)(?=.*[@#$%^&+=]).{8,}$")) {
            throw new ValidationException("Password must be at least 8 characters long, contain an uppercase letter, a lowercase letter, a digit, and a special character.");
        }
        return true;
    }
}