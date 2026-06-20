package com.example.domain.entities.user;

import java.util.List;

import jakarta.persistence.TypedQuery;

public class UserQuery {

    /**
     * 検索クエリクラス
     */
    public static class Query {
        private TypedQuery<Long> count;
        private TypedQuery<User> user;

        public TypedQuery<Long> getCount() {
            return count;
        }

        public void setCount(TypedQuery<Long> count) {
            this.count = count;
        }

        public TypedQuery<User> getUser() {
            return user;
        }

        public void setUser(TypedQuery<User> user) {
            this.user = user;
        }
    }

    /**
     * 検索結果クラス
     */
    public static class Result {
        private final long count;
        private final List<User> users;

        public Result(long count, List<User> users) {
            this.count = count;
            this.users = (users == null) ? List.of() : List.copyOf(users); // イミュータブル（不変）のリスト
        }

        public long getCount() {
            return count;
        }

        public List<User> getUsers() {
            return users;
        }
    }
}
