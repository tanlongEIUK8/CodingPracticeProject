package models;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public abstract class Group {
	private final String inviteCode = UUID.randomUUID().toString();
	private static int count = 0;
	private String id;
	protected GroupType groupType;
	private List<User> memberList;
	private List<User> adminList;
	private User owner;

	public Group() {
		id = "group"+count++;
		memberList = new ArrayList<>();
		adminList = new ArrayList<>();
	}

	public String getInviteCode() {
		return inviteCode;
	}

	public String getId() {
		return id;
	}

	public GroupType getGroupType() {
		return groupType;
	}

	public void setGroupType(GroupType groupType) {
		this.groupType = groupType;
	}

	public List<User> getMemberList() {
		return memberList;
	}

	public void setMemberList(List<User> memberList) {
		this.memberList = memberList;
	}

	public List<User> getAdminList() {
		return adminList;
	}

	public void setAdminList(List<User> adminList) {
		this.adminList = adminList;
	}
	
	public void addAdmin(User user) {
		adminList.add(user);
	}
	
	public void addMember(User user) {
		memberList.add(user);
	}

	public void removeMember(User user) {
		memberList.remove(user);
	}
	
	public void removeAdmin(User user) {
		adminList.remove(user);
	}

	public User getOwner() {
		return owner;
	}

	public void setOwner(User owner) {
		this.owner = owner;
	}
}