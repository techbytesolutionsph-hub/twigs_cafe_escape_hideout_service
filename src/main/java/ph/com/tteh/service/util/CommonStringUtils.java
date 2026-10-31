package ph.com.tteh.service.util;

/**
 * This is a CommonStringUtility Class. It contains all string messages used in the project.
 */

public class CommonStringUtils {

    private CommonStringUtils() {}

    /*==========================CUSTOM SUCCESS MESSAGES FOR LOGIN ========================*/
    public static final String  SUCCESS_MSG_LOGGED_IN = "Logged in successfully";

    /*==========================CUSTOM ERROR MESSAGES FOR LOGIN ========================*/
    public static final String  ERR_CODE_USER_NOT_FOUND_FOR_EMAIL = "User not found for email address %s!";
    public static final String  ERR_CODE_INACTIVE_SUSPENDED = "User account is inactive or suspended. Please contact the administrator to resolve this issue.";
    public static final String  ERR_CODE_LOGIN_INCORRECT_PASSWORD = "The password you have entered is incorrect.";
    public static final String  ERR_CODE_LOGIN_USERNAME_NOT_CONNECTED = "The username you entered is not connected to an account.";
    public static final String  ERR_CODE_LOGIN_TEMPORARY_LOCKED = "You have entered an incorrect password too many times and your account is temporarily locked. Please contact the administrator to resolve this issue.";

    /*==========================CUSTOM ERROR MESSAGES FOR PRODUCT ========================*/
    public static final String  ERR_CODE_PRODUCT_NOT_FOUND = "Product not found";
    public static final String  ERR_CODE_CATEGORY_NOT_FOUND = "Category not found";

    /*==========================CUSTOM SUCCESS MESSAGES FOR PRODUCT ========================*/
    public static final String  SUCCESS_MSG_RETRIEVED_PRODUCT = "Product retrieved successfully";
    public static final String  SUCCESS_MSG_RETRIEVED_PRODUCTS = "Products retrieved successfully";
    public static final String  SUCCESS_MSG_CREATED_PRODUCT = "Your product has been created successfully";
    public static final String  SUCCESS_MSG_UPDATED_PRODUCT = "Your product has been updated successfully";
    public static final String  SUCCESS_MSG_DELETED_PRODUCT = "Your product has been deleted successfully";
    public static final String  SUCCESS_MSG_CHECK_SLUG = "Slug checked successfully";

    /*==========================CUSTOM SUCCESS MESSAGES FOR CATEGORY ========================*/
    public static final String  SUCCESS_MSG_RETRIEVED_CATEGORY = "Category retrieved successfully";
    public static final String  SUCCESS_MSG_RETRIEVED_CATEGORIES = "Categories retrieved successfully";
    public static final String  SUCCESS_MSG_CREATED_CATEGORY = "Category created successfully";
    public static final String  SUCCESS_MSG_UPDATED_CATEGORY = "Category updated successfully";
    public static final String  SUCCESS_MSG_DELETED_CATEGORY = "Category deleted successfully";


    /*========================CUSTOMIZED ERROR MESSAGE FOR ADD REGISTRATION====================*/
    public static final String ERR_CODE_001_USERNAME_TAKEN = "Username %s has already been taken.";

    /*=================================CUSTOM REGEX PATTERNS==================================*/
    public static final String REGEX_PATTERN_SPECIAL_CHAR_NUM = "[^a-zA-Z\\s+]";
    public static final String REGEX_PATTERN_EMAIL = ".+@.+\\..+";
    public static final String REGEX_PATTERN_PWD = "^.*(?=.{8,})(?=.*\\d.*\\d)(?=.*[a-z].*[a-z])(?=.*[A-Z].*[A-Z])(?=.*[!@#$%^&*+=].*[!@#$%^&*+=]).*$";

    /*========================CUSTOMIZED ERROR MESSAGE FOR ADD REGISTRATION====================*/
    public static final String  ERR_CODE_001_REQ_FIRSTNAME = "First name is mandatory";
    public static final String  ERR_CODE_001_REQ_LASTNAME = "Last name is mandatory";
    public static final String  ERR_CODE_001_REQ_PASSWORD = "Password is mandatory";
    public static final String  ERR_CODE_001_REQ_CELLPHONE_NO = "Cellphone number is mandatory";
    public static final String ERR_CODE_001_CELL_NO_TAKEN = "Cellphone number has already been taken.";
    public static final String ERR_CODE_001_EMAIL_TAKEN = "Email address has already been taken.";
    public static final String ERR_CODE_001_ALPHABET_ALLOWED = "Only alphabetical characters allowed.";
    public static final String ERR_CODE_001_INVALID_EMAIL = "Please enter a valid email.";
    public static final String ERR_CODE_001_PASSWORD_ALLOWED = "Password must have at least an uppercase and lowercase alphabet, a digit and a special character";

