//package ru.otus.dobrovolsky.test;
//
//import org.junit.Assert;
//import org.junit.FixMethodOrder;
//import org.junit.Test;
//import org.junit.runners.MethodSorters;
//import ru.otus.dobrovolsky.base.DBService;
//import ru.otus.dobrovolsky.base.dataSets.AddressDataSet;
//import ru.otus.dobrovolsky.base.dataSets.PhoneDataSet;
//import ru.otus.dobrovolsky.base.dataSets.UserDataSet;
//import ru.otus.dobrovolsky.dbService.DBServiceHibernateImpl;
//
//import java.util.ArrayList;
//import java.util.List;
//
//@FixMethodOrder(MethodSorters.NAME_ASCENDING)
//public class HibernateTest {
//    private static UserDataSet createUser() {
//        AddressDataSet address = new AddressDataSet("Lenina 3", 460000);
//
//        List<PhoneDataSet> phones = new ArrayList<>();
//        phones.add(new PhoneDataSet(5678, "1234567890"));
//        phones.add(new PhoneDataSet(6789, "0987654321"));
//
//        return new UserDataSet("Test User", address, phones);
//    }
//
//    @Test
//    public void aInsertTestUser() {
//        UserDataSet testUser = createUser();
//        DBService dbService = new DBServiceHibernateImpl();
//        dbService.save(testUser);
//
//        UserDataSet retUser = dbService.read(UserDataSet.class, 1);
//        Assert.assertEquals("User for insert must be equal to returned user", retUser.getName(), testUser.getName());
//        Assert.assertEquals("User for insert must be equal to returned user", retUser.getId(), testUser.getId());
//    }
//
//    @Test
//    public void bInsertTestUser() {
//        UserDataSet testUser = createUser();
//        DBService dbService = new DBServiceHibernateImpl();
//        dbService.save(testUser);
//
//        UserDataSet retUser = dbService.read(UserDataSet.class, 1);
//        String addressToInsert = testUser.getAddress().toString();
//        String addressFromDB = retUser.getAddress().toString();
//        Assert.assertEquals("User for insert must be equal to returned user", addressFromDB, addressToInsert);
//        Assert.assertEquals("User for insert must be equal to returned user", retUser.getId(), testUser.getId());
//    }
//
//    @Test
//    public void cInsertTestUser() {
//        UserDataSet testUser = createUser();
//        DBService dbService = new DBServiceHibernateImpl();
//        dbService.save(testUser);
//
//        UserDataSet retUser = dbService.read(UserDataSet.class, 1);
//        String phoneToInsert = testUser.getPhones().toString();
//        String phoneFromDB = retUser.getPhones().toString();
//        Assert.assertEquals("User for insert must be equal to returned user", phoneFromDB, phoneToInsert);
//        Assert.assertEquals("User for insert must be equal to returned user", retUser.getId(), testUser.getId());
//    }
//}
