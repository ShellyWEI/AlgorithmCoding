package LinkedIn.PhoneScreen;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class TextFile implements Iterable<String> {
    BufferedReader buffer;

    public TextFile(String filePath)  {
        try {
            buffer = new BufferedReader(new FileReader(filePath));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
    /*begin reading the file, line by line,
    * The returned iterator.next() will return a line.*/
    @Override
    public Iterator<String> iterator() {
        return new MyIterator(buffer);
    }

    private class MyIterator implements Iterator<String> {
        BufferedReader buffer;
        String next;

        MyIterator (BufferedReader buffer) {
            this.buffer = buffer;
        }
        // hasNext 不会移动位置
        @Override
        public boolean hasNext () {
            if (buffer != null && next == null) {
                try {
                    next = buffer.readLine();
                    if (next == null) {
                        buffer.close();
                        buffer = null;
                    }
                } catch (IOException e) {
                    throw new RuntimeException();
                }
            }

            return next != null;
        }
        // next 指针才移动
        @Override
        public String next () {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            String nextLine = next;
            // for next calling, will do buffer.readline() again.
            next = null;
            return nextLine;
        }
    }
}

