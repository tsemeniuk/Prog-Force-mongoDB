##### Prog-Force-mongoDB

Основной функционал реализован, создается база, заполняется данными, данные изменяются в потоке.
Так как сущности продуктов, являються embedded в внутренних массивах, доступ к ним засчет главного Document обьекта. На stockoverflow
пишут, что парсить их в сущности при запросах с бд не получаеться, так как они являються частью document данных. По этому в основном 
изменения через обьекты в джаве. 

#####Update.
Ок, отошел от системы embedded. Переделал на @Referenced, таким образом есть прямой маппер для обьектов. Так что можно с запроса получить, замапить в pojo. 
