package comm;

import java.io.IOException;

public interface ICommClient {

    CommResponse Query(ClientCommData data) throws InterruptedException, IOException, Exception;

}
