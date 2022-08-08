package models;

public class File {
	private static int count = 0;
	private String id;
	private String fileName;
	private String filePath;
	private FileType fileType;
	private String senderId;
	private String receiverId;
	private UserType receiverType;

	public File(String fileName, String filePath, String senderId, String receiverId) {
		id = "file"+count++;
		this.fileName = fileName;
		this.filePath = filePath;
		this.senderId = senderId;
		this.receiverId = receiverId;
		if (filePath.endsWith("mp4")) {
			fileType = FileType.Video;
		} else if (filePath.endsWith("mp3")) {
			fileType = FileType.Audio;
		} else if (filePath.endsWith("png") || filePath.endsWith("jpg")) {
			fileType = FileType.Image;
		}
	}

	public String getId() {
		return id;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	public FileType getFileType() {
		return fileType;
	}

	public void setFileType(FileType fileType) {
		this.fileType = fileType;
	}

	public String getSenderId() {
		return senderId;
	}

	public String getReceiverId() {
		return receiverId;
	}

	public UserType getReceiverType() {
		return receiverType;
	}

	public void setReceiverType(UserType receiverType) {
		this.receiverType = receiverType;
	}

}
