package nio;

import java.io.IOException;
import java.nio.channels.SelectionKey;

public interface Protocol {
	public void handleAccept(SelectionKey key) throws IOException;

	public void handleRead(SelectionKey key) throws IOException;

	public void handleWrite(SelectionKey key) throws IOException;
}
