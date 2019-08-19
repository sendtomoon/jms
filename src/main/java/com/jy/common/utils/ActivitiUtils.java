package com.jy.common.utils;

import com.jy.entity.system.account.*;
import com.jy.entity.system.org.*;
import org.activiti.engine.impl.persistence.entity.*;
import org.activiti.engine.identity.*;
import java.util.*;

public class ActivitiUtils
{
    public static UserEntity toActivitiUser(final Account accout) {
        final UserEntity userEntity = new UserEntity();
        userEntity.setId(accout.getAccountId());
        userEntity.setRevision(1);
        return userEntity;
    }
    
    public static GroupEntity toActivitiGroup(final Position bGroup) {
        final GroupEntity groupEntity = new GroupEntity();
        groupEntity.setRevision(1);
        groupEntity.setType("assignment");
        groupEntity.setId(bGroup.getId());
        groupEntity.setName(bGroup.getName());
        return groupEntity;
    }
    
    public static List<Group> toActivitiGroups(final List<Position> bGroups) {
        final List<Group> groupEntitys = new ArrayList<Group>();
        for (final Position bGroup : bGroups) {
            final GroupEntity groupEntity = toActivitiGroup(bGroup);
            groupEntitys.add((Group)groupEntity);
        }
        return groupEntitys;
    }
}