    /*========================CUSTOMIZED ERROR MESSAGE FOR DISPLAY RECORDS====================*/
    public static final String ERR_MSG_NO_DISPLAY_RECORDS = "There are no records to display.";

    /*========================CUSTOMIZED SUCCESS MESSAGE FOR DEPARTMENT====================*/
    public static final String SUCCESS_MSG_CREATE_DEPT = "Department has been created successfully.";

    /*========================CUSTOMIZED SUCCESS MESSAGE FOR DEPARTMENT====================*/
    public static final String SUCCESS_MSG_CREATE_NOTIF_JOB = "%s name %s has been scheduled successfully.";

    /*========================CUSTOMIZED SUCCESS MESSAGE FOR ACCOUNT====================*/
    public static final String SUCCESS_MSG_CREATE_ACCT = "Congratulations! Account has been created successfully.";
    public static final String SUCCESS_MSG_CHANGE_ACCT_PW = "Hello %s,\nCongratulations! Your account is ready. Next step is to change your default password in order to secure your account.";
    public static final String SUCCESS_MSG_UPDATE_ACCT = "Account with username %s has been updated successfully.";
    public static final String SUCCESS_MSG_DELETE_ACCT = "Account with username %s has been deleted successfully.";

    /*========================CUSTOMIZED ERROR MESSAGE FOR ACCOUNT====================*/
    public static final String ERR_MSG_ACCOUNT_NOT_EXIST = "User not found in the database.";

    /*========================CUSTOMIZED SUCCESS MESSAGE FOR DEPARTMENT====================*/
    public static final String SUCCESS_MSG_DELETE_DEPT = "Department name %s has been deleted successfully.";

    /*========================CUSTOMIZED EXCEPTION MESSAGE FOR ACCOUNT====================*/
    public static final String ERR_MSG_001_STORE_FILE = "Could not store file %s. Please try again!";
    public static final String ERR_MSG_001_UPDATE_STATUS = "Could not update document status. Please try again!";
    public static final String ERR_MSG_001_CREATE_DIR = "Could not create the directory where the uploaded files will be stored.";
    public static final String ERR_MSG_001_INVALID_PATHFILE = "Sorry! Filename contains invalid path sequence";

    /*========================CUSTOMIZED SUCCESS MESSAGE FOR QR CODE====================*/
    public static final String SUCCESS_MSG_GEN_QR_CODE = "QR code has been generated successfully.";

    /*========================DESCRIPTION FOR ACTIVITY LOGS====================*/
    public static final String SUCCESS_DESC_QR_DOC_GEN = "Document name %s has been created successfully.";
    public static final String SUCCESS_DESC_QR_CODE_GEN = "QR code for payroll has been generated successfully.";
    public static final String SUCCESS_DESC_UPDATE_DOC_STATUS = "Document status has been updated successfully.";
    public static final String SUCCESS_DESC_UPDATE_QR_CODE_GEN_STATUS = "Document status has been updated successfully.";
    public static final String SUCCESS_DESC_SIMPLE_BATCH_JOB = "Simple job %s has been scheduled successfully.";
    public static final String SUCCESS_DESC_CRON_BATCH_JOB = "Cron job %s has been scheduled successfully.";


    public static final String STAT_MSG_PROCESSED = "Document (%s) has been processed successfully.";
    public static final String STAT_MSG_RELEASED = "Document has been released and ready to deliver.";
    public static final String STAT_MSG_RECEIVED = "Document has been received.</br>Recipient: %s";
    public static final String STAT_MSG_APPROVED = "Document (%s) has been approved.";
    public static final String STAT_MSG_RETURNED = "Document has been returned to the sender.</br>Returned by: %s</br>Reason: %s";

    /*======================== STR ID'S FOR MOVIE, ACTOR AND REVIEW ====================*/
    public static final String DTS_USER_ID = "DTS%s";
    public static final String DTS_DEPT_ID = "DEP%s";
    public static final String DTS_DOC_ID = "TUP%s";
    public static final String DTS_NOTIF_ID = "NTF%s";
    public static final String DTS_BATCH_ID = "CRN%s";

