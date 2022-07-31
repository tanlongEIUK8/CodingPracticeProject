package services;

import java.util.ArrayList;
import java.util.List;

import database.DataStorage;
import models.File;
import models.Message;
import models.UserType;

public class MessageService {
	UserService userService;
	GroupService groupService;
	DataStorage dataStorage;

	public MessageService() {
		this.userService = new UserService();
		this.groupService = new GroupService();
		this.dataStorage = DataStorage.getInstance();
	}

	public void sendMessageToGroup(String content, String senderId, String receiverId) {
		Message message = new Message(content, senderId, receiverId);
		message.setReceiverType(UserType.Group);
		dataStorage.addMessage(message);
	}

	public void sendMessageToUser(String content, String senderId, String receiverId) {
		Message message = new Message(content, senderId, receiverId);
		message.setReceiverType(UserType.User);
		dataStorage.addMessage(message);
	}

	public void sendFileToGroup(String fileName, String filePath, String senderId, String receiverId) {
		File file = new File(fileName, filePath, senderId, receiverId);
		file.setReceiverType(UserType.Group);
		dataStorage.addFile(file);
	}

	public void sendFileToUser(String fileName, String filePath, String senderId, String receiverId) {
		File file = new File(fileName, filePath, senderId, receiverId);
		file.setReceiverType(UserType.User);
		dataStorage.addFile(file);
	}

	public List<Message> showAllMessageGroup(String groupId, String userId) {
		List<Message> messageList = new ArrayList<Message>();
		for (Message message : dataStorage.getMessageList()) {
			if (message.getReceiverType() == UserType.Group) {
				if ((message.getReceiverId().equalsIgnoreCase(groupId) && message.getSenderId().equalsIgnoreCase(userId))) {
					messageList.add(message);
				}
			}
		}
		return messageList;
	}

	public List<Message> showAllMessageUser(String userId1, String userId2) {
		List<Message> messageList = new ArrayList<Message>();
		retrieveUserMessage(userId1, userId2, messageList);
		retrieveUserMessage(userId2, userId1, messageList);
		return messageList;
	}

	public List<Message> retrieveUserMessage(String userId1, String userId2, List<Message> userMessages) {
		List<Message> messagesList = dataStorage.getMessageList();
		for (Message message : messagesList) {
			if (message.getReceiverType() == UserType.User) {
				if (message.getSenderId().equalsIgnoreCase(userId1) && message.getReceiverId().equalsIgnoreCase(userId2)) {
					userMessages.add(message);
				}
			}
		}
		return userMessages;

	}

	public List<Message> showKLatestMessageGroup(String groupId, int k) {
		List<Message> groupMessage = new ArrayList<Message>();
		List<Message> messageList = dataStorage.getMessageList();
		for (int i = messageList.size() - 1; i >= 0; i--) {
			Message message = messageList.get(i);
			if (message.getReceiverType() == UserType.Group && message.getReceiverId().equalsIgnoreCase(groupId)) {
				groupMessage.add(message);
				k--;
			}
			if (k == 0) {
				return groupMessage;
			}
		}
		return groupMessage;
	}

	public List<Message> showLatestMessageGroupExceptM(String groupId, int k, int m) {
		List<Message> messageList = showKLatestMessageGroup(groupId, k);
		for (int i = 0; i < m; i++) {
			messageList.remove(i);
		}
		return messageList;
	}

	public List<File> showAllFileInGroup(String groupId) {
		List<File> fileList = dataStorage.getFileList();
		List<File> groupFileList = new ArrayList<File>();
		for (File file : fileList) {
			if (file.getReceiverType() == UserType.Group && file.getReceiverId().equalsIgnoreCase(groupId)) {
				groupFileList.add(file);
			}
		}

		return groupFileList;
	}

	public void deleteMessage(String messageId) {
		ArrayList<Message> messageList = dataStorage.getMessageList();
		for (int i = 0; i < messageList.size(); i++) {
			if (messageList.get(i).getId().equalsIgnoreCase(messageId)) {
				messageList.remove(messageList.get(i));
			}
		}
	}

	public void deleteFile(String fileId) {
		List<File> fileList = dataStorage.getFileList();
		for (int i = 0; i < fileList.size(); i++) {
			if (fileList.get(i).getId().equalsIgnoreCase(fileId)) {
				fileList.remove(fileList.get(i));
			}
		}
	}

	public List<Message> findMessageByKeywordInGroupChat(String keyword, String groupId, String userId) {
		List<Message> groupMessageList = showAllMessageGroup(groupId, userId);
		List<Message> containKeyWordMessages = new ArrayList<>();

		for (Message message : groupMessageList) {
			if (message.getContent().toLowerCase().contains(keyword.toLowerCase())) {
				containKeyWordMessages.add(message);
			}
		}
		return containKeyWordMessages;
	}

	public List<Message> findMessageByKeywordInUserChat(String keyword, String userId1, String userId2) {
		List<Message> userMessageList = showAllMessageUser(userId1, userId2);
		List<Message> containKeyWordMessages = new ArrayList<>();
		for (Message message : userMessageList) {
			if (message.getContent().toLowerCase().contains(keyword.toLowerCase())) {
				containKeyWordMessages.add(message);
			}
		}
		return containKeyWordMessages;
	}
}
