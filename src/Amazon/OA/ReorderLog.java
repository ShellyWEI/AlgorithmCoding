package Amazon.OA;

import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.stream.Collectors;

public class ReorderLog {
    private static class LogNode implements Comparable<LogNode> {
        String logId;
        String value;
        int index;
        boolean isNumberLog;

        @Override
        public int compareTo(LogNode other) {
            if (this.isNumberLog && other.isNumberLog) {
                // if both are number log, log with lower index should be ahead
                return this.index < other.index ? -1 : 1;
            } else if (this.isNumberLog) {
                // this log should be behind other log
                return 1;
            } else if (other.isNumberLog) {
                // this log should be ahead of other log
                return -1;
            } else {
                // if both are text log, compare actual log value first; if tied, compare logId
                if (this.value.equals(other.value)) {
                    return this.logId.compareTo(other.logId);
                } else {
                    return this.value.compareTo(other.value);
                }
            }
        }

        @Override
        public String toString() {
            return this.logId + " " + this.value;
        }
    }
    public String[] reorderLogFiles(String[] logs) {
        if (null == logs || logs.length == 0) {
            return new String[0];
        }
        LogNode[] logNodeArray = new LogNode[logs.length];

        for (int i = 0; i < logs.length; i++) {
            String log = logs[i];
            logNodeArray[i] = new LogNode();
            logNodeArray[i].index = i;
            int splitIndex = log.indexOf(" ");
            logNodeArray[i].logId = logs[i].substring(0, splitIndex);
            logNodeArray[i].value = logs[i].substring(splitIndex + 1);

            // decide whether it's a numeric log
            String[] valueTokens = logNodeArray[i].value.split(" ");
            try {
                double d = Double.parseDouble(valueTokens[0]);
                logNodeArray[i].isNumberLog = true;
            } catch (NumberFormatException | NullPointerException nfe) {
                logNodeArray[i].isNumberLog = false;
            }
        }
        Arrays.sort(logNodeArray);
        String[] result = new String[logs.length];
        for (int i = 0; i < logs.length; i++) {
            result[i] = logNodeArray[i].toString();
        }
        return result;
    }
}
