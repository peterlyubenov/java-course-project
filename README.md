# My Day
Курсов проект по Java от Петър Любенов

ф.№ 1709010883


[peshka.ml](https://peshka.ml)

## Технологии

Проекта използва Spring MVC Framework, Spring Security за authentication на потребители, Thymeleaf за html темплейти, MySQL база данни.

По отношение на css, използва bootstrap и [Material UI Kit от creative tim](https://www.creative-tim.com/product/material-kit)

## Връзка с базата данни

Базата данни е пусната от xampp. Създадена е празна БД от phpmyadmin (при работещ xampp, [localhost/phpmyadmin](http://localhost/phpmyadmin)). След това са посочени данните за връзка в /src/main/resources/application.properties

```
spring.datasource.url = jdbc:mysql://localhost:3306/myday
spring.datasource.username = root
spring.datasource.password = 
```

Името на базата данни е myday, хоста е localhost на порт 3306 с потребителско име root и празна парола

## Модели (Entity-та)
Моделите са дефинирани в /src/main/java/ml/peshka/myday/model

Ентити представлява ред от таблица в базата данни представен чрез код. Посочва се името на базата данни и са описани всичките колони като java примитиви (int, boolean) и обекти (String, други entity-та), техните свойства (primary key, foreign key, др.) и правила за валидация

## Repository
Repository-тата са дефинирани в директория repository

Всяка таблица в базата данни (която също си има модел) има точно едно repository. То отговаря за вземане и писане на данни от БД. SQL заявките са написани от spring, ние само указваме кои специални методи ще ни трябват когато работим с тази таблица

## Service
Директория service

В service класовете се съдържа бизнес логиката. Как да взема данни, как да пише данни, какви условия да има. Service-ите действат като допълнителен слой абстракция за да може контролерите да се фокусират върху подготвяне на данните за презентация

## Controller
...

Контролерите обработват заявки, използват сървис и подготвят view-то за презентация на данните

## View
View-тата са html файлове в src/main/resources/templates

fragments/general.html е главния темплейт, който всички други "наследяват" 

## Login/register логика

Поведението на сайта по отношение на потребители (кои страници са видими без вход, кои само за админ, на коя страница да препраща при вход, т.н) е дефинирано в configuration/WebSecurityConfiguration.java

## Грешки

#### 500
При грешка 500, вижте си конзолата. Ще има 3000 "at neshtosi.java[ 69:10 ]". Веднага над тях пише точната грешка.
Пример: `java.lang.NullPointerException: user is null`. Обикновено първото at пише точно къде в кода user е null.

#### 404
Ако не намира статичен файл (напр. styles.css) вижте дали е на правилното място. /src/main/resources/static/ е главната директория за статични файлове. Поддиректорията css/ отговаря на localhost:8080/css/. Също проверете в WebSecurityConfiguration в метода configure дали поддиректорията е посочена, ако не е, добавете я

Ако не намира страница, проверете в контролера `@GetMapping` или @PostMapping със стойност пътя който се опитвате да достъпите. Ако страницата `localhost:8080/student/all` бие грешка, проверете дали метода в контролера има `@GetMapping('/student/all')`. Това не е името на вюто

#### 400
Вижте в конзолата. Последния ред ще даде подсказка за това къде ви е грешката

#### 999
Тази грешка е мистериозна. Може да възникне по всяка причина и няма оправия. Успех!