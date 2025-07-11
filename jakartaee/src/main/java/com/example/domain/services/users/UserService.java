package com.example.domain.services.users;

import com.example.domain.entities.user.User;
import com.example.domain.entities.user.UserRepository;

import jakarta.ejb.Stateless;
import jakarta.inject.Inject;
import jakarta.persistence.EntityNotFoundException;

@Stateless
public class UserService {

    @Inject
    private UserRepository repository;

    public User findUser(String userId) {
        return repository.findActive(userId);
    }

    public void createUser(User user) {

        // 削除済みも含めて存在チェック
        User existingUser = repository.find(user.getUserId());

        // 無ければ新規作成
        if (existingUser == null) {
            user.setDeleted(false);
            repository.persist(user);
            return;
        }

        // 削除されていれば復活させて更新する
        if (existingUser.isDeleted()) {
            existingUser.setDeleted(false);
            existingUser.setUserName(user.getUserName());
            repository.merge(existingUser);
            return;
        }

        // throw new EntityExistsException("ユーザーは存在しています。");
        repository.persist(user);
    }

    public void createOrReplaceUser(User user) {

        // 削除済みも含めて存在チェック
        User existingUser = repository.find(user.getUserId());

        // 無ければ新規作成
        if (existingUser == null) {
            repository.persist(user);
            return;
        }

        existingUser.setUserName(user.getUserName());
        existingUser.setDeleted(false);
        repository.merge(existingUser);
    }

    public void deactivateUser(String userId) {
        User existingUser = repository.findActive(userId);
        if (existingUser == null) {
            throw new EntityNotFoundException("削除対象のユーザは存在しません。");
        }
        existingUser.setDeleted(true);
        repository.merge(existingUser);
    }

    public void deleteUser(String userId) {
        User entity = repository.find(userId);
        if (entity == null) {
            throw new EntityNotFoundException("削除対象のユーザは存在しません。");
        }
        repository.remove(entity);
    }
}
