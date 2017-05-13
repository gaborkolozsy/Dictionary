# Dictionary - Java application

Dark Theme | Light Theme
:---------:|:---------:
![Image of Dictionary](https://cloud.githubusercontent.com/assets/23102020/26023652/79d4d702-37c1-11e7-9ecf-ced7c3e97b80.png) | ![Image of Dictionary](https://cloud.githubusercontent.com/assets/23102020/26023666/bcb63156-37c1-11e7-99b4-0bca3f4f0827.png)
<br>
## Content of table

* [Description](#escription)
* [Construction](#construction)
    * [Refit splash screen on Eclipse](#refit-splash-screen-on-eclipse)
* [Dictionaries](#dictionaries)

# Description

This `Dictionary` is an offline dictionary. It includes all combination of the following
3 languages: english, german and hungarian.
You can search for over `70.000` words, combination of words and sentences per dictionary. :smile:

# Construction

The application is a simple java project and it was edited in NetBeans. So the project is a NetBeans project.
The project may open in Eclipse as well. In this case the splash screen will **not** show automatically.

## Refit splash screen on Eclipse

For fix this problem follow the next:

1 Open project -> Properties
2 Choose -> Run/Debug Settings
3 Mark out -> Dictionary then click Edit...
4 Choose -> Arguments
5 In VM arguments field paste -> -splash:resources/images/Splash.png

<br>
<br>

# Dictionaries

|Dictionaries                  | Count of Words |
|:----------------------------:|:--------------:|
| :gb: `English-Hungarian` HU  | **169425**     |
|  HU `Hungarian-English` :gb: | **91688**      |
| :de: `German-Hungarian`  HU  | **129481**     |
|  HU `Hungarian-German`  :de: | **69995**      |
| :gb: `English-German`   :de: | **141287**     |
| :de: `German-English`   :gb: | **193860**     |
