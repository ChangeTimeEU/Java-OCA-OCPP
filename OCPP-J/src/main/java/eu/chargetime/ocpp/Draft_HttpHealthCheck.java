package eu.chargetime.ocpp;
/*
 * Based ON https://github.com/TooTallNate/Java-WebSocket/issues/1077
 */
import java.nio.ByteBuffer;
import java.util.Collections;
import java.util.List;
import org.java_websocket.WebSocketImpl;
import org.java_websocket.drafts.Draft;
import org.java_websocket.enums.CloseHandshakeType;
import org.java_websocket.enums.HandshakeState;
import org.java_websocket.exceptions.InvalidDataException;
import org.java_websocket.exceptions.InvalidHandshakeException;
import org.java_websocket.framing.Framedata;
import org.java_websocket.handshake.*;
import org.java_websocket.util.Charsetfunctions;

class Draft_HttpHealthCheck extends Draft {

  static final int HTTP_HEALTH_CHECK_CLOSE_CODE = 10200;

  static Boolean isHttp(ClientHandshake handshakedata) {
    String upgradeField = handshakedata.getFieldValue("Upgrade");
    return upgradeField == null || upgradeField == "";
  }

  @Override
  public List<ByteBuffer> createHandshake(Handshakedata handshakedata, boolean withcontent) {
    byte[] content = Charsetfunctions.asciiBytes("<h1>OCPP-J Websocket OK</h1>");
    byte[] header =
        Charsetfunctions.asciiBytes(
            "HTTP/1.0 200 OK\r\n"
                + "Mime-Version: 1.0\r\n"
                + "Content-Type: text/html\r\n"
                + "Content-Length: "
                + content.length
                + " \r\n"
                + "Connection: close\r\n"
                + "\r\n");

    ByteBuffer bytebuffer = ByteBuffer.allocate(content.length + header.length);
    bytebuffer.put(header);
    bytebuffer.put(content);
    bytebuffer.flip();
    return Collections.singletonList(bytebuffer);
  }

  @Override
  public HandshakeState acceptHandshakeAsClient(ClientHandshake request, ServerHandshake response)
      throws InvalidHandshakeException {
    throw new InvalidHandshakeException("This draft can't be used on a client");
  }

  @Override
  public HandshakeState acceptHandshakeAsServer(ClientHandshake handshakedata)
      throws InvalidHandshakeException {
    return (isHttp(handshakedata)) ? HandshakeState.MATCHED : HandshakeState.NOT_MATCHED;
  }

  @Override
  public ByteBuffer createBinaryFrame(Framedata framedata) {
    return null;
  }

  @Override
  public List<Framedata> createFrames(ByteBuffer binary, boolean mask) {
    return null;
  }

  @Override
  public List<Framedata> createFrames(String text, boolean mask) {
    return null;
  }

  @Override
  public void processFrame(WebSocketImpl webSocketImpl, Framedata frame)
      throws InvalidDataException {
    throw new InvalidDataException(0, "This draft can't be used on a client");
  }

  @Override
  public void reset() {
    // Nothing to Do
  }

  @Override
  public ClientHandshakeBuilder postProcessHandshakeRequestAsClient(ClientHandshakeBuilder request)
      throws InvalidHandshakeException {
    throw new InvalidHandshakeException("This draft can't be used on a client");
  }

  @Override
  public HandshakeBuilder postProcessHandshakeResponseAsServer(
      ClientHandshake request, ServerHandshakeBuilder response) throws InvalidHandshakeException {
    return response;
  }

  @Override
  public List<Framedata> translateFrame(ByteBuffer buffer) throws InvalidDataException {
    throw new InvalidHandshakeException("This draft doesn't work with frames");
  }

  @Override
  public CloseHandshakeType getCloseHandshakeType() {
    return CloseHandshakeType.NONE;
  }

  @Override
  public Draft copyInstance() {
    return this;
  }
}
