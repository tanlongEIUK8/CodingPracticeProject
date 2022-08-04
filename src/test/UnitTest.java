package test;

//import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.*;

import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import database.DataStorage;
import models.File;
import models.Group;
import models.GroupType;
import models.Message;
import models.User;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import services.GroupService;
import services.MessageService;
import services.UserService;

public class UnitTest {
	DataStorage dataStorage;
	UserService userService = new UserService();
	GroupService groupService = new GroupService();
	MessageService messageService = new MessageService();
	String contentMessage1 = "Welcome";
	String contentMessage2 = "Hi";
	String fileName = "Anime";
	String filePath = "anime.jpg";

	@BeforeEach
	void setUp() throws Exception {
		dataStorage = DataStorage.getInstance();
		userService.createNewUser("Son", "Goku", "songoku", "123456789", "Male", "11031999");
		userService.createNewUser("Uzimaki", "Naruto", "naruto", "999999999", "Male", "13031999");
		userService.createNewUser("Dragon.D", "Luffy", "luffy", "000000000", "Female", "09081999");
		groupService.createGroup(GroupType.Private, "user0");
		groupService.createGroup(GroupType.Public, "user0");
		groupService.createGroup(GroupType.Private, "user1");
	}

	@Test
	public void addUserTest() throws NoSuchAlgorithmException, NoSuchProviderException {
		assertTrue(userService.createNewUser("Kevin", "Max", "KevinMax99", "123456789", "Male", "01011999"));
	}

	@Test
	public void addExistUserTest() throws NoSuchAlgorithmException, NoSuchProviderException {
		assertFalse(userService.createNewUser("Son", "Goku", "songoku", "123456789", "Male", "11031999"));
	}

	@Test
	public void addPrivateGroupTest() {
		assertTrue(groupService.createGroup(GroupType.Private, "1"));
	}

	@Test
	public void addPublicGroupTest() {
		assertTrue(groupService.createGroup(GroupType.Public, "1"));
	}

	@Test
	public void loginWithTruePasswordTest() {
		assertTrue(userService.login("songoku", "123456789"));
	}

	@Test
	public void loginWithWrongPasswordTest() {
		assertFalse(userService.login("songoku", "123456780"));
	}

	@Test
	public void loginNoExistTest() {
		assertFalse(userService.login("kakalot", "123456789"));
	}

	@Test
	public void checkHashPasswordtest() {
		assertTrue(userService.checkHashPassword("songoku", "123456789"));
	}

	@Test
	public void getUserByUsernameTest() {
		User user = userService.getUserByUsername("naruto");
		assertEquals("user1", user.getId());
	}

	@Test
	public void getUserByIDTest() {
		User user = userService.getUserByUserId("user0");
		assertEquals("songoku", user.getUsername());
	}

	@Test
	public void setAliastest() {
		User user1 = userService.getUserByUsername("songoku");
		User user2 = userService.getUserByUsername("naruto");
		assertTrue(userService.setAlias(user1, user2, "Shonen Jump"));
	}

	@Test
	public void addPrivateMemberTest() {
		User user1 = userService.getUserByUsername("songoku");
		User user2 = userService.getUserByUsername("naruto");
		assertTrue(groupService.addMember(user1.getId(), user2.getId(), "group0"));
	}

	@Test
	public void addPublicMemberTest1() {
		User user1 = userService.getUserByUsername("songoku");
		User user2 = userService.getUserByUsername("naruto");
		assertTrue(groupService.addMember(user1.getId(), user2.getId(), "group1"));
	}

	@Test
	public void addPublicMemberTest2() {
		User user1 = userService.getUserByUsername("songoku");
		User user2 = userService.getUserByUsername("naruto");
		assertTrue(groupService.addMember(user1.getId(), user2.getId(), "group0"));
	}

	@Test
	public void addMemberByInviteCodeTest1() {
		User user1 = userService.getUserByUsername("songoku");
		assertTrue(
				groupService.addMemberByInviteCode(dataStorage.getGroupList().get(1).getInviteCode(), user1.getId()));
	}



	@ParameterizedTest(name = "groupId = {0}, userName = {1}, expected = {2}")
	@CsvSource({"group0, songoku, true", "group0, luffy, false " , "group09090, songoku, false"})
	public void checkAdminPrivateGroupTest(String groupId, String userName, boolean expected) {
		User user1 = userService.getUserByUsername(userName);
		assertEquals(expected, groupService.checkMemberGroup(user1.getId(), groupId));
	}

	@Test
	public void leaveGroupTest() {
		User user1 = userService.getUserByUsername("songoku");
		Group group = groupService.getGroupByGroupId("group0");
		assertTrue(groupService.leaveGroup(user1.getId(), group.getId()));
	}

