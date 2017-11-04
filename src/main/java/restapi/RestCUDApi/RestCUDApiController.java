package restapi.RestCUDApi;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("/api")
public class RestCUDApiController {

	public static final Logger logger = LoggerFactory.getLogger(RestCUDApiController.class);

	@Autowired
	UserInfoService userInfoService;

	@RequestMapping(value = "/userInfo/", method = RequestMethod.POST)
	public ResponseEntity<?> createUser(@RequestBody UserInfo user, UriComponentsBuilder ucBuilder) {
		logger.info("Creating User : {}", user);

		userInfoService.createUser(user);

		HttpHeaders headers = new HttpHeaders();
		headers.setLocation(ucBuilder.path("/api/userInfo/{userId}").buildAndExpand(user.getUserId()).toUri());
		return new ResponseEntity<String>(headers, HttpStatus.CREATED);
	}

	@RequestMapping(value = "/userInfo/{userId}", method = RequestMethod.PUT)
	public ResponseEntity<?> updateUser(@PathVariable("userId") String userId, @RequestBody UserInfo user) {
		logger.info("Updating User with userId {}", userId);

		UserInfo currentUser = userInfoService.findById(userId);

		if (currentUser == null) {
			logger.error("Unable to update. User with id {} not found.", userId);
			return new ResponseEntity(HttpStatus.NOT_FOUND);
		}

		currentUser.setfName(user.getfName());
		currentUser.setlName(user.getlName());
		currentUser.setEmail(user.getEmail());
		currentUser.setPinCode(user.getPinCode());
		currentUser.setBirthDate(user.getBirthDate());
		currentUser.setActive(user.isActive());

		userInfoService.updateUser(currentUser);
		return new ResponseEntity<UserInfo>(currentUser, HttpStatus.OK);
	}

	@RequestMapping(value = "/user/{userId}", method = RequestMethod.DELETE)
	public ResponseEntity<?> deleteUser(@PathVariable("userId") String userId) {
		logger.info("Fetching & Deleting User with id {}", userId);

		UserInfo user = userInfoService.findById(userId);
		if (user == null) {
			logger.error("Unable to delete. User with id {} not found.", userId);
			return new ResponseEntity(HttpStatus.NOT_FOUND);
		}
		userInfoService.deleteUserById(userId);
		return new ResponseEntity<UserInfo>(HttpStatus.NO_CONTENT);
	}
}