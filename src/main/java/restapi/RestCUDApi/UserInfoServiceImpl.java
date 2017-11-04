package restapi.RestCUDApi;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

import org.springframework.stereotype.Service;

	@Service("userInfoService")
	public class UserInfoServiceImpl implements UserInfoService{
		
		private static final AtomicLong counter = new AtomicLong();
		
		private static List<UserInfo> users;
		
		public void createUser(UserInfo user) {
			user.setUserId(counter.incrementAndGet()+"");
			users.add(user);
		}

		public void updateUser(UserInfo user) {
			int index = users.indexOf(user);
			users.set(index, user);
		}

		public void deleteUserById(String id) {
			
			for (Iterator<UserInfo> iterator = users.iterator(); iterator.hasNext(); ) {
				UserInfo user = iterator.next();
			    if (user.getUserId() == id) {
			        iterator.remove();
			    }
			}
		}
		
		public UserInfo findById(String userId) {
			for(UserInfo user : users){
				if(user.getUserId() == userId){
					return user;
				}
			}
			return null;
		}
	}

