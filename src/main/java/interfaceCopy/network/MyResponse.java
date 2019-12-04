package interfaceCopy.network;

import okhttp3.Response;

import java.io.IOException;

public class MyResponse {
    private Response response;
    private byte[] bytes;

    MyResponse(Response response) {
        this.response = response;
    }

    private byte[] bytes() throws IOException {
        if (response == null) {
            return "".getBytes();
        }
        return bytes = response.body().bytes();
    }

    public String string(String resultCharset) throws IOException {
        return new String(bytes(), resultCharset);
    }
}
