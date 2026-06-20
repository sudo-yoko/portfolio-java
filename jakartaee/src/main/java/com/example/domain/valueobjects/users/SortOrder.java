package com.example.domain.valueobjects.users;

import java.util.ArrayList;
import java.util.List;

public class SortOrder {

    public static class Order {
        private final List<Key> keys;

        private Order(List<Key> keys) {
            this.keys = keys;
        }

        public List<Key> getKeys() {
            return keys;
        }

        public static Order of(String orderString) {
            List<Key> keys = normalize(orderString);
            return new Order(keys);

        }

        private static List<Key> normalize(String orderString) {
            List<Key> keys = new ArrayList<>();
            String items[] = orderString.strip().split(",");
            for (String item : items ){
                String parts[] = item.strip().split(":");
            }
            return keys;
        }

    }

    public static class Key {
        private final String column;
        private final String direction;

        public Key(String column, String direction) {
            this.column = column;
            this.direction = direction;
        }

        public String getColumn() {
            return column;
        }

        public String getDirection() {
            return direction;
        }

        @Override
        public String toString() {
            StringBuilder sb = new StringBuilder();
            sb.append("{");
            sb.append("column=").append(column).append(", ");
            sb.append("direction=").append(direction);
            sb.append("}");
            return sb.toString();
        }
    }
}
