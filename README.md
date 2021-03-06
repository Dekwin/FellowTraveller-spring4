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




## GET users
получить юзера по id
### Заголовок

**Authorization:  `<token>`

### Параметры
*required* **int** `id`. user id



## PATCH profile

Обновление информации личного профиля.

### Заголовок 
Content-Type: application/json
Authorization: `<token>`

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
Authorization: `<token>`

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
Authorization: `<token>`

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
Authorization: `<token>`

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
Authorization: `<token>`





# Map API 


## POST map/routes

Добавление маршрута.

### Заголовок 
Authorization: `<token>`

### Параметры
- параметры где null - передавать не обязательно
- price - цена за км
- priceForRoute - цена за весь путь (присылать price = null и priceForRoute != null для того, чтобы указать цену за весь путь)

Запрос:
```
{
   "id": null,
   "title":"title",
   "car":1,
   "seats":2,
   "price": 215.12,
   "priceForRoute": 2222.33
   "points":[
      {
         
         "latitude":617.9,
         "longitude":68.7,
         "collectionData":{
            "id": null,
            "subscribers":null,
            "datetime":7777777
         }
      },
      {
         
         "latitude":67.9,
         "longitude":683.7,
         "collectionData":null
      },
      {
         
         "latitude":672.9,
         "longitude":682.7,
         "collectionData":{
            "id": null,
            "subscribers":null,
            "datetime":7777777
         }
      }
   ]
}
```
Ответ:

Аналогичен запросу. (+ приходит id созданного маршрута) Статус код: CREATED или код ошибки.


## GET map/routes

Получение маршрутов.

### Заголовок 
Authorization: `<token>`

### Параметры
@RequestParam("id") Optional<Long> routeId,
@RequestParam("owner") Optional<Long> ownerId,
@RequestParam("offset") Optional<Long> offset,
@RequestParam("limit") Optional<Long> limit
 
 Ответ: массив аналогичных сущностей, как в POST map/routes
 
## GET map/routes/search

Получение маршрута по двум радиусам.

### Заголовок 
Authorization: `<token>`

### Параметры
 
 @RequestParam("latitude1") Double latitude1,
 @RequestParam("longitude1") Double longitude1,
 @RequestParam("radius1") Double radius1,
 @RequestParam("latitude2") Double latitude2,
 @RequestParam("longitude2") Double longitude2,
 @RequestParam("radius2")Double radius2,
 @RequestParam("offset") Optional<Long> offset,
 @RequestParam("limit") Optional<Long> limit
 
 Ответ: аналогичен GET map/routes,
 но при этом в массиве points приходит только часть маршрута(от найденной точки посадки до точки высадки), а в price - сумма ТОЛЬКО за путь, который пришел в массиве points 

P.S. пока что поиск происходит как на плоскости(lon,lat = x,y соотв. radius = обычный радиус на плоскости), а стоимость вычисляется с учетом шара земли.

### Примеры

- map/routes/search?latitude1=2&longitude1=4&radius1=1.1&latitude2=34&longitude2=26&radius2=6


## DELETE map/routes/{routeId}

Удаление маршрута.

### Заголовок 
Authorization: `<token>`



## GET map/subscribes

Получение маршрутов, на точки которых подписан текущий пользователь.

### Заголовок 
Authorization: `<token>`

### Параметры
@RequestParam("offset") Optional<Long> offset,
@RequestParam("limit") Optional<Long> limit



## PUT map/subscribes/{collectionDataId}

Подписка на точку маршрута.
где {collectionDataId} - id точки сбора

### Заголовок 
Authorization: `<token>`

## DELETE map/subscribes/{collectionDataId}

Отписка с точки маршрута.
где {collectionDataId} - id точки сбора

### Заголовок 
Authorization: `<token>`



# Reviews


## POST reviews

Добавление отзыва.
### Заголовок 
Authorization: `<token>`

### Параметры
- *required* **string** `title`. заголовок отзыва
- *required* **long** `datetime`. время в миллисекундах 
- *required* **bool** `forDriver`. отзыв для типа водитель если true, если false - для пассажира 
- *required* **long** `text`. тело отзыва 
- *required* **int** `rating`. оценка к отзыву от 0 до 5
- *required* **object(User)** `recipient`. в объекте передается только id пользователя-получателя

Запрос:
```
{
  "id": null,
  "title": "Круто доехали",
  "datetime": 87,
  "forDriver": "false",
  "text": "Водитель явно снимался в фильме 'такси'",
  "rating": 4,
  "sender": null,
  "recipient": {
    "id":3
  }
}
```
Ответ:
```
{
    "id": 14,
    "title": "Круто доехали",
    "datetime": 87,
    "forDriver": false,
    "text": "Водитель явно снимался в фильме 'такси'",
    "rating": 4,
    "sender": {
        "id": 4,
        "firstName": "lol",
        "lastName": "hsjhjslast",
        "imageUrl": null
    },
    "recipient": {
        "id": 3,
        "firstName": "lol",
        "lastName": "hsjhjslast",
        "imageUrl": null
    }
}
```



## GET reviews
Получение отзывов.
(сортируются по дате добавления)
### Заголовок 
Authorization: `<token>`

### Параметры
- *optional* **string** `recipient`. id получателя (свой в т.ч.), если параметр не передан, то выдаются отправленные отзывы текущего пользователя
- *optional* **bool** `forDriver`. отзыв для типа водитель если true, если false - для пассажира (работает только в связке с пареметром `recipient`)
- *optional* **int** `offset`. смещение
- *optional* **int** `limit`. лимит

Пример запроса:
```
/reviews?recipient=3&forDriver=false
```

Ответ:

массив объектов Review(т.е. массив объектов как в ответе метода выше)



## DELETE reviews/{id}
Удаление отзыва по его id.

### Заголовок 
Authorization: `<token>`