	@Test
	public void sendMessageToGroupTest() {
		messageService.sendMessageToGroup(contentMessage1, "user1", "group0");
		List<Message> messageList = dataStorage.getMessageList();
		assertEquals("Welcome", messageList.get(messageList.size() - 1).getContent());
	}

	@Test
	public void sendMessageToUserTest() {
		messageService.sendMessageToUser(contentMessage1, "user1", "user0");
		List<Message> messageList = dataStorage.getMessageList();
		assertEquals("Welcome", messageList.get(messageList.size() - 1).getContent());
	}

	@Test
	public void sendFileToUserTest() {
		messageService.sendFileToUser(fileName, filePath, "user0", "user0");
		List<File> fileList = dataStorage.getFileList();
		assertEquals("Anime", fileList.get(fileList.size() - 1).getFileName());
	}

	@Test
	public void sendFileToGroupTest() {
		messageService.sendFileToGroup(fileName, filePath, "user1", "group0");
		List<File> fileList = dataStorage.getFileList();
		assertEquals("Anime", fileList.get(fileList.size() - 1).getFileName());
	}

	@Test
	public void showAllMessageGroupTest() {
		for (int i = 0; i < 4; i++) {
			messageService.sendMessageToGroup(contentMessage1, "user0", "group0");
		}
		List<Message> messageList = messageService.showAllMessageGroup("group0","user0");
		assertEquals(4, messageList.size());

	}

	@Test
	public void showAllMessageUserTest() {
		for (int i = 0; i < 12; i++) {
			messageService.sendMessageToUser(contentMessage1, "user1", "user0");

		}
		List<Message> messageList = messageService.showAllMessageUser("user0", "user1");
		assertEquals(12, messageList.size());
	}

	@ParameterizedTest(name = "k = {0}, expected = {1}")
	@CsvSource({"3, 3", "100, 24"})
	public void showKLatestMessageGroupTest(int k, int expected) {

		for (int i = 0; i < 10; i++) {
			messageService.sendMessageToGroup(contentMessage1, "user0", "group0");
		}
		List<Message> messageList = messageService.showKLatestMessageGroup("group0", k);
		assertEquals(expected, messageList.size());

	}

	@Test
	public void showLatestMessageGroupExceptMTest() {

		for (int i = 0; i < 10; i++) {
			messageService.sendMessageToGroup(contentMessage1, "user0", "group0");
		}
		List<Message> messageList = messageService.showLatestMessageGroupExceptM("group0", 3, 1);
		assertEquals(2, messageList.size());

	}

	@Test
	public void showAllFileInGroupTest() {
		Group group = groupService.getGroupByGroupId("group0");
		for (int i = 0; i < 2; i++) {
			messageService.sendFileToGroup(fileName, filePath, "user0", "group0");
		}
		List<File> fileList = messageService.showAllFileInGroup(group.getId());
		assertEquals(2, fileList.size());

	}

	@Test
	public void deleteMessageTest() {
		for (int i = 0; i < 2; i++) {
			messageService.sendMessageToGroup(contentMessage1, "user1", "group2");
		}
		int size = dataStorage.getMessageList().size();
		messageService.deleteMessage("msg0");
		assertEquals(size - 1, dataStorage.getMessageList().size());
	}

	@Test
	public void deleteFileTest() {
		for (int i = 0; i < 2; i++) {
			messageService.sendFileToGroup(fileName, filePath, "file1", "group1");
		}
		int size = dataStorage.getFileList().size();
		messageService.deleteFile("file0");
		assertEquals(size - 1, dataStorage.getFileList().size());
	}

	@Test
	public void findMessageByKeywordInGroupChatTest() {
		for (int i = 0; i < 3; i++) {
			messageService.sendMessageToGroup(contentMessage1, "user1", "group2");
		}
		for (int i = 0; i < 3; i++) {
			messageService.sendMessageToGroup(contentMessage2, "user1", "group2");
		}
		List<Message> messageList =
				messageService.findMessageByKeywordInGroupChat("Hi", "group2","user1");
		assertEquals(3, messageList.size());
	}

	@Test
	public void findMessageByKeywordInUserChatTest() {
		for (int i = 0; i < 3; i++) {
			messageService.sendMessageToUser(contentMessage1, "user0", "user1");
		}
		for (int i = 0; i < 3; i++) {
			messageService.sendMessageToUser(contentMessage2, "user0", "user2");
		}
		for (int i = 0; i < 3; i++) {
			messageService.sendMessageToUser(contentMessage2, "user1", "user0");
		}

		List<Message> messageList =
				messageService.findMessageByKeywordInUserChat("Hi", "user0", "user1");
		assertEquals(3, messageList.size());
	}

}
