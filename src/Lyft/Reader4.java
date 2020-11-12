package Lyft;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

public class Reader4 {
    Reader r;
    int offset;
    public Reader4(String s) {
        this.r = new StringReader(s);
        this.offset = 0;
    }
    public int read4(char[] buf) {
        int read = 0;
        try {
            read = r.read(buf, offset, 4);
        } catch (IOException e) {
            e.printStackTrace();
        }
        offset += read;
        return read;
    }

    LinkedList<Character> prevRead = new LinkedList<>();
    public int read(char[] buf, int n) {
        int totalRead = 0;
        int curReadIndex = 0;
        // first read from previous remains, maybe more or less than needed
        while (!prevRead.isEmpty()) {
            if (curReadIndex < n) {
                buf[curReadIndex++] = prevRead.pollFirst();
                totalRead++;
            } else {
                break;
            }
        }

        // if still not reaches current need, read more
        while (curReadIndex < n) {
            char[] temp = new char[4];
            int curRead = read4(temp);
            if (curRead == 0) {
                break;
            }
            for (int i = 0; i < curRead; i++) {
                if (curReadIndex < n) {
                    buf[curReadIndex++] = temp[i];
                    totalRead++;
                } else {
                    prevRead.offerLast(temp[i]);
                }
            }
        }
        return totalRead;
    }
    public static void main(String[] args) {
        String s = "abc";
        Reader4 ts = new Reader4(s);
        char[] buf = new char[s.length()];
        ts.read(buf, 1);
        ts.read(buf, 2);
        ts.read(buf, 1);
    }
}
