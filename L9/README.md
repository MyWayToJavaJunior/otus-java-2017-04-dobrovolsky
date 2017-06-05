## ДЗ 9

```Создать в базе таблицу:```

| Field | Type         | Null | Key | Default | Extra          |
|------:|:------------:|:----:|:---:|:-------:|:--------------:|
| id    | bigint(20)   | NO   | PRI | NULL    | auto_increment |
| name  | varchar(255) | YES  |     | NULL    |                |
| age   | int(3)       | NO   |     | 0       |                |

```
Разметить класс User, аннотациями JPA так, чтобы он соответствовал таблице.
Написать Executor, который сохраняет объект класса User в базу и читает
объект класса User из базы по id и классу.
```


**void save(User user){…}**

**User load(long id, Class<?> clazz){...}**