import java.io.IOException;
import java.nio.CharBuffer;

/**
 * Mock readable class that always throws exceptions.
 */
public class BadReadable implements Readable {

  @Override
  public int read(CharBuffer cb) throws IOException {
    throw new IOException("always throws i/o exceptions haha");
  }
}
