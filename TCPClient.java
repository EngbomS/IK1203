package tcpclient;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class TCPClient {

    public TCPClient() {
    }

    public byte[] askServer(String hostname, int port, byte[] toServerBytes) throws IOException {
        try (Socket socket = new Socket(hostname, port)) {
            socket.setSoTimeout(10000); 

            InputStream input = socket.getInputStream();
            OutputStream output = socket.getOutputStream();

            if (toServerBytes != null && toServerBytes.length > 0) {
                output.write(toServerBytes);
                output.flush();
            }

            ByteArrayOutputStream responseBuffer = new ByteArrayOutputStream();
            byte[] buffer = new byte[1024];
            int bytesRead;

            while ((bytesRead = input.read(buffer)) != -1) {
                responseBuffer.write(buffer, 0, bytesRead);
            }

            return responseBuffer.toByteArray();
        }
    }

    public byte[] askServer(String hostname, int port) throws IOException {
        return askServer(hostname, port, null);
    }
}
