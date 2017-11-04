package restapi.RestCUDApi;

import java.util.List;

public interface UserInfoService {
	
	UserInfo findById(String id);
	void createUser(UserInfo user);
	
	void updateUser(UserInfo user);
	
	void deleteUserById(String id);
}
