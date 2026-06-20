package com.example.domain.entities.user;

import java.util.ArrayList;
import java.util.List;

/**
 * 検索条件クラス
 */
public class UserCriteria {

    public static class Criteria {
        private String userId;
        private String userName;
        private String userNameContains; // 部分一致検索用
        private String email;
    }

    public static class Hint {
        private List<String> hints = new ArrayList<>();

    }

    public enum Sort {
        USERID_ASC("userId", "asc"),
        USERID_DESC("userId", "desc"),
        USERNAME_ASC("userName", "asc"),
        USERNAME_DESC("userName", "desc"),
        EMAIL_ASC("email", "asc"),
        EMAIL_DESC("email", "desc"),
        TIMESTAMP_ASC("timestamp", "asc"),
        TIMESTAMP_DESC("timestamp", "desc"),
        ;

        private String column;
        private String direction;

        Sort(String column, String direction) {
            this.column = column;
            this.direction = direction;
        }

    }

}
