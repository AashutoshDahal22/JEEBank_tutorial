package hibernate.repository;

import interfaces.UsersRepositoryInterface;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Named;
import models.UsersModel;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

import java.util.List;

@ApplicationScoped
@Named("hibernateUsers")
public class UsersRepository implements UsersRepositoryInterface {

    @PersistenceContext(unitName = "JeebankPU")
    private EntityManager entityManager; //JPA api object to interact with DB and PersistentContext

    @Override
    public void insertUsers(UsersModel usersModel) {
        entityManager.persist(usersModel);
    }

    @Override
    public UsersModel findUsersById(Long id) {
        return entityManager.find(UsersModel.class, id); //maps the result into instance of the .class provided
    }

    @Override
    public void updateUsers(UsersModel usersModel) {
        entityManager.merge(usersModel);
    }

    @Override
    public void deleteUsersById(Long id) {
        UsersModel user = entityManager.find(UsersModel.class, id);
        if (user != null) {
            entityManager.remove(user);
        }
    }

    @Override
    public List<UsersModel> getAllUsers(int offset, int size) {
        return entityManager.createQuery("SELECT u from UsersModel u", UsersModel.class)
                .setFirstResult(offset)
                .setMaxResults(size)
                .getResultList();
    }

//    @Override
//    @Transactional
//    public void indexExistingUser() {
//        try {
//            SearchSession searchSession = Search.session(entityManager);
//            searchSession.massIndexer(UsersModel.class)
//                    .startAndWait();
//        } catch (InterruptedException e) {
//            throw new RuntimeException("Indexing interrupted", e);
//        }
//    }
//
//    @Override
//    @Transactional
//    public List<UsersModel> searchUserByName(String keyword) {
//        SearchSession searchSession = Search.session(entityManager);
//        return searchSession.search(UsersModel.class)
//                .where(f -> f.match()
//                        .field("name").matching(keyword)).fetchHits(20);
//    }
}