    /*======================== STR ACTION CODE FOR FOR DOCUMENT TRACKING SYSTEM ====================*/
    public static final String DTS_CREATE_USER_ACCT = "Create User Account";
    public static final String DTS_UPDATE_USER_ACCT = "Update User Account";
    public static final String DTS_DELETE_USER_ACCT = "Delete User Account";
    public static final String DTS_CREATE_DEPT = "Create Department";
    public static final String DTS_DELETE_DEPT = "Delete Department";
    public static final String DTS_SAVE_DOC = "Save Document";
    public static final String DTS_SAVE_QR = "Save QR Code";
    public static final String DTS_UPDATE_DOC = "Update Document";
    public static final String DTS_UPDATE_QR = "Update QR Code Status";
    public static final String DTS_SIMPLE_JOB = "Simple Job";

    /*======================== STR MODULE CODE FOR FOR DOCUMENT TRACKING SYSTEM ====================*/
    public static final String DTS_SUBJ_CHANGE_PW = "CHANGE PASSWORD";
    public static final String DTS_SUBJ_ACCT_CREATION = "TUP-DTS ACCOUNT";
    public static final String DTS_SUBJ_PROCESSED_DOCS = "PROCESSED DOCUMENT";
    public static final String DTS_SUBJ_PROCESSED_PAYSLIP = "PROCESSED PAYSLIP";
    public static final String DTS_SUBJ_RELEASED_DOCS = "RELEASED DOCUMENT";
    public static final String DTS_SUBJ_RELEASED_PAYSLIP = "RELEASED PAYSLIP";
    public static final String DTS_SUBJ_RECEIVED_DOCS = "RECEIVED DOCUMENT";
    public static final String DTS_SUBJ_RECEIVED_PAYSLIP = "RECEIVED PAYSLIP";
    public static final String DTS_SUBJ_APPROVED_DOCS = "APPROVED DOCUMENT";
    public static final String DTS_SUBJ_APPROVED_PAYSLIP = "APPROVED PAYSLIP";
    public static final String DTS_SUBJ_RETURNED_DOCS = "RETURNED DOCUMENT";
    public static final String DTS_SUBJ_RETURNED_PAYSLIP = "RETURNED PAYSLIP";
    public static final String DTS_SUBJ_DOCUMENT_NOT_RECEIVED = "DOCUMENT NOT FORWARDED";

    /*======================== STR MODULE CODE FOR FOR DOCUMENT TRACKING SYSTEM ====================*/
    public static final String DTS_DEPARTMENT = "Department";
    public static final String DTS_USER_ACCT = "User Account";
    public static final String DTS_GEN_QR_CODE = "Payroll";
    public static final String DTS_QR_DOC_GEN = "Document w/ QR Code";

    /*========================NOTIFICATION MESSAGES FOR DOCUMENT CREATION====================*/
    public static final String DTS_PROCESSED_DOCS = "Document with tracking number %s has been processed.";
    public static final String DTS_PROCESSED_PAYSLIP = "Payslip with tracking number %s has been processed.";
    public static final String DTS_RELEASED_DOCS = "%s sent you a %s. Please confirm and accept the document in DTS App within 3 days.";
    public static final String DTS_RELEASED_PAYSLIP = "%s sent you a %s. Please confirm and accept the payslip in DTS App within 3 days.";
    public static final String DTS_RECEIVED_DOCS = "Document with tracking number %s has been received.</br>Recipient: %s";
    public static final String DTS_RECEIVED_PAYSLIP = "Payslip with tracking number %s has been received.</br>Recipient: %s";
    public static final String DTS_APPROVED_DOCS = "Document (%s) has been approved.";
    public static final String DTS_APPROVED_PAYSLIP = "Document (%s) has been approved.";
    public static final String DTS_RETURNED_DOCS = "Document with tracking number %s has been returned to the sender.</br>Returned by: %s</br>Reason: %s";
    public static final String DTS_RETURNED_PAYSLIP = "Payslip with tracking number %s has been returned to the sender.</br>Returned by: %s</br>Reason: %s";
    public static final String DTS_DOCUMENT_NOT_RECEIVED = "Reminder/s from %s: Have received the %s document? If so, please confirm in TUP-DTS QR Code Scanner App immediately. Thank you.";

    public static final String TRACKING_NO = "TRACKING NO: %s";
    public static final String ORIGINAL_FILENAME = "QRCodeSizePDF.pdf";
    public static final String CONTENT_TYPE = "text/plain";
    public static final String FILENAME = "QRCodeSizePDF.pdf";
}

