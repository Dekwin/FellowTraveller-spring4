# FellowTraveller-spring4



# FellowTraveller HTTP API (pre-alpha)

## Base path: http://5.45.81.178:8080/fellowTraveler_war_exploded1/
### Пример http://5.45.81.178:8080/fellowTraveler_war_exploded1/signup - регистрация

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

**Authorization:  `<token>`** - token, полученный из **POST signin**


## PATCH profile

Обновление информации личного профиля.

### Заголовок 
Content-Type: application/json
Authorization: <token>

### Параметры
- аналогично signup, можно отправлять только измененные поля

### Примеры

Запрос:

```
{
  "firstName": "jhg",
  "lastName": "hhh9",
  "email": "eujkhadsawwsss@mail.ru",
  "gender": "male"
}
``` 

Ответ:

```
{
  "ssoId": "sam2",
  "password": "abc125",
  "firstName": "jhg",
  "lastName": "hhh9",
  "email": "eujkhadsawwsss@mail.ru",
  "gender": "male",
  "imageUrl": null,
  "cars": [],
  "userProfiles": []
}
```


## POST profile/photo

Обновление фотографии личного профиля.

### Заголовок 
Authorization: <token>

### Примеры

Запрос:

```
curl -X POST -H "Authorization: eyJhbGciOiJIUzUxMiJ9.eyJzc29JZCI6InNhbTIiLCJUT0tFTl9DUkVBVEVfREFURSI6MTQ5MDExNjgyNzY0NiwiY2xpZW50VHlwZSI6InVzZXIiLCJUT0tFTl9FWFBJUkFUSU9OX0RBVEUiOjQ2NDU3OTA0Mjc2NDYsInVzZXJJRCI6IjEifQ.dKKQ9Y4TU7YWDHpT5RN6UFKL8RfTN3Nu_toPGboeOQ5ClORyDT5tEA13eCWdgUlqpfo7-rVFzfPO6bHwh8_vXw" -H "Cache-Control: no-cache" -H "Content-Type: multipart/form-data;  -F "file=@screamer.jpg" "http://example.com/profile/photo"
``` 

Ответ:

```
{
  "url":"http://example.com/photo67.jpg"
}
```

## POST profile/cars

Добавление автомобиля.

### Заголовок 
Content-Type: application/json
Authorization: <token>

### Параметры

- *required* **int** `id`. Уникальный идентификатор автомобиля.
- *required* **str** `title`. Навание
- *required* **int** `capacity`. Вместимость
- *required* **int** `year`. Год выпуска
- *required* **int** `condition`. Состояние

### Примеры

Запрос:

```
{
  "title": "Жигули 2104",
  "capacity": 6,
  "year": 1978,
  "condition": 3
}
``` 

Ответ:

```
{ 
  "id": 1,
  "title": "Жигули 2104",
  "capacity": 6,
  "year": 1978,
  "condition": 3
}
```


## POST profile/cars/`<id авто>`/photo

Добавление фото автомобиля.

### Заголовок 
Authorization: <token>

### Параметры

- Аналогично как для фото пользователя

Ответ:

```
{
  "url":"http://example.com/photo647.jpg"
}
```

## DELETE profile/cars/`<id авто>`
Удаление автомобиля

### Заголовок 
Authorization: <token>


