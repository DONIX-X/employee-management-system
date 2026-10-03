package interface_;

import java.util.List;

/**
 * ReportGenerator interface - Defines a contract for workforce reports.
 */
public interface ReportGenerator {
    String generateReport(List<?> data);
    String getReportTitle();
}