import java.io.IOException;

/**
 * Mock appendable which always throws i/o exceptions in all of its methods.
 */
public class BadAppendable implements Appendable {

  @Override
  public Appendable append(CharSequence csq) throws IOException {
    throw new IOException("always throws i/o exceptions haha");
  }

  @Override
  public Appendable append(CharSequence csq, int start, int end) throws IOException {
    throw new IOException("always throws i/o exceptions haha");
  }

  @Override
  public Appendable append(char c) throws IOException {
    throw new IOException("always throws i/o exceptions haha");
  }
}
