package services;

import database.DataStorage;
import models.Group;
import models.GroupType;
import models.PrivateGroup;
import models.PublicGroup;
import models.User;

public class GroupService {
	DataStorage dataStorage;
	UserService userService;

	public GroupService() {
		this.dataStorage = DataStorage.getInstance();
		this.userService = new UserService();
	}

	public boolean createGroup(GroupType groupType, String userId) {
		User tempUser = userService.getUserByUserId(userId);
		if (groupType.equals(GroupType.Private)) {
			PrivateGroup group = new PrivateGroup();
			group.addAdmin(tempUser);
			group.setOwner(tempUser);
			group.addMember(tempUser);
			dataStorage.addGroup(group);
			return true;
		} else {
			PublicGroup group = new PublicGroup();
			group.addAdmin(tempUser);
			group.setOwner(tempUser);
			group.addMember(tempUser);
			dataStorage.addGroup(group);
			return true;
		}
	}

	public boolean addMember(String inviterId, String inviteeId, String groupId) {
		User inviteeUser = userService.getUserByUserId(inviteeId);
		Group group = getGroupByGroupId(groupId);
		if (group.getGroupType().equals(GroupType.Private)) {
			if (checkAdminPrivateGroup(inviterId, groupId)) {
				group.addMember(inviteeUser);
				return true;
			}
		} else if (checkMemberGroup(inviterId, groupId)) {
			group.addMember(inviteeUser);
			return true;
		}
		return false;
	}

	public boolean addMemberByInviteCode(String inviteCode, String userId) {
		User user = userService.getUserByUserId(userId);
		Group group = getGroupByInviteCode(inviteCode);
		if (group != null) {
			group.addMember(user);
			return true;
		}
		return false;
	}

	private boolean checkAdminPrivateGroup(String userId, String groupId) {
		Group group = getGroupByGroupId(groupId);
		User admin = userService.getUserByUserId(userId);
		if (group != null) {
			if (group.getAdminList().contains(admin)) {
				return true;
			} else {
				return false;
			}
		} else {
			return false;
		}
	}

	public boolean checkMemberGroup(String userId, String groupId) {
		Group group = getGroupByGroupId(groupId);
		User user = userService.getUserByUserId(userId);
		if (group != null) {
			if (group.getMemberList().contains(user)) {
				return true;
			} else {
				return false;
			}
		} else {
			return false;
		}
	}

	public boolean leaveGroup(String userId, String groupId) {
		User user = userService.getUserByUserId(userId);
		boolean result = false;
		Group group = getGroupByGroupId(groupId);
		if (checkMemberGroup(userId, groupId)) {
			group.removeMember(user);
			result = true;
		}
		if (checkAdminPrivateGroup(userId, groupId)) {
			group.removeAdmin(user);
			result = true;
		}
		return result;
	}

	public Group getGroupByGroupId(String id) {
		Group tempGroup = null;
		for (Group group : dataStorage.getGroupList()) {
			if (group.getId().equalsIgnoreCase(id)) {
				tempGroup = group;
			}
		}
		return tempGroup;
	}

	public Group getGroupByInviteCode(String inviteCode) {
		Group tempGroup = null;
		for (Group group : dataStorage.getGroupList()) {
			if (group.getInviteCode().equals(inviteCode)) {
				tempGroup = group;
			}
		}
		return tempGroup;
	}

}
