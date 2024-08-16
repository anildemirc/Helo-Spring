package tr.anil.questapp.enums;

import tr.anil.questapp.entity.Comment;

public enum NotificationTypes {

    LIKE(1),
    COMMENT(2),
    FOLLOW(3);

    private int type;

    NotificationTypes(int type) {
        this.type = type;
    }

    public int getNotificationTypes() {
        return type;
    }

    public static NotificationTypes getType(int type) {
        switch (type) {
            case 1:
                return LIKE;
            case 2:
                return COMMENT;
            case 3:
                return FOLLOW;
        }
        return null;
    }

}
