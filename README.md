# FellowTraveller-spring4
# FellowTraveller HTTP API


## POST signup

Создание нового пользователя.
### Заголовок 
Content-Type: application/json

### Параметры


- *required* **str** `ssoId`. Уникальный идентификатор. Это мобильный номер пользователя.
- *required* **str** `password`. Пароль
- *required* **str** `firstName`. Имя
- *required* **str** `lastName`. Фамилия
- *required* **str** `email`. Email
- *required* **str** `gender`. Пол



### Формат ответа
Такой же как и в **POST signup**, + поле **int** id


### Примеры

Запрос:

```
{
  "ssoId": "+88005553535",
  "password": "izuken676",
  "firstName": "Dimetrus",
  "lastName": "Pedota",
  "email": "superhuman2@mail.ru",
  "gender": "male"
}
```


Ответ:

```

{
  "id": 5,
  "ssoId": "+88005553535",
  "password": "izuken676",
  "firstName": "Dimetrus",
  "lastName": "Pedota",
  "email": "superhuman2@mail.ru",
  "gender": "male"
}
```


## POST signin

Авторизация. Получение JWT.

### Заголовок 
Content-Type: application/json

### Параметры
- `ssoId`. Уникальный идентификатор.
- `password`. Пароль.

### Заголовок ответа

**Authorization: Bearer `<token>`** - token для пользовательских запросов

### Примеры

Запрос:

```
{
  "ssoId": "+79879879793",
  "password": "abtygc125"
}
``` 

Ответ:

```
Authorization: Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJzYW0xIiwiZXhwIjoxNDg5NjA5OTgyfQ.iYznUqaZfRjrgmktuK8CxcJP7Au4QVTDcULe4WAvps_fP8lsCOPzTtVplRd9u5t1xQAHuZFvTJ61OUTFCtkZVQ
```



## GET <приватный url>

### Заголовок

**Authorization:  <token>** - token, полученный из **POST signin**
