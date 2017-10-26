package protocol;

import comm.CommDataBase;
import comm.CommResponse;

public interface IProtocolCodec {

	void ClientEncode(CommDataBase data) throws Exception;

    CommResponse ClientDecode(CommDataBase data);

    //void ServerEncode(CommDataBase data) throws Exception;

    CommResponse ServerDecode(CommDataBase data);

	void ServerEncode(CommDataBase data) throws Exception;


}
