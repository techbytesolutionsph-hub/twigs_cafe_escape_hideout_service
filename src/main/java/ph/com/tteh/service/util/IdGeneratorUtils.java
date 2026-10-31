package ph.com.tteh.service.util;

import org.springframework.stereotype.Component;

import java.time.Year;

@Component
public class IdGeneratorUtils {

    private static final String COMPANY_PREFIX = "3LC";
    private static final String CLIENT_ID_PREFIX = "CLI";
    private static final String ASSIGNMENT_ID_PREFIX = "CA";
    private static final String INITIAL_ASSESSMENT_ID_PREFIX = "IA";
    private static final String ASSESSMENT_ID_PREFIX = "ASCH";
    private static final String UPGRADING_PROGRAM_ID_PREFIX = "UPCH";
    private static final String PROGRESS_ID_PREFIX = "PR";
    private static final String JOB_ORDER_ID_PREFIX = "JO";


    private IdGeneratorUtils() {
    }

    /**
     * =========================================================
     * USER ID FORMAT
     * =========================================================
     * 3LC-0326-0001
     * 3LC-0326-0002
     * 3LC-0326-0003
     */
    public String generateEmployeeId(String dateHired, long runningCode) {

        return String.format(
                "%s-%s-%04d",
                COMPANY_PREFIX,
                dateHired,
                runningCode
        );
    }

    /**
     * =========================================================
     * CLIENT ID FORMAT
     * =========================================================
     * CLI-000001
     * CLI-000002
     * CLI-000003
     */
    public String generateClientId(long runningCode) {

        return String.format(
                "%s-%06d",
                CLIENT_ID_PREFIX,
                runningCode
        );
    }

    /**
     * =========================================================
     * CLIENT ID FORMAT
     * =========================================================
     * CA-2026-00001
     * CA-2026-00002
     * CA-2026-00003
     */
    public String generateAssignmentId(String assignYear, long runningCode) {

        return String.format(
                "%s-%s-%05d",
                ASSIGNMENT_ID_PREFIX,
                assignYear,
                runningCode
        );
    }

    /**
     * =========================================================
     * CLIENT ID FORMAT
     * =========================================================
     * IA-2026-00001
     * IA-2026-00002
     * IA-2026-00003
     */
    public String generateInitialAssessmentId(String assignYear, long runningCode) {

        return String.format(
                "%s-%s-%05d",
                INITIAL_ASSESSMENT_ID_PREFIX,
                assignYear,
                runningCode
        );
    }

    /**
     * =========================================================
     * CLIENT ID FORMAT
     * =========================================================
     * ASCH-2026-00001
     * ASCH-2026-00002
     * ASCH-2026-00003
     */
    public String generateAssessmentId(String assignYear, long runningCode) {

        return String.format(
                "%s-%s-%05d",
                ASSESSMENT_ID_PREFIX,
                assignYear,
                runningCode
        );
    }

    /**
     * =========================================================
     * CLIENT ID FORMAT
     * =========================================================
     * UPCH-2026-00001
     * UPCH-2026-00002
     * UPCH-2026-00003
     */
    public String generateUpgradingProgramId(String assignYear, long runningCode) {

        return String.format(
                "%s-%s-%05d",
                UPGRADING_PROGRAM_ID_PREFIX,
                assignYear,
                runningCode
        );
    }

    /**
     * =========================================================
     * CLIENT ID FORMAT
     * =========================================================
     * PR-00001
     * PR-00002
     * PR-00003
     */
    public String generateProgressReportId(long runningCode) {

        return String.format(
                "%s-%06d",
                PROGRESS_ID_PREFIX,
                runningCode
        );
    }

    /**
     * =========================================================
     * JOB ORDER ID FORMAT
     * =========================================================
     * JO-2026-0001
     * JO-2026-0002
     * JO-2026-0003
     */
    public String generateJobOrderId(String dateOffered, long runningCode) {

        return String.format(
                "%s-%s-%04d",
                COMPANY_PREFIX,
                dateOffered,
                runningCode
        );
    }

    /**
     * =========================================================
     * KID / CLIENT ID FORMAT
     * =========================================================
     * LLLC-2026-0001
     */
    public String generateKidId(long runningCode) {

        int year = Year.now().getValue();

        return String.format(
                "%s-%d-%06d",
                COMPANY_PREFIX,
                year,
                runningCode
        );
    }
}
